package de.cubeattack.cuberunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Configuration {
   private FileConfiguration config;
   private File configFile;
   public String language;
   public boolean mysql;
   public String host;
   public int port;
   public String user;
   public String password;
   public String database;
   public String tablePrefix;
   public boolean prefixInFrontOfEveryMessages;
   public boolean autostart;
   public int countdownTime;
   public boolean saveAndClearInventory;
   public boolean teleportAfterEnding;
   public boolean broadcastStartup;
   public boolean broadcastAchievement;
   public boolean broadcastEndingSingle;
   public boolean broadcastEndingMulti;
   public boolean lookForUpdates;
   public boolean economyRewards;
   public double pricePerScore;
   public boolean achievementsRewards;
   public List<String> winnerCommands;
   public List<String> playerCommands;
   public List<String> endingCommands;

   public Configuration(CubeRunner plugin) {
      this.configFile = new File(plugin.getDataFolder(), "config.yml");
      if (!this.configFile.exists()) {
         plugin.saveDefaultConfig();
      }

      this.loadConfiguration(plugin);
   }

   public void loadConfiguration(Plugin plugin) {
      this.config = YamlConfiguration.loadConfiguration(this.configFile);
      this.language = this.config.getString("language", "en-US");
      this.mysql = this.config.getBoolean("mysql", false);
      if (this.mysql) {
         this.host = this.config.getString("host", "127.0.0.1");
         if (this.host.equalsIgnoreCase("localhost")) {
            this.host = "127.0.0.1";
         }

         this.port = this.config.getInt("host", 3306);
         this.user = this.config.getString("user");
         this.password = this.config.getString("password");
         this.database = this.config.getString("database");
         this.tablePrefix = this.config.getString("tablePrefix", "cuberunner_");
      }

      this.lookForUpdates = this.config.getBoolean("checkForUpdates", true);
      this.prefixInFrontOfEveryMessages = this.config.getBoolean("prefixInFrontOfEveryMessages", true);
      this.countdownTime = this.config.getInt("countdownTime", 15);
      this.autostart = this.config.getBoolean("autostart", true);
      this.saveAndClearInventory = this.config.getBoolean("saveAndClearInventory", true);
      this.teleportAfterEnding = this.config.getBoolean("teleportAfterEnding", true);
      this.broadcastStartup = this.config.getBoolean("broadcasts.startup", true);
      this.broadcastAchievement = this.config.getBoolean("broadcasts.achievement", true);
      this.broadcastEndingSingle = this.config.getBoolean("broadcasts.ending.singleplayer", true);
      this.broadcastEndingMulti = this.config.getBoolean("broadcasts.ending.multiplayer", true);
      this.economyRewards = this.config.getBoolean("economyRewards", false);
      this.pricePerScore = this.config.getDouble("pricePerScore", 0.05D);
      this.achievementsRewards = this.config.getBoolean("achievementsRewards");
      this.winnerCommands = new ArrayList();
      this.playerCommands = new ArrayList();
      this.endingCommands = new ArrayList();
      Iterator var3 = this.config.getStringList("commands").iterator();

      while(var3.hasNext()) {
         String command = (String)var3.next();
         if (command.contains("%winner%")) {
            this.winnerCommands.add(command);
         } else if (command.contains("%player%")) {
            this.playerCommands.add(command);
         } else {
            this.endingCommands.add(command);
         }
      }

   }

   public static enum CommandType {
      WINNER,
      PLAYER,
      NOTHING;
   }
}
