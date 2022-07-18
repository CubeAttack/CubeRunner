package de.cubeattack.cuberunner.commands.inventories;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Map.Entry;

import de.cubeattack.cuberunner.commands.InventoryItem;
import de.cubeattack.cuberunner.AchievementManager;
import de.cubeattack.cuberunner.CRPlayer;
import de.cubeattack.cuberunner.CRStats;
import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.utils.ItemStackManager;
import de.cubeattack.cuberunner.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class CRInventoryChallenge extends CRInventory {
   public CRInventoryChallenge(CRPlayer crPlayer) {
      super(crPlayer);
      Language local = crPlayer.getLanguage();
      this.title = local.get(Language.Messages.STATS_CHALLENGES_TITLE);
      this.amountOfRows = 3;
      this.createInventory();

      try {
         this.fillInventory();
      } catch (CRPlayer.PlayerStatsException var4) {
         var4.printStackTrace();
      }

   }

   public void fillInventory() throws CRPlayer.PlayerStatsException {
      Language local = this.crPlayer.getLanguage();
      InventoryItem icon = new InventoryItem(new ItemStackManager(Material.GRAY_STAINED_GLASS_PANE));
      icon.getItem().setData((short)10);
      icon.getItem().setDisplayName("" + ChatColor.RED);
      int i = 0;

      while(i < this.inventory.getSize()) {
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
            icon.setPosition(i);
            icon.addToInventory(this.inventory);
         default:
            ++i;
         }
      }

      NumberFormat format2 = new DecimalFormat("#0.00");
      NumberFormat format3 = new DecimalFormat("#0.000");
      icon = new InventoryItem(new ItemStackManager(Material.PAPER), 4);
      icon.getItem().setDisplayName(ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.STATS_GUI_TITLE) + " : CubeRunner"));
      icon.getItem().addToLore("" + ChatColor.STRIKETHROUGH + ChatColor.YELLOW + "----------------------------");
      icon.getItem().addToLore(local.get(Language.Messages.STATS_INFO_AVERAGE_SCORE) + " : &e" + format2.format(this.crPlayer.getDouble(CRStats.AVERAGE_SCORE)));
      icon.getItem().addToLore(local.get(Language.Messages.STATS_INFO_DISTANCE_RAN) + " : &e" + format3.format(this.crPlayer.getDouble(CRStats.TOTAL_DISTANCE) / 1000.0D) + " " + ChatColor.GREEN + local.get(Language.Messages.KEYWORD_GENERAL_DISTANCE));
      icon.getItem().addToLore("" + ChatColor.STRIKETHROUGH + ChatColor.YELLOW + "----------------------------");
      icon.getItem().addToLore(local.get(Language.Messages.STATS_INFO_GAMES) + " : &e" + this.crPlayer.getInt(CRStats.GAMES_PLAYED));
      icon.getItem().addToLore(local.get(Language.Messages.STATS_INFO_TOTAL_SCORE) + " : &e" + this.crPlayer.getInt(CRStats.TOTAL_SCORE));
      icon.getItem().addToLore(local.get(Language.Messages.STATS_INFO_KILLS) + " : &e" + this.crPlayer.getInt(CRStats.KILLS));
      icon.getItem().addToLore(local.get(Language.Messages.STATS_INFO_MULTIPLAYER_WON) + " : &e" + this.crPlayer.getInt(CRStats.MULTIPLAYER_WON));
      icon.getItem().addToLore("" + ChatColor.STRIKETHROUGH + ChatColor.YELLOW + "----------------------------");
      icon.getItem().addToLore(ChatColor.LIGHT_PURPLE + Utils.strip(local.get(Language.Messages.STATS_INFO_TIME_PLAYED)) + ": " + this.getTimePLayed(local, this.crPlayer.getInt(CRStats.TIME_PLAYED)));
      if (CubeRunner.get().isEconomyEnabled()) {
         icon.getItem().addToLore(ChatColor.LIGHT_PURPLE + Utils.strip(local.get(Language.Messages.STATS_INFO_MONEY)) + ": &e" + format2.format(this.crPlayer.getDouble(CRStats.MONEY)) + ChatColor.GREEN + CubeRunner.get().getEconomy().currencyNamePlural());
      }

      icon.addToInventory(this.inventory);
      int location = 19;
      Iterator var7 = CubeRunner.get().getAchievementManager().getChallenges().entrySet().iterator();

      while(var7.hasNext()) {
         Entry<AchievementManager.Challenge, Double> challenge = (Entry)var7.next();
         if (location == 22) {
            ++location;
         }

         icon = new InventoryItem(new ItemStackManager(Material.INK_SAC));
         boolean done = this.crPlayer.hasChallenge(((AchievementManager.Challenge)challenge.getKey()).getCrStats());
         icon.getItem().setData((short)(done ? 10 : 8));
         icon.getItem().setDisplayName((done ? "" + ChatColor.GREEN : "" + ChatColor.RED) + Utils.strip(local.get(((AchievementManager.Challenge)challenge.getKey()).getMessage())));
         icon.getItem().addToLore(ChatColor.YELLOW + "----------------------------");
         icon.getItem().addToLore(ChatColor.AQUA + Utils.strip(local.get(Language.Messages.KEYWORD_STATS_PROGRESSION)) + ": " + (done ? ChatColor.GREEN + Utils.strip(local.get(Language.Messages.KEYWORD_STATS_COMPLETED)) : ChatColor.RED + Utils.strip(local.get(Language.Messages.KEYWORD_STATS_NOT_COMPLETED))));
         if (!done && CubeRunner.get().isEconomyEnabled() && CubeRunner.get().getConfiguration().achievementsRewards) {
            icon.getItem().addToLore(ChatColor.AQUA + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.KEYWORD_STATS_REWARD) + ": ")) + ChatColor.YELLOW + challenge.getValue() + CubeRunner.get().getEconomy().currencyNamePlural());
         }

         icon.setPosition(location++);
         icon.addToInventory(this.inventory);
      }

      icon = new InventoryItem(new ItemStackManager(Material.ARROW));
      icon.getItem().setDisplayName(local.get(Language.Messages.STATS_GUI_TITLE));
      icon.setPosition(8);
      icon.addToInventory(this.inventory);
      this.openInventory();
   }

   private String getTimePLayed(Language local, int timePlayed) {
      long hours = 0L;

      for(timePlayed /= 60000; timePlayed > 60; ++hours) {
         timePlayed -= 60;
      }

      return ChatColor.YELLOW + String.valueOf(hours) + ChatColor.GREEN + " " + Utils.strip(local.get(Language.Messages.KEYWORD_GENERAL_HOURS)) + ChatColor.YELLOW + " " + timePlayed + ChatColor.GREEN + " " + Utils.strip(local.get(Language.Messages.KEYWORD_GENERAL_MINUTES));
   }

   public void update(ItemStack itemStack, InventoryAction action) {
      Language local = this.crPlayer.getLanguage();
      if (Utils.isEqualOnColorStrip(itemStack.getItemMeta().getDisplayName(), local.get(Language.Messages.STATS_GUI_TITLE))) {
         new CRInventoryStats(this.crPlayer);
      }

   }
}
