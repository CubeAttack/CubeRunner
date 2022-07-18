package de.cubeattack.cuberunner.commands;

import de.cubeattack.cuberunner.utils.ItemBannerManager;
import de.cubeattack.cuberunner.utils.ItemHeadManager;
import de.cubeattack.cuberunner.utils.ItemStackManager;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryItem {
   private int position;
   private ItemStackManager item;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$org$bukkit$Material;

   public InventoryItem(ItemStackManager item, int position) {
      this.item = item;
      this.position = position;
   }

   public InventoryItem(ItemStackManager item) {
      this.item = item;
   }

   public InventoryItem(ItemStack itemStack) {
      switch(itemStack.getType().ordinal()) {
      case 361:
         this.item = new ItemHeadManager(itemStack);
         break;
      case 389:
         this.item = new ItemBannerManager(itemStack);
         break;
      default:
         this.item = new ItemStackManager(itemStack);
      }

   }

   public InventoryItem(Material material) {
      switch(material.ordinal()) {
      case 361:
         this.item = new ItemHeadManager();
         break;
      case 389:
         this.item = new ItemBannerManager();
         break;
      default:
         this.item = new ItemStackManager(material);
      }

   }

   public InventoryItem(Material material, int position) {
      this(material);
      this.position = position;
   }

   public ItemStackManager getItem() {
      return this.item;
   }

   public void setPosition(int position) {
      this.position = position;
   }

   public void addToInventory(Inventory inventory) {
      inventory.setItem(this.position, this.item.getItem());
   }
   
}
