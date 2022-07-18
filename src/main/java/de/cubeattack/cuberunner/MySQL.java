package de.cubeattack.cuberunner;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MySQL {
   private CubeRunner plugin;
   private String prefix;
   private String host;
   private int port;
   private String database;
   private String user;
   private String password;
   private Connection connection;

   public MySQL(CubeRunner plugin, String host, int port, String database, String user, String password, String prefix) {
      this.plugin = plugin;
      this.prefix = prefix;
      this.host = host;
      this.port = port;
      this.database = database;
      this.user = user;
      this.password = password;
      this.connect();
      if (this.hasConnection())
         this.initializeDatabases();
   }

   private void initializeDatabases() {
      this.update("CREATE TABLE IF NOT EXISTS " + this.prefix + "SIGNS (" + "uuid varchar(64) UNIQUE, type varchar(32), locationWorld varchar(32), locationX INT DEFAULT 0, locationY INT DEFAULT 0, locationZ INT DEFAULT 0);");
      this.update("ALTER TABLE " + this.prefix + "SIGNS CONVERT TO CHARACTER SET utf8;");
      this.update("CREATE TABLE IF NOT EXISTS " + this.prefix + "ARENAS (name varchar(32) UNIQUE,world varchar(32), minAmountPlayer INT DEFAULT 1, maxAmountPlayer INT DEFAULT 8, firstHighestScore varchar(32) DEFAULT 'null, 0', secondHighestScore varchar(32) DEFAULT 'null, 0', thirdHighestScore varchar(32) DEFAULT 'null, 0', colorIndice LONG, minPointX INT DEFAULT 0, minPointY INT DEFAULT 0, minPointZ INT DEFAULT 0, maxPointX INT DEFAULT 0, maxPointY INT DEFAULT 0, maxPointZ INT DEFAULT 0, lobbyX DOUBLE DEFAULT 0, lobbyY DOUBLE DEFAULT 0, lobbyZ DOUBLE DEFAULT 0, lobbyPitch FLOAT DEFAULT 0, lobbyYaw FLOAT DEFAULT 0, startPointX DOUBLE DEFAULT 0, startPointY DOUBLE DEFAULT 0, startPointZ DOUBLE DEFAULT 0, startPointPitch FLOAT DEFAULT 0, startPointYaw FLOAT DEFAULT 0, headLoc1 varchar(32) DEFAULT null, headLoc2 varchar(32) DEFAULT null, headLoc3 varchar(32) DEFAULT null);");
      this.update("ALTER TABLE " + this.prefix + "ARENAS CONVERT TO CHARACTER SET utf8;");
      this.update("CREATE TABLE IF NOT EXISTS " + this.prefix + "PLAYERS (UUID varchar(64) UNIQUE, name varchar(64), language varchar(32), timePlayed INT DEFAULT 0, money DOUBLE DEFAULT 0, owningBlocks varchar(32) DEFAULT 'WHITE_WOOL', selectedBlocks varchar(32), owningPets varchar(32), selectedPet varchar(32), owningWeapons varchar(32), selectedWeapon varchar(32), averageDistancePerGame DOUBLE DEFAULT 0, totalDistance DOUBLE DEFAULT 0, games INT DEFAULT 0, totalScore INT DEFAULT 0, kills INT DEFAULT 0, multiplayerWon INT DEFAULT 0, survive5Minutes BOOLEAN DEFAULT FALSE, reachHeight10 BOOLEAN DEFAULT FALSE, fillTheArena BOOLEAN DEFAULT FALSE, theAnswerToLife BOOLEAN DEFAULT FALSE, theRageQuit BOOLEAN DEFAULT FALSE, theKillerBunny BOOLEAN DEFAULT FALSE);");
      this.update("ALTER TABLE " + this.prefix + "PLAYERS CONVERT TO CHARACTER SET utf8;");
   }

   public MySQL() {
      this.connection = null;
   }

   public void updateInfo(CubeRunner plugin) {
      Configuration config = plugin.getConfiguration();
      this.plugin = plugin;
      this.host = config.host;
      this.port = config.port;
      this.database = config.database;
      this.user = config.user;
      this.password = config.password;
      this.connect();
   }

   public void connect() {
      try {
         this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.user, this.password);
      } catch (SQLException var2) {
         this.plugin.getLogger().info("[MySQL] The connection to MySQL couldn't be made! reason: " + var2.getMessage());
         Bukkit.getScheduler().scheduleSyncRepeatingTask(CubeRunner.get(), new Runnable() {
            int count = 5;
            @Override
            public void run() {
               if(count <= 0) {
                  Bukkit.getPluginManager().disablePlugin(CubeRunner.get());
                  return;
               }
               plugin.getLogger().severe("Plugin stopped due to missing mysql connect in " + count +  " seconds.");
               count--;
            }
         }, 20, 20);
      }
   }

   public void close() {
      try {
         if (this.connection != null) {
            this.connection.close();
         }
      } catch (SQLException var2) {
         this.plugin.getLogger().info("[MySQL] The connection couldn't be closed! reason: " + var2.getMessage());
      }

   }

   public void update(String qry) {
      try {
         PreparedStatement st = this.connection.prepareStatement(qry);
         st.execute();
         st.close();
      } catch (SQLException var3) {
         this.connect();
         System.err.println(qry);
         System.err.println(var3);
      }

   }

   public void newPlayer(UUID uuid, String name) {
      String qry = String.format("INSERT INTO %1$sPLAYERS (UUID, name) VALUES ('%2$s','%3$s');", this.prefix, uuid.toString(), name);
      this.update(qry);
   }

   public void updatePlayer(UUID uuid, CRStats key, String value) {
      String qry = String.format("UPDATE %1$sPLAYERS SET %2$s='%3$s' WHERE UUID='%4$s';", this.prefix, key.getNameMySQL(), value, uuid.toString());
      this.update(qry);
   }

   public boolean hasConnection() {
      return this.connection != null;
   }

   public ResultSet query(String qry) {
      ResultSet rs = null;

      try {
         PreparedStatement st = this.connection.prepareStatement(qry);
         rs = st.executeQuery();
      } catch (SQLException var4) {
         this.connect();
         System.err.println(qry);
         System.err.println(var4);
      }

      return rs;
   }

   public ResultSet queryAll() {
      String qry = String.format("SELECT * FROM %1$sPLAYERS", this.prefix);
      return this.query(qry);
   }

   public ResultSet queryView(String viewName) {
      return this.query("SELECT * FROM " + this.prefix + viewName + ";");
   }
}
