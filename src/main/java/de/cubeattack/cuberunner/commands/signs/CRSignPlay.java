package de.cubeattack.cuberunner.commands.signs;

import java.util.UUID;

import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.commands.CRCommand;
import de.cubeattack.cuberunner.game.Arena;
import de.cubeattack.cuberunner.game.GameState;
import de.cubeattack.cuberunner.utils.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

public class CRSignPlay extends CRSignPlayers {
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$me$poutineqc$cuberunner$game$GameState;

   public CRSignPlay(SignChangeEvent event, Arena arena) {
      super(event.getBlock().getLocation(), CRSign.SignType.PLAY);
      this.arena = arena;
      Language local = Language.getDefault();
      event.setLine(0, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.PREFIX_LONG)));
      event.setLine(1, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.KEYWORD_SIGN_PLAY)));
      switch($SWITCH_TABLE$me$poutineqc$cuberunner$game$GameState()[arena.getGameState().ordinal()]) {
      case 1:
      case 2:
         event.setLine(3, ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.KEYWORD_SCOREBOARD_PLAYERS))) + " : " + arena.getAmountOfPlayerInGame() + "/" + arena.getMaxPlayer());
         break;
      case 3:
      case 4:
         event.setLine(3, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.KEYWORD_GAMESTATE_ACTIVE)));
         break;
      case 5:
         event.setLine(3, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.KEYWORD_GAMESTATE_UNSET)));
      }

      signs.add(this);
      updateSigns(arena);
   }

   public CRSignPlay(UUID uuid, Location location) {
      super(uuid, location, CRSign.SignType.PLAY);
      boolean delete = false;
      BlockState block = null;

      try {
         block = location.getBlock().getState();
         if (!(block instanceof Sign)) {
            delete = true;
         } else {
            Sign sign = (Sign)block;
            this.arena = Arena.getArena(sign.getLine(2));
            if (this.arena == null) {
               delete = true;
            } else if (this.arena.getWorld() != location.getWorld()) {
               delete = true;
            }
         }
      } catch (NullPointerException var6) {
         delete = true;
      }

      if (delete) {
         this.removeSign();
      } else {
         signs.add(this);
         updateSigns(this.arena);
      }
   }

   protected boolean updateSign(Language local, Sign sign) {
      sign.setLine(0, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.PREFIX_LONG)));
      sign.setLine(1, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.KEYWORD_SIGN_PLAY)));
      sign.update();
      if (this.arena == null) {
         return false;
      } else {
         this.updateDisplay(local, sign);
         return true;
      }
   }

   public void onInteract(Player player) {
      if (Permissions.hasPermission(CRCommand.JOIN.getPermission(), player, true)) {
         CRCommand.JOIN.execute(plugin, player, new String[]{"join", this.arena.getName()}, false);
      }

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
