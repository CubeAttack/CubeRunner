package de.cubeattack.cuberunner.listeners;

import java.util.UUID;

import de.cubeattack.cuberunner.CRPlayer;
import de.cubeattack.cuberunner.CRStats;
import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.game.Arena;
import de.cubeattack.cuberunner.game.GameState;
import de.cubeattack.cuberunner.game.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class ListenerPlayerDamage implements Listener {
   @EventHandler
   public void onPlayerDamageByEntity(EntityDamageByEntityEvent event) throws CRPlayer.PlayerStatsException {
      if (event.getEntity() instanceof Player) {
         Player player = (Player)event.getEntity();
         Arena arena = Arena.getArenaFromPlayer(player);
         if (arena != null) {
            if (event.getCause() != DamageCause.FALLING_BLOCK) {
               event.setCancelled(true);
            } else if (arena.getGameState() != GameState.ACTIVE) {
               event.setCancelled(true);
            } else {
               User user = arena.getUser(player);
               if (user.isEliminated()) {
                  event.setCancelled(true);
               } else {
                  event.setDamage(0.0D);
                  arena.eliminateUser(arena.getUser(player), Arena.LeavingReason.CRUSHED);
                  String dammagerUUID = event.getDamager().getCustomName();
                  if (!dammagerUUID.equalsIgnoreCase(player.getUniqueId().toString())) {
                     CubeRunner.get().getCRPlayer(Bukkit.getPlayer(UUID.fromString(dammagerUUID))).increment(CRStats.KILLS, true);
                  }
               }
            }
         }
      }
   }

   @EventHandler
   public void onPlayerDamage(EntityDamageEvent event) {
      if (event.getEntity() instanceof Player) {
         Player player = (Player)event.getEntity();
         Arena arena = Arena.getArenaFromPlayer(player);
         if (arena != null) {
            if (event.getCause() != DamageCause.FALLING_BLOCK) {
               event.setCancelled(true);
            } else {
               User user = arena.getUser(player);
               if (user.isEliminated()) {
                  event.setCancelled(true);
               }
            }
         }
      }
   }
}
