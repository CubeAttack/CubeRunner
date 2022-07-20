package de.cubeattack.cuberunner.utils;

import de.cubeattack.cuberunner.Configuration;
import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.MySQL;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Economy {

    String currency;
    MySQL mysql;
    Configuration config;
    CubeRunner plugin;

    public Economy(String currency){
        this.currency = currency;
        this.plugin = CubeRunner.get();
        this.mysql = CubeRunner.get().getMySQL();;
        this.config = plugin.getConfiguration();

    }

    public void depositPlayer(OfflinePlayer player, double coins){
        try {
            mysql.update("UPDATE " + config.tablePrefix + "PLAYERS SET money =money + " + coins + " WHERE uuid='" + player.getUniqueId()+ "';");
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public boolean withdrawPlayer(OfflinePlayer player, double coins){
        try {
            ResultSet result = mysql.query("SELECT * FROM " + config.tablePrefix + "PLAYERS WHERE uuid='" + player.getUniqueId()+ "';");
            result.next();
           if((result.getDouble("money") - coins) < 0){
               return false;
            }else {
               mysql.update("UPDATE " + config.tablePrefix + "PLAYERS SET money =money - " + coins + " WHERE uuid='" + player.getUniqueId()+ "';");
           }
        } catch (NullPointerException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public double getMoney(Player player){
        if (mysql.hasConnection()) {
            ResultSet query = mysql.query("SELECT * FROM " + config.tablePrefix + "PLAYERS WHERE uuid='" + player.getUniqueId()+ "';");
            try {
                query.next();
                return query.getDouble("money");
            } catch (SQLException | NullPointerException ex) {
                CubeRunner.get().getLogger().warning("MySQL Error");
            }
        }
        return 0;
    }

    public String currencyNamePlural(){
        return currency;
    }

    public Economy getEconomy() {
        return this;
    }
}
