package de.cubeattack.cuberunner;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

import de.cubeattack.cuberunner.game.Arena;
import de.cubeattack.cuberunner.commands.signs.CRSign;
import de.cubeattack.cuberunner.game.Head;
import de.cubeattack.cuberunner.game.User;
import de.cubeattack.cuberunner.listeners.*;
import de.cubeattack.cuberunner.utils.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CubeRunner extends JavaPlugin {
   private static CubeRunner plugin;
   private static int pluginid = 15831;
   public static final String name = "CubeRunner";
   private static final String currency = "Coins";
   private Configuration config;
   private MySQL mysql = new MySQL();
   private PlayerData playerData;
   private AchievementManager achievementManager;
   private Economy economy;

   public void onLoad(){
      plugin = this;
      this.config = new Configuration(this);

   }

   public void onEnable() {
      new Metrics(this, pluginid);
      this.connectMySQL();
      this.loadLanguages();
      this.initialiseEconomy();

      //this.economy.depositPlayer(Bukkit.getOfflinePlayer("EinfacheSache"), 100);
      boolean b = this.economy.withdrawPlayer(Bukkit.getOfflinePlayer("EinfacheSache"), 100);
      this.playerData = new PlayerData(this);
      this.playerData.loadPlayers(plugin);
      this.achievementManager = new AchievementManager(this);
      CRSign.setVariables(this);
      User.setVariables(this);
      Head.setVariables(this);
      new Arena(this);
      Objects.requireNonNull(this.getCommand("cuberunner")).setExecutor(new ListenerCommand());
      Objects.requireNonNull(this.getCommand("cuberunner")).setTabCompleter(new ListenerTabComplete());
      this.enableListeners();

      Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
         Arena.loadExistingArenas();
         CRSign.loadAllSigns();
         Head.loadHeads();
         Head.updateHeads();
      }, 0L);
      this.getLogger().info(this.getDescription().getName() + " has been enabled (v" + this.getDescription().getVersion() + ")");
   }

   public void onDisable() {
      PluginDescriptionFile pdfFile = this.getDescription();
      Logger logger = this.getLogger();

      if (this.mysql.hasConnection()) {
         this.mysql.close();
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
      this.mysql = new MySQL(this, this.config.host, this.config.port, this.config.database, this.config.user, this.config.password, this.config.tablePrefix);
   }

   public void loadLanguages() {
      new Language(this, "en");
      new Language(this, "fr");
      new Language(this, "de");
   }

   public boolean initialiseEconomy() {
      if (!this.config.economyRewards){
         this.economy = null;
         return false;
      }

      this.getLogger().info("Loading Economy System ....");
      this.economy = new Economy(currency);
      return true;
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

   public PlayerData getPlayerData() {
      return this.playerData;
   }

   public static CubeRunner get() {
      return plugin;
   }

   public CRPlayer getCRPlayer(Player player) {
      return this.playerData.getCRPlayer(player);
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
