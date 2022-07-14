package de.cubeattack.cuberunner;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ArenaData {
   private File arenaFile;
   private FileConfiguration arenaData;

   public ArenaData(CubeRunner plugin) {
      this.arenaFile = new File(plugin.getDataFolder(), "arenaData.yml");
      if (!this.arenaFile.exists()) {
         try {
            this.arenaFile.createNewFile();
         } catch (IOException var3) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create arenaData.ylm.");
         }
      }

      this.loadArenaData();
   }

   public void loadArenaData() {
      this.arenaData = YamlConfiguration.loadConfiguration(this.arenaFile);
   }

   public FileConfiguration getData() {
      return this.arenaData;
   }

   public void saveArenaData() {
      try {
         this.arenaData.save(this.arenaFile);
      } catch (IOException var2) {
         Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save arenaData.yml!");
      }

   }

   public void reloadArenaData() {
      this.arenaData = YamlConfiguration.loadConfiguration(this.arenaFile);
   }
}
