package de.cubeattack.cuberunner.commands.signs;

import java.util.UUID;

import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.game.GameState;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;

public abstract class CRSignPlayers extends CRSignDisplay {
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$me$poutineqc$cuberunner$game$GameState;

   public CRSignPlayers(Location location, CRSign.SignType type) {
      super(location, type);
   }

   public CRSignPlayers(UUID uuid, Location location, CRSign.SignType type) {
      super(uuid, location, type);
   }

   protected void updateDisplay(Language local, Sign sign) {
      switch($SWITCH_TABLE$me$poutineqc$cuberunner$game$GameState()[this.arena.getGameState().ordinal()]) {
      case 1:
      case 2:
         sign.setLine(3, ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.KEYWORD_SCOREBOARD_PLAYERS))) + " : " + this.arena.getAmountOfPlayerInGame() + "/" + this.arena.getMaxPlayer());
         break;
      case 3:
      case 4:
         sign.setLine(3, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.KEYWORD_GAMESTATE_ACTIVE)));
         break;
      case 5:
         sign.setLine(3, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.KEYWORD_GAMESTATE_UNSET)));
      }

      sign.update();
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$me$poutineqc$cuberunner$game$GameState() {
      int[] var10000 = $SWITCH_TABLE$me$poutineqc$cuberunner$game$GameState;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[GameState.values().length];

         try {
            var0[GameState.ACTIVE.ordinal()] = 3;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[GameState.ENDING.ordinal()] = 4;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[GameState.READY.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[GameState.STARTUP.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[GameState.UNREADY.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$me$poutineqc$cuberunner$game$GameState = var0;
         return var0;
      }
   }
}
