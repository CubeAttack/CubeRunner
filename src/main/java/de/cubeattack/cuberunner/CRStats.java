package de.cubeattack.cuberunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.configuration.ConfigurationSection;

public enum CRStats {
   GAMES_PLAYED("games", "games", 0) {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getInt(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getInt(this.getNameFlatFile(), (Integer)this.getDefaultValue());
      }

      public Object getDefault() {
         return 0;
      }
   },
   TOTAL_SCORE("totalScore", "totalScore", 0) {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getInt(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getInt(this.getNameFlatFile(), (Integer)this.getDefaultValue());
      }

      public Object getDefault() {
         return 0;
      }
   },
   KILLS("kills", "kills", 0) {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getInt(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getInt(this.getNameFlatFile(), (Integer)this.getDefaultValue());
      }

      public Object getDefault() {
         return 0;
      }
   },
   MULTIPLAYER_WON("multiplayerWon", "multiplayerWon", 0) {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getInt(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getInt(this.getNameFlatFile(), (Integer)this.getDefaultValue());
      }

      public Object getDefault() {
         return 0;
      }
   },
   TOTAL_DISTANCE("totalDistance", "totalDistance", 0.0D) {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getDouble(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getDouble(this.getNameFlatFile(), (Double)this.getDefaultValue());
      }

      public Object getDefault() {
         return 0.0D;
      }
   },
   AVERAGE_SCORE("averageDistancePerGame", "averageDistancePerGame", 0.0D) {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getDouble(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getDouble(this.getNameFlatFile(), (Double)this.getDefaultValue());
      }

      public Object getDefault() {
         return 0.0D;
      }
   },
   TIME_PLAYED("timePlayed", "timePlayed", 0) {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getInt(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getInt(this.getNameFlatFile(), (Integer)this.getDefaultValue());
      }

      public Object getDefault() {
         return 0;
      }
   },
   MONEY("money", "money", 0.0D) {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getDouble(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getDouble(this.getNameFlatFile(), (Double)this.getDefaultValue());
      }

      public Object getDefault() {
         return 0.0D;
      }
   },
   SURVIVE_5_MINUTES("survive5Minutes", "achievement.survive5Minutes", false) {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getBoolean(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getBoolean(this.getNameFlatFile(), (Boolean)this.getDefaultValue());
      }

      public Object getDefault() {
         return false;
      }
   },
   REACH_HEIGHT_10("reachHeight10", "achievement.reachHeight10", false) {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getBoolean(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getBoolean(this.getNameFlatFile(), (Boolean)this.getDefaultValue());
      }

      public Object getDefault() {
         return false;
      }
   },
   FILL_THE_ARENA("fillTheArena", "achievement.fillTheArena", false) {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getBoolean(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getBoolean(this.getNameFlatFile(), (Boolean)this.getDefaultValue());
      }

      public Object getDefault() {
         return false;
      }
   },
   THE_ANSWER_TO_LIFE("theAnswerToLife", "achievement.theAnswerToLife", false) {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getBoolean(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getBoolean(this.getNameFlatFile(), (Boolean)this.getDefaultValue());
      }

      public Object getDefault() {
         return false;
      }
   },
   THE_RAGE_QUIT("theRageQuit", "achievement.theRageQuit", false) {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getBoolean(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getBoolean(this.getNameFlatFile(), (Boolean)this.getDefaultValue());
      }

      public Object getDefault() {
         return false;
      }
   },
   THE_KILLER_BUNNY("theKillerBunny", "achievement.theKillerBunny", false) {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getBoolean(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getBoolean(this.getNameFlatFile(), (Boolean)this.getDefaultValue());
      }

      public Object getDefault() {
         return false;
      }
   },
   NAME("name", "name", "default") {
      public Object getValue(ResultSet query) throws SQLException {
         return query.getString(this.getNameMySQL());
      }

      public Object getValue(ConfigurationSection config) {
         return config.getString(this.getNameFlatFile(), (String)this.getDefaultValue());
      }

      public Object getDefault() {
         return null;
      }
   },
   LANGUAGE("language", "language", "default") {
      public Object getValue(ResultSet query) throws SQLException {
         return Language.getKeyLanguage(query.getString(this.getNameMySQL()));
      }

      public Object getValue(ConfigurationSection config) {
         return Language.getKeyLanguage(config.getString(this.getNameFlatFile(), (String)this.getDefaultValue()));
      }

      public Object getDefault() {
         return Language.getKeyLanguage("default");
      }
   };

   private final String nameMySQL;
   private final String nameFlatFile;
   private final Object defaultValue;

   public abstract Object getValue(ResultSet var1) throws SQLException;

   public abstract Object getValue(ConfigurationSection var1);

   public abstract Object getDefault();

   private CRStats(String nameMySQL, String nameFlatFile, Object defaultValue) {
      this.nameMySQL = nameMySQL;
      this.nameFlatFile = nameFlatFile;
      this.defaultValue = defaultValue;
   }

   public String getNameMySQL() {
      return this.nameMySQL;
   }

   public String getNameFlatFile() {
      return this.nameFlatFile;
   }

   public Object getDefaultValue() {
      return this.defaultValue;
   }

   // $FF: synthetic method
   CRStats(String var3, String var4, Object var5, CRStats var6) {
      this(var3, var4, var5);
   }
}
