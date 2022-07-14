package de.cubeattack.cuberunner.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemHeadManager extends ItemStackManager {
   private String playerName;

   public ItemHeadManager(String playerName) {
      super(Material.PLAYER_HEAD);
      this.durability = 3;
      this.playerName = playerName;
   }

   public ItemHeadManager(ItemStack itemStack) {
      super(itemStack);
      SkullMeta meta = (SkullMeta)itemStack.getItemMeta();
      this.playerName = meta.hasOwner() ? meta.getOwner() : null;
   }

   public ItemHeadManager() {
      super(Material.PLAYER_HEAD);
      this.durability = 3;
   }

   public ItemStack getItem() {
      ItemStack itemStack = super.getItem();
      SkullMeta meta = (SkullMeta)itemStack.getItemMeta();
      if (this.playerName != null) {
         meta.setOwner(this.playerName);
      }

      itemStack.setItemMeta(meta);
      return itemStack;
   }

   public boolean isSame(ItemStack itemStack) {
      if (!super.isSame(itemStack)) {
         return false;
      } else if (this.durability != 3) {
         return true;
      } else {
         SkullMeta meta = (SkullMeta)itemStack.getItemMeta();
         if (meta.hasOwner()) {
            if (this.playerName == null) {
               return false;
            }

            if (!meta.getOwner().equalsIgnoreCase(this.playerName)) {
               return false;
            }
         } else if (this.playerName != null) {
            return false;
         }

         return true;
      }
   }

   public void setPlayerName(String playerName) {
      this.playerName = playerName;
   }

   public String getPlayerName() {
      return this.playerName;
   }
}
