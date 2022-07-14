package de.cubeattack.cuberunner.listeners;

import de.cubeattack.cuberunner.game.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class ListenerEntityGlide implements Listener {
   @EventHandler
   public void onEntityToggleGlide(EntityToggleGlideEvent event) {
      if (event.isGliding()) {
         if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            Arena arena = Arena.getArenaFromPlayer(player);
            if (arena != null) {
               event.setCancelled(true);
            }
         }
      }
   }
}
