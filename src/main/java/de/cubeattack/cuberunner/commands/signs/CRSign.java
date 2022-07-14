package de.cubeattack.cuberunner.commands.signs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import de.cubeattack.cuberunner.game.Arena;
import de.cubeattack.cuberunner.utils.MinecraftConfiguration;
import de.cubeattack.cuberunner.Configuration;
import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.MySQL;
import de.cubeattack.cuberunner.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public abstract class CRSign {
   protected static CubeRunner plugin;
   protected static Configuration config;
   private static MySQL mysql;
   protected static PlayerData playerData;
   private static MinecraftConfiguration signData;
   protected static List<CRSign> signs = new ArrayList();
   private UUID uuid;
   protected Location location;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$me$poutineqc$cuberunner$commands$signs$CRSign$SignType;

   public static void setVariables(CubeRunner plugin) {
      CRSign.plugin = plugin;
      config = plugin.getConfiguration();
      mysql = plugin.getMySQL();
      playerData = plugin.getPlayerData();
      signData = new MinecraftConfiguration((String)null, "signData", false);
   }

   public static void loadAllSigns() {
      signs.clear();
      if (mysql.hasConnection()) {
         ResultSet query = mysql.query("SELECT * FROM " + config.tablePrefix + "SIGNS;");

         try {
            while(query.next()) {
               UUID uuid = UUID.fromString(query.getString("uuid"));
               Location location = new Location(Bukkit.getWorld(query.getString("locationWorld")), (double)query.getInt("locationX"), (double)query.getInt("locationY"), (double)query.getInt("locationZ"));

               try {
                  switch($SWITCH_TABLE$me$poutineqc$cuberunner$commands$signs$CRSign$SignType()[CRSign.SignType.valueOf(query.getString("type")).ordinal()]) {
                  case 1:
                     new CRSignJoin(uuid, location);
                     break;
                  case 2:
                     new CRSignPlay(uuid, location);
                     break;
                  case 3:
                     new CRSignStart(uuid, location);
                     break;
                  case 4:
                     new CRSignQuit(uuid, location);
                     break;
                  case 5:
                     new CRSignStats(uuid, location);
                     break;
                  case 6:
                     new CRSignTop(uuid, location);
                  }
               } catch (IllegalArgumentException var6) {
                  removeSign(uuid, location);
               }
            }
         } catch (SQLException var7) {
            var7.printStackTrace();
         }
      } else {
         if (!signData.get().contains("signs")) {
            return;
         }

         Iterator var9 = signData.get().getConfigurationSection("signs").getKeys(false).iterator();

         while(var9.hasNext()) {
            String uuid = (String)var9.next();
            ConfigurationSection cs = signData.get().getConfigurationSection("signs." + uuid);
            Location location = new Location(Bukkit.getWorld(cs.getString("location.world")), (double)cs.getInt("location.X", 0), (double)cs.getInt("location.Y", 0), (double)cs.getInt("location.Z"));

            try {
               switch($SWITCH_TABLE$me$poutineqc$cuberunner$commands$signs$CRSign$SignType()[CRSign.SignType.valueOf(cs.getString("type", UUID.randomUUID().toString())).ordinal()]) {
               case 1:
                  new CRSignJoin(UUID.fromString(uuid), location);
                  break;
               case 2:
                  new CRSignPlay(UUID.fromString(uuid), location);
                  break;
               case 3:
                  new CRSignStart(UUID.fromString(uuid), location);
                  break;
               case 4:
                  new CRSignQuit(UUID.fromString(uuid), location);
                  break;
               case 5:
                  new CRSignStats(UUID.fromString(uuid), location);
                  break;
               case 6:
                  new CRSignTop(UUID.fromString(uuid), location);
               }
            } catch (IllegalArgumentException var5) {
               removeSign(UUID.fromString(uuid), location);
            }
         }
      }

      updateSigns();
   }

   public CRSign(UUID uuid, Location location, CRSign.SignType type) {
      this.uuid = uuid;
      this.location = location;
   }

   public CRSign(Location location, CRSign.SignType type) {
      this.uuid = UUID.randomUUID();
      this.location = location;
      if (mysql.hasConnection()) {
         mysql.update("INSERT INTO " + config.tablePrefix + "SIGNS (uuid, type ,locationWorld, locationX, locationY, locationZ) " + "VALUES ('" + this.uuid + "','" + type.name() + "','" + location.getWorld().getName() + "','" + location.getBlockX() + "','" + location.getBlockY() + "','" + location.getBlockZ() + "');");
      } else {
         signData.get().set("signs." + this.uuid.toString() + ".type", type.name());
         signData.get().set("signs." + this.uuid.toString() + ".location.world", location.getWorld().getName());
         signData.get().set("signs." + this.uuid.toString() + ".location.X", location.getBlockX());
         signData.get().set("signs." + this.uuid.toString() + ".location.Y", location.getBlockY());
         signData.get().set("signs." + this.uuid.toString() + ".location.Z", location.getBlockZ());
         signData.save();
      }

   }

   private static void removeSign(UUID uuid, Location location) {
      if (location.getBlock().getState() instanceof Sign) {
         Sign sign = (Sign)location.getBlock().getState();
         sign.setLine(0, " ");
         sign.setLine(1, " ");
         sign.setLine(2, " ");
         sign.setLine(3, " ");
         sign.getLocation().getChunk().load();
         sign.update();
      }

      if (mysql.hasConnection()) {
         mysql.update("DELETE FROM " + config.tablePrefix + "SIGNS WHERE uuid='" + uuid.toString() + "';");
      } else {
         signData.get().set("signs." + uuid.toString(), (Object)null);
         signData.save();
      }

   }

   public void removeSign() {
      removeSign(this.uuid, this.location);
      signs.remove(this);
   }

   public abstract void onInteract(Player var1);

   protected abstract boolean updateSign(Language var1, Sign var2);

   public static void updateSigns() {
      List<CRSign> errors = new ArrayList();
      Iterator var2 = signs.iterator();

      CRSign dacsign;
      while(var2.hasNext()) {
         dacsign = (CRSign)var2.next();
         BlockState block = dacsign.location.getBlock().getState();
         if (!(block instanceof Sign)) {
            errors.add(dacsign);
         } else if (!dacsign.updateSign(Language.getDefault(), (Sign)block)) {
            errors.add(dacsign);
         }
      }

      var2 = errors.iterator();

      while(var2.hasNext()) {
         dacsign = (CRSign)var2.next();
         dacsign.removeSign();
      }

   }

   public static void updateSigns(Arena arena) {
      if (arena != null) {
         List<CRSign> errors = new ArrayList();
         Iterator var3 = signs.iterator();

         CRSign dacsign;
         while(var3.hasNext()) {
            dacsign = (CRSign)var3.next();
            if (dacsign instanceof CRSignDisplay) {
               CRSignDisplay displaySign = (CRSignDisplay)dacsign;
               if (displaySign.arena == arena) {
                  BlockState block = dacsign.location.getBlock().getState();
                  if (!(block instanceof Sign)) {
                     errors.add(dacsign);
                  } else {
                     displaySign.updateDisplay(Language.getDefault(), (Sign)block);
                  }
               }
            }
         }

         var3 = errors.iterator();

         while(var3.hasNext()) {
            dacsign = (CRSign)var3.next();
            dacsign.removeSign();
         }

      }
   }

   public static void arenaDelete(Arena arena) {
      if (arena != null) {
         List<CRSign> errors = new ArrayList();
         Iterator var3 = signs.iterator();

         CRSign dacsign;
         while(var3.hasNext()) {
            dacsign = (CRSign)var3.next();
            if (dacsign instanceof CRSignDisplay) {
               CRSignDisplay displaySign = (CRSignDisplay)dacsign;
               if (displaySign.arena == arena && arena.getName().equalsIgnoreCase(((Sign)dacsign.location.getBlock().getState()).getLine(2))) {
                  errors.add(dacsign);
               }
            }
         }

         var3 = errors.iterator();

         while(var3.hasNext()) {
            dacsign = (CRSign)var3.next();
            dacsign.removeSign();
         }

      }
   }

   public static CRSign getCrSign(Location location) {
      Iterator var2 = signs.iterator();

      while(var2.hasNext()) {
         CRSign dacsign = (CRSign)var2.next();
         if (dacsign.location.equals(location)) {
            return dacsign;
         }
      }

      return null;
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$me$poutineqc$cuberunner$commands$signs$CRSign$SignType() {
      int[] var10000 = $SWITCH_TABLE$me$poutineqc$cuberunner$commands$signs$CRSign$SignType;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[CRSign.SignType.values().length];

         try {
            var0[CRSign.SignType.JOIN.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[CRSign.SignType.PLAY.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[CRSign.SignType.QUIT.ordinal()] = 4;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[CRSign.SignType.START.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[CRSign.SignType.STATS.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[CRSign.SignType.TOP.ordinal()] = 6;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$me$poutineqc$cuberunner$commands$signs$CRSign$SignType = var0;
         return var0;
      }
   }

   public static enum SignType {
      JOIN,
      PLAY,
      START,
      QUIT,
      STATS,
      TOP;
   }
}
