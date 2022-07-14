package de.cubeattack.cuberunner.listeners;

import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.game.Arena;
import de.cubeattack.cuberunner.commands.signs.CRSignJoin;
import de.cubeattack.cuberunner.commands.signs.CRSignPlay;
import de.cubeattack.cuberunner.commands.signs.CRSignQuit;
import de.cubeattack.cuberunner.commands.signs.CRSignStart;
import de.cubeattack.cuberunner.commands.signs.CRSignStats;
import de.cubeattack.cuberunner.commands.signs.CRSignTop;
import de.cubeattack.cuberunner.utils.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class ListenerSignUpdate implements Listener {
   @EventHandler
   public void onSignPlace(SignChangeEvent event) {
      Language local = CubeRunner.get().getLang(event.getPlayer());
      if (this.isPrefixInLine(event.getLine(0))) {
         if (!Permissions.hasPermission("cuberunner.admin.edit.sign", event.getPlayer(), false)) {
            this.setNoPermissionsSign(event, local);
            return;
         }

         Arena arena;
         if (event.getLine(1).equalsIgnoreCase("join")) {
            arena = Arena.getArena(event.getLine(2));
            if (arena != null) {
               new CRSignJoin(event, arena);
            } else {
               this.setNoValidSign(event, local);
            }
         } else if (event.getLine(1).equalsIgnoreCase("play")) {
            arena = Arena.getArena(event.getLine(2));
            if (arena != null) {
               if (arena.getWorld() == null) {
                  this.setNoValidSign(event, local);
               } else if (arena.getWorld() != event.getBlock().getWorld()) {
                  this.setNoValidSign(event, local);
               } else {
                  new CRSignPlay(event, arena);
               }
            } else {
               this.setNoValidSign(event, local);
            }
         } else if (event.getLine(1).equalsIgnoreCase("quit")) {
            new CRSignQuit(event);
         } else if (event.getLine(1).equalsIgnoreCase("top")) {
            arena = Arena.getArena(event.getLine(2));
            if (arena != null) {
               new CRSignTop(event, arena);
            } else {
               this.setNoValidSign(event, local);
            }
         } else if (event.getLine(1).equalsIgnoreCase("start")) {
            new CRSignStart(event);
         } else if (event.getLine(1).equalsIgnoreCase("stats")) {
            new CRSignStats(event);
         } else {
            this.setNoValidSign(event, local);
         }
      } else if (this.isPrefixInLine(event.getLine(1)) || this.isPrefixInLine(event.getLine(2)) || this.isPrefixInLine(event.getLine(3))) {
         if (Permissions.hasPermission("cuberunner.admin.edit.sign", event.getPlayer(), false)) {
            this.setNoValidSign(event, local);
         } else {
            this.setNoPermissionsSign(event, local);
         }
      }

   }

   private void setNoPermissionsSign(SignChangeEvent e, Language local) {
      e.setLine(0, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.SIGN_PERM_0)));
      e.setLine(1, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.SIGN_PERM_1)));
      e.setLine(2, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.SIGN_PERM_2)));
      e.setLine(3, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.SIGN_PERM_3)));
   }

   private void setNoValidSign(SignChangeEvent e, Language local) {
      e.setLine(0, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.PREFIX_LONG)));
      e.setLine(1, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.SIGN_VALID_1)));
      e.setLine(2, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.SIGN_VALID_2)));
      e.setLine(3, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.SIGN_VALID_3)));
   }

   private boolean isPrefixInLine(String line) {
      Language local = Language.getDefault();
      String stipedLine = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', line)).toLowerCase();
      return stipedLine.contains("[cr]") || stipedLine.contains(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.PREFIX_LONG).toLowerCase().trim()))) || stipedLine.contains(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.PREFIX_SHORT).toLowerCase().trim())));
   }
}
