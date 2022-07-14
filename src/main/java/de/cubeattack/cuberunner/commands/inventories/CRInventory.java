package de.cubeattack.cuberunner.commands.inventories;

import de.cubeattack.cuberunner.CRPlayer;
import de.cubeattack.cuberunner.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class CRInventory {
   protected Inventory inventory;
   protected CRPlayer crPlayer;
   protected int amountOfRows;
   protected String title;

   public CRInventory(CRPlayer crPlayer) {
      this.crPlayer = crPlayer;
   }

   public abstract void fillInventory() throws CRPlayer.PlayerStatsException;

   public abstract void update(ItemStack var1, InventoryAction var2);

   public static boolean areEqualOnColorStrip(String itemA, String itemB) {
      return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', itemA)).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', itemB)));
   }

   protected void createInventory() {
      this.inventory = Bukkit.createInventory(this.crPlayer.getPlayer(), this.amountOfRows * 9, this.getFullTitle());
   }

   protected String getFullTitle() {
      String title = ChatColor.translateAlternateColorCodes('&', this.crPlayer.getLanguage().get(Language.Messages.PREFIX_SHORT) + " " + this.title);
      return title;
   }

   protected void openInventory() {
      this.crPlayer.getPlayer().openInventory(this.inventory);
      this.crPlayer.setCurrentInventory(this);
   }

   protected void closeInventory() {
      this.crPlayer.getPlayer().closeInventory();
      this.crPlayer.setCurrentInventory((CRInventory)null);
   }
}
