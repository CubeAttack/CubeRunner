package de.cubeattack.cuberunner;

import java.util.UUID;
import java.util.logging.Logger;

import de.cubeattack.cuberunner.game.Arena;
import de.cubeattack.cuberunner.commands.signs.CRSign;
import de.cubeattack.cuberunner.listeners.*;
import de.cubeattack.cuberunner.utils.MinecraftConfiguration;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class CubeRunner extends JavaPlugin {
   private static CubeRunner plugin;
   public static final String name = "CubeRunner";
   private Configuration config;
   private MySQL mysql = new MySQL();
   private ArenaData arenaData;
   private PlayerData playerData;
   private AchievementManager achievementManager;
   private Economy economy;
   public static String NMS_VERSION;

   public void onEnable() {

      plugin = this;
      NMS_VERSION = this.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
      this.config = new Configuration(this);

      if (this.initialiseEconomy()) {
         this.loadLanguages();
         this.connectMySQL();
         this.arenaData = new ArenaData(this);
         this.playerData = new PlayerData(this);
         this.playerData.loadPlayers(plugin);
         this.achievementManager = new AchievementManager(this);
         CRSign.setVariables(this);
         new Arena(this);
         this.getCommand("cuberunner").setExecutor(new ListenerCommand());
         this.getCommand("cuberunner").setTabCompleter(new ListenerTabComplete());
         this.enableListeners();

         Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            public void run() {
               Arena.loadExistingArenas();
               CRSign.loadAllSigns();
            }
         }, 0L);
         this.getLogger().info(this.getDescription().getName() + " has been enabled (v" + this.getDescription().getVersion() + ")");
      }
   }

   public void onDisable() {
      PluginDescriptionFile pdfFile = this.getDescription();
      Logger logger = this.getLogger();

      if (this.mysql.hasConnection()) {
         this.mysql.close();
      } else {
         this.arenaData.loadArenaData();
      }

      logger.info(pdfFile.getName() + " has been diabled");
   }

   public void reload() {
      Language.clear();
      this.config.loadConfiguration(this);

      if (this.initialiseEconomy()) {
         this.loadLanguages();
         this.playerData.clear();
         this.playerData.loadViews();
         this.playerData.loadPlayers(this);
         this.achievementManager = new AchievementManager(this);
         CRSign.setVariables(this);
         Arena.loadExistingArenas();
         CRSign.loadAllSigns();
      }
   }

   public void connectMySQL() {
      if (this.config.mysql) {
         this.mysql = new MySQL(this, this.config.host, this.config.port, this.config.database, this.config.user, this.config.password, this.config.tablePrefix);
      } else {
         this.mysql = new MySQL(this);
      }

   }

   public void loadLanguages() {
      new Language(this, "en");
      new Language(this, "fr");
      new Language(this, "de");
   }

   public boolean initialiseEconomy() {
      if (this.config.economyRewards && !this.setupEconomy()) {
         this.getLogger().warning("Vault not found.");
         this.getLogger().warning("Add Vault to your plugins or disable monetary rewards in the config.");
         this.getLogger().info("Disabling CubeRunner...");
         this.getServer().getPluginManager().disablePlugin(this);
         return false;
      } else {
         return true;
      }
   }

   private boolean setupEconomy() {
      this.economy = null;
      if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
         return false;
      } else {
         RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
         if (rsp == null) {
            return false;
         } else {
            this.economy = rsp.getProvider();
            return this.economy != null;
         }
      }
   }

   public boolean isEconomyEnabled() {
      return this.economy != null;
   }

   public Economy getEconomy() {
      return this.economy;
   }

   private void enableListeners() {
      PluginManager pm = this.getServer().getPluginManager();
      pm.registerEvents(this.playerData, this);
      pm.registerEvents(new ListenerPlayerDamage(), this);
      pm.registerEvents(new ListenerPlayerTeleport(), this);
      pm.registerEvents(new ListenerPlayerDisconnect(), this);
      pm.registerEvents(new ListenerPlayerDeath(), this);
      pm.registerEvents(new ListenerSignUpdate(), this);
      pm.registerEvents(new ListenerPlayerInteract(), this);
      pm.registerEvents(new ListenerInventoryClose(), this);
      pm.registerEvents(new ListenerPlayerMove(), this);
      pm.registerEvents(new ListenerSignBreak(), this);
      pm.registerEvents(new ListenerInventoryClick(), this);
      pm.registerEvents(new ListenerEntityChangeBlock(), this);
      pm.registerEvents(new ListenerEntityGlide(), this);


   }

   public Configuration getConfiguration() {
      return this.config;
   }

   public MySQL getMySQL() {
      return this.mysql;
   }

   public ArenaData getArenaData() {
      return this.arenaData;
   }

   public PlayerData getPlayerData() {
      return this.playerData;
   }

   public static CubeRunner get() {
      return plugin;
   }

   public CRPlayer getCRPlayer(Player player) {
      return this.playerData.getCRPlayer(player);
   }

   public MinecraftConfiguration getPlayerConfig() {
      return this.playerData.getConfig();
   }

   public Language getLang(Player player) {
      return this.getCRPlayer(player).getLanguage();
   }

   public AchievementManager getAchievementManager() {
      return this.achievementManager;
   }

   public void updateAll(CRPlayer crPlayer) {
      try {
         this.playerData.updateAll(crPlayer);
      } catch (CRPlayer.PlayerStatsException var3) {
         var3.printStackTrace();
      }

   }

   public CRPlayer getCRPlayer(UUID uuid) {
      return this.playerData.getCRPlayer(uuid);
   }
}
