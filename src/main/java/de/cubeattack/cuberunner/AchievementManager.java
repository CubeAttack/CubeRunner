package de.cubeattack.cuberunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import de.cubeattack.cuberunner.utils.MinecraftConfiguration;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AchievementManager {
   private HashMap<AchievementManager.Achievement, LinkedHashMap<Integer, Double>> achievements;
   private HashMap<AchievementManager.Challenge, Double> challenges;

   public AchievementManager(CubeRunner plugin) {
      MinecraftConfiguration config = new MinecraftConfiguration((String)null, "achievements", true);
      this.generateAchievements(config);
      this.generateChallenges(config);
   }

   private void generateAchievements(MinecraftConfiguration config) {
      this.achievements = new HashMap();
      AchievementManager.Achievement[] var5;
      int var4 = (var5 = AchievementManager.Achievement.values()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         AchievementManager.Achievement achievement = var5[var3];
         this.achievements.put(achievement, new LinkedHashMap());
         Iterator var7 = config.get().getStringList(achievement.configName).iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            AchievementManager.AchievementReader reader = new AchievementManager.AchievementReader(line, (AchievementManager.AchievementReader)null);
            if (!reader.ok) {
               Bukkit.getLogger().warning("Could not read line [" + line + "] from achievements.yml.. Ignoring");
            } else {
               ((LinkedHashMap)this.achievements.get(achievement)).put(reader.level, reader.reward);
            }
         }
      }

   }

   private void generateChallenges(MinecraftConfiguration config) {
      this.challenges = new HashMap();
      AchievementManager.Challenge[] var5;
      int var4 = (var5 = AchievementManager.Challenge.values()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         AchievementManager.Challenge challenge = var5[var3];
         this.challenges.put(challenge, config.get().getDouble(challenge.configName));
      }

   }

   public void complete(CRPlayer player, CRStats crStats, int value) {
      AchievementManager.Achievement achievement = (AchievementManager.Achievement)AchievementManager.Achievement.association.get(crStats);
      if (achievement != null) {
         if (((LinkedHashMap)this.achievements.get(achievement)).containsKey(value)) {
            this.congradulate(player, achievement, value);
            if (CubeRunner.get().isEconomyEnabled()) {
               double reward = (Double)((LinkedHashMap)this.achievements.get(achievement)).get(value);
               Economy economy = CubeRunner.get().getEconomy();
               economy.depositPlayer(player.getPlayer(), reward);
               Language local = player.getLanguage();
               local.sendMsg(player.getPlayer(), local.get(Language.Messages.ACHIEVEMENT_REWARD).replace("%amount%", String.valueOf(reward)).replace("%currency%", economy.currencyNamePlural()));

               try {
                  player.addDouble(CRStats.MONEY, reward);
               } catch (CRPlayer.PlayerStatsException var10) {
                  var10.printStackTrace();
               }
            }

         }
      }
   }

   private void congradulate(CRPlayer player, AchievementManager.Achievement achievement, int amount) {
      if (CubeRunner.get().getConfiguration().broadcastAchievement) {
         Iterator var5 = Bukkit.getOnlinePlayers().iterator();

         while(var5.hasNext()) {
            Player onlinePlayer = (Player)var5.next();
            Language local = CubeRunner.get().getLang(onlinePlayer);
            local.sendMsg(onlinePlayer, local.get(Language.Messages.ACHIEVEMENT_BROADCAST).replace("%player%", player.getPlayer().getDisplayName()).replace("%achievementName%", local.get(achievement.achievementMessage).replace("%amount%", String.valueOf(amount))));
         }
      }

   }

   public void complete(CRPlayer player, CRStats crStats) {
      AchievementManager.Challenge challenge = (AchievementManager.Challenge)AchievementManager.Challenge.association.get(crStats);
      if (challenge != null) {
         this.congradulate(player, challenge);
         if (CubeRunner.get().isEconomyEnabled()) {
            double reward = (Double)this.challenges.get(challenge);
            Economy economy = CubeRunner.get().getEconomy();
            economy.depositPlayer(player.getPlayer(), reward);
            Language local = player.getLanguage();
            local.sendMsg(player.getPlayer(), local.get(Language.Messages.ACHIEVEMENT_REWARD).replace("%amount%", String.valueOf(reward)).replace("%currency%", economy.currencyNamePlural()));

            try {
               player.addDouble(CRStats.MONEY, reward);
            } catch (CRPlayer.PlayerStatsException var9) {
               var9.printStackTrace();
            }
         }

      }
   }

   private void congradulate(CRPlayer player, AchievementManager.Challenge challenge) {
      if (CubeRunner.get().getConfiguration().broadcastAchievement) {
         Iterator var4 = Bukkit.getOnlinePlayers().iterator();

         while(var4.hasNext()) {
            Player onlinePlayer = (Player)var4.next();
            Language local = CubeRunner.get().getLang(onlinePlayer);
            local.sendMsg(onlinePlayer, local.get(Language.Messages.ACHIEVEMENT_BROADCAST).replace("%player%", player.getPlayer().getDisplayName()).replace("%achievementName%", local.get(challenge.message)));
         }
      }

   }

   public HashMap<AchievementManager.Achievement, LinkedHashMap<Integer, Double>> getAchievements() {
      return this.achievements;
   }

   public HashMap<AchievementManager.Challenge, Double> getChallenges() {
      return this.challenges;
   }

   public static enum Achievement {
      GAMES_PLAYED(CRStats.GAMES_PLAYED, "amountOfGamesPlayed", Language.Messages.ACHIEVEMENT_GAMES, Language.Messages.STATS_INFO_GAMES),
      TOTAL_SCORE(CRStats.TOTAL_SCORE, "totalScore", Language.Messages.ACHIEVEMENT_TOTAL_SCORE, Language.Messages.STATS_INFO_GAMES),
      KILLS(CRStats.KILLS, "amountPlayerKills", Language.Messages.ACHIEVEMENT_KILLS, Language.Messages.STATS_INFO_GAMES),
      MULTIPLAYER_WON(CRStats.MULTIPLAYER_WON, "multiplayerGamesWon", Language.Messages.ACHIEVEMENT_MULTIPLAYER_WON, Language.Messages.STATS_INFO_GAMES);

      private final CRStats stats;
      private final String configName;
      private final Language.Messages achievementMessage;
      private final Language.Messages statsMessage;
      private static HashMap<CRStats, AchievementManager.Achievement> association = new HashMap();

      static {
         AchievementManager.Achievement[] var3;
         int var2 = (var3 = values()).length;

         for(int var1 = 0; var1 < var2; ++var1) {
            AchievementManager.Achievement achievement = var3[var1];
            association.put(achievement.stats, achievement);
         }

      }

      private Achievement(CRStats stats, String configName, Language.Messages achievementMessage, Language.Messages statsMessage) {
         this.stats = stats;
         this.configName = configName;
         this.achievementMessage = achievementMessage;
         this.statsMessage = statsMessage;
      }

      public CRStats getCrStats() {
         return this.stats;
      }

      public Language.Messages getMessage() {
         return this.statsMessage;
      }

      public Language.Messages getAchievementMessage() {
         return this.achievementMessage;
      }
   }

   private class AchievementReader {
      private boolean ok;
      private int level;
      private double reward;

      private AchievementReader(String line) {
         this.ok = true;
         String[] lineArgs = line.split(";");

         try {
            this.level = Integer.parseInt(lineArgs[0]);
            this.reward = Double.parseDouble(lineArgs[1]);
         } catch (IndexOutOfBoundsException | NumberFormatException var5) {
            this.ok = false;
         }

      }

      // $FF: synthetic method
      AchievementReader(String var2, AchievementManager.AchievementReader var3) {
         this(var2);
      }
   }

   public static enum Challenge {
      SURVIVE_5_MINUTES(CRStats.SURVIVE_5_MINUTES, "rewardSurvive5Minutes", Language.Messages.ACHIEVEMENT_SURVIVE_5_MINUTES),
      FILL_THE_ARENA(CRStats.FILL_THE_ARENA, "rewardFillTheArenasFloor", Language.Messages.ACHIEVEMENT_FILL_THE_ARENA),
      REACH_HEIGHT_10(CRStats.REACH_HEIGHT_10, "rewardReachHeight10", Language.Messages.ACHIEVEMENT_REACH_HEIGHT_10),
      THE_RAGE_QUIT(CRStats.THE_RAGE_QUIT, "rewardTheRageQuit", Language.Messages.ACHIEVEMENT_RAGE_QUIT),
      THE_KILLER_BUNNY(CRStats.THE_KILLER_BUNNY, "rewardTheKillerBunny", Language.Messages.ACHIEVEMENT_KILLER_BUNNY),
      THE_ANSWER_TO_LIFE(CRStats.THE_ANSWER_TO_LIFE, "rewardTheAnswerToLife", Language.Messages.ACHIEVEMENT_ANSWER_TO_LIFE);

      private final CRStats stats;
      private final String configName;
      private final Language.Messages message;
      private static HashMap<CRStats, AchievementManager.Challenge> association = new HashMap();

      static {
         AchievementManager.Challenge[] var3;
         int var2 = (var3 = values()).length;

         for(int var1 = 0; var1 < var2; ++var1) {
            AchievementManager.Challenge challenge = var3[var1];
            association.put(challenge.stats, challenge);
         }

      }

      private Challenge(CRStats stats, String configName, Language.Messages message) {
         this.stats = stats;
         this.configName = configName;
         this.message = message;
      }

      public CRStats getCrStats() {
         return this.stats;
      }

      public Language.Messages getMessage() {
         return this.message;
      }
   }
}
