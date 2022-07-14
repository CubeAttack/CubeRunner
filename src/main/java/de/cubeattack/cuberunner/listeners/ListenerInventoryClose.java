package de.cubeattack.cuberunner.listeners;

import de.cubeattack.cuberunner.CRPlayer;
import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.commands.inventories.CRInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ListenerInventoryClose implements Listener {
   @EventHandler
   public void onInventoryClose(InventoryCloseEvent event) {
      if (event.getPlayer() instanceof Player) {
         CRPlayer player = CubeRunner.get().getCRPlayer((Player)event.getPlayer());
         player.setCurrentInventory((CRInventory)null);
      }
   }
}
