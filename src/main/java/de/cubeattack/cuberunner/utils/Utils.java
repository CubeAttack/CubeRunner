package de.cubeattack.cuberunner.utils;

import de.cubeattack.cuberunner.CubeRunner;
import org.bukkit.ChatColor;

public class Utils {

   public static String color(String string) {
      return ChatColor.translateAlternateColorCodes('&', string);
   }

   public static boolean isEqualOnColorStrip(String l2, String l1) {
      return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', l1)).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', l2)));
   }

   public static String strip(String string) {
      return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string));
   }
}
