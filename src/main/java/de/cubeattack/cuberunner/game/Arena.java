package de.cubeattack.cuberunner.game;

import com.google.gson.JsonObject;
import com.mysql.cj.Session;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.SessionManager;
import com.sk89q.worldedit.session.SessionOwner;
import de.cubeattack.cuberunner.ArenaData;
import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.CRPlayer;
import de.cubeattack.cuberunner.CRStats;
import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.MySQL;
import de.cubeattack.cuberunner.commands.signs.CRSign;
import de.cubeattack.cuberunner.utils.ItemStackManager;
import de.cubeattack.cuberunner.utils.Permissions;
import de.cubeattack.cuberunner.utils.Utils;
import net.milkbowl.vault.chat.Chat;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

public class Arena {
   private static CubeRunner plugin;
   private static MySQL mysql;
   private static ArenaData arenaData;
   private static List<Arena> arenas = new ArrayList();
   private String name;
   private World world;
   private Location minPoint;
   private Location maxPoint;
   private Location lobby;
   private Location startPoint;
   private int highestScore;
   private String highestPlayer;
   private int minAmountPlayer;
   private int maxAmountPlayer;
   private ColorManager colorManager;
   private Objective objective;
   private Scoreboard scoreboard;
   private List<User> users = new ArrayList();
   private GameState gameState;
   private boolean multiplayerGame;
   private boolean filled;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$me$poutineqc$cuberunner$game$GameState;

   public Arena(CubeRunner plugin) {
      this.gameState = GameState.UNREADY;
      this.multiplayerGame = false;
      Arena.plugin = plugin;
      mysql = plugin.getMySQL();
      arenaData = plugin.getArenaData();
   }

   public static void loadExistingArenas() {
      mysql = plugin.getMySQL();
      Arena.arenas = new ArrayList();
      Location minPoint;
      Location maxPoint;
      Location lobby;
      Location startPoint;
      int minAmountPlayer;
      int maxAmountPlayer;
      int highestScore;
      String highestPlayer;
      if (mysql.hasConnection()) {
         try {
            ResultSet arenas = mysql.query("SELECT * FROM " + CubeRunner.get().getConfiguration().tablePrefix + "ARENAS;");

            while(arenas.next()) {
               String name = arenas.getString("name");
               Long colorIndice = arenas.getLong("colorIndice");
               World world = Bukkit.getServer().getWorld(arenas.getString("world"));
               minPoint = new Location(world, (double)arenas.getInt("minPointX"), (double)arenas.getInt("minPointY"), (double)arenas.getInt("minPointZ"));
               maxPoint = new Location(world, (double)arenas.getInt("maxPointX"), (double)arenas.getInt("maxPointY"), (double)arenas.getInt("maxPointZ"));
               lobby = new Location(world, arenas.getDouble("lobbyX"), arenas.getDouble("lobbyY"), arenas.getDouble("lobbyZ"));
               lobby.setPitch(arenas.getFloat("lobbyPitch"));
               lobby.setYaw(arenas.getFloat("lobbyYaw"));
               startPoint = new Location(world, arenas.getDouble("startPointX"), arenas.getDouble("startPointY"), arenas.getDouble("startPointZ"));
               startPoint.setPitch(arenas.getFloat("startPointPitch"));
               startPoint.setYaw(arenas.getFloat("startPointYaw"));
               minAmountPlayer = arenas.getInt("minAmountPlayer");
               maxAmountPlayer = arenas.getInt("maxAmountPlayer");
               highestScore = arenas.getInt("highestScore");
               highestPlayer = arenas.getString("highestPlayer");
               new Arena(name, world, minPoint, maxPoint, lobby, startPoint, minAmountPlayer, maxAmountPlayer, highestScore, highestPlayer, colorIndice);
            }
         } catch (SQLException var12) {
            plugin.getLogger().info("[MySQL] Error while loading arenas.");
         }
      } else {
         if (!arenaData.getData().contains("arenas")) {
            return;
         }

         Iterator var14 = arenaData.getData().getConfigurationSection("arenas").getKeys(false).iterator();

         while(var14.hasNext()) {
            String arenaName = (String)var14.next();
            World world = Bukkit.getServer().getWorld(arenaData.getData().getString("arenas." + arenaName + ".world"));
            Long colorIndice = arenaData.getData().getLong("arenas." + arenaName + ".colorIndice", 1L);
            minPoint = new Location(world, (double)arenaData.getData().getInt("arenas." + arenaName + ".minPoint.X", 0), (double)arenaData.getData().getInt("arenas." + arenaName + ".minPoint.Y", 0), (double)arenaData.getData().getInt("arenas." + arenaName + ".minPoint.Z", 0));
            maxPoint = new Location(world, (double)arenaData.getData().getInt("arenas." + arenaName + ".maxPoint.X", 0), (double)arenaData.getData().getInt("arenas." + arenaName + ".maxPoint.Y", 0), (double)arenaData.getData().getInt("arenas." + arenaName + ".maxPoint.Z", 0));
            lobby = new Location(world, arenaData.getData().getDouble("arenas." + arenaName + ".lobby.X", 0.0D), arenaData.getData().getDouble("arenas." + arenaName + ".lobby.Y", 0.0D), arenaData.getData().getDouble("arenas." + arenaName + ".lobby.Z", 0.0D));
            lobby.setPitch((float)arenaData.getData().getDouble("arenas." + arenaName + ".lobby.Pitch", 0.0D));
            lobby.setYaw((float)arenaData.getData().getDouble("arenas." + arenaName + ".lobby.Yaw", 0.0D));
            startPoint = new Location(world, arenaData.getData().getDouble("arenas." + arenaName + ".startPoint.X", 0.0D), arenaData.getData().getDouble("arenas." + arenaName + ".startPoint.Y", 0.0D), arenaData.getData().getDouble("arenas." + arenaName + ".startPoint.Z", 0.0D));
            startPoint.setPitch((float)arenaData.getData().getDouble("arenas." + arenaName + ".startPoint.Pitch", 0.0D));
            startPoint.setYaw((float)arenaData.getData().getDouble("arenas." + arenaName + ".startPoint.Yaw", 0.0D));
            minAmountPlayer = arenaData.getData().getInt("arenas." + arenaName + ".minAmountPlayer", 1);
            maxAmountPlayer = arenaData.getData().getInt("arenas." + arenaName + ".maxAmountPlayer", 8);
            highestScore = arenaData.getData().getInt("arenas." + arenaName + ".highestScore.score", 0);
            highestPlayer = arenaData.getData().getString("arenas." + arenaName + ".highestScore.player", "null");
            new Arena(arenaName, world, minPoint, maxPoint, lobby, startPoint, minAmountPlayer, maxAmountPlayer, highestScore, highestPlayer, colorIndice);
         }
      }

   }

   private Arena(String name, World world, Location minPoint, Location maxPoint, Location lobby, Location startPoint, int minAmountPlayer, int maxAmountPlayer, int highestScore, String highestPlayer, Long colorIndice) {
      this.gameState = GameState.UNREADY;
      this.multiplayerGame = false;
      this.name = name;
      this.world = world;
      this.minPoint = minPoint;
      this.maxPoint = maxPoint;
      this.lobby = lobby;
      this.startPoint = startPoint;
      this.minAmountPlayer = minAmountPlayer;
      this.maxAmountPlayer = maxAmountPlayer;
      this.highestScore = highestScore;
      this.highestPlayer = highestPlayer;
      this.colorManager = new ColorManager(colorIndice, plugin, this);
      this.setNullIfDefault();
      arenas.add(this);
      this.resetScoreboard();
   }

   private void resetScoreboard() {
      Language local = Language.getDefault();
      this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
      this.objective = this.scoreboard.registerNewObjective(this.name, "dummy");
      this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
      this.objective.setDisplayName(ChatColor.LIGHT_PURPLE + this.name + ChatColor.WHITE + " : " + ChatColor.GREEN + local.get(Language.Messages.KEYWORD_SCOREBOARD_PLAYERS));
      this.objective.getScore(ChatColor.GREEN + "-------------------").setScore(1);
      this.objective.getScore(ChatColor.GREEN + local.get(Language.Messages.KEYWORD_INFO_BEST_SCORE) + " = " + ChatColor.LIGHT_PURPLE + this.highestScore).setScore(this.highestScore);
      this.objective.getScore(ChatColor.GREEN + local.get(Language.Messages.KEYWORD_INFO_MINIMUM) + " " + local.get(Language.Messages.KEYWORD_SCOREBOARD_PLAYERS) + " = " + ChatColor.LIGHT_PURPLE + this.minAmountPlayer).setScore(2);
      this.objective.getScore(ChatColor.GREEN + local.get(Language.Messages.KEYWORD_INFO_MAXIMUM) + " " + local.get(Language.Messages.KEYWORD_SCOREBOARD_PLAYERS) + " = " + ChatColor.LIGHT_PURPLE + this.maxAmountPlayer).setScore(3);
   }

   public Arena(String name, Player player) {
      this.gameState = GameState.UNREADY;
      this.multiplayerGame = false;
      this.name = name;
      this.world = player.getWorld();
      arenas.add(this);
      this.colorManager = new ColorManager(1L, plugin, this);
      this.highestPlayer = "null";
      this.highestScore = 0;
      this.minAmountPlayer = 1;
      this.maxAmountPlayer = 8;
      this.resetScoreboard();
      if (mysql.hasConnection()) {
         mysql.update("INSERT INTO " + CubeRunner.get().getConfiguration().tablePrefix + "ARENAS (name, world) " + "VALUES ('" + name + "','" + this.world.getName() + "');");
         mysql.update("UPDATE " + CubeRunner.get().getConfiguration().tablePrefix + "ARENAS SET colorIndice=" + 1L + " WHERE name='" + name + "';");
      } else {
         arenaData.getData().set("arenas." + name + ".world", this.world.getName());
         arenaData.getData().set("arenas." + name + ".colorIndice", 1L);
         arenaData.saveArenaData();
      }

   }

   private void setNullIfDefault() {
      if (0.0D == this.minPoint.getX() && 0.0D == this.minPoint.getY() && 0.0D == this.minPoint.getZ()) {
         this.minPoint = null;
      }

      if (0.0D == this.maxPoint.getX() && 0.0D == this.maxPoint.getY() && 0.0D == this.maxPoint.getZ()) {
         this.maxPoint = null;
      }

      if (0.0D == this.lobby.getX() && 0.0D == this.lobby.getY() && 0.0D == this.lobby.getZ()) {
         this.lobby = null;
      }

      if (0.0D == this.startPoint.getX() && 0.0D == this.startPoint.getY() && 0.0D == this.startPoint.getZ()) {
         this.startPoint = null;
      }

      if (this.isReady()) {
         this.gameState = GameState.READY;
      }

   }

   public void delete(Player player) {
      Language local = CubeRunner.get().getCRPlayer(player).getLanguage();
      local.sendMsg(player, local.get(Language.Messages.EDIT_DELETE));
      arenas.remove(this);
      if (mysql.hasConnection()) {
         mysql.update("DELETE FROM " + CubeRunner.get().getConfiguration().tablePrefix + "ARENAS WHERE name='" + this.name + "';");
      } else {
         arenaData.getData().set("arenas." + this.name, (Object)null);
         arenaData.saveArenaData();
      }

   }

   public void setArena(Player player) throws IncompleteRegionException {
      Language local = CubeRunner.get().getCRPlayer(player).getLanguage();
      SessionManager manager = WorldEdit.getInstance().getSessionManager();
      Region r;
      try {
         r = manager.findByName(player.getName()).getSelection(manager.findByName(player.getName()).getSelectionWorld());
      }catch (Exception ex){
         local.sendMsg(player, local.get(Language.Messages.EDIT_REGION_WORLDEDIT));
         return;
      }

      this.gameState = GameState.UNREADY;
      this.world = Bukkit.getServer().getWorld(r.getWorld().getName());
      Vector3 minVec3 = r.getMinimumPoint().toVector3();
      Vector3 maxVec3 = r.getMaximumPoint().toVector3();
      this.minPoint = new Location(world, minVec3.getX(), minVec3.getY(), minVec3.getZ());
      this.maxPoint = new Location(world, maxVec3.getX(), maxVec3.getY(), maxVec3.getZ());
      local.sendMsg(player, local.get(Language.Messages.EDIT_REGION).replace("%arena%", this.name));
      if (mysql.hasConnection()) {
         mysql.update("UPDATE " + CubeRunner.get().getConfiguration().tablePrefix + "ARENAS SET world='" + this.world.getName() + "',minPointX=" + this.minPoint.getBlockX() + ",minPointY=" + this.minPoint.getBlockY() + ",minPointZ=" + this.minPoint.getBlockZ() + ",maxPointX=" + this.maxPoint.getBlockX() + ",maxPointY=" + this.maxPoint.getBlockY() + ",maxPointZ=" + this.maxPoint.getBlockZ() + " WHERE name='" + this.name + "';");
      } else {
         arenaData.getData().set("arenas." + this.name + ".world", this.world.getName());
         arenaData.getData().set("arenas." + this.name + ".minPoint.X", this.minPoint.getBlockX());
         arenaData.getData().set("arenas." + this.name + ".minPoint.Y", this.minPoint.getBlockY());
         arenaData.getData().set("arenas." + this.name + ".minPoint.Z", this.minPoint.getBlockZ());
         arenaData.getData().set("arenas." + this.name + ".maxPoint.X", this.maxPoint.getBlockX());
         arenaData.getData().set("arenas." + this.name + ".maxPoint.Y", this.maxPoint.getBlockY());
         arenaData.getData().set("arenas." + this.name + ".maxPoint.Z", this.maxPoint.getBlockZ());
         arenaData.saveArenaData();
      }

      if (this.isReady()) {
         this.gameState = GameState.READY;
      }
   }

   public void setLobby(Player player) {
      Language local = CubeRunner.get().getCRPlayer(player).getLanguage();
      this.gameState = GameState.UNREADY;
      this.world = player.getWorld();
      this.lobby = player.getLocation();
      this.lobby.add(new Vector(0.0D, 0.5D, 0.0D));
      local.sendMsg(player, local.get(Language.Messages.EDIT_LOBBY).replace("%arena%", this.name));
      if (mysql.hasConnection()) {
         mysql.update("UPDATE " + CubeRunner.get().getConfiguration().tablePrefix + "ARENAS SET world='" + this.world.getName() + "',lobbyX=" + this.lobby.getX() + ",lobbyY=" + this.lobby.getY() + ",lobbyZ=" + this.lobby.getZ() + ",lobbyPitch=" + this.lobby.getPitch() + ",lobbyYaw=" + this.lobby.getYaw() + " WHERE name='" + this.name + "';");
      } else {
         arenaData.getData().set("arenas." + this.name + ".world", this.world.getName());
         arenaData.getData().set("arenas." + this.name + ".lobby.X", this.lobby.getX());
         arenaData.getData().set("arenas." + this.name + ".lobby.Y", this.lobby.getY());
         arenaData.getData().set("arenas." + this.name + ".lobby.Z", this.lobby.getZ());
         arenaData.getData().set("arenas." + this.name + ".lobby.Pitch", this.lobby.getPitch());
         arenaData.getData().set("arenas." + this.name + ".lobby.Yaw", this.lobby.getYaw());
         arenaData.saveArenaData();
      }

      if (this.isReady()) {
         this.gameState = GameState.READY;
      }

   }

   public void setStartPoint(Player player) {
      Language local = CubeRunner.get().getCRPlayer(player).getLanguage();
      this.gameState = GameState.UNREADY;
      this.world = player.getWorld();
      this.startPoint = player.getLocation();
      this.startPoint.add(new Vector(0.0D, 0.5D, 0.0D));
      local.sendMsg(player, local.get(Language.Messages.EDIT_STARTPOINT).replace("%arena%", this.name));
      if (mysql.hasConnection()) {
         mysql.update("UPDATE " + CubeRunner.get().getConfiguration().tablePrefix + "ARENAS SET world='" + this.world.getName() + "',startPointX=" + this.startPoint.getX() + ",startPointY=" + this.startPoint.getY() + ",startPointZ=" + this.startPoint.getZ() + ",startPointPitch=" + this.startPoint.getPitch() + ",startPointYaw=" + this.startPoint.getYaw() + " WHERE name='" + this.name + "';");
      } else {
         arenaData.getData().set("arenas." + this.name + ".world", this.world.getName());
         arenaData.getData().set("arenas." + this.name + ".startPoint.X", this.startPoint.getX());
         arenaData.getData().set("arenas." + this.name + ".startPoint.Y", this.startPoint.getY());
         arenaData.getData().set("arenas." + this.name + ".startPoint.Z", this.startPoint.getZ());
         arenaData.getData().set("arenas." + this.name + ".startPoint.Pitch", this.startPoint.getPitch());
         arenaData.getData().set("arenas." + this.name + ".startPoint.Yaw", this.startPoint.getYaw());
         arenaData.saveArenaData();
      }

      if (this.isReady()) {
         this.gameState = GameState.READY;
      }

   }


   public void setMinPlayer(int amount, Player player) {
      Language local = CubeRunner.get().getCRPlayer(player).getLanguage();
      if (amount < 1) {
         local.sendMsg(player, local.get(Language.Messages.EDIT_PLAYERS_ERROR).replace("%error%", local.get(Language.Messages.EDIT_PLAYERS_ERROR)));
      } else if (amount > this.maxAmountPlayer) {
         local.sendMsg(player, local.get(Language.Messages.EDIT_PLAYERS_ERROR).replace("%error%", local.get(Language.Messages.EDIT_PLAYERS_MINIMUM_MAXIMUM)));
      } else {
         if (mysql.hasConnection()) {
            mysql.update("UPDATE " + CubeRunner.get().getConfiguration().tablePrefix + "ARENAS SET minAmountPlayer=" + amount + " WHERE name='" + this.name + "';");
         } else {
            arenaData.getData().set("arenas." + this.name + ".minAmountPlayer", amount);
            arenaData.saveArenaData();
         }

         this.scoreboard.resetScores(ChatColor.GREEN + local.get(Language.Messages.KEYWORD_INFO_MINIMUM) + " = " + ChatColor.LIGHT_PURPLE + this.minAmountPlayer);
         this.minAmountPlayer = amount;
         this.objective.getScore(ChatColor.GREEN + local.get(Language.Messages.KEYWORD_INFO_MINIMUM) + " = " + ChatColor.LIGHT_PURPLE + this.minAmountPlayer).setScore(3);
         local.sendMsg(player, local.get(Language.Messages.COMMAND_SETMINPLAYER).replace("%arena%", this.name));
      }
   }

   public void setMaxPlayer(int amount, Player player) {
      Language local = CubeRunner.get().getCRPlayer(player).getLanguage();
      if (amount > 12) {
         local.sendMsg(player, local.get(Language.Messages.EDIT_PLAYERS_ERROR).replace("%error%", local.get(Language.Messages.EDIT_PLAYERS_MAXIMUM_10)));
      } else if (amount < this.minAmountPlayer) {
         local.sendMsg(player, local.get(Language.Messages.EDIT_PLAYERS_ERROR).replace("%error%", local.get(Language.Messages.EDIT_PLAYERS_MAXIMUM_MINIMUM)));
      } else {
         if (mysql.hasConnection()) {
            mysql.update("UPDATE " + CubeRunner.get().getConfiguration().tablePrefix + "ARENAS SET maxAmountPlayer=" + amount + " WHERE name='" + this.name + "';");
         } else {
            arenaData.getData().set("arenas." + this.name + ".maxAmountPlayer", amount);
            arenaData.saveArenaData();
         }

         this.scoreboard.resetScores(ChatColor.GREEN + local.get(Language.Messages.KEYWORD_INFO_MAXIMUM) + " = " + ChatColor.LIGHT_PURPLE + this.maxAmountPlayer);
         this.maxAmountPlayer = amount;
         this.objective.getScore(ChatColor.GREEN + local.get(Language.Messages.KEYWORD_INFO_MAXIMUM) + " = " + ChatColor.LIGHT_PURPLE + this.maxAmountPlayer).setScore(3);
         local.sendMsg(player, local.get(Language.Messages.COMMAND_SETMAXPLAYER).replace("%arena%", this.name));
      }
   }

   private boolean isReady() {
      return this.isOutsideArena(this.lobby) && this.isInsideArena(this.startPoint);
   }

   public void displayInformation(Player player) {
      Language local = CubeRunner.get().getCRPlayer(player).getLanguage();
      String stringGameState;
      switch($SWITCH_TABLE$me$poutineqc$cuberunner$game$GameState()[this.gameState.ordinal()]) {
      case 1:
         stringGameState = local.get(Language.Messages.KEYWORD_GAMESTATE_READY);
         break;
      case 2:
         stringGameState = local.get(Language.Messages.KEYWORD_GAMESTATE_STARTUP);
         break;
      case 3:
         stringGameState = local.get(Language.Messages.KEYWORD_GAMESTATE_ACTIVE);
         break;
      case 4:
      case 5:
      default:
         stringGameState = local.get(Language.Messages.KEYWORD_GAMESTATE_UNSET);
      }

      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m" + StringUtils.repeat(" ", 13) + "&r &5CubeRunner &d" + local.get(Language.Messages.KEYWORD_INFO) + " &5: &d" + this.name + " &8&m" + StringUtils.repeat(" ", 13)));
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_INFO_CURRENT) + " " + local.get(Language.Messages.KEYWORD_INFO_GAME_STATE) + ": &7" + stringGameState));
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_INFO_CURRENT) + " " + local.get(Language.Messages.KEYWORD_INFO_AMOUNT_OF_PLAYER) + ": &7" + this.users.size()));
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_INFO_MINIMUM) + " " + local.get(Language.Messages.KEYWORD_INFO_AMOUNT_OF_PLAYER) + ": &7" + this.minAmountPlayer));
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_INFO_MAXIMUM) + " " + local.get(Language.Messages.KEYWORD_INFO_AMOUNT_OF_PLAYER) + ": &7" + this.maxAmountPlayer));
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_INFO_BEST_SCORE) + ": &7" + this.highestScore + " &5" + local.get(Language.Messages.KEYWORD_GENERAL_BY) + " &7" + this.highestPlayer));
      player.sendMessage("\n");
      if (Permissions.hasPermission("cuberunner.admin.info", player, false)) {
         player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m" + StringUtils.repeat(" ", 5) + "&r &5CubeRunner &d" + local.get(Language.Messages.KEYWORD_INFO_ADVANCED) + " &5: &d" + this.name + " &8&m" + StringUtils.repeat(" ", 5)));
         player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_INFO_WORLD) + ": &7" + this.world.getName()));
         if (this.lobby == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_INFO_LOBBY) + ": &7null"));
         } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_INFO_LOBBY) + ": &7{" + (double)((int)(this.lobby.getX() * 100.0D)) / 100.0D + ", " + (double)((int)(this.lobby.getY() * 100.0D)) / 100.0D + ", " + (double)((int)(this.lobby.getZ() * 100.0D)) / 100.0D + "}"));
         }

         if (this.startPoint == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_INFO_START_POINT) + ": &7null"));
         } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_INFO_START_POINT) + ": &7{" + (double)((int)(this.startPoint.getX() * 100.0D)) / 100.0D + ", " + (double)((int)(this.startPoint.getY() * 100.0D)) / 100.0D + ", " + (double)((int)(this.startPoint.getZ() * 100.0D)) / 100.0D + "}"));
         }

         if (this.minPoint == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_INFO_MINIMUM) + local.get(Language.Messages.KEYWORD_INFO_ZONE_COORDINATE) + ": &7null"));
         } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_INFO_MINIMUM) + local.get(Language.Messages.KEYWORD_INFO_ZONE_COORDINATE) + ": &7{" + this.minPoint.getBlockX() + ", " + this.minPoint.getBlockY() + ", " + this.minPoint.getBlockZ() + "}"));
         }

         if (this.maxPoint == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_INFO_MAXIMUM) + local.get(Language.Messages.KEYWORD_INFO_ZONE_COORDINATE) + ": &7null"));
         } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_INFO_MAXIMUM) + local.get(Language.Messages.KEYWORD_INFO_ZONE_COORDINATE) + ": &7{" + this.maxPoint.getBlockX() + ", " + this.maxPoint.getBlockY() + ", " + this.maxPoint.getBlockZ() + "}"));
         }

         player.sendMessage("\n");
      }
   }

   public void addPlayer(Player player, boolean teleport) {
      Language local = CubeRunner.get().getCRPlayer(player).getLanguage();
      if (getArenaFromPlayer(player) != null) {
         local.sendMsg(player, local.get(Language.Messages.ERROR_ALREADY_IN_GAME));
      } else if (this.gameState != GameState.UNREADY && this.gameState != GameState.ENDING) {
         player.setScoreboard(this.scoreboard);
         User user = null;
         if (teleport || this.gameState != GameState.ACTIVE) {
            user = new User(CubeRunner.get().getConfiguration(), CubeRunner.get().getCRPlayer(player), this, this.gameState == GameState.ACTIVE, teleport);
            this.users.add(user);
            CRSign.updateSigns(this);
         }

         if (this.gameState == GameState.ACTIVE) {
            local.sendMsg(player, local.get(Language.Messages.JOIN_ACTIVE));
            if (user != null) {
               local.sendMsg(player, local.get(Language.Messages.JOIN_SPECTATOR));
            }

         } else {
            this.objective.getScore(user.getName()).setScore(0);
            if (this.gameState == GameState.STARTUP) {
               player.teleport(this.startPoint);
            }

            local.sendMsg(player, local.get(Language.Messages.JOIN_PLAYER).replace("%arena%", this.name));
            Iterator var6 = this.users.iterator();

            while(var6.hasNext()) {
               User u = (User)var6.next();
               if (u != user) {
                  Language localAlt = u.getCRPlayer().getLanguage();
                  localAlt.sendMsg(u, localAlt.get(Language.Messages.JOIN_OTHERS).replace("%player%", player.getDisplayName()));
               }
            }

            if (this.gameState == GameState.READY && CubeRunner.get().getConfiguration().autostart && this.getAmountOfPlayerInGame() >= this.minAmountPlayer) {
               this.initiateGame((Player)null);
            }

         }
      } else {
         local.sendMsg(player, local.get(Language.Messages.JOIN_UNREADY));
      }
   }

   public void removePlayer(Player player, Arena.LeavingReason reason) {
      Language local = CubeRunner.get().getCRPlayer(player).getLanguage();
      User user = this.getUser(player);
      if (user != null) {
         User u;
         Iterator var6;
         if (!user.isEliminated()) {
            local.sendMsg(user, local.get(Language.Messages.QUIT_PLAYER).replace("%arena%", this.name));
            var6 = this.users.iterator();

            while(var6.hasNext()) {
               u = (User)var6.next();
               if (u != user) {
                  Language l = u.getCRPlayer().getLanguage();
                  l.sendMsg(u, l.get(Language.Messages.QUIT_OTHERS).replace("%player%", user.getDisplayName()));
               }
            }

            if (this.gameState == GameState.ACTIVE) {
               this.eliminateUser(user, reason);
            }
         }

         if (this.gameState == GameState.STARTUP) {
            user.getPlayer().teleport(this.lobby);
         }

         if (this.gameState == GameState.STARTUP || this.gameState == GameState.READY) {
            this.objective.getScoreboard().resetScores(user.getName());
         }

         user.ireturnStats();
         this.users.remove(user);
         CRSign.updateSigns(this);
         if (this.gameState == GameState.STARTUP && this.getAmountOfPlayerInGame() < this.minAmountPlayer) {
            this.gameState = GameState.READY;
            var6 = this.users.iterator();

            while(var6.hasNext()) {
               u = (User)var6.next();
               u.getPlayer().teleport(this.lobby);
            }
         }

      }
   }

   public void initiateGame(Player player) {
      Language local = player == null ? null : CubeRunner.get().getCRPlayer(player).getLanguage();
      if (this.users.size() < this.minAmountPlayer) {
         if (local != null) {
            local.sendMsg(player, local.get(Language.Messages.START_MINIMUM).replace("%amount%", String.valueOf(this.minAmountPlayer)));
         }

      } else if (this.users.size() > this.maxAmountPlayer) {
         if (local != null) {
            local.sendMsg(player, local.get(Language.Messages.START_MAXIMUM).replace("%amount%", String.valueOf(this.maxAmountPlayer)));
         }

      } else {
         Iterator var4;
         if (CubeRunner.get().getConfiguration().broadcastStartup) {
            var4 = Bukkit.getServer().getOnlinePlayers().iterator();

            while(var4.hasNext()) {
               Player p = (Player)var4.next();
               Language localTemp = CubeRunner.get().getLang(p);
               localTemp.sendMsg(p, localTemp.get(Language.Messages.START_BROADCAST).replace("%arena%", this.name));
            }
         }

         this.resetArena();
         this.gameState = GameState.STARTUP;
         var4 = this.users.iterator();

         while(var4.hasNext()) {
            User user = (User)var4.next();
            user.getPlayer().teleport(this.startPoint);
            user.imaxStats();
         }

         this.countdown(this, CubeRunner.get().getConfiguration().countdownTime * 20);
      }
   }

   private void countdown(final Arena arena, final int cooldownTimer) {
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
         public void run() {
            int level = (int)Math.floor((double)(cooldownTimer / 20));

            User user;
            Iterator var3;
            for(var3 = arena.users.iterator(); var3.hasNext(); user.getPlayer().setExp((float)((double)(cooldownTimer % 20) / 20.0D))) {
               user = (User)var3.next();
               if (cooldownTimer != 0) {
                  user.getPlayer().setLevel(level + 1);
               } else {
                  user.getPlayer().setLevel(0);
               }
            }

            Language local;
            if (Arena.this.gameState == GameState.STARTUP) {
               switch(cooldownTimer / 20) {
               case 0:
                  if ((double)cooldownTimer % 20.0D == 0.0D) {
                     arena.startGame();
                     return;
                  }
                  break;
               case 1:
               case 2:
               case 3:
               case 4:
               case 5:
               case 10:
               case 30:
                  var3 = arena.users.iterator();

                  while(var3.hasNext()) {
                     user = (User)var3.next();
                     if ((double)cooldownTimer % 20.0D == 0.0D) {
                        Sound sound = Sound.valueOf("UI_BUTTON_CLICK");
                        user.getPlayer().playSound(user.getPlayer().getLocation(), sound, 1.0F, 1.0F);
                        local = CubeRunner.get().getCRPlayer(user.getPlayer()).getLanguage();

                        StringBuilder title = new StringBuilder();
                        title
                                .append(ChatColor.GOLD)
                                .append(ChatColor.BOLD)
                                .append(cooldownTimer / 20);

                        StringBuilder subtitle = new StringBuilder();
                        subtitle
                                .append(ChatColor.GRAY)
                                .append(ChatColor.ITALIC)
                                .append(local.get(Language.Messages.KEYWORD_GENERAL_SECONDS));

                        user.getPlayer().sendTitle(title.toString(), subtitle.toString(), 5, 10, 5);
                     }
                  }
               }

               arena.countdown(arena, cooldownTimer - 1);
            } else {
               var3 = arena.users.iterator();

               while(var3.hasNext()) {
                  user = (User)var3.next();
                  Player p = user.getPlayer();
                  p.setLevel(0);
                  p.setExp(0.0F);
                  local = user.getCRPlayer().getLanguage();
                  local.sendMsg(user.getPlayer(), local.get(Language.Messages.START_STOP));
               }
            }

         }
      }, 1L);
   }

   protected void startGame() {
      Language local = Language.getDefault();
      this.gameState = GameState.ACTIVE;
      CRSign.updateSigns(this);
      this.multiplayerGame = this.getAmountOfPlayerInGame() > 1;
      this.objective.setDisplayName(ChatColor.AQUA + this.name + ChatColor.WHITE + " : " + ChatColor.GREEN + local.get(Language.Messages.KEYWORD_SCOREBOARD_SCORE));
      this.objective.getScoreboard().resetScores(ChatColor.GREEN + "-------------------");
      this.objective.getScoreboard().resetScores(ChatColor.GREEN + local.get(Language.Messages.KEYWORD_INFO_MINIMUM) + " " + local.get(Language.Messages.KEYWORD_SCOREBOARD_PLAYERS) + " = " + ChatColor.LIGHT_PURPLE + this.minAmountPlayer);
      this.objective.getScoreboard().resetScores(ChatColor.GREEN + local.get(Language.Messages.KEYWORD_INFO_MAXIMUM) + " " + local.get(Language.Messages.KEYWORD_SCOREBOARD_PLAYERS) + " = " + ChatColor.LIGHT_PURPLE + this.maxAmountPlayer);
      blockShower(1L, this);
      Iterator var3 = this.users.iterator();

      while(var3.hasNext()) {
         User user = (User)var3.next();
         Player player = user.getPlayer();
         player.setGameMode(GameMode.ADVENTURE);
         player.setFlying(false);
         user.setStartTime();
      }

   }

   private void resetArena() {
      for(int x = this.minPoint.getBlockX(); x <= this.maxPoint.getBlockX(); ++x) {
         for(int y = this.maxPoint.getBlockY(); y >= this.minPoint.getBlockY(); --y) {
            for(int z = this.minPoint.getBlockZ(); z <= this.maxPoint.getBlockZ(); ++z) {
               Location location = new Location(this.world, (double)x, (double)y, (double)z);
               Block block = location.getBlock();
               if (block.getType().name().toLowerCase().endsWith("clay") || block.getType().name().toLowerCase().endsWith("wool")) {
                  Iterator var7 = this.colorManager.getOnlyChoosenBlocks().iterator();

                  while(var7.hasNext()) {
                     ItemStackManager item = (ItemStackManager)var7.next();
                     if (item.getMaterial().name().endsWith(block.getType().name().split("_")[1])) {
                        block.setType(Material.AIR);
                        break;
                     }
                  }
               }
            }
         }
      }

   }

   public void resetArena(ItemStack item) {
      for(int x = this.minPoint.getBlockX(); x <= this.maxPoint.getBlockX(); ++x) {
         for(int y = this.maxPoint.getBlockY(); y >= this.minPoint.getBlockY(); --y) {
            for(int z = this.minPoint.getBlockZ(); z <= this.maxPoint.getBlockZ(); ++z) {
               Location location = new Location(this.world, (double)x, (double)y, (double)z);
               Block block = location.getBlock();
               if ((block.getType() == Material.CLAY || block.getType() == Material.GRAY_WOOL) && item.getType() == block.getType() && item.getDurability() == block.getData()) {
                  block.setType(Material.AIR);
               }
            }
         }
      }

   }

   private static void blockShower(final long number, final Arena arena) {
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
         public void run() {
            if (arena.getAmountOfPlayerInGame() != 0) {
               User user;
               Iterator var2;
               if (!arena.filled && arena.isArenaFull()) {
                  arena.filled = true;
                  var2 = arena.users.iterator();

                  while(var2.hasNext()) {
                     user = (User)var2.next();
                     if (!user.isEliminated()) {
                        try {
                           user.getCRPlayer().doneChallenge(CRStats.FILL_THE_ARENA);
                        } catch (CRPlayer.PlayerStatsException var19) {
                           var19.printStackTrace();
                        }
                     }
                  }
               }

               if (number == 6000L) {
                  var2 = arena.users.iterator();

                  while(var2.hasNext()) {
                     user = (User)var2.next();
                     if (!user.isEliminated()) {
                        try {
                           user.getCRPlayer().doneChallenge(CRStats.SURVIVE_5_MINUTES);
                        } catch (CRPlayer.PlayerStatsException var18) {
                           var18.printStackTrace();
                        }
                     }
                  }
               }

               var2 = arena.users.iterator();

               while(var2.hasNext()) {
                  user = (User)var2.next();
                  if (!user.isEliminated()) {
                     if (user.getLastTreeSecondsDistance() < 1.0D && user.getScore() >= 2) {
                        arena.eliminateUser(user, Arena.LeavingReason.HIDING);
                     }

                     Player player = user.getPlayer();
                     Location l;
                     if (arena.maxPoint.getBlockY() > player.getLocation().getBlockY() + 10) {
                        l = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 10.0D, player.getLocation().getZ());
                     } else {
                        l = new Location(player.getWorld(), player.getLocation().getX(), arena.maxPoint.getY(), player.getLocation().getZ());
                     }

                     NumberFormat formater = new DecimalFormat("#.#####");
                     double xOffset = Double.parseDouble(formater.format(l.getX() > 0.0D ? l.getX() % 1.0D : 1.0D + l.getX() % 1.0D).replace(",", "."));
                     double zOffset = Double.parseDouble(formater.format(l.getZ() > 0.0D ? l.getZ() % 1.0D : 1.0D + l.getZ() % 1.0D).replace(",", "."));
                     if (xOffset <= 0.7D && xOffset >= 0.3D) {
                        l.setX(Math.floor(l.getX()) + 0.5D);
                     }

                     if (zOffset <= 0.7D && zOffset >= 0.3D) {
                        l.setZ(Math.floor(l.getZ()) + 0.5D);
                     }

                     player.setSaturation(20.0F);
                     if (number % 20L == 0L) {
                        player.setLevel((int)number / 20);
                        player.setSaturation(20.0F);
                        user.addToScore();
                        arena.objective.getScore(user.getName()).setScore(user.getScore());

                        try {
                           user.getCRPlayer().increment(CRStats.TOTAL_SCORE, false);
                        } catch (CRPlayer.PlayerStatsException var16) {
                           var16.printStackTrace();
                        }
                     }

                     ItemStackManager itemStack = arena.colorManager.getRandomAvailableBlock();

                     try {
                        MaterialData iBlockData = itemStack.getItem().getData();
                        FallingBlock entityFallingBlock = l.getWorld().spawnFallingBlock(l, iBlockData);
                        entityFallingBlock.setCustomName(player.getUniqueId().toString());
                        entityFallingBlock.setCustomNameVisible(false);
                        entityFallingBlock.setDropItem(false);
                        entityFallingBlock.setTicksLived(1);
                        entityFallingBlock.setHurtEntities(true);

                       // entityFallingBlock.getClass().getMethod("a", Boolean.TYPE).invoke(entityFallingBlock, true);

                        if (number % 2L == 0L) {
                           entityFallingBlock.setVelocity(new Vector((1.0D - Math.random() * 2.0D) / 10.0D, 0, (1.0D - Math.random() * 2.0D) / 10.0D));
                        }

                     } catch (Exception var17) {
                        var17.printStackTrace();
                     }
                  }
               }

               Arena.blockShower(number + 1L, arena);
            }
         }
      }, 1L);
   }

   private boolean isArenaFull() {
      for(int i = this.minPoint.getBlockX(); i < this.maxPoint.getBlockX(); ++i) {
         for(int j = this.minPoint.getBlockZ(); j < this.maxPoint.getBlockZ(); ++j) {
            if ((new Location(this.world, (double)i, (double)this.minPoint.getBlockY(), (double)j)).getBlock().getType() == Material.AIR) {
               return false;
            }
         }
      }

      return true;
   }

   public void eliminateUser(User user, Arena.LeavingReason reason) {
      user.getPlayer().teleport(this.lobby);
      user.setEliminated(true);
      Language local;
      User u;
      Iterator var5;
      Language l;
      if (reason == Arena.LeavingReason.CRUSHED) {
         local = user.getCRPlayer().getLanguage();
         local.sendMsg(user, local.get(Language.Messages.END_CRUSH_PLAYER).replace("%score%", String.valueOf(user.getScore())));
         var5 = this.users.iterator();

         while(var5.hasNext()) {
            u = (User)var5.next();
            if (u != user) {
               l = u.getCRPlayer().getLanguage();
               l.sendMsg(u, l.get(Language.Messages.END_CRUSH_OTHERS).replace("%player%", user.getDisplayName()).replace("%score%", String.valueOf(user.getScore())));
            }
         }
      }

      if (reason == Arena.LeavingReason.HIDING) {
         local = user.getCRPlayer().getLanguage();
         local.sendMsg(user, local.get(Language.Messages.END_HIDE_PLAYER));
         var5 = this.users.iterator();

         while(var5.hasNext()) {
            u = (User)var5.next();
            if (u != user) {
               l = u.getCRPlayer().getLanguage();
               l.sendMsg(u, l.get(Language.Messages.END_HIDE_OTHERS).replace("%player%", user.getDisplayName()));
            }
         }
      }

      try {
         user.getCRPlayer().increment(CRStats.GAMES_PLAYED, true);
         user.getCRPlayer().addInt(CRStats.TIME_PLAYED, user.getGameLenght());
         user.getCRPlayer().addDouble(CRStats.TOTAL_DISTANCE, user.getDistanceRan());
         user.getCRPlayer().saveIncrementor(CRStats.TOTAL_SCORE);
      } catch (CRPlayer.PlayerStatsException var10) {
         var10.printStackTrace();
      }

      user.getCRPlayer().updateAverageScorePerGame();
      if (CubeRunner.get().isEconomyEnabled()) {
         Player player = user.getPlayer();
         double amount = CubeRunner.get().getConfiguration().pricePerScore * (double)user.getScore();
         CubeRunner.get().getEconomy().depositPlayer(player, amount);

         try {
            user.getCRPlayer().addDouble(CRStats.MONEY, amount);
         } catch (CRPlayer.PlayerStatsException var9) {
            var9.printStackTrace();
         }

         l = user.getCRPlayer().getLanguage();
         l.sendMsg(player, l.get(Language.Messages.END_REWARD).replace("%amount2%", String.valueOf(user.getScore())).replace("%amount%", String.valueOf((double)((int)(amount * 100.0D)) / 100.0D)).replace("%currency%", CubeRunner.get().getEconomy().currencyNamePlural()));
      }

      if (user.getScore() == 42) {
         try {
            user.getCRPlayer().doneChallenge(CRStats.THE_ANSWER_TO_LIFE);
         } catch (CRPlayer.PlayerStatsException var8) {
            var8.printStackTrace();
         }
      }

      if (this.getAmountOfPlayerInGame() == 0) {
         this.endingSequence();
      }

      this.commandReward(user, this.getAmountOfPlayerInGame() == 0);

      try {
         CubeRunner.get().getPlayerData().updateAll(user.getCRPlayer());
      } catch (CRPlayer.PlayerStatsException var7) {
         var7.printStackTrace();
      }

   }

   private void commandReward(User user, boolean lastPlayer) {
      Iterator var4 = CubeRunner.get().getConfiguration().playerCommands.iterator();

      while(true) {
         String command;
         User u;
         Iterator var6;
         while(var4.hasNext()) {
            command = (String)var4.next();
            if (command.contains("%all%")) {
               var6 = this.users.iterator();

               while(var6.hasNext()) {
                  u = (User)var6.next();
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%all%", u.getName()).replace("%player%", user.getName()).replace("%arena%", this.name).replace("%prefix%", u.getCRPlayer().getLanguage().get(Language.Messages.PREFIX_SHORT)));
               }
            } else {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", user.getName()).replace("%arena%", this.name).replace("%prefix%", user.getCRPlayer().getLanguage().get(Language.Messages.PREFIX_SHORT)));
            }
         }

         if (lastPlayer) {
            if (this.multiplayerGame) {
               var4 = CubeRunner.get().getConfiguration().winnerCommands.iterator();

               label85:
               while(true) {
                  while(true) {
                     if (!var4.hasNext()) {
                        break label85;
                     }

                     command = (String)var4.next();
                     if (command.contains("%all%")) {
                        var6 = this.users.iterator();

                        while(var6.hasNext()) {
                           u = (User)var6.next();
                           Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%all%", u.getName()).replace("%winner%", user.getName()).replace("%arena%", this.name).replace("%prefix%", u.getCRPlayer().getLanguage().get(Language.Messages.PREFIX_SHORT)));
                        }
                     } else {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%winner%", user.getName()).replace("%arena%", this.name).replace("%prefix%", user.getCRPlayer().getLanguage().get(Language.Messages.PREFIX_SHORT)));
                     }
                  }
               }
            }

            var4 = CubeRunner.get().getConfiguration().endingCommands.iterator();

            while(true) {
               while(var4.hasNext()) {
                  command = (String)var4.next();
                  if (command.contains("%all%")) {
                     var6 = this.users.iterator();

                     while(var6.hasNext()) {
                        u = (User)var6.next();
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%all%", u.getName()).replace("%arena%", this.name).replace("%prefix%", u.getCRPlayer().getLanguage().get(Language.Messages.PREFIX_SHORT)));
                     }
                  } else {
                     Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%arena%", this.name).replace("%prefix%", user.getCRPlayer().getLanguage().get(Language.Messages.PREFIX_SHORT)));
                  }
               }

               Player player;
               Language local;
               if (this.multiplayerGame) {
                  if (CubeRunner.get().getConfiguration().broadcastEndingMulti) {
                     var4 = Bukkit.getOnlinePlayers().iterator();

                     while(var4.hasNext()) {
                        player = (Player)var4.next();
                        local = CubeRunner.get().getLang(player);
                        local.sendMsg(player, local.get(Language.Messages.END_BROADCAST_MULTIPLAYER).replace("%player%", user.getDisplayName()).replace("%arena%", this.name).replace("%score%", String.valueOf(user.getScore())));
                     }
                  }
               } else if (CubeRunner.get().getConfiguration().broadcastEndingSingle) {
                  var4 = Bukkit.getOnlinePlayers().iterator();

                  while(var4.hasNext()) {
                     player = (Player)var4.next();
                     local = CubeRunner.get().getLang(player);
                     local.sendMsg(player, local.get(Language.Messages.END_BROADCAST_SINGLEPLAYER).replace("%player%", user.getDisplayName()).replace("%arena%", this.name).replace("%score%", String.valueOf(user.getScore())));
                  }
               }
               break;
            }
         }

         return;
      }
   }

   private void endingSequence() {
      this.gameState = GameState.ENDING;
      CRSign.updateSigns(this);
      User user = this.getHighestScore();
      if (this.multiplayerGame) {
         try {
            user.getCRPlayer().increment(CRStats.MULTIPLAYER_WON, true);
         } catch (CRPlayer.PlayerStatsException var5) {
            var5.printStackTrace();
         }
      }

      Iterator var3;
      Language local;
      if (user.getScore() > this.highestScore) {
         this.highestScore = user.getScore();
         this.highestPlayer = user.getPlayer().getName();
         var3 = Bukkit.getServer().getOnlinePlayers().iterator();

         while(var3.hasNext()) {
            Player player = (Player)var3.next();
            local = CubeRunner.get().getCRPlayer(player).getLanguage();
            local.sendMsg(player, local.get(Language.Messages.END_BEST).replace("%player%", user.getDisplayName()).replace("%score%", String.valueOf(user.getScore())).replace("%arena%", this.name));
         }

         if (mysql.hasConnection()) {
            mysql.update("UPDATE " + CubeRunner.get().getConfiguration().tablePrefix + "ARENAS SET highestScore='" + user.getScore() + "', highestPlayer='" + user.getPlayer().getName() + "' WHERE name='" + this.name + "';");
         } else {
            arenaData.getData().set("arenas." + this.name + ".highestScore.score", this.highestScore);
            arenaData.getData().set("arenas." + this.name + ".highestScore.player", user.getPlayer().getName());
            arenaData.saveArenaData();
         }
      }

      this.resetScoreboard();
      if (CubeRunner.get().getConfiguration().teleportAfterEnding) {
         var3 = this.users.iterator();

         while(var3.hasNext()) {
            User u = (User)var3.next();
            local = u.getCRPlayer().getLanguage();
            local.sendMsg(u, local.get(Language.Messages.END_TELEPORT));
         }

         Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
               Arena.this.kickUsers(false);
            }
         }, 100L);
      } else {
         this.kickUsers(true);
      }

   }

   public User getHighestScore() {
      User user = new User(0);
      Iterator var3 = this.users.iterator();

      while(var3.hasNext()) {
         User u = (User)var3.next();
         if (user.getScore() <= u.getScore()) {
            user = u;
         }
      }

      return user;
   }

   private void kickUsers(boolean wait) {
      Iterator var3 = this.users.iterator();

      while(var3.hasNext()) {
         User user = (User)var3.next();
         user.allowTeleport();
         user.ireturnStats();
      }

      this.users.clear();
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
         public void run() {
            Arena.this.gameState = GameState.READY;
            CRSign.updateSigns(Arena.this);
         }
      }, wait ? 20L : 0L);
   }

   public boolean isInsideArena(Location location) {
      if (this.minPoint == null) {
         return false;
      } else if (this.maxPoint == null) {
         return false;
      } else if (location == null) {
         return false;
      } else if (location.getBlockX() >= this.minPoint.getBlockX() && location.getBlockX() <= this.maxPoint.getBlockX()) {
         if (location.getBlockY() >= this.minPoint.getBlockY() && location.getBlockY() <= this.maxPoint.getBlockY()) {
            return location.getBlockZ() >= this.minPoint.getBlockZ() && location.getBlockZ() <= this.maxPoint.getBlockZ();
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean isOutsideArena(Location location) {
      if (this.minPoint == null) {
         return false;
      } else if (this.maxPoint == null) {
         return false;
      } else if (location == null) {
         return false;
      } else if (location.getBlockX() >= this.minPoint.getBlockX() && location.getBlockX() <= this.maxPoint.getBlockX()) {
         if (location.getBlockY() >= this.minPoint.getBlockY() && location.getBlockY() <= this.maxPoint.getBlockY()) {
            return location.getBlockZ() < this.minPoint.getBlockZ() || location.getBlockZ() > this.maxPoint.getBlockZ();
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   public static List<Arena> getArenas() {
      return arenas;
   }

   public String getName() {
      return this.name;
   }

   public GameState getGameState() {
      return this.gameState;
   }

   public static Arena getArena(String name) {
      Iterator var2 = arenas.iterator();

      while(var2.hasNext()) {
         Arena arena = (Arena)var2.next();
         if (arena.name.toLowerCase().equalsIgnoreCase(name.toLowerCase())) {
            return arena;
         }
      }

      return null;
   }

   public static Arena getArenaFromPlayer(Player player) {
      Iterator var2 = arenas.iterator();

      while(var2.hasNext()) {
         Arena arena = (Arena)var2.next();
         Iterator var4 = arena.users.iterator();

         while(var4.hasNext()) {
            User user = (User)var4.next();
            if (user.getPlayer() == player) {
               return arena;
            }
         }
      }

      return null;
   }

   public User getUser(Player player) {
      Iterator var3 = this.users.iterator();

      while(var3.hasNext()) {
         User user = (User)var3.next();
         if (user.getPlayer() == player) {
            return user;
         }
      }

      return null;
   }

   public int getAmountOfPlayerInGame() {
      int i = 0;
      Iterator var3 = this.users.iterator();

      while(var3.hasNext()) {
         User user = (User)var3.next();
         if (!user.isEliminated()) {
            ++i;
         }
      }

      return i;
   }

   public Location getLobby() {
      return this.lobby;
   }

   public Location getStartPoint() {
      return this.startPoint;
   }

   public String getHighestPlayer() {
      return this.highestPlayer;
   }

   public int getHighestPlayerScore() {
      return this.highestScore;
   }

   public Location getMinPoint() {
      return this.minPoint;
   }

   public ColorManager getColorManager() {
      return this.colorManager;
   }

   public World getWorld() {
      return this.world;
   }

   public int getMaxPlayer() {
      return this.maxAmountPlayer;
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$me$poutineqc$cuberunner$game$GameState() {
      int[] var10000 = $SWITCH_TABLE$me$poutineqc$cuberunner$game$GameState;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[GameState.values().length];

         try {
            var0[GameState.ACTIVE.ordinal()] = 3;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[GameState.ENDING.ordinal()] = 4;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[GameState.READY.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[GameState.STARTUP.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[GameState.UNREADY.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$me$poutineqc$cuberunner$game$GameState = var0;
         return var0;
      }
   }

   public static enum LeavingReason {
      DISCONNECT,
      HIDING,
      CRUSHED,
      COMMAND;
   }
}
