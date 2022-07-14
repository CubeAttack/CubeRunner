package de.cubeattack.cuberunner.game;

import java.util.HashMap;
import java.util.UUID;
import de.cubeattack.cuberunner.CRPlayer;
import de.cubeattack.cuberunner.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class User {
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
   private HashMap<Long, Double> totalDistance = new HashMap();

   public User(Configuration config, CRPlayer player, Arena arena, boolean eliminated, boolean tpAuto) {
      this.player = player;
      this.displayName = player.getPlayer().getDisplayName();
      this.eliminated = eliminated;
      this.originalStats = new OriginalPlayerStats(config, player.getPlayer());
      if (tpAuto) {
         player.getPlayer().teleport(arena.getLobby());
      }

      this.originalStats.ifillOtherStats(player.getPlayer());
      this.imaxStats();
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
