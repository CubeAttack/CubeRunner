package de.cubeattack.cuberunner.game;

import java.util.ArrayList;
import java.util.List;

import de.cubeattack.cuberunner.ArenaData;
import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.MySQL;
import de.cubeattack.cuberunner.utils.ItemStackManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class ColorManager {
   private long colorIndice;
   private List<ItemStackManager> allBlocks;
   private List<ItemStackManager> onlyChoosenBlocks;
   private MySQL mysql;
   private Arena arena;
   private ArenaData arenaData;

   public ColorManager(Long colorIndice, CubeRunner plugin, Arena arena) {
      this.colorIndice = colorIndice;
      this.mysql = plugin.getMySQL();
      this.arenaData = plugin.getArenaData();
      this.arena = arena;
      this.updateLists();
   }

   public void setColorIndice(long colorIndice) {
      this.colorIndice = colorIndice;
      this.updateLists();
      if (this.mysql.hasConnection()) {
         this.mysql.update("UPDATE " + CubeRunner.get().getConfiguration().tablePrefix + "ARENAS SET colorIndice=" + colorIndice + " WHERE name='" + this.arena.getName() + "';");
      } else {
         this.arenaData.getData().set("arenas." + this.arena.getName() + ".colorIndice", colorIndice);
         this.arenaData.saveArenaData();
      }

   }

   public void updateLists() {
      this.allBlocks = new ArrayList();
      this.onlyChoosenBlocks = new ArrayList();
      long tempColorIndice = this.colorIndice;

      for(int i = 31; i >= 0; --i) {
         ItemStackManager icon;
         if (i >= 16) {
            icon = new ItemStackManager(Material.LEGACY_STAINED_CLAY);
         } else {
            icon = new ItemStackManager(Material.LEGACY_WOOL);
         }

         icon.setData((short)(i % 16));
         int value = (int)Math.pow(2.0D, (double)i);
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

   }

   public ItemStackManager getRandomAvailableBlock() {
      return (ItemStackManager)this.onlyChoosenBlocks.get((int)Math.floor(Math.random() * (double)this.onlyChoosenBlocks.size()));
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
