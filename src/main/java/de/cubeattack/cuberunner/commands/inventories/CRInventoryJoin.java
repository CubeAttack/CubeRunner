package de.cubeattack.cuberunner.commands.inventories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.cubeattack.cuberunner.CRPlayer;
import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.commands.InventoryItem;
import de.cubeattack.cuberunner.game.Arena;
import de.cubeattack.cuberunner.game.GameState;
import de.cubeattack.cuberunner.utils.ItemBannerManager;
import de.cubeattack.cuberunner.utils.ItemStackManager;
import de.cubeattack.cuberunner.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class CRInventoryJoin extends CRInventory {
   private int page;

   public CRInventoryJoin(CRPlayer crPlayer, int page) {
      super(crPlayer);
      Language local = crPlayer.getLanguage();
      this.title = local.get(Language.Messages.JOIN_GUI_TITLE);
      this.page = page;
      this.amountOfRows = 6;
      this.createInventory();
      this.fillInventory();
   }

   public void fillInventory() {
      List<String> arenas = new ArrayList();
      Iterator var3 = Arena.getArenas().iterator();

      while(var3.hasNext()) {
         Arena arena = (Arena)var3.next();
         arenas.add(arena.getName());
      }

      Collections.sort(arenas);
      Language local = this.crPlayer.getLanguage();
      InventoryItem icon = new InventoryItem(new ItemStackManager(Material.BOOKSHELF), 4);
      icon.getItem().setDisplayName(local.get(Language.Messages.KEYWORD_GUI_INSTRUCTIONS));
      String[] var7;
      int var6 = (var7 = local.get(Language.Messages.JOIN_GUI_INFO).split("\n")).length;

      int i;
      for(i = 0; i < var6; ++i) {
         String loreLine = var7[i];
         icon.getItem().addToLore(loreLine);
      }

      icon.addToInventory(this.inventory);
      icon = new InventoryItem(new ItemStackManager(Material.GRAY_STAINED_GLASS_PANE));
      icon.getItem().setData((short)10);
      icon.getItem().setDisplayName("" + ChatColor.RED);
      int slot = 0;

      while(slot < this.inventory.getSize()) {
         switch(slot) {
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
            icon.setPosition(slot);
            icon.addToInventory(this.inventory);
         default:
            ++slot;
         }
      }

      icon = new InventoryItem(new ItemStackManager(Material.INK_SAC));
      slot = 18;

      for(i = (this.page - 1) * 27; arenas.size() > i && slot < 45; ++i) {
         Arena arena = Arena.getArena((String)arenas.get(i));
         icon.getItem().clearLore();
         if (arena.getGameState() == GameState.UNREADY) {
            icon.getItem().setData((short)8);
            icon.getItem().setDisplayName(ChatColor.GOLD + (String)arenas.get(i));
            icon.getItem().addToLore(local.get(Language.Messages.KEYWORD_GAMESTATE_UNSET));
         } else if (arena.getGameState() != GameState.ACTIVE && arena.getGameState() != GameState.ENDING) {
            icon.getItem().setData((short)10);
            icon.getItem().setDisplayName(ChatColor.GOLD + (String)arenas.get(i));
            icon.getItem().addToLore(local.get(Language.Messages.KEYWORD_GAMESTATE_READY));
            icon.getItem().addToLore(ChatColor.YELLOW + local.get(Language.Messages.KEYWORD_SCOREBOARD_PLAYERS) + " : " + arena.getAmountOfPlayerInGame() + "/" + arena.getMaxPlayer());
         } else {
            icon.getItem().setData((short)12);
            icon.getItem().setDisplayName(ChatColor.GOLD + (String)arenas.get(i));
            icon.getItem().addToLore(local.get(Language.Messages.KEYWORD_GAMESTATE_ACTIVE));
         }

         icon.setPosition(slot++);
         icon.addToInventory(this.inventory);
      }

      if (arenas.size() - (this.page - 1) * 27 > 27) {
         icon = new InventoryItem(new ItemBannerManager(ItemBannerManager.CustomPattern.ARROW_RIGHT), 50);
         icon.getItem().setDisplayName(local.get(Language.Messages.KEYWORD_GUI_PAGE).replace("%number%", String.valueOf(this.page + 1)));
         icon.addToInventory(this.inventory);
      }

      icon = new InventoryItem(new ItemBannerManager(ItemBannerManager.CustomPattern.ARROW_ACTUAL), 49);
      icon.getItem().setDisplayName(local.get(Language.Messages.KEYWORD_GUI_PAGE).replace("%number%", String.valueOf(this.page)));
      icon.addToInventory(this.inventory);
      if (this.page > 1) {
         icon = new InventoryItem(new ItemBannerManager(ItemBannerManager.CustomPattern.ARROW_LEFT), 48);
         icon.getItem().setDisplayName(local.get(Language.Messages.KEYWORD_GUI_PAGE).replace("%number%", String.valueOf(this.page - 1)));
         icon.addToInventory(this.inventory);
      }

      this.openInventory();
   }

   public void update(ItemStack itemStack, InventoryAction action) {
      Language local = this.crPlayer.getLanguage();
      String itemName = itemStack.getItemMeta().getDisplayName();
      if (Utils.isEqualOnColorStrip(itemName, local.get(Language.Messages.KEYWORD_GUI_PAGE).replace("%number%", String.valueOf(this.page + 1)))) {
         new CRInventoryJoin(this.crPlayer, this.page + 1);
      } else if (Utils.isEqualOnColorStrip(itemName, local.get(Language.Messages.KEYWORD_GUI_PAGE).replace("%number%", String.valueOf(this.page - 1)))) {
         new CRInventoryJoin(this.crPlayer, this.page - 1);
      } else {
         Arena arena = Arena.getArena(ChatColor.stripColor(itemName));
         if (arena != null) {
            if (action == InventoryAction.PICKUP_HALF) {
               arena.displayInformation(this.crPlayer.getPlayer());
            } else {
               arena.addPlayer(this.crPlayer.getPlayer(), true);
            }

            this.crPlayer.getPlayer().closeInventory();
         }
      }
   }
}
