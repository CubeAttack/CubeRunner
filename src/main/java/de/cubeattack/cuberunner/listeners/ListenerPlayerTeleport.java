package de.cubeattack.cuberunner.listeners;

import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.game.Arena;
import de.cubeattack.cuberunner.game.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class ListenerPlayerTeleport implements Listener {
   @EventHandler
   public void onPlayerTeleport(PlayerTeleportEvent event) {
      Player player = event.getPlayer();
      Arena arena = Arena.getArenaFromPlayer(player);
      if (arena != null) {
         if (!this.teleportNearPoint(event.getTo(), arena.getLobby()) && !this.teleportNearPoint(event.getTo(), arena.getStartPoint()) && !arena.isInsideArena(event.getTo())) {
            User user = arena.getUser(player);
            if (!user.hasAllowTeleport()) {
               Language local = CubeRunner.get().getLang(player);
               local.sendMsg(player, local.get(Language.Messages.ERROR_TELEPORT));
               event.setCancelled(true);
            }
         }
      }
   }

   private boolean teleportNearPoint(Location to, Location point) {
      if (to.getWorld() != point.getWorld()) {
         return false;
      } else {
         return !(to.distance(point) > 1.0D);
      }
   }
}
