package de.cubeattack.cuberunner.game;

import de.cubeattack.cuberunner.Configuration;
import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.error.MarkedYAMLException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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

        setHead(loc1, 0, arena);
        setHead(loc2, 1, arena);
        setHead(loc3, 2, arena);

        setInfoSign(loc1, 0, arena);
        setInfoSign(loc2, 1, arena);
        setInfoSign(loc3, 2, arena);
    }

    public static void updateHeads(){
        for (Arena arena : Arena.getArenas()) {
            Location loc1 = arena.getHeadsList().get(0);
            Location loc2 = arena.getHeadsList().get(1);
            Location loc3 = arena.getHeadsList().get(2);

            setHead(loc1, 0, arena);
            setHead(loc2, 1, arena);
            setHead(loc3, 2, arena);

            setInfoSign(loc1, 0, arena);
            setInfoSign(loc2, 1, arena);
            setInfoSign(loc3, 2, arena);
        }
    }

    public static void setInfoSign(Location loc, int count, Arena arena){
        if(loc == null)return;
        loc.setY(loc.getY()-1);
        if(loc.getBlock().getType().name().toLowerCase().endsWith("sign")) {
            if (getBestPlayers(arena) != null) {
                OfflinePlayer player = getBestPlayers(arena).get(count);
                Sign sign = (Sign) loc.getBlock().getState();
                sign.setLine(2, "");
                if(player == null){
                    sign.setLine(0, "");
                    sign.setLine(1, "");
                    sign.update();
                    return;
                }
                sign.setLine(0, Objects.requireNonNull(player.getName()));
                sign.setLine(1, "Score: " + Objects.requireNonNull(getBestPlayersWithScore(arena)).get(player));
                sign.update();
            }
        }else{
            CubeRunner.get().getLogger().severe("No Sign is placed at one or more positions under the player heads!");
        }
    }


        public static void setHead(Location loc, int count, Arena arena){
            if(loc == null)return;
            if(loc.getBlock().getType() == Material.PLAYER_WALL_HEAD || loc.getBlock().getType() == Material.PLAYER_HEAD) {
                if (getBestPlayers(arena) != null) {
                    OfflinePlayer player = getBestPlayers(arena).get(count);
                    if(player == null)return;
                    Skull skull = (Skull) loc.getBlock().getState();
                    skull.setOwningPlayer(player);
                    skull.update();
                }
            }else{
                CubeRunner.get().getLogger().severe("No head is placed at one or more positions of the player heads!");
            }
        }

    public static ArrayList<OfflinePlayer> getBestPlayers(Arena arena){
        String[] firstHighestScore;
        String[] secondHighestScore;
        String[] thirdHighestScore;
        ArrayList<OfflinePlayer> players = new ArrayList<>();
        if (mysql.hasConnection()) {
            ResultSet query = mysql.query("SELECT * FROM " + config.tablePrefix + "ARENAS WHERE name='" + arena.getName() + "';");
            try {
                while(query.next()) {
                    firstHighestScore = query.getString("firstHighestScore").split(", ");
                    secondHighestScore = query.getString("secondHighestScore").split(", ");
                    thirdHighestScore = query.getString("thirdHighestScore").split(", ");

                    players.add(!firstHighestScore[0].contains("null") ? Bukkit.getOfflinePlayer(firstHighestScore[0]) : null);
                    players.add(!secondHighestScore[0].contains("null") ? Bukkit.getOfflinePlayer(secondHighestScore[0]) : null);
                    players.add(!thirdHighestScore[0].contains("null") ? Bukkit.getOfflinePlayer(thirdHighestScore[0]) : null);

                    return players;
                }
            } catch (SQLException ex) {
                CubeRunner.get().getLogger().warning("No BestPlayers found!");
            }
        }
        return null;
    }

    public static HashMap<OfflinePlayer, Integer> getBestPlayersWithScore(Arena arena){
        String[] firstHighestScore;
        String[] secondHighestScore;
        String[] thirdHighestScore;
        HashMap<OfflinePlayer, Integer> players = new HashMap<>();
        if (mysql.hasConnection()) {
            ResultSet query = mysql.query("SELECT * FROM " + config.tablePrefix + "ARENAS WHERE name='" + arena.getName() + "';");
            try {
                while(query.next()) {
                    firstHighestScore = query.getString("firstHighestScore").split(", ");
                    secondHighestScore = query.getString("secondHighestScore").split(", ");
                    thirdHighestScore = query.getString("thirdHighestScore").split(", ");

                    players.put(Bukkit.getOfflinePlayer(firstHighestScore[0]), Integer.valueOf(firstHighestScore[1]));
                    players.put(Bukkit.getOfflinePlayer(secondHighestScore[0]),  Integer.valueOf(secondHighestScore[1]));
                    players.put(Bukkit.getOfflinePlayer(thirdHighestScore[0]),  Integer.valueOf(thirdHighestScore[1]));

                    return players;
                }
            } catch (SQLException ex) {
                CubeRunner.get().getLogger().warning("No BestPlayers found!");
            }
        }
        return null;
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
            } catch (SQLException | NullPointerException ex) {
                CubeRunner.get().getLogger().warning("Playerhead locations aren't set!");
            }
        }
    }
}
