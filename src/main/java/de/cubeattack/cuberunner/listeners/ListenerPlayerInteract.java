package de.cubeattack.cuberunner.listeners;

import de.cubeattack.cuberunner.commands.signs.CRSign;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerPlayerInteract implements Listener {
   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
         if (event.getClickedBlock().getState() instanceof Sign) {
            Sign s = (Sign)event.getClickedBlock().getState();
            CRSign crSign = CRSign.getCrSign(s.getLocation());
            if (crSign != null) {
               event.setCancelled(true);
               crSign.onInteract(event.getPlayer());
            }
         }
      }
   }
}
