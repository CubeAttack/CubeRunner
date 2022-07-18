package de.cubeattack.cuberunner.commands.signs;

import java.util.UUID;

import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.game.Arena;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

public class CRSignTop extends CRSignDisplay {
   public CRSignTop(SignChangeEvent event, Arena arena) {
      super(event.getBlock().getLocation(), CRSign.SignType.TOP);
      this.arena = arena;
      Language local = Language.getDefault();
      event.setLine(0, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.PREFIX_LONG)));
      event.setLine(1, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.KEYWORD_SIGN_TOP)));
      //event.setLine(3, arena.getHighestPlayer());
      signs.add(this);
   }

   public CRSignTop(UUID uuid, Location location) {
      super(uuid, location, CRSign.SignType.TOP);
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
            }
         }
      } catch (NullPointerException var6) {
         delete = true;
      }

      if (delete) {
         this.removeSign();
      } else {
         signs.add(this);
      }
   }

   protected boolean updateSign(Language local, Sign sign) {
      sign.setLine(0, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.PREFIX_LONG)));
      sign.setLine(1, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.KEYWORD_SIGN_TOP)));
      sign.update();
      if (this.arena == null) {
         return false;
      } else {
         this.updateDisplay(local, sign);
         return true;
      }
   }

   protected void updateDisplay(Language local, Sign sign) {
     // sign.setLine(3, this.arena.getHighestPlayer());
      sign.update();
   }

   public void onInteract(Player player) {
      Language local = CubeRunner.get().getLang(player);
      local.sendMsg(player, local.get(Language.Messages.KEYWORD_SIGN_TOP) + ChatColor.WHITE + " : " + this.arena.getHighestPlayerScore() + " " + local.get(Language.Messages.KEYWORD_GENERAL_BY));
   }
}
