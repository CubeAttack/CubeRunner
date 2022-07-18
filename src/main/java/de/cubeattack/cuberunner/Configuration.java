package de.cubeattack.cuberunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Configuration {
   private final File configFile;
   public String language;
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
      FileConfiguration config = YamlConfiguration.loadConfiguration(this.configFile);
      this.language = config.getString("language", "de-DE");
      this.host = config.getString("host", "127.0.0.1");
      if (this.host.equalsIgnoreCase("localhost")) {
         this.host = "127.0.0.1";
      }

      this.port = config.getInt("host", 3306);
      this.user = config.getString("user", "root");
      this.password = config.getString("password", "");
      this.database = config.getString("database", "cuberunner");
      this.tablePrefix = config.getString("tablePrefix", "cuberunner_");


      this.prefixInFrontOfEveryMessages = config.getBoolean("prefixInFrontOfEveryMessages", true);
      this.countdownTime = config.getInt("countdownTime", 15);
      this.autostart = config.getBoolean("autostart", true);
      this.saveAndClearInventory = config.getBoolean("saveAndClearInventory", true);
      this.teleportAfterEnding = config.getBoolean("teleportAfterEnding", true);
      this.broadcastStartup = config.getBoolean("broadcasts.startup", true);
      this.broadcastAchievement = config.getBoolean("broadcasts.achievement", true);
      this.broadcastEndingSingle = config.getBoolean("broadcasts.ending.singleplayer", true);
      this.broadcastEndingMulti = config.getBoolean("broadcasts.ending.multiplayer", true);
      this.economyRewards = config.getBoolean("economyRewards", false);
      this.pricePerScore = config.getDouble("pricePerScore", 0.05D);
      this.achievementsRewards = config.getBoolean("achievementsRewards");
      this.winnerCommands = new ArrayList();
      this.playerCommands = new ArrayList();
      this.endingCommands = new ArrayList();

      for (String command : config.getStringList("commands")) {
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
