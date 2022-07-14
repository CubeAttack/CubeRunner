package de.cubeattack.cuberunner.utils;

import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.Language;
import org.bukkit.entity.Player;

public class Permissions {
   public static final String createSign = "cuberunner.admin.edit.sign";
   public static final String advancedInfo = "cuberunner.admin.info";

   public static boolean hasPermission(String permission, Player player, boolean warning) {
      if (player.hasPermission(permission)) {
         return true;
      } else {
         if (warning) {
            Language local = CubeRunner.get().getCRPlayer(player).getLanguage();
            local.sendMsg(player, local.get(Language.Messages.ERROR_PERMISSION));
         }

         return false;
      }
   }
}
