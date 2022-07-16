package de.cubeattack.cuberunner.game;

import de.cubeattack.cuberunner.Configuration;
import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.plugin.Plugin;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Head {

    private static Plugin plugin;
    private static MySQL mysql;
    private static Configuration config;

    public static void setVariables(CubeRunner plugin) {
        Head.plugin = plugin;
        mysql = plugin.getMySQL();
        config = plugin.getConfiguration();
    }

    public static void updateHeads(Arena arena){
        Location loc1 = arena.getHeadsList().get(0);
        Location loc2 = arena.getHeadsList().get(1);
        Location loc3 = arena.getHeadsList().get(2);

        setHead(loc1, 0);
        setHead(loc2, 1);
        setHead(loc3, 2);;
    }

    public static void updateHeads(){
        for (Arena arena : Arena.getArenas()) {
            Location loc1 = arena.getHeadsList().get(0);
            Location loc2 = arena.getHeadsList().get(1);
            Location loc3 = arena.getHeadsList().get(2);

            setHead(loc1, 0);
            setHead(loc2, 1);
            setHead(loc3, 2);
        }
    }

    public static void setHead(Location loc, int count){
        loc.getBlock().setType(Material.PLAYER_WALL_HEAD);

        Skull skull = (Skull) loc.getBlock().getState();
        skull.setOwningPlayer(Bukkit.getOfflinePlayer("EinfacheSache"));
        skull.update();
    }

    public static void loadHeads(){
        if (mysql.hasConnection()) {
            ResultSet query = mysql.query("SELECT * FROM " + config.tablePrefix + "ARENAS;");
            try {
                while(query.next()) {
                    Arena arena = Arena.getArena(query.getString("name"));
                    String[] headLocString1 = query.getString("headLoc1").split(", ");
                    String[] headLocString2 = query.getString("headLoc2").split(", ");;
                    String[] headLocString3 = query.getString("headLoc3").split(", ");;
                    double x1 = Double.parseDouble(headLocString1[0]);
                    double y1 = Double.parseDouble(headLocString1[1]);
                    double z1 = Double.parseDouble(headLocString1[2]);

                    double x2 = Double.parseDouble(headLocString2[0]);
                    double y2 = Double.parseDouble(headLocString2[1]);
                    double z2 = Double.parseDouble(headLocString2[2]);

                    double x3 = Double.parseDouble(headLocString3[0]);
                    double y3 = Double.parseDouble(headLocString3[1]);
                    double z3 = Double.parseDouble(headLocString3[2]);

                    Location headLoc1 = new Location(Bukkit.getWorld(query.getString("world")), x1, y1, z1);
                    Location headLoc2 = new Location(Bukkit.getWorld(query.getString("world")), x2, y2, z2);
                    Location headLoc3 = new Location(Bukkit.getWorld(query.getString("world")), x3, y3, z3);

                    arena.getHeadsList().put(0, headLoc1);
                    arena.getHeadsList().put(1, headLoc2);
                    arena.getHeadsList().put(2, headLoc3);
                }
            } catch (SQLException var7) {
                var7.printStackTrace();
            }
        }
    }
}
