package de.cubeattack.cuberunner.game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import de.cubeattack.cuberunner.CRPlayer;
import de.cubeattack.cuberunner.Configuration;
import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.MySQL;
import de.cubeattack.cuberunner.utils.ItemStackManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class User {
   private static Plugin plugin;
   private static MySQL mysql;
   private CRPlayer player;
   private String displayName;
   private OriginalPlayerStats originalStats;
   private boolean allowTeleport = false;
   private boolean eliminated = false;
   private long startTime;
   private int score = 0;
   private int jump = 0;
   private boolean jumping = false;
   private double distanceRan = 0.0D;
   private List<ItemStackManager> onlyChoosenBlocks;
   private HashMap<Long, Double> totalDistance = new HashMap();

   public static void setVariables(CubeRunner plugin) {
      User.plugin = plugin;
      mysql = plugin.getMySQL();
   }


   public User(Configuration config, CRPlayer player, Arena arena, boolean eliminated, boolean tpAuto) {
      this.player = player;
      this.displayName = player.getPlayer().getDisplayName();
      this.eliminated = eliminated;
      this.onlyChoosenBlocks = new ArrayList<>();
      this.originalStats = new OriginalPlayerStats(config, player.getPlayer());
      this.originalStats.ifillOtherStats(player.getPlayer());
      this.imaxStats();
      if (tpAuto) {
         player.getPlayer().teleport(arena.getLobby());
      }

      if (mysql.hasConnection()) {
         ResultSet query = mysql.query("SELECT * FROM " + config.tablePrefix + "PLAYERS WHERE UUID='" + getPlayer().getUniqueId() + "';");
         try {
            while(query.next()) {
               if(query.getString("selectedBlocks") != null){
                  for (String s: query.getString("selectedBlocks").split(", ")) {
                     onlyChoosenBlocks.add(new ItemStackManager(Material.getMaterial(s)));
                  }
               }else if (query.getString("owningBlocks") != null){
                  for (String s : query.getString("owningBlocks").split(", ")) {
                     onlyChoosenBlocks.add(new ItemStackManager(Material.getMaterial(s)));
                  }
               }else{
                  onlyChoosenBlocks.add(new ItemStackManager(Material.WHITE_WOOL));
               }
            }
         } catch (SQLException ex) {
            ex.printStackTrace();
         }
      }
   }

   public ItemStackManager getRandomAvailableBlock(){
      return this.onlyChoosenBlocks.get((int)Math.floor(Math.random() * (double)this.onlyChoosenBlocks.size()));
   }

   public User(int time) {
      this.score = time;
   }

   protected void setStartTime() {
      this.startTime = System.currentTimeMillis();
   }

   public Player getPlayer() {
      return this.player.getPlayer();
   }

   public CRPlayer getCRPlayer() {
      return this.player;
   }

   protected void addToScore() {
      ++this.score;
   }

   protected int getScore() {
      return this.score;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   protected UUID getUUID() {
      return this.player.getUUID();
   }

   protected int getGameLenght() {
      return (int)(System.currentTimeMillis() - this.startTime);
   }

   protected void ireturnStats() {
      this.player.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
      this.allowTeleport = true;
      this.originalStats.ireturnStats(this.player.getPlayer());
   }

   protected void imaxStats() {
      this.originalStats.imaxStats(this.player.getPlayer());
   }

   public boolean isEliminated() {
      return this.eliminated;
   }

   public void setEliminated(boolean eliminated) {
      this.eliminated = eliminated;
   }

   public void allowTeleport() {
      this.allowTeleport = true;
   }

   public boolean hasAllowTeleport() {
      return this.allowTeleport;
   }

   public void jump() {
      ++this.jump;
   }

   public int getJump() {
      return this.jump;
   }

   public void setJumping(boolean jumping) {
      this.jumping = jumping;
   }

   public boolean isJumping() {
      return this.jumping;
   }

   public void addToDistanceRan(Vector vector) {
      this.distanceRan += vector.length();
      this.totalDistance.put(System.currentTimeMillis(), Math.sqrt(Math.pow(vector.getX(), 2.0D) + Math.pow(vector.getZ(), 2.0D)));
   }

   public double getDistanceRan() {
      return this.distanceRan;
   }

   public String getName() {
      return this.player.getName();
   }

   public double getLastTreeSecondsDistance() {
      long currentTime = System.currentTimeMillis();
      double distanceDone = 0.0D;

      for(long i = currentTime - 2000L; i <= currentTime; ++i) {
         distanceDone += (Double)this.totalDistance.getOrDefault(i, 0.0D);
      }

      return distanceDone;
   }
}
