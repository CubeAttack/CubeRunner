package de.cubeattack.cuberunner.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackManager {
   protected Material material;
   protected int amount = 1;
   protected short durability = 0;
   protected String name;
   protected List<String> lore = new ArrayList();
   protected Map<Enchantment, Integer> enchantments = new HashMap();

   public ItemStackManager(Material material) {
      this.material = material;
   }

   public ItemStackManager(ItemStack itemStack) {
      this.material = itemStack.getType();
      this.amount = itemStack.getAmount();
      this.durability = itemStack.getDurability();
      ItemMeta meta = itemStack.getItemMeta();
      this.name = meta.hasDisplayName() ? meta.getDisplayName() : null;
      this.enchantments = (Map)(meta.hasEnchants() ? meta.getEnchants() : new HashMap());
      this.lore = meta.hasLore() ? meta.getLore() : null;
   }

   public ItemStack getItem() {
      ItemStack itemStack = new ItemStack(this.material, this.amount, this.durability);
      ItemMeta meta = itemStack.getItemMeta();
      meta.setDisplayName(this.name);
      meta.setLore(this.lore);
      Iterator var4 = this.enchantments.entrySet().iterator();

      while(var4.hasNext()) {
         Entry<Enchantment, Integer> enchantment = (Entry)var4.next();
         meta.addEnchant((Enchantment)enchantment.getKey(), (Integer)enchantment.getValue(), true);
      }

      itemStack.setItemMeta(meta);
      return itemStack;
   }

   public boolean isSame(ItemStack itemStack) {
      if (this.material != itemStack.getType()) {
         return false;
      } else if (this.durability != itemStack.getDurability()) {
         return false;
      } else {
         if (itemStack.getItemMeta().hasDisplayName()) {
            if (this.name == null) {
               return false;
            }

            if (!Utils.isEqualOnColorStrip(itemStack.getItemMeta().getDisplayName(), this.name)) {
               return false;
            }
         } else if (this.name != null) {
            return false;
         }

         if (itemStack.getItemMeta().hasEnchants()) {
            Iterator var3 = itemStack.getItemMeta().getEnchants().entrySet().iterator();

            while(var3.hasNext()) {
               Entry<Enchantment, Integer> enchantment = (Entry)var3.next();
               if (!this.hasEnchantement((Enchantment)enchantment.getKey(), (Integer)enchantment.getValue())) {
                  return false;
               }
            }
         } else if (this.enchantments.size() > 0) {
            return false;
         }

         return true;
      }
   }

   public void setData(short durability) {
      this.durability = durability;
   }

   public void setDisplayName(String displayName) {
      this.name = ChatColor.translateAlternateColorCodes('&', displayName);
   }

   public void addToLore(String loreLine) {
      this.lore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
   }

   public void setLore(List<String> lore) {
      this.lore = lore;
   }

   public void clearLore() {
      this.lore = new ArrayList();
   }

   public void addEnchantement(Enchantment enchantment, int level) {
      this.enchantments.put(enchantment, level);
   }

   public void setEnchantements(Map<Enchantment, Integer> enchantments) {
      this.enchantments = enchantments;
   }

   public void clearEnchantements() {
      this.enchantments.clear();
   }

   public int getMaxStackSize() {
      return this.material.getMaxStackSize();
   }

   public Material getMaterial() {
      return this.material;
   }

   public short getDurability() {
      return this.durability;
   }

   public boolean hasEnchantement(Enchantment enchantement) {
      Iterator var3 = this.enchantments.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<Enchantment, Integer> entry = (Entry)var3.next();
         if (entry.getKey() == enchantement) {
            return true;
         }
      }

      return false;
   }

   public boolean hasEnchantement(Enchantment enchantement, int value) {
      Iterator var4 = this.enchantments.entrySet().iterator();

      while(var4.hasNext()) {
         Entry<Enchantment, Integer> entry = (Entry)var4.next();
         if (entry.getKey() == enchantement) {
            if ((Integer)entry.getValue() == value) {
               return true;
            }

            return false;
         }
      }

      return false;
   }

   public String getDisplayName() {
      return this.name;
   }

   public List<String> getLore() {
      return this.lore;
   }
}
