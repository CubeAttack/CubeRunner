package de.cubeattack.cuberunner.game;

import java.util.Collection;
import java.util.Iterator;

import de.cubeattack.cuberunner.Configuration;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class OriginalPlayerStats {
   private Configuration config;
   private int level;
   private float experience;
   private GameMode gameMode;
   private double health;
   private int foodLevel;
   private float saturation;
   private Collection<PotionEffect> effects;
   private Location location;
   private boolean flying;
   private boolean allowFlight;
   private ItemStack[] inventoryItems;

   public OriginalPlayerStats(Configuration config, Player player) {
      this.config = config;
      this.location = player.getLocation();
   }

   public void ireturnStats(Player player) {
      Iterator var3 = player.getActivePotionEffects().iterator();

      while(var3.hasNext()) {
         PotionEffect effect = (PotionEffect)var3.next();
         player.removePotionEffect(effect.getType());
      }

      player.setAllowFlight(this.allowFlight);
      if (this.flying) {
         player.setAllowFlight(true);
         player.setFlying(this.flying);
      }

      player.setFallDistance(0.0F);
      player.setVelocity(new Vector());
      player.setLevel(this.level);
      player.setExp(this.experience);
      player.setGameMode(this.gameMode);
      player.setHealth(this.health);
      player.setFoodLevel(this.foodLevel);
      player.setSaturation(this.saturation);
      player.addPotionEffects(this.effects);
      if (this.config.saveAndClearInventory) {
         player.getInventory().setContents(this.inventoryItems);
         player.updateInventory();
      }


      player.teleport(this.location);

   }

   public void imaxStats(Player player) {
      player.setFallDistance(0.0F);
      player.setVelocity(new Vector());
      player.setFlying(false);
      player.setAllowFlight(false);
      player.setLevel(0);
      player.setExp(0.0F);
      player.setGameMode(GameMode.ADVENTURE);
      player.setHealth(player.getMaxHealth());
      player.setFoodLevel(20);
      player.setSaturation(20.0F);
      Iterator var3 = player.getActivePotionEffects().iterator();

      while(var3.hasNext()) {
         PotionEffect effect = (PotionEffect)var3.next();
         player.removePotionEffect(effect.getType());
      }

      if (this.config.saveAndClearInventory) {
         player.getInventory().clear();
         player.updateInventory();
      }

   }

   public void ifillOtherStats(Player player) {
      this.flying = player.isFlying();
      this.allowFlight = player.getAllowFlight();
      this.level = player.getLevel();
      this.experience = player.getExp();
      this.gameMode = player.getGameMode();
      this.health = player.getHealth();
      this.foodLevel = player.getFoodLevel();
      this.saturation = player.getSaturation();
      this.effects = player.getActivePotionEffects();
      this.inventoryItems = player.getInventory().getContents().clone();
   }
}
