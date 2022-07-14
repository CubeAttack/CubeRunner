package de.cubeattack.cuberunner.listeners;

import de.cubeattack.cuberunner.CRPlayer;
import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.commands.inventories.CRInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerInventoryClick implements Listener {
   @EventHandler
   public void onInventoryClick(InventoryClickEvent event) {
      if (event.getAction() != InventoryAction.NOTHING && event.getAction() != InventoryAction.UNKNOWN) {
         if (event.getWhoClicked() instanceof Player) {
            ItemStack item = event.getCurrentItem();
            CRPlayer player = CubeRunner.get().getCRPlayer((Player)event.getWhoClicked());
            CRInventory inv = player.getCurrentInventory();
            if (inv != null) {
               event.setCancelled(true);
               inv.update(item, event.getAction());
            }
         }
      }
   }
}
