package de.cubeattack.cuberunner.listeners;

import de.cubeattack.cuberunner.commands.signs.CRSign;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ListenerSignBreak implements Listener {
   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onBlockBreak(BlockBreakEvent event) {
      if (event.getBlock().getState() instanceof Sign) {
         if (!event.isCancelled()) {
            Sign sign = (Sign)event.getBlock().getState();
            CRSign dacSign = CRSign.getCrSign(sign.getLocation());
            if (dacSign != null) {
               dacSign.removeSign();
            }

         }
      }
   }
}
