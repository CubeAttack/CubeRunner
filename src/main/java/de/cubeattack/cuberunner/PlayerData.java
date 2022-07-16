package de.cubeattack.cuberunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import de.cubeattack.cuberunner.utils.MinecraftConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerData implements Listener {
   private MinecraftConfiguration playerData;
   private HashMap<UUID, CRPlayer> players = new HashMap();
   private HashMap<CRStats, PlayerData.View> views;

   public PlayerData(CubeRunner plugin) {
      this.loadViews();
   }

   public void loadViews() {
      this.views = new HashMap();

      try {
         this.views.put(CRStats.AVERAGE_SCORE, new PlayerData.View(CRStats.AVERAGE_SCORE));
         this.views.put(CRStats.GAMES_PLAYED, new PlayerData.View(CRStats.GAMES_PLAYED));
         this.views.put(CRStats.MULTIPLAYER_WON, new PlayerData.View(CRStats.MULTIPLAYER_WON));
         this.views.put(CRStats.TOTAL_SCORE, new PlayerData.View(CRStats.TOTAL_SCORE));
         this.views.put(CRStats.TOTAL_DISTANCE, new PlayerData.View(CRStats.TOTAL_DISTANCE));
         this.views.put(CRStats.KILLS, new PlayerData.View(CRStats.KILLS));
      } catch (CRPlayer.PlayerStatsException var2) {
         var2.printStackTrace();
      }

   }

   public void loadPlayers(CubeRunner plugin) {
      this.loadExistingPlayers(plugin);
      Iterator var3 = Bukkit.getOnlinePlayers().iterator();

      while(var3.hasNext()) {
         Player player = (Player)var3.next();
         this.loadPlayer(player);
      }

   }

   private void loadExistingPlayers(CubeRunner plugin) {
      if (plugin.getMySQL().hasConnection()) {
         ResultSet query = plugin.getMySQL().queryAll();

         try {
            while(query.next()) {
               CRPlayer player = new CRPlayer(query);
               this.players.put(player.getUUID(), player);
            }
         } catch (SQLException var5) {
            var5.printStackTrace();
         }
      }

   }

   private void loadPlayer(Player player) {
      UUID uuid = player.getUniqueId();
      CRPlayer crPlayer = (CRPlayer)this.players.get(uuid);
      if (crPlayer == null) {
         this.players.put(uuid, new CRPlayer(uuid, player.getName()));
      } else if (!crPlayer.getName().equalsIgnoreCase(player.getName())) {
         crPlayer.setName(player.getName());
      }

   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      this.loadPlayer(event.getPlayer());
   }

   public CRPlayer getCRPlayer(Player player) {
      return (CRPlayer)this.players.get(player.getUniqueId());
   }

   public MinecraftConfiguration getConfig() {
      return this.playerData;
   }

   public void updateAll(CRPlayer crPlayer) throws CRPlayer.PlayerStatsException {
      Iterator var3 = this.views.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<CRStats, PlayerData.View> set = (Entry)var3.next();
         ((PlayerData.View)set.getValue()).update(crPlayer);
      }

   }

   public HashMap<CRStats, PlayerData.View> getViews() {
      return this.views;
   }

   public CRPlayer getCRPlayer(UUID uuid) {
      return (CRPlayer)this.players.get(uuid);
   }

   public void clear() {
      this.players = new HashMap();
   }

   public class View {
      CRStats stats;
      List<CRPlayer> list = new ArrayList(10);

      public View(CRStats stats) throws CRPlayer.PlayerStatsException {
         this.stats = stats;
         Iterator var4 = PlayerData.this.players.entrySet().iterator();

         while(var4.hasNext()) {
            Entry<UUID, CRPlayer> player = (Entry)var4.next();
            this.update((CRPlayer)player.getValue());
         }

      }

      public void update(CRPlayer crPlayer) throws CRPlayer.PlayerStatsException {
         if (this.list.contains(crPlayer)) {
            this.list.remove(crPlayer);
         }

         boolean over = false;
         int originalSize = this.list.size();
         this.list.add(crPlayer);

         for(int i = originalSize; i > 0 && !over; --i) {
            Object value = ((CRPlayer)this.list.get(i)).get(this.stats);
            CRPlayer temp;
            if (value instanceof Integer) {
               if ((Integer)value > ((CRPlayer)this.list.get(i - 1)).getInt(this.stats)) {
                  temp = (CRPlayer)this.list.get(i);
                  this.list.set(i, (CRPlayer)this.list.get(i - 1));
                  this.list.set(i - 1, temp);
               } else {
                  over = true;
               }
            } else if (value instanceof Double) {
               if ((Double)value > ((CRPlayer)this.list.get(i - 1)).getDouble(this.stats)) {
                  temp = (CRPlayer)this.list.get(i);
                  this.list.set(i, (CRPlayer)this.list.get(i - 1));
                  this.list.set(i - 1, temp);
               } else {
                  over = true;
               }
            }
         }

         if (originalSize == 10) {
            this.list.remove(originalSize);
         }

      }

      public List<CRPlayer> getList() {
         return this.list;
      }
   }
}
