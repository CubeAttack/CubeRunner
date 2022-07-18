package de.cubeattack.cuberunner.game;

import java.util.*;

import com.sk89q.worldedit.world.block.BlockType;
import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.MySQL;
import de.cubeattack.cuberunner.utils.ItemStackManager;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.enchantments.Enchantment;


public class ColorManager {
   private long colorIndice;
   private List<ItemStackManager> allBlocks;
   private List<ItemStackManager> onlyChoosenBlocks;
   private MySQL mysql;
   private Arena arena;

   public ColorManager(Long colorIndice, CubeRunner plugin, Arena arena) {
      this.colorIndice = colorIndice;
      this.mysql = plugin.getMySQL();
      this.arena = arena;
      this.updateLists();
   }

   public void setColorIndice(long colorIndice) {
      this.colorIndice = colorIndice;
      this.updateLists();
      if (this.mysql.hasConnection()) {
         this.mysql.update("UPDATE " + CubeRunner.get().getConfiguration().tablePrefix + "ARENAS SET colorIndice=" + colorIndice + " WHERE name='" + this.arena.getName() + "';");
      }
   }

   private Object getRandomObject(Collection from) {
      Random rnd = new Random();
      int i = rnd.nextInt(from.size());
      return from.toArray()[i];
   }

   public void updateLists() {
      this.allBlocks = new ArrayList();
      this.onlyChoosenBlocks = new ArrayList();
      long tempColorIndice = this.colorIndice;

      Tag<Material> wool = Tag.WOOL;
      Tag<Material> terracotta = Tag.TERRACOTTA;
      ItemStackManager icon;
      int i = 31;

      for (Material m : terracotta.getValues()) {
         icon = new ItemStackManager(m);

         int value = (int)Math.pow(2.0D, i);
         if ((long)value <= tempColorIndice) {
            icon.addEnchantement(Enchantment.DURABILITY, 1);
            tempColorIndice -= (long)value;
            this.onlyChoosenBlocks.add(0, icon);
         }

         this.allBlocks.add(0, icon);
         i--;
      }

      for (Material m : wool.getValues()) {
         icon = new ItemStackManager(m);

         int value = (int)Math.pow(2.0D, i);
         if ((long)value <= tempColorIndice) {
            icon.addEnchantement(Enchantment.DURABILITY, 1);
            tempColorIndice -= (long)value;
            this.onlyChoosenBlocks.add(0, icon);
         }

         this.allBlocks.add(0, icon);
         i--;
      }

      if (this.onlyChoosenBlocks.size() == 0) {
         this.onlyChoosenBlocks = this.allBlocks;
      }

      /*for(int i = 31; i >= 0; --i) {
         ItemStackManager icon;
         if (i >= 16) {
            icon = new ItemStackManager()
           // icon = new ItemStackManager(Material.LEGACY_STAINED_CLAY);
         } else {
            //icon = new ItemStackManager(Material.LEGACY_WOOL);
         }


         icon.setData((short)(i % 16));
         int value = (int)Math.pow(2.0D, i);
         if ((long)value <= tempColorIndice) {
            icon.addEnchantement(Enchantment.DURABILITY, 1);
            tempColorIndice -= (long)value;
            this.onlyChoosenBlocks.add(0, icon);
         }

         this.allBlocks.add(0, icon);
      }

      if (this.onlyChoosenBlocks.size() == 0) {
         this.onlyChoosenBlocks = this.allBlocks;
      }
      #
       */

   }


   public ItemStackManager getRandomAvailableBlock() {
      return this.onlyChoosenBlocks.get((int)Math.floor(Math.random() * (double)this.onlyChoosenBlocks.size()));
   }

   public List<ItemStackManager> getAllBlocks() {
      return this.allBlocks;
   }

   public List<ItemStackManager> getOnlyChoosenBlocks() {
      return this.onlyChoosenBlocks;
   }

   public long getColorIndice() {
      return this.colorIndice;
   }
}
