package de.cubeattack.cuberunner.commands.inventories;

import java.util.List;

import de.cubeattack.cuberunner.CRPlayer;
import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.commands.InventoryItem;
import de.cubeattack.cuberunner.game.Arena;
import de.cubeattack.cuberunner.game.GameState;
import de.cubeattack.cuberunner.utils.ItemStackManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class CRInventoryColor extends CRInventory {
   private Arena arena;

   public CRInventoryColor(CRPlayer crPlayer, Arena arena) {
      super(crPlayer);
      this.arena = arena;
      Language local = crPlayer.getLanguage();
      this.title = local.get(Language.Messages.EDIT_COLOR_GUI_TITLE);
      this.amountOfRows = 6;
      this.createInventory();
      this.fillInventory();
   }

   public void fillInventory() {
      Language local = this.crPlayer.getLanguage();
      InventoryItem icon = new InventoryItem(new ItemStackManager(Material.BOOKSHELF), 4);
      icon.getItem().setDisplayName(local.get(Language.Messages.KEYWORD_GUI_INSTRUCTIONS));
      String[] var6;
      int var5 = (var6 = local.get(Language.Messages.EDIT_COLOR_GUI_INFO).split("\n")).length;

      int i;
      for(i = 0; i < var5; ++i) {
         String loreLine = var6[i];
         icon.getItem().addToLore(loreLine);
      }

      icon.addToInventory(this.inventory);
      icon = new InventoryItem(new ItemStackManager(Material.GRAY_STAINED_GLASS_PANE));
      icon.getItem().setData((short)10);
      icon.getItem().setDisplayName("" + ChatColor.RED);
      i = 0;

      while( i < this.inventory.getSize()) {
         switch(i) {
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 27:
         case 36:
         case 45:
            icon.setPosition(i);
            icon.addToInventory(this.inventory);
         default:
            ++i;
         }
      }

      List<ItemStackManager> colorManager = this.arena.getColorManager().getAllBlocks();

      for(i = 0; i < 32; ++i) {
         icon = new InventoryItem((ItemStackManager)colorManager.get(i));
         icon.setPosition((int)(Math.floor((double)i / 8.0D) * 9.0D + 19.0D + (double)(i % 8)));
         icon.addToInventory(this.inventory);
      }

      this.openInventory();
   }

   public void update(ItemStack itemStack, InventoryAction action) {
      if (itemStack.getType().name().toLowerCase().endsWith("terracotta") || itemStack.getType().name().toLowerCase().endsWith("wool")) {
         if (this.arena.getGameState() != GameState.ACTIVE && this.arena.getGameState() != GameState.ENDING) {
            int valueOfItem = itemStack.getDurability();
            if (itemStack.getType().name().toLowerCase().contains("terracotta")) {
               valueOfItem += 16;
            }

            if (itemStack.getItemMeta().hasEnchants()) {
               this.arena.getColorManager().setColorIndice(this.arena.getColorManager().getColorIndice() - (long)((int)Math.pow(2.0D, (double)valueOfItem)));
            } else {
               this.arena.getColorManager().setColorIndice(this.arena.getColorManager().getColorIndice() + (long)((int)Math.pow(2.0D, (double)valueOfItem)));
            }

            this.arena.resetArena(itemStack);
            new CRInventoryColor(this.crPlayer, this.arena);
         } else {
            this.crPlayer.getPlayer().closeInventory();
            this.crPlayer.setCurrentInventory((CRInventory)null);
            this.crPlayer.getLanguage().sendMsg(this.crPlayer.getPlayer(), this.crPlayer.getLanguage().get(Language.Messages.EDIT_COLOR_ERROR));
         }
      }
   }
}
