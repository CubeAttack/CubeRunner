package de.cubeattack.cuberunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import de.cubeattack.cuberunner.commands.inventories.CRInventory;
import de.cubeattack.cuberunner.utils.MinecraftConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class CRPlayer {
   private UUID uuid;
   private HashMap<CRStats, Object> stats = new HashMap();
   private CRInventory crInventory;

   public CRPlayer(UUID uuid, String name) {
      this.uuid = uuid;
      int var5;
      if (CubeRunner.get().getMySQL().hasConnection()) {
         CubeRunner.get().getMySQL().newPlayer(uuid, name);
      }

      CRStats[] var10;
      var5 = (var10 = CRStats.values()).length;

      for(int var9 = 0; var9 < var5; ++var9) {
         CRStats stats = var10[var9];
         this.stats.put(stats, stats.getDefault());
         if (stats == CRStats.NAME) {
            this.stats.put(stats, name);
         }
      }

   }

   public CRPlayer(ResultSet query) {
      try {
         this.uuid = UUID.fromString(query.getString("UUID"));
         CRStats[] var5;
         int var4 = (var5 = CRStats.values()).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            CRStats stats = var5[var3];
            this.stats.put(stats, stats.getValue(query));
         }
      } catch (SQLException var6) {
         Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could read some informations from playerData.ylm.");
      }

      CubeRunner.get().updateAll(this);
   }

   private void updateInformation(CRStats key, String value) {
      if (CubeRunner.get().getMySQL().hasConnection()) {
         CubeRunner.get().getMySQL().updatePlayer(this.uuid, key, value);
      }
   }

   private void updateInformation(CRStats key, double value) {
      if (CubeRunner.get().getMySQL().hasConnection()) {
         CubeRunner.get().getMySQL().updatePlayer(this.uuid, key, String.valueOf(value));
      }

   }

   private void updateInformation(CRStats key, int value) {
      if (CubeRunner.get().getMySQL().hasConnection()) {
         CubeRunner.get().getMySQL().updatePlayer(this.uuid, key, String.valueOf(value));
      }

   }

   private void updateInformation(CRStats key, boolean value) {
      if (CubeRunner.get().getMySQL().hasConnection()) {
         CubeRunner.get().getMySQL().updatePlayer(this.uuid, key, value ? "1" : "0");
      }

   }

   public void setCurrentInventory(CRInventory crInventory) {
      this.crInventory = crInventory;
   }

   public CRInventory getCurrentInventory() {
      return this.crInventory;
   }

   public UUID getUUID() {
      return this.uuid;
   }

   public Player getPlayer() {
      return Bukkit.getOfflinePlayer(this.uuid).getPlayer();
   }

   public void setName(String name) {
      this.stats.put(CRStats.NAME, name);
      this.updateInformation(CRStats.NAME, name);
   }

   public String getName() {
      return (String)this.stats.get(CRStats.NAME);
   }

   public void setLanguage(Language local) {
      this.stats.put(CRStats.LANGUAGE, local);
      this.updateInformation(CRStats.LANGUAGE, local.getFileName());
   }

   public Language getLanguage() {
      return (Language)this.stats.get(CRStats.LANGUAGE);
   }

   public void updateAverageScorePerGame() {
      int distance = (Integer)this.stats.get(CRStats.TOTAL_SCORE);
      int games = (Integer)this.stats.get(CRStats.GAMES_PLAYED);
      this.stats.put(CRStats.AVERAGE_SCORE, (double)distance / (double)games);
      this.updateInformation(CRStats.AVERAGE_SCORE, distance / games);
   }

   public void addInt(CRStats crStats, int amount) throws CRPlayer.PlayerStatsException {
      Object value = this.stats.get(crStats);
      if (!(value instanceof Integer)) {
         throw new CRPlayer.PlayerStatsException("Stat [" + crStats.name() + "] is not an Integer.");
      } else {
         this.stats.put(crStats, (Integer)value + amount);
         this.updateInformation(crStats, (Integer)value + amount);
      }
   }

   public void addDouble(CRStats crStats, double amount) throws CRPlayer.PlayerStatsException {
      Object value = this.stats.get(crStats);
      if (!(value instanceof Double)) {
         throw new CRPlayer.PlayerStatsException("Stat [" + crStats.name() + "] is not a Double.");
      } else {
         this.stats.put(crStats, (Double)value + amount);
         this.updateInformation(crStats, (Double)value + amount);
      }
   }

   public void increment(CRStats crStats, boolean save) throws CRPlayer.PlayerStatsException {
      Object value = this.stats.get(crStats);
      if (!(value instanceof Integer)) {
         throw new CRPlayer.PlayerStatsException("Stat [" + crStats.name() + "] is not an Incrementor.");
      } else {
         this.stats.put(crStats, (Integer)value + 1);
         CubeRunner.get().getAchievementManager().complete(this, crStats, (Integer)value + 1);
         if (save) {
            this.saveIncrementor(crStats);
         }

      }
   }

   public void saveIncrementor(CRStats crStats) throws CRPlayer.PlayerStatsException {
      Object value = this.stats.get(crStats);
      if (!(value instanceof Integer)) {
         throw new CRPlayer.PlayerStatsException("Stat [" + crStats.name() + "] is not an Incrementor.");
      } else {
         this.updateInformation(crStats, (Integer)value);
      }
   }

   public void doneChallenge(CRStats challenge) throws CRPlayer.PlayerStatsException {
      Object didIt = this.stats.get(challenge);
      if (!(didIt instanceof Boolean)) {
         throw new CRPlayer.PlayerStatsException("Stat [" + challenge.name() + "] is not a challenge.");
      } else {
         if (!(Boolean)didIt) {
            this.stats.put(challenge, true);
            this.updateInformation(challenge, true);
            CubeRunner.get().getAchievementManager().complete(this, challenge);
         }

      }
   }

   public int getInt(CRStats crStats) throws CRPlayer.PlayerStatsException {
      Object value = this.stats.get(crStats);
      if (!(value instanceof Integer)) {
         throw new CRPlayer.PlayerStatsException("Stat [" + crStats.name() + "] is not an Integer.");
      } else {
         return (Integer)value;
      }
   }

   public double getDouble(CRStats crStats) throws CRPlayer.PlayerStatsException {
      Object value = this.stats.get(crStats);
      if (!(value instanceof Double) && !(value instanceof Integer)) {
         throw new CRPlayer.PlayerStatsException("Stat [" + crStats.name() + "] is not a Double.");
      } else {
         return (Double)value;
      }
   }

   public boolean hasChallenge(CRStats challenge) throws CRPlayer.PlayerStatsException {
      Object didIt = this.stats.get(challenge);
      if (!(didIt instanceof Boolean)) {
         throw new CRPlayer.PlayerStatsException("Stat [" + challenge.name() + "] is not a challenge.");
      } else {
         return (Boolean)didIt;
      }
   }

   public Object get(CRStats stat) {
      return this.stats.get(stat);
   }

   public class PlayerStatsException extends Exception {
      private static final long serialVersionUID = -4844283181470739524L;

      public PlayerStatsException(String message) {
         super(message);
      }
   }
}
