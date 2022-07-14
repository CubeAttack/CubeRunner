package de.cubeattack.cuberunner.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import de.cubeattack.cuberunner.CubeRunner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MinecraftConfiguration {
   File folder;
   File file;
   FileConfiguration config;

   public MinecraftConfiguration(String folderPath, String fileName, boolean buildIn) {
      this.getFile(folderPath, fileName, buildIn);
      if (this.file.exists()) {
         this.config = YamlConfiguration.loadConfiguration(this.file);
      }
   }

   public void getFile(String folderPath, String fileName, boolean buildIn) {
      this.folder = CubeRunner.get().getDataFolder();
      if (folderPath != null) {
         this.getFolder(folderPath);
      }

      this.file = new File(this.folder, fileName + ".yml");
      if (!this.file.exists()) {
         if (!buildIn) {
            try {
               this.file.createNewFile();
               return;
            } catch (IOException var5) {
               Bukkit.getServer().getLogger().severe("Could not create playerData.ylm.");
               Bukkit.getServer().getLogger().severe("Review your minecraft server's permissions to write and edit files in it's plugin directory");
               Bukkit.getServer().getLogger().severe("Disabling CubeRunner...");
               CubeRunner.get().getPluginLoader().disablePlugin(CubeRunner.get());
            }
         } else {
            folderPath = folderPath == null ? "" : folderPath + "/";
            InputStream local = CubeRunner.get().getResource(folderPath + fileName + ".yml");
            if (local != null) {
               CubeRunner.get().saveResource(folderPath + fileName + ".yml", false);
            } else {
               CubeRunner.get().getLogger().severe("Could not find " + fileName + ".yml");
               CubeRunner.get().getLogger().severe("Contact the developper as fast as possible, this should not happend.");
               Bukkit.getServer().getLogger().severe("Disabling CubeRunner...");
               CubeRunner.get().getPluginLoader().disablePlugin(CubeRunner.get());
            }
         }
      }

   }

   private void getFolder(String folderPath) {
      String[] folderNames = folderPath.split("/");
      String[] var6 = folderNames;
      int var5 = folderNames.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         String names = var6[var4];
         this.folder = new File(this.folder, names);
         if (!this.folder.exists()) {
            this.folder.mkdir();
         }
      }

   }

   public boolean hasFile() {
      return this.file != null;
   }

   public void save() {
      try {
         this.config.save(this.file);
      } catch (IOException var2) {
         Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save " + (this.file == null ? "the file." : this.file.getName() + ".yml."));
      }

   }

   public File getFile() {
      return this.file;
   }

   public FileConfiguration get() {
      return this.config;
   }
}
