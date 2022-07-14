package de.cubeattack.cuberunner.commands.inventories;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
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

public class CRInventoryStats extends CRInventory {
   public CRInventoryStats(CRPlayer crPlayer) {
      super(crPlayer);
      Language local = crPlayer.getLanguage();
      this.title = local.get(Language.Messages.STATS_GUI_TITLE);
      this.amountOfRows = 6;
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
         case 1:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 19:
         case 28:
         case 37:
         case 46:
            icon.setPosition(i);
            icon.addToInventory(this.inventory);
         default:
            ++i;
         }
      }

      NumberFormat format2 = new DecimalFormat("#0.00");
      NumberFormat format3 = new DecimalFormat("#0.000");
      icon = new InventoryItem(new ItemStackManager(Material.PAPER), 2);
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
      int position = 0;
      CRInventoryStats.Top10[] var10;
      int var9 = (var10 = CRInventoryStats.Top10.values()).length;

      for(int var8 = 0; var8 < var9; ++var8) {
         CRInventoryStats.Top10 top = var10[var8];
         icon = new InventoryItem(new ItemStackManager(Material.MAP));
         icon.getItem().setDisplayName(top.getName(local));
         icon.getItem().addToLore("" + ChatColor.STRIKETHROUGH + ChatColor.YELLOW + "----------------------------");
         List<CRPlayer> view = (CubeRunner.get().getPlayerData().getViews().get(top.getCrStats())).getList();

         for(i = 0; i < 10 && i < view.size(); ++i) {
            icon.getItem().addToLore(ChatColor.LIGHT_PURPLE + ((CRPlayer)view.get(i)).getName() + " : " + ChatColor.YELLOW + top.getAmount((CRPlayer)view.get(i)));
         }

         icon.setPosition(position);
         icon.addToInventory(this.inventory);
         position += 9;
      }

      int generalLocation = 20;
      HashMap<AchievementManager.Achievement, LinkedHashMap<Integer, Double>> entries = CubeRunner.get().getAchievementManager().getAchievements();
      AchievementManager.Achievement[] var22;
      int var21 = (var22 = AchievementManager.Achievement.values()).length;

      for(int var20 = 0; var20 < var21; ++var20) {
         AchievementManager.Achievement achievement = var22[var20];
         int location = generalLocation;
         Iterator var14 = ((LinkedHashMap)entries.get(achievement)).entrySet().iterator();

         while(var14.hasNext()) {
            Entry<Integer, Double> goal = (Entry)var14.next();
            boolean done = (Integer)goal.getKey() <= this.crPlayer.getInt(achievement.getCrStats());
            icon = new InventoryItem(new ItemStackManager(Material.WHITE_WOOL));
            icon.getItem().setData((short)(done ? 5 : 8));
            icon.getItem().setDisplayName((done ? ChatColor.GREEN : ChatColor.RED) + Utils.strip(local.get(achievement.getAchievementMessage()).replace("%amount%", String.valueOf(goal.getKey()))));
            icon.getItem().addToLore(ChatColor.YELLOW + "----------------------------");
            icon.getItem().addToLore(ChatColor.AQUA + Utils.strip(local.get(Language.Messages.KEYWORD_STATS_PROGRESSION) + ": ") + (done ? ChatColor.GREEN + Utils.strip(local.get(Language.Messages.KEYWORD_STATS_COMPLETED)) : ChatColor.YELLOW + String.valueOf(this.crPlayer.getInt(achievement.getCrStats())) + "/" + goal.getKey()));
            if (!done && CubeRunner.get().isEconomyEnabled() && CubeRunner.get().getConfiguration().achievementsRewards) {
               icon.getItem().addToLore(ChatColor.AQUA + Utils.strip(local.get(Language.Messages.KEYWORD_STATS_REWARD)) + ": " + ChatColor.YELLOW + goal.getValue() + CubeRunner.get().getEconomy().currencyNamePlural());
            }

            icon.setPosition(location++);
            icon.addToInventory(this.inventory);
         }

         generalLocation += 9;
      }

      icon = new InventoryItem(new ItemStackManager(Material.ARROW));
      icon.getItem().setDisplayName(local.get(Language.Messages.STATS_CHALLENGES_TITLE));
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
      if (Utils.isEqualOnColorStrip(itemStack.getItemMeta().getDisplayName(), local.get(Language.Messages.STATS_CHALLENGES_TITLE))) {
         new CRInventoryChallenge(this.crPlayer);
      }

   }

   private static enum Top10 {
      AVERAGE_SCORE(CRStats.AVERAGE_SCORE, Language.Messages.STATS_INFO_AVERAGE_SCORE) {
         public String getAmount(CRPlayer player) {
            try {
               return ChatColor.YELLOW + (new DecimalFormat("#0.00")).format(player.getDouble(this.getCrStats()));
            } catch (CRPlayer.PlayerStatsException var3) {
               var3.printStackTrace();
               return "";
            }
         }
      },
      TOTAL_DISTANCE(CRStats.TOTAL_DISTANCE, Language.Messages.STATS_INFO_DISTANCE_RAN) {
         public String getAmount(CRPlayer player) {
            try {
               return ChatColor.YELLOW + (new DecimalFormat("#0.000")).format(player.getDouble(this.getCrStats()) / 1000.0D) + " " + ChatColor.GREEN + player.getLanguage().get(Language.Messages.KEYWORD_GENERAL_DISTANCE);
            } catch (CRPlayer.PlayerStatsException var3) {
               var3.printStackTrace();
               return "";
            }
         }
      },
      GAMES(CRStats.GAMES_PLAYED, Language.Messages.STATS_INFO_GAMES) {
         public String getAmount(CRPlayer player) {
            try {
               return String.format("%1$d", player.getInt(this.getCrStats()));
            } catch (CRPlayer.PlayerStatsException var3) {
               var3.printStackTrace();
               return "";
            }
         }
      },
      TOTAL_SCORE(CRStats.TOTAL_SCORE, Language.Messages.STATS_INFO_TOTAL_SCORE) {
         public String getAmount(CRPlayer player) {
            try {
               return String.format("%1$d", player.getInt(this.getCrStats()));
            } catch (CRPlayer.PlayerStatsException var3) {
               var3.printStackTrace();
               return "";
            }
         }
      },
      KILLS(CRStats.KILLS, Language.Messages.STATS_INFO_KILLS) {
         public String getAmount(CRPlayer player) {
            try {
               return String.format("%1$d", player.getInt(this.getCrStats()));
            } catch (CRPlayer.PlayerStatsException var3) {
               var3.printStackTrace();
               return "";
            }
         }
      },
      MULTIPLAYER_WON(CRStats.MULTIPLAYER_WON, Language.Messages.STATS_INFO_MULTIPLAYER_WON) {
         public String getAmount(CRPlayer player) {
            try {
               return String.format("%1$d", player.getInt(this.getCrStats()));
            } catch (CRPlayer.PlayerStatsException var3) {
               var3.printStackTrace();
               return "";
            }
         }
      };

      private CRStats crStats;
      private Language.Messages message;

      private Top10(CRStats crStats, Language.Messages message) {
         this.message = message;
         this.crStats = crStats;
      }

      public abstract String getAmount(CRPlayer var1);

      public String getName(Language local) {
         return ChatColor.GOLD + local.get(Language.Messages.KEYWORD_STATS_TOP10) + " : " + local.get(this.message);
      }

      public CRStats getCrStats() {
         return this.crStats;
      }

      // $FF: synthetic method
      Top10(CRStats var3, Language.Messages var4, CRInventoryStats.Top10 var5) {
         this(var3, var4);
      }
   }
}
