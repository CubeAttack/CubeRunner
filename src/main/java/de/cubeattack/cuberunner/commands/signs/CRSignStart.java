package de.cubeattack.cuberunner.commands.signs;

import java.util.UUID;

import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.commands.CRCommand;
import de.cubeattack.cuberunner.utils.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

public class CRSignStart extends CRSign {
   public CRSignStart(SignChangeEvent event) {
      super(event.getBlock().getLocation(), CRSign.SignType.START);
      Language local = Language.getDefault();
      event.setLine(0, "");
      event.setLine(1, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.PREFIX_LONG)));
      event.setLine(2, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.KEYWORD_SIGN_START)));
      event.setLine(3, "");
      signs.add(this);
   }

   public CRSignStart(UUID uuid, Location location) {
      super(uuid, location, CRSign.SignType.START);
      boolean delete = false;
      BlockState block = null;

      try {
         block = location.getBlock().getState();
         if (!(block instanceof Sign)) {
            delete = true;
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
      sign.setLine(1, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.PREFIX_LONG)));
      sign.setLine(2, ChatColor.translateAlternateColorCodes('&', local.get(Language.Messages.KEYWORD_SIGN_START)));
      sign.update();
      return true;
   }

   public void onInteract(Player player) {
      if (Permissions.hasPermission(CRCommand.START.getPermission(), player, true)) {
         CRCommand.START.execute(plugin, player, new String[0]);
      }

   }
}
