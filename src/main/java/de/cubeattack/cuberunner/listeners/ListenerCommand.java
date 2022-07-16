package de.cubeattack.cuberunner.listeners;

import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.commands.CRCommand;
import de.cubeattack.cuberunner.utils.Permissions;
import de.cubeattack.cuberunner.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListenerCommand implements CommandExecutor {
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$me$poutineqc$cuberunner$commands$CRCommand;

   public boolean onCommand(CommandSender sender, Command cmd, String cmdValue, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("Only players can use CubeRunner's commands");
         return true;
      } else {
         CubeRunner plugin = CubeRunner.get();
         Player player = (Player)sender;
         Language local = CubeRunner.get().getLang(player);
         if (args.length == 0) {
            player.sendMessage(Utils.color("&8&m" + StringUtils.repeat(" ", 15) + "&r&8| &5CubeRunner " + "&8&m|" + StringUtils.repeat(" ", 40)));
            player.sendMessage(Utils.color(local.get(Language.Messages.DEVELOPPER).replace("%developper%", plugin.getDescription().getAuthors().toString())));
            player.sendMessage(Utils.color(local.get(Language.Messages.VERSION).replace("%version%", plugin.getDescription().getVersion())));
            player.sendMessage(Utils.color(local.get(Language.Messages.DESCRIPTION).replace("%command%", cmdValue)));
            player.sendMessage("\n");
            return true;
         } else {
            CRCommand crCommand = CRCommand.getCommand(args[0]);
            if (crCommand == null) {
               local.sendMsg(player, local.get(Language.Messages.ERROR_COMMAND).replace("%cmd%", cmdValue));
               return true;
            } else if (!Permissions.hasPermission(crCommand.getPermission(), player, true)) {
               return true;
            } else {
               switch($SWITCH_TABLE$me$poutineqc$cuberunner$commands$CRCommand()[crCommand.ordinal()]) {
               case 1:
               case 2:
               case 5:
                  crCommand.execute(plugin, player, args, cmdValue);
                  break;
               case 3:
               case 4:
               default:
                  crCommand.execute(plugin, player, args);
                  break;
               case 6:
                  crCommand.execute(plugin, player, args, "false");
               }

               return true;
            }
         }
      }
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$me$poutineqc$cuberunner$commands$CRCommand() {
      int[] var10000 = $SWITCH_TABLE$me$poutineqc$cuberunner$commands$CRCommand;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[CRCommand.values().length];

         try {
            var0[CRCommand.SETHEADS.ordinal()] = 18;
         } catch (NoSuchFieldError var18) {
         }

         try {
            var0[CRCommand.DELETE.ordinal()] = 10;
         } catch (NoSuchFieldError var17) {
         }

         try {
            var0[CRCommand.HELP.ordinal()] = 1;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[CRCommand.INFO.ordinal()] = 5;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[CRCommand.JOIN.ordinal()] = 6;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[CRCommand.LANGUAGE.ordinal()] = 2;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[CRCommand.LIST.ordinal()] = 4;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[CRCommand.NEW.ordinal()] = 9;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[CRCommand.QUIT.ordinal()] = 7;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[CRCommand.RELOAD.ordinal()] = 17;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[CRCommand.SETCOLOR.ordinal()] = 16;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[CRCommand.SETLOBBY.ordinal()] = 12;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[CRCommand.SETMAXPLAYER.ordinal()] = 15;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[CRCommand.SETMINPLAYER.ordinal()] = 14;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[CRCommand.SETSTARTPOINT.ordinal()] = 13;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[CRCommand.SETZONE.ordinal()] = 11;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[CRCommand.START.ordinal()] = 8;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[CRCommand.STATS.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$me$poutineqc$cuberunner$commands$CRCommand = var0;
         return var0;
      }
   }
}
