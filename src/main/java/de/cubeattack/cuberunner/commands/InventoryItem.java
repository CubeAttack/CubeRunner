package de.cubeattack.cuberunner.commands;

import de.cubeattack.cuberunner.utils.ItemBannerManager;
import de.cubeattack.cuberunner.utils.ItemHeadManager;
import de.cubeattack.cuberunner.utils.ItemStackManager;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryItem {
   private int position;
   private ItemStackManager item;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$org$bukkit$Material;

   public InventoryItem(ItemStackManager item, int position) {
      this.item = item;
      this.position = position;
   }

   public InventoryItem(ItemStackManager item) {
      this.item = item;
   }

   public InventoryItem(ItemStack itemStack) {
      switch($SWITCH_TABLE$org$bukkit$Material()[itemStack.getType().ordinal()]) {
      case 361:
         this.item = new ItemHeadManager(itemStack);
         break;
      case 389:
         this.item = new ItemBannerManager(itemStack);
         break;
      default:
         this.item = new ItemStackManager(itemStack);
      }

   }

   public InventoryItem(Material material) {
      switch($SWITCH_TABLE$org$bukkit$Material()[material.ordinal()]) {
      case 361:
         this.item = new ItemHeadManager();
         break;
      case 389:
         this.item = new ItemBannerManager();
         break;
      default:
         this.item = new ItemStackManager(material);
      }

   }

   public InventoryItem(Material material, int position) {
      this(material);
      this.position = position;
   }

   public ItemStackManager getItem() {
      return this.item;
   }

   public void setPosition(int position) {
      this.position = position;
   }

   public void addToInventory(Inventory inventory) {
      inventory.setItem(this.position, this.item.getItem());
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$org$bukkit$Material() {
      int[] var10000 = $SWITCH_TABLE$org$bukkit$Material;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[Material.values().length];

         try {
            var0[Material.LEGACY_ACACIA_DOOR.ordinal()] = 197;
         } catch (NoSuchFieldError var424) {
         }

         try {
            var0[Material.LEGACY_ACACIA_DOOR_ITEM.ordinal()] = 394;
         } catch (NoSuchFieldError var423) {
         }

         try {
            var0[Material.LEGACY_ACACIA_FENCE.ordinal()] = 193;
         } catch (NoSuchFieldError var422) {
         }

         try {
            var0[Material.LEGACY_ACACIA_FENCE_GATE.ordinal()] = 188;
         } catch (NoSuchFieldError var421) {
         }

         try {
            var0[Material.LEGACY_ACACIA_STAIRS.ordinal()] = 164;
         } catch (NoSuchFieldError var420) {
         }

         try {
            var0[Material.LEGACY_ACTIVATOR_RAIL.ordinal()] = 158;
         } catch (NoSuchFieldError var419) {
         }

         try {
            var0[Material.LEGACY_AIR.ordinal()] = 1;
         } catch (NoSuchFieldError var418) {
         }

         try {
            var0[Material.LEGACY_ANVIL.ordinal()] = 146;
         } catch (NoSuchFieldError var417) {
         }

         try {
            var0[Material.LEGACY_APPLE.ordinal()] = 224;
         } catch (NoSuchFieldError var416) {
         }

         try {
            var0[Material.LEGACY_ARMOR_STAND.ordinal()] = 380;
         } catch (NoSuchFieldError var415) {
         }

         try {
            var0[Material.LEGACY_ARROW.ordinal()] = 226;
         } catch (NoSuchFieldError var414) {
         }

         try {
            var0[Material.LEGACY_BAKED_POTATO.ordinal()] = 357;
         } catch (NoSuchFieldError var413) {
         }

         try {
            var0[Material.LEGACY_BANNER.ordinal()] = 389;
         } catch (NoSuchFieldError var412) {
         }

         try {
            var0[Material.LEGACY_BARRIER.ordinal()] = 167;
         } catch (NoSuchFieldError var411) {
         }

         try {
            var0[Material.LEGACY_BEACON.ordinal()] = 139;
         } catch (NoSuchFieldError var410) {
         }

         try {
            var0[Material.LEGACY_BED.ordinal()] = 319;
         } catch (NoSuchFieldError var409) {
         }

         try {
            var0[Material.LEGACY_BEDROCK.ordinal()] = 8;
         } catch (NoSuchFieldError var408) {
         }

         try {
            var0[Material.LEGACY_BED_BLOCK.ordinal()] = 27;
         } catch (NoSuchFieldError var407) {
         }

         try {
            var0[Material.LEGACY_BEETROOT.ordinal()] = 398;
         } catch (NoSuchFieldError var406) {
         }

         try {
            var0[Material.LEGACY_BEETROOT_BLOCK.ordinal()] = 208;
         } catch (NoSuchFieldError var405) {
         }

         try {
            var0[Material.LEGACY_BEETROOT_SEEDS.ordinal()] = 399;
         } catch (NoSuchFieldError var404) {
         }

         try {
            var0[Material.LEGACY_BEETROOT_SOUP.ordinal()] = 400;
         } catch (NoSuchFieldError var403) {
         }

         try {
            var0[Material.LEGACY_BIRCH_DOOR.ordinal()] = 195;
         } catch (NoSuchFieldError var402) {
         }

         try {
            var0[Material.LEGACY_BIRCH_DOOR_ITEM.ordinal()] = 392;
         } catch (NoSuchFieldError var401) {
         }

         try {
            var0[Material.LEGACY_BIRCH_FENCE.ordinal()] = 190;
         } catch (NoSuchFieldError var400) {
         }

         try {
            var0[Material.LEGACY_BIRCH_FENCE_GATE.ordinal()] = 185;
         } catch (NoSuchFieldError var399) {
         }

         try {
            var0[Material.LEGACY_BIRCH_WOOD_STAIRS.ordinal()] = 136;
         } catch (NoSuchFieldError var398) {
         }

         try {
            var0[Material.LEGACY_BLAZE_POWDER.ordinal()] = 341;
         } catch (NoSuchFieldError var397) {
         }

         try {
            var0[Material.LEGACY_BLAZE_ROD.ordinal()] = 333;
         } catch (NoSuchFieldError var396) {
         }

         try {
            var0[Material.LEGACY_BOAT.ordinal()] = 297;
         } catch (NoSuchFieldError var395) {
         }

         try {
            var0[Material.LEGACY_BOAT_ACACIA.ordinal()] = 411;
         } catch (NoSuchFieldError var394) {
         }

         try {
            var0[Material.LEGACY_BOAT_BIRCH.ordinal()] = 409;
         } catch (NoSuchFieldError var393) {
         }

         try {
            var0[Material.LEGACY_BOAT_DARK_OAK.ordinal()] = 412;
         } catch (NoSuchFieldError var392) {
         }

         try {
            var0[Material.LEGACY_BOAT_JUNGLE.ordinal()] = 410;
         } catch (NoSuchFieldError var391) {
         }

         try {
            var0[Material.LEGACY_BOAT_SPRUCE.ordinal()] = 408;
         } catch (NoSuchFieldError var390) {
         }

         try {
            var0[Material.LEGACY_BONE.ordinal()] = 316;
         } catch (NoSuchFieldError var389) {
         }

         try {
            var0[Material.LEGACY_BONE_BLOCK.ordinal()] = 217;
         } catch (NoSuchFieldError var388) {
         }

         try {
            var0[Material.LEGACY_BOOK.ordinal()] = 304;
         } catch (NoSuchFieldError var387) {
         }

         try {
            var0[Material.LEGACY_BOOKSHELF.ordinal()] = 48;
         } catch (NoSuchFieldError var386) {
         }

         try {
            var0[Material.LEGACY_BOOK_AND_QUILL.ordinal()] = 350;
         } catch (NoSuchFieldError var385) {
         }

         try {
            var0[Material.LEGACY_BOW.ordinal()] = 225;
         } catch (NoSuchFieldError var384) {
         }

         try {
            var0[Material.LEGACY_BOWL.ordinal()] = 245;
         } catch (NoSuchFieldError var383) {
         }

         try {
            var0[Material.LEGACY_BREAD.ordinal()] = 261;
         } catch (NoSuchFieldError var382) {
         }

         try {
            var0[Material.LEGACY_BREWING_STAND.ordinal()] = 118;
         } catch (NoSuchFieldError var381) {
         }

         try {
            var0[Material.LEGACY_BREWING_STAND_ITEM.ordinal()] = 343;
         } catch (NoSuchFieldError var380) {
         }

         try {
            var0[Material.LEGACY_BRICK.ordinal()] = 46;
         } catch (NoSuchFieldError var379) {
         }

         try {
            var0[Material.LEGACY_BRICK_STAIRS.ordinal()] = 109;
         } catch (NoSuchFieldError var378) {
         }

         try {
            var0[Material.LEGACY_BROWN_MUSHROOM.ordinal()] = 40;
         } catch (NoSuchFieldError var377) {
         }

         try {
            var0[Material.LEGACY_BUCKET.ordinal()] = 289;
         } catch (NoSuchFieldError var376) {
         }

         try {
            var0[Material.LEGACY_BURNING_FURNACE.ordinal()] = 63;
         } catch (NoSuchFieldError var375) {
         }

         try {
            var0[Material.LEGACY_CACTUS.ordinal()] = 82;
         } catch (NoSuchFieldError var374) {
         }

         try {
            var0[Material.LEGACY_CAKE.ordinal()] = 318;
         } catch (NoSuchFieldError var373) {
         }

         try {
            var0[Material.LEGACY_CAKE_BLOCK.ordinal()] = 93;
         } catch (NoSuchFieldError var372) {
         }

         try {
            var0[Material.LEGACY_CARPET.ordinal()] = 172;
         } catch (NoSuchFieldError var371) {
         }

         try {
            var0[Material.LEGACY_CARROT.ordinal()] = 142;
         } catch (NoSuchFieldError var370) {
         }

         try {
            var0[Material.LEGACY_CARROT_ITEM.ordinal()] = 355;
         } catch (NoSuchFieldError var369) {
         }

         try {
            var0[Material.LEGACY_CARROT_STICK.ordinal()] = 362;
         } catch (NoSuchFieldError var368) {
         }

         try {
            var0[Material.LEGACY_CAULDRON.ordinal()] = 119;
         } catch (NoSuchFieldError var367) {
         }

         try {
            var0[Material.LEGACY_CAULDRON_ITEM.ordinal()] = 344;
         } catch (NoSuchFieldError var366) {
         }

         try {
            var0[Material.LEGACY_CHAINMAIL_BOOTS.ordinal()] = 269;
         } catch (NoSuchFieldError var365) {
         }

         try {
            var0[Material.LEGACY_CHAINMAIL_CHESTPLATE.ordinal()] = 267;
         } catch (NoSuchFieldError var364) {
         }

         try {
            var0[Material.LEGACY_CHAINMAIL_HELMET.ordinal()] = 266;
         } catch (NoSuchFieldError var363) {
         }

         try {
            var0[Material.LEGACY_CHAINMAIL_LEGGINGS.ordinal()] = 268;
         } catch (NoSuchFieldError var362) {
         }

         try {
            var0[Material.LEGACY_CHEST.ordinal()] = 55;
         } catch (NoSuchFieldError var361) {
         }

         try {
            var0[Material.LEGACY_CHORUS_FLOWER.ordinal()] = 201;
         } catch (NoSuchFieldError var360) {
         }

         try {
            var0[Material.LEGACY_CHORUS_FRUIT.ordinal()] = 396;
         } catch (NoSuchFieldError var359) {
         }

         try {
            var0[Material.LEGACY_CHORUS_FRUIT_POPPED.ordinal()] = 397;
         } catch (NoSuchFieldError var358) {
         }

         try {
            var0[Material.LEGACY_CHORUS_PLANT.ordinal()] = 200;
         } catch (NoSuchFieldError var357) {
         }

         try {
            var0[Material.LEGACY_CLAY.ordinal()] = 83;
         } catch (NoSuchFieldError var356) {
         }

         try {
            var0[Material.LEGACY_CLAY_BALL.ordinal()] = 301;
         } catch (NoSuchFieldError var355) {
         }

         try {
            var0[Material.LEGACY_CLAY_BRICK.ordinal()] = 300;
         } catch (NoSuchFieldError var354) {
         }

         try {
            var0[Material.LEGACY_COAL.ordinal()] = 227;
         } catch (NoSuchFieldError var353) {
         }

         try {
            var0[Material.LEGACY_COAL_BLOCK.ordinal()] = 174;
         } catch (NoSuchFieldError var352) {
         }

         try {
            var0[Material.LEGACY_COAL_ORE.ordinal()] = 17;
         } catch (NoSuchFieldError var351) {
         }

         try {
            var0[Material.LEGACY_COBBLESTONE.ordinal()] = 5;
         } catch (NoSuchFieldError var350) {
         }

         try {
            var0[Material.LEGACY_COBBLESTONE_STAIRS.ordinal()] = 68;
         } catch (NoSuchFieldError var349) {
         }

         try {
            var0[Material.LEGACY_COBBLE_WALL.ordinal()] = 140;
         } catch (NoSuchFieldError var348) {
         }

         try {
            var0[Material.LEGACY_COCOA.ordinal()] = 128;
         } catch (NoSuchFieldError var347) {
         }

         try {
            var0[Material.LEGACY_COMMAND.ordinal()] = 138;
         } catch (NoSuchFieldError var346) {
         }

         try {
            var0[Material.LEGACY_COMMAND_CHAIN.ordinal()] = 212;
         } catch (NoSuchFieldError var345) {
         }

         try {
            var0[Material.LEGACY_COMMAND_MINECART.ordinal()] = 386;
         } catch (NoSuchFieldError var344) {
         }

         try {
            var0[Material.LEGACY_COMMAND_REPEATING.ordinal()] = 211;
         } catch (NoSuchFieldError var343) {
         }

         try {
            var0[Material.LEGACY_COMPASS.ordinal()] = 309;
         } catch (NoSuchFieldError var342) {
         }

         try {
            var0[Material.LEGACY_COOKED_BEEF.ordinal()] = 328;
         } catch (NoSuchFieldError var341) {
         }

         try {
            var0[Material.LEGACY_COOKED_CHICKEN.ordinal()] = 330;
         } catch (NoSuchFieldError var340) {
         }

         try {
            var0[Material.LEGACY_COOKED_FISH.ordinal()] = 314;
         } catch (NoSuchFieldError var339) {
         }

         try {
            var0[Material.LEGACY_COOKED_MUTTON.ordinal()] = 388;
         } catch (NoSuchFieldError var338) {
         }

         try {
            var0[Material.LEGACY_COOKED_RABBIT.ordinal()] = 376;
         } catch (NoSuchFieldError var337) {
         }

         try {
            var0[Material.LEGACY_COOKIE.ordinal()] = 321;
         } catch (NoSuchFieldError var336) {
         }

         try {
            var0[Material.LEGACY_CROPS.ordinal()] = 60;
         } catch (NoSuchFieldError var335) {
         }

         try {
            var0[Material.LEGACY_DARK_OAK_DOOR.ordinal()] = 198;
         } catch (NoSuchFieldError var334) {
         }

         try {
            var0[Material.LEGACY_DARK_OAK_DOOR_ITEM.ordinal()] = 395;
         } catch (NoSuchFieldError var333) {
         }

         try {
            var0[Material.LEGACY_DARK_OAK_FENCE.ordinal()] = 192;
         } catch (NoSuchFieldError var332) {
         }

         try {
            var0[Material.LEGACY_DARK_OAK_FENCE_GATE.ordinal()] = 187;
         } catch (NoSuchFieldError var331) {
         }

         try {
            var0[Material.LEGACY_DARK_OAK_STAIRS.ordinal()] = 165;
         } catch (NoSuchFieldError var330) {
         }

         try {
            var0[Material.LEGACY_DAYLIGHT_DETECTOR.ordinal()] = 152;
         } catch (NoSuchFieldError var329) {
         }

         try {
            var0[Material.LEGACY_DAYLIGHT_DETECTOR_INVERTED.ordinal()] = 179;
         } catch (NoSuchFieldError var328) {
         }

         try {
            var0[Material.LEGACY_DEAD_BUSH.ordinal()] = 33;
         } catch (NoSuchFieldError var327) {
         }

         try {
            var0[Material.LEGACY_DETECTOR_RAIL.ordinal()] = 29;
         } catch (NoSuchFieldError var326) {
         }

         try {
            var0[Material.LEGACY_DIAMOND.ordinal()] = 228;
         } catch (NoSuchFieldError var325) {
         }

         try {
            var0[Material.LEGACY_DIAMOND_AXE.ordinal()] = 243;
         } catch (NoSuchFieldError var324) {
         }

         try {
            var0[Material.LEGACY_DIAMOND_BARDING.ordinal()] = 383;
         } catch (NoSuchFieldError var323) {
         }

         try {
            var0[Material.LEGACY_DIAMOND_BLOCK.ordinal()] = 58;
         } catch (NoSuchFieldError var322) {
         }

         try {
            var0[Material.LEGACY_DIAMOND_BOOTS.ordinal()] = 277;
         } catch (NoSuchFieldError var321) {
         }

         try {
            var0[Material.LEGACY_DIAMOND_CHESTPLATE.ordinal()] = 275;
         } catch (NoSuchFieldError var320) {
         }

         try {
            var0[Material.LEGACY_DIAMOND_HELMET.ordinal()] = 274;
         } catch (NoSuchFieldError var319) {
         }

         try {
            var0[Material.LEGACY_DIAMOND_HOE.ordinal()] = 257;
         } catch (NoSuchFieldError var318) {
         }

         try {
            var0[Material.LEGACY_DIAMOND_LEGGINGS.ordinal()] = 276;
         } catch (NoSuchFieldError var317) {
         }

         try {
            var0[Material.LEGACY_DIAMOND_ORE.ordinal()] = 57;
         } catch (NoSuchFieldError var316) {
         }

         try {
            var0[Material.LEGACY_DIAMOND_PICKAXE.ordinal()] = 242;
         } catch (NoSuchFieldError var315) {
         }

         try {
            var0[Material.LEGACY_DIAMOND_SPADE.ordinal()] = 241;
         } catch (NoSuchFieldError var314) {
         }

         try {
            var0[Material.LEGACY_DIAMOND_SWORD.ordinal()] = 240;
         } catch (NoSuchFieldError var313) {
         }

         try {
            var0[Material.LEGACY_DIODE.ordinal()] = 320;
         } catch (NoSuchFieldError var312) {
         }

         try {
            var0[Material.LEGACY_DIODE_BLOCK_OFF.ordinal()] = 94;
         } catch (NoSuchFieldError var311) {
         }

         try {
            var0[Material.LEGACY_DIODE_BLOCK_ON.ordinal()] = 95;
         } catch (NoSuchFieldError var310) {
         }

         try {
            var0[Material.LEGACY_DIRT.ordinal()] = 4;
         } catch (NoSuchFieldError var309) {
         }

         try {
            var0[Material.LEGACY_DISPENSER.ordinal()] = 24;
         } catch (NoSuchFieldError var308) {
         }

         try {
            var0[Material.LEGACY_DOUBLE_PLANT.ordinal()] = 176;
         } catch (NoSuchFieldError var307) {
         }

         try {
            var0[Material.LEGACY_DOUBLE_STEP.ordinal()] = 44;
         } catch (NoSuchFieldError var306) {
         }

         try {
            var0[Material.LEGACY_DOUBLE_STONE_SLAB2.ordinal()] = 182;
         } catch (NoSuchFieldError var305) {
         }

         try {
            var0[Material.LEGACY_DRAGONS_BREATH.ordinal()] = 401;
         } catch (NoSuchFieldError var304) {
         }

         try {
            var0[Material.LEGACY_DRAGON_EGG.ordinal()] = 123;
         } catch (NoSuchFieldError var303) {
         }

         try {
            var0[Material.LEGACY_DROPPER.ordinal()] = 159;
         } catch (NoSuchFieldError var302) {
         }

         try {
            var0[Material.LEGACY_EGG.ordinal()] = 308;
         } catch (NoSuchFieldError var301) {
         }

         try {
            var0[Material.LEGACY_ELYTRA.ordinal()] = 407;
         } catch (NoSuchFieldError var300) {
         }

         try {
            var0[Material.LEGACY_EMERALD.ordinal()] = 352;
         } catch (NoSuchFieldError var299) {
         }

         try {
            var0[Material.LEGACY_EMERALD_BLOCK.ordinal()] = 134;
         } catch (NoSuchFieldError var298) {
         }

         try {
            var0[Material.LEGACY_EMERALD_ORE.ordinal()] = 130;
         } catch (NoSuchFieldError var297) {
         }

         try {
            var0[Material.LEGACY_EMPTY_MAP.ordinal()] = 359;
         } catch (NoSuchFieldError var296) {
         }

         try {
            var0[Material.LEGACY_ENCHANTED_BOOK.ordinal()] = 367;
         } catch (NoSuchFieldError var295) {
         }

         try {
            var0[Material.LEGACY_ENCHANTMENT_TABLE.ordinal()] = 117;
         } catch (NoSuchFieldError var294) {
         }

         try {
            var0[Material.LEGACY_ENDER_CHEST.ordinal()] = 131;
         } catch (NoSuchFieldError var293) {
         }

         try {
            var0[Material.LEGACY_ENDER_PEARL.ordinal()] = 332;
         } catch (NoSuchFieldError var292) {
         }

         try {
            var0[Material.LEGACY_ENDER_PORTAL.ordinal()] = 120;
         } catch (NoSuchFieldError var291) {
         }

         try {
            var0[Material.LEGACY_ENDER_PORTAL_FRAME.ordinal()] = 121;
         } catch (NoSuchFieldError var290) {
         }

         try {
            var0[Material.LEGACY_ENDER_STONE.ordinal()] = 122;
         } catch (NoSuchFieldError var289) {
         }

         try {
            var0[Material.LEGACY_END_BRICKS.ordinal()] = 207;
         } catch (NoSuchFieldError var288) {
         }

         try {
            var0[Material.LEGACY_END_CRYSTAL.ordinal()] = 390;
         } catch (NoSuchFieldError var287) {
         }

         try {
            var0[Material.LEGACY_END_GATEWAY.ordinal()] = 210;
         } catch (NoSuchFieldError var286) {
         }

         try {
            var0[Material.LEGACY_END_ROD.ordinal()] = 199;
         } catch (NoSuchFieldError var285) {
         }

         try {
            var0[Material.LEGACY_EXPLOSIVE_MINECART.ordinal()] = 371;
         } catch (NoSuchFieldError var284) {
         }

         try {
            var0[Material.LEGACY_EXP_BOTTLE.ordinal()] = 348;
         } catch (NoSuchFieldError var283) {
         }

         try {
            var0[Material.LEGACY_EYE_OF_ENDER.ordinal()] = 345;
         } catch (NoSuchFieldError var282) {
         }

         try {
            var0[Material.LEGACY_FEATHER.ordinal()] = 252;
         } catch (NoSuchFieldError var281) {
         }

         try {
            var0[Material.LEGACY_FENCE.ordinal()] = 86;
         } catch (NoSuchFieldError var280) {
         }

         try {
            var0[Material.LEGACY_FENCE_GATE.ordinal()] = 108;
         } catch (NoSuchFieldError var279) {
         }

         try {
            var0[Material.LEGACY_FERMENTED_SPIDER_EYE.ordinal()] = 340;
         } catch (NoSuchFieldError var278) {
         }

         try {
            var0[Material.LEGACY_FIRE.ordinal()] = 52;
         } catch (NoSuchFieldError var277) {
         }

         try {
            var0[Material.LEGACY_FIREBALL.ordinal()] = 349;
         } catch (NoSuchFieldError var276) {
         }

         try {
            var0[Material.LEGACY_FIREWORK.ordinal()] = 365;
         } catch (NoSuchFieldError var275) {
         }

         try {
            var0[Material.LEGACY_FIREWORK_CHARGE.ordinal()] = 366;
         } catch (NoSuchFieldError var274) {
         }

         try {
            var0[Material.LEGACY_FISHING_ROD.ordinal()] = 310;
         } catch (NoSuchFieldError var273) {
         }

         try {
            var0[Material.LEGACY_FLINT.ordinal()] = 282;
         } catch (NoSuchFieldError var272) {
         }

         try {
            var0[Material.LEGACY_FLINT_AND_STEEL.ordinal()] = 223;
         } catch (NoSuchFieldError var271) {
         }

         try {
            var0[Material.LEGACY_FLOWER_POT.ordinal()] = 141;
         } catch (NoSuchFieldError var270) {
         }

         try {
            var0[Material.LEGACY_FLOWER_POT_ITEM.ordinal()] = 354;
         } catch (NoSuchFieldError var269) {
         }

         try {
            var0[Material.LEGACY_FROSTED_ICE.ordinal()] = 213;
         } catch (NoSuchFieldError var268) {
         }

         try {
            var0[Material.LEGACY_FURNACE.ordinal()] = 62;
         } catch (NoSuchFieldError var267) {
         }

         try {
            var0[Material.LEGACY_GHAST_TEAR.ordinal()] = 334;
         } catch (NoSuchFieldError var266) {
         }

         try {
            var0[Material.LEGACY_GLASS.ordinal()] = 21;
         } catch (NoSuchFieldError var265) {
         }

         try {
            var0[Material.LEGACY_GLASS_BOTTLE.ordinal()] = 338;
         } catch (NoSuchFieldError var264) {
         }

         try {
            var0[Material.LEGACY_GLOWING_REDSTONE_ORE.ordinal()] = 75;
         } catch (NoSuchFieldError var263) {
         }

         try {
            var0[Material.LEGACY_GLOWSTONE.ordinal()] = 90;
         } catch (NoSuchFieldError var262) {
         }

         try {
            var0[Material.LEGACY_GLOWSTONE_DUST.ordinal()] = 312;
         } catch (NoSuchFieldError var261) {
         }

         try {
            var0[Material.LEGACY_GOLDEN_APPLE.ordinal()] = 286;
         } catch (NoSuchFieldError var260) {
         }

         try {
            var0[Material.LEGACY_GOLDEN_CARROT.ordinal()] = 360;
         } catch (NoSuchFieldError var259) {
         }

         try {
            var0[Material.LEGACY_GOLD_AXE.ordinal()] = 250;
         } catch (NoSuchFieldError var258) {
         }

         try {
            var0[Material.LEGACY_GOLD_BARDING.ordinal()] = 382;
         } catch (NoSuchFieldError var257) {
         }

         try {
            var0[Material.LEGACY_GOLD_BLOCK.ordinal()] = 42;
         } catch (NoSuchFieldError var256) {
         }

         try {
            var0[Material.LEGACY_GOLD_BOOTS.ordinal()] = 281;
         } catch (NoSuchFieldError var255) {
         }

         try {
            var0[Material.LEGACY_GOLD_CHESTPLATE.ordinal()] = 279;
         } catch (NoSuchFieldError var254) {
         }

         try {
            var0[Material.LEGACY_GOLD_HELMET.ordinal()] = 278;
         } catch (NoSuchFieldError var253) {
         }

         try {
            var0[Material.LEGACY_GOLD_HOE.ordinal()] = 258;
         } catch (NoSuchFieldError var252) {
         }

         try {
            var0[Material.LEGACY_GOLD_INGOT.ordinal()] = 230;
         } catch (NoSuchFieldError var251) {
         }

         try {
            var0[Material.LEGACY_GOLD_LEGGINGS.ordinal()] = 280;
         } catch (NoSuchFieldError var250) {
         }

         try {
            var0[Material.LEGACY_GOLD_NUGGET.ordinal()] = 335;
         } catch (NoSuchFieldError var249) {
         }

         try {
            var0[Material.LEGACY_GOLD_ORE.ordinal()] = 15;
         } catch (NoSuchFieldError var248) {
         }

         try {
            var0[Material.LEGACY_GOLD_PICKAXE.ordinal()] = 249;
         } catch (NoSuchFieldError var247) {
         }

         try {
            var0[Material.LEGACY_GOLD_PLATE.ordinal()] = 148;
         } catch (NoSuchFieldError var246) {
         }

         try {
            var0[Material.LEGACY_GOLD_RECORD.ordinal()] = 413;
         } catch (NoSuchFieldError var245) {
         }

         try {
            var0[Material.LEGACY_GOLD_SPADE.ordinal()] = 248;
         } catch (NoSuchFieldError var244) {
         }

         try {
            var0[Material.LEGACY_GOLD_SWORD.ordinal()] = 247;
         } catch (NoSuchFieldError var243) {
         }

         try {
            var0[Material.LEGACY_GRASS.ordinal()] = 3;
         } catch (NoSuchFieldError var242) {
         }

         try {
            var0[Material.LEGACY_GRASS_PATH.ordinal()] = 209;
         } catch (NoSuchFieldError var241) {
         }

         try {
            var0[Material.LEGACY_GRAVEL.ordinal()] = 14;
         } catch (NoSuchFieldError var240) {
         }

         try {
            var0[Material.LEGACY_GREEN_RECORD.ordinal()] = 414;
         } catch (NoSuchFieldError var239) {
         }

         try {
            var0[Material.LEGACY_GRILLED_PORK.ordinal()] = 284;
         } catch (NoSuchFieldError var238) {
         }

         try {
            var0[Material.LEGACY_HARD_CLAY.ordinal()] = 173;
         } catch (NoSuchFieldError var237) {
         }

         try {
            var0[Material.LEGACY_HAY_BLOCK.ordinal()] = 171;
         } catch (NoSuchFieldError var236) {
         }

         try {
            var0[Material.LEGACY_HOPPER.ordinal()] = 155;
         } catch (NoSuchFieldError var235) {
         }

         try {
            var0[Material.LEGACY_HOPPER_MINECART.ordinal()] = 372;
         } catch (NoSuchFieldError var234) {
         }

         try {
            var0[Material.LEGACY_HUGE_MUSHROOM_1.ordinal()] = 100;
         } catch (NoSuchFieldError var233) {
         }

         try {
            var0[Material.LEGACY_HUGE_MUSHROOM_2.ordinal()] = 101;
         } catch (NoSuchFieldError var232) {
         }

         try {
            var0[Material.LEGACY_ICE.ordinal()] = 80;
         } catch (NoSuchFieldError var231) {
         }

         try {
            var0[Material.LEGACY_INK_SACK.ordinal()] = 315;
         } catch (NoSuchFieldError var230) {
         }

         try {
            var0[Material.LEGACY_IRON_AXE.ordinal()] = 222;
         } catch (NoSuchFieldError var229) {
         }

         try {
            var0[Material.LEGACY_IRON_BARDING.ordinal()] = 381;
         } catch (NoSuchFieldError var228) {
         }

         try {
            var0[Material.LEGACY_IRON_BLOCK.ordinal()] = 43;
         } catch (NoSuchFieldError var227) {
         }

         try {
            var0[Material.LEGACY_IRON_BOOTS.ordinal()] = 273;
         } catch (NoSuchFieldError var226) {
         }

         try {
            var0[Material.LEGACY_IRON_CHESTPLATE.ordinal()] = 271;
         } catch (NoSuchFieldError var225) {
         }

         try {
            var0[Material.LEGACY_IRON_DOOR.ordinal()] = 294;
         } catch (NoSuchFieldError var224) {
         }

         try {
            var0[Material.LEGACY_IRON_DOOR_BLOCK.ordinal()] = 72;
         } catch (NoSuchFieldError var223) {
         }

         try {
            var0[Material.LEGACY_IRON_FENCE.ordinal()] = 102;
         } catch (NoSuchFieldError var222) {
         }

         try {
            var0[Material.LEGACY_IRON_HELMET.ordinal()] = 270;
         } catch (NoSuchFieldError var221) {
         }

         try {
            var0[Material.LEGACY_IRON_HOE.ordinal()] = 256;
         } catch (NoSuchFieldError var220) {
         }

         try {
            var0[Material.LEGACY_IRON_INGOT.ordinal()] = 229;
         } catch (NoSuchFieldError var219) {
         }

         try {
            var0[Material.LEGACY_IRON_LEGGINGS.ordinal()] = 272;
         } catch (NoSuchFieldError var218) {
         }

         try {
            var0[Material.LEGACY_IRON_ORE.ordinal()] = 16;
         } catch (NoSuchFieldError var217) {
         }

         try {
            var0[Material.LEGACY_IRON_PICKAXE.ordinal()] = 221;
         } catch (NoSuchFieldError var216) {
         }

         try {
            var0[Material.LEGACY_IRON_PLATE.ordinal()] = 149;
         } catch (NoSuchFieldError var215) {
         }

         try {
            var0[Material.LEGACY_IRON_SPADE.ordinal()] = 220;
         } catch (NoSuchFieldError var214) {
         }

         try {
            var0[Material.LEGACY_IRON_SWORD.ordinal()] = 231;
         } catch (NoSuchFieldError var213) {
         }

         try {
            var0[Material.LEGACY_IRON_TRAPDOOR.ordinal()] = 168;
         } catch (NoSuchFieldError var212) {
         }

         try {
            var0[Material.LEGACY_ITEM_FRAME.ordinal()] = 353;
         } catch (NoSuchFieldError var211) {
         }

         try {
            var0[Material.LEGACY_JACK_O_LANTERN.ordinal()] = 92;
         } catch (NoSuchFieldError var210) {
         }

         try {
            var0[Material.LEGACY_JUKEBOX.ordinal()] = 85;
         } catch (NoSuchFieldError var209) {
         }

         try {
            var0[Material.LEGACY_JUNGLE_DOOR.ordinal()] = 196;
         } catch (NoSuchFieldError var208) {
         }

         try {
            var0[Material.LEGACY_JUNGLE_DOOR_ITEM.ordinal()] = 393;
         } catch (NoSuchFieldError var207) {
         }

         try {
            var0[Material.LEGACY_JUNGLE_FENCE.ordinal()] = 191;
         } catch (NoSuchFieldError var206) {
         }

         try {
            var0[Material.LEGACY_JUNGLE_FENCE_GATE.ordinal()] = 186;
         } catch (NoSuchFieldError var205) {
         }

         try {
            var0[Material.LEGACY_JUNGLE_WOOD_STAIRS.ordinal()] = 137;
         } catch (NoSuchFieldError var204) {
         }

         try {
            var0[Material.LEGACY_LADDER.ordinal()] = 66;
         } catch (NoSuchFieldError var203) {
         }

         try {
            var0[Material.LEGACY_LAPIS_BLOCK.ordinal()] = 23;
         } catch (NoSuchFieldError var202) {
         }

         try {
            var0[Material.LEGACY_LAPIS_ORE.ordinal()] = 22;
         } catch (NoSuchFieldError var201) {
         }

         try {
            var0[Material.LEGACY_LAVA.ordinal()] = 11;
         } catch (NoSuchFieldError var200) {
         }

         try {
            var0[Material.LEGACY_LAVA_BUCKET.ordinal()] = 291;
         } catch (NoSuchFieldError var199) {
         }

         try {
            var0[Material.LEGACY_LEASH.ordinal()] = 384;
         } catch (NoSuchFieldError var198) {
         }

         try {
            var0[Material.LEGACY_LEATHER.ordinal()] = 298;
         } catch (NoSuchFieldError var197) {
         }

         try {
            var0[Material.LEGACY_LEATHER_BOOTS.ordinal()] = 265;
         } catch (NoSuchFieldError var196) {
         }

         try {
            var0[Material.LEGACY_LEATHER_CHESTPLATE.ordinal()] = 263;
         } catch (NoSuchFieldError var195) {
         }

         try {
            var0[Material.LEGACY_LEATHER_HELMET.ordinal()] = 262;
         } catch (NoSuchFieldError var194) {
         }

         try {
            var0[Material.LEGACY_LEATHER_LEGGINGS.ordinal()] = 264;
         } catch (NoSuchFieldError var193) {
         }

         try {
            var0[Material.LEGACY_LEAVES.ordinal()] = 19;
         } catch (NoSuchFieldError var192) {
         }

         try {
            var0[Material.LEGACY_LEAVES_2.ordinal()] = 162;
         } catch (NoSuchFieldError var191) {
         }

         try {
            var0[Material.LEGACY_LEVER.ordinal()] = 70;
         } catch (NoSuchFieldError var190) {
         }

         try {
            var0[Material.LEGACY_LINGERING_POTION.ordinal()] = 405;
         } catch (NoSuchFieldError var189) {
         }

         try {
            var0[Material.LEGACY_LOG.ordinal()] = 18;
         } catch (NoSuchFieldError var188) {
         }

         try {
            var0[Material.LEGACY_LOG_2.ordinal()] = 163;
         } catch (NoSuchFieldError var187) {
         }

         try {
            var0[Material.LEGACY_LONG_GRASS.ordinal()] = 32;
         } catch (NoSuchFieldError var186) {
         }

         try {
            var0[Material.LEGACY_MAGMA.ordinal()] = 214;
         } catch (NoSuchFieldError var185) {
         }

         try {
            var0[Material.LEGACY_MAGMA_CREAM.ordinal()] = 342;
         } catch (NoSuchFieldError var184) {
         }

         try {
            var0[Material.LEGACY_MAP.ordinal()] = 322;
         } catch (NoSuchFieldError var183) {
         }

         try {
            var0[Material.LEGACY_MELON.ordinal()] = 324;
         } catch (NoSuchFieldError var182) {
         }

         try {
            var0[Material.LEGACY_MELON_BLOCK.ordinal()] = 104;
         } catch (NoSuchFieldError var181) {
         }

         try {
            var0[Material.LEGACY_MELON_SEEDS.ordinal()] = 326;
         } catch (NoSuchFieldError var180) {
         }

         try {
            var0[Material.LEGACY_MELON_STEM.ordinal()] = 106;
         } catch (NoSuchFieldError var179) {
         }

         try {
            var0[Material.LEGACY_MILK_BUCKET.ordinal()] = 299;
         } catch (NoSuchFieldError var178) {
         }

         try {
            var0[Material.LEGACY_MINECART.ordinal()] = 292;
         } catch (NoSuchFieldError var177) {
         }

         try {
            var0[Material.LEGACY_MOB_SPAWNER.ordinal()] = 53;
         } catch (NoSuchFieldError var176) {
         }

         try {
            var0[Material.LEGACY_MONSTER_EGG.ordinal()] = 347;
         } catch (NoSuchFieldError var175) {
         }

         try {
            var0[Material.LEGACY_MONSTER_EGGS.ordinal()] = 98;
         } catch (NoSuchFieldError var174) {
         }

         try {
            var0[Material.LEGACY_MOSSY_COBBLESTONE.ordinal()] = 49;
         } catch (NoSuchFieldError var173) {
         }

         try {
            var0[Material.LEGACY_MUSHROOM_SOUP.ordinal()] = 246;
         } catch (NoSuchFieldError var172) {
         }

         try {
            var0[Material.LEGACY_MUTTON.ordinal()] = 387;
         } catch (NoSuchFieldError var171) {
         }

         try {
            var0[Material.LEGACY_MYCEL.ordinal()] = 111;
         } catch (NoSuchFieldError var170) {
         }

         try {
            var0[Material.LEGACY_NAME_TAG.ordinal()] = 385;
         } catch (NoSuchFieldError var169) {
         }

         try {
            var0[Material.LEGACY_NETHERRACK.ordinal()] = 88;
         } catch (NoSuchFieldError var168) {
         }

         try {
            var0[Material.LEGACY_NETHER_BRICK.ordinal()] = 113;
         } catch (NoSuchFieldError var167) {
         }

         try {
            var0[Material.LEGACY_NETHER_BRICK_ITEM.ordinal()] = 369;
         } catch (NoSuchFieldError var166) {
         }

         try {
            var0[Material.LEGACY_NETHER_BRICK_STAIRS.ordinal()] = 115;
         } catch (NoSuchFieldError var165) {
         }

         try {
            var0[Material.LEGACY_NETHER_FENCE.ordinal()] = 114;
         } catch (NoSuchFieldError var164) {
         }

         try {
            var0[Material.LEGACY_NETHER_STALK.ordinal()] = 336;
         } catch (NoSuchFieldError var163) {
         }

         try {
            var0[Material.LEGACY_NETHER_STAR.ordinal()] = 363;
         } catch (NoSuchFieldError var162) {
         }

         try {
            var0[Material.LEGACY_NETHER_WARTS.ordinal()] = 116;
         } catch (NoSuchFieldError var161) {
         }

         try {
            var0[Material.LEGACY_NETHER_WART_BLOCK.ordinal()] = 215;
         } catch (NoSuchFieldError var160) {
         }

         try {
            var0[Material.LEGACY_NOTE_BLOCK.ordinal()] = 26;
         } catch (NoSuchFieldError var159) {
         }

         try {
            var0[Material.LEGACY_OBSIDIAN.ordinal()] = 50;
         } catch (NoSuchFieldError var158) {
         }

         try {
            var0[Material.LEGACY_PACKED_ICE.ordinal()] = 175;
         } catch (NoSuchFieldError var157) {
         }

         try {
            var0[Material.LEGACY_PAINTING.ordinal()] = 285;
         } catch (NoSuchFieldError var156) {
         }

         try {
            var0[Material.LEGACY_PAPER.ordinal()] = 303;
         } catch (NoSuchFieldError var155) {
         }

         try {
            var0[Material.LEGACY_PISTON_BASE.ordinal()] = 34;
         } catch (NoSuchFieldError var154) {
         }

         try {
            var0[Material.LEGACY_PISTON_EXTENSION.ordinal()] = 35;
         } catch (NoSuchFieldError var153) {
         }

         try {
            var0[Material.LEGACY_PISTON_MOVING_PIECE.ordinal()] = 37;
         } catch (NoSuchFieldError var152) {
         }

         try {
            var0[Material.LEGACY_PISTON_STICKY_BASE.ordinal()] = 30;
         } catch (NoSuchFieldError var151) {
         }

         try {
            var0[Material.LEGACY_POISONOUS_POTATO.ordinal()] = 358;
         } catch (NoSuchFieldError var150) {
         }

         try {
            var0[Material.LEGACY_PORK.ordinal()] = 283;
         } catch (NoSuchFieldError var149) {
         }

         try {
            var0[Material.LEGACY_PORTAL.ordinal()] = 91;
         } catch (NoSuchFieldError var148) {
         }

         try {
            var0[Material.LEGACY_POTATO.ordinal()] = 143;
         } catch (NoSuchFieldError var147) {
         }

         try {
            var0[Material.LEGACY_POTATO_ITEM.ordinal()] = 356;
         } catch (NoSuchFieldError var146) {
         }

         try {
            var0[Material.LEGACY_POTION.ordinal()] = 337;
         } catch (NoSuchFieldError var145) {
         }

         try {
            var0[Material.LEGACY_POWERED_MINECART.ordinal()] = 307;
         } catch (NoSuchFieldError var144) {
         }

         try {
            var0[Material.LEGACY_POWERED_RAIL.ordinal()] = 28;
         } catch (NoSuchFieldError var143) {
         }

         try {
            var0[Material.LEGACY_PRISMARINE.ordinal()] = 169;
         } catch (NoSuchFieldError var142) {
         }

         try {
            var0[Material.LEGACY_PRISMARINE_CRYSTALS.ordinal()] = 374;
         } catch (NoSuchFieldError var141) {
         }

         try {
            var0[Material.LEGACY_PRISMARINE_SHARD.ordinal()] = 373;
         } catch (NoSuchFieldError var140) {
         }

         try {
            var0[Material.LEGACY_PUMPKIN.ordinal()] = 87;
         } catch (NoSuchFieldError var139) {
         }

         try {
            var0[Material.LEGACY_PUMPKIN_PIE.ordinal()] = 364;
         } catch (NoSuchFieldError var138) {
         }

         try {
            var0[Material.LEGACY_PUMPKIN_SEEDS.ordinal()] = 325;
         } catch (NoSuchFieldError var137) {
         }

         try {
            var0[Material.LEGACY_PUMPKIN_STEM.ordinal()] = 105;
         } catch (NoSuchFieldError var136) {
         }

         try {
            var0[Material.LEGACY_PURPUR_BLOCK.ordinal()] = 202;
         } catch (NoSuchFieldError var135) {
         }

         try {
            var0[Material.LEGACY_PURPUR_DOUBLE_SLAB.ordinal()] = 205;
         } catch (NoSuchFieldError var134) {
         }

         try {
            var0[Material.LEGACY_PURPUR_PILLAR.ordinal()] = 203;
         } catch (NoSuchFieldError var133) {
         }

         try {
            var0[Material.LEGACY_PURPUR_SLAB.ordinal()] = 206;
         } catch (NoSuchFieldError var132) {
         }

         try {
            var0[Material.LEGACY_PURPUR_STAIRS.ordinal()] = 204;
         } catch (NoSuchFieldError var131) {
         }

         try {
            var0[Material.LEGACY_QUARTZ.ordinal()] = 370;
         } catch (NoSuchFieldError var130) {
         }

         try {
            var0[Material.LEGACY_QUARTZ_BLOCK.ordinal()] = 156;
         } catch (NoSuchFieldError var129) {
         }

         try {
            var0[Material.LEGACY_QUARTZ_ORE.ordinal()] = 154;
         } catch (NoSuchFieldError var128) {
         }

         try {
            var0[Material.LEGACY_QUARTZ_STAIRS.ordinal()] = 157;
         } catch (NoSuchFieldError var127) {
         }

         try {
            var0[Material.LEGACY_RABBIT.ordinal()] = 375;
         } catch (NoSuchFieldError var126) {
         }

         try {
            var0[Material.LEGACY_RABBIT_FOOT.ordinal()] = 378;
         } catch (NoSuchFieldError var125) {
         }

         try {
            var0[Material.LEGACY_RABBIT_HIDE.ordinal()] = 379;
         } catch (NoSuchFieldError var124) {
         }

         try {
            var0[Material.LEGACY_RABBIT_STEW.ordinal()] = 377;
         } catch (NoSuchFieldError var123) {
         }

         try {
            var0[Material.LEGACY_RAILS.ordinal()] = 67;
         } catch (NoSuchFieldError var122) {
         }

         try {
            var0[Material.LEGACY_RAW_BEEF.ordinal()] = 327;
         } catch (NoSuchFieldError var121) {
         }

         try {
            var0[Material.LEGACY_RAW_CHICKEN.ordinal()] = 329;
         } catch (NoSuchFieldError var120) {
         }

         try {
            var0[Material.LEGACY_RAW_FISH.ordinal()] = 313;
         } catch (NoSuchFieldError var119) {
         }

         try {
            var0[Material.LEGACY_RECORD_10.ordinal()] = 422;
         } catch (NoSuchFieldError var118) {
         }

         try {
            var0[Material.LEGACY_RECORD_11.ordinal()] = 423;
         } catch (NoSuchFieldError var117) {
         }

         try {
            var0[Material.LEGACY_RECORD_12.ordinal()] = 424;
         } catch (NoSuchFieldError var116) {
         }

         try {
            var0[Material.LEGACY_RECORD_3.ordinal()] = 415;
         } catch (NoSuchFieldError var115) {
         }

         try {
            var0[Material.LEGACY_RECORD_4.ordinal()] = 416;
         } catch (NoSuchFieldError var114) {
         }

         try {
            var0[Material.LEGACY_RECORD_5.ordinal()] = 417;
         } catch (NoSuchFieldError var113) {
         }

         try {
            var0[Material.LEGACY_RECORD_6.ordinal()] = 418;
         } catch (NoSuchFieldError var112) {
         }

         try {
            var0[Material.LEGACY_RECORD_7.ordinal()] = 419;
         } catch (NoSuchFieldError var111) {
         }

         try {
            var0[Material.LEGACY_RECORD_8.ordinal()] = 420;
         } catch (NoSuchFieldError var110) {
         }

         try {
            var0[Material.LEGACY_RECORD_9.ordinal()] = 421;
         } catch (NoSuchFieldError var109) {
         }

         try {
            var0[Material.LEGACY_REDSTONE.ordinal()] = 295;
         } catch (NoSuchFieldError var108) {
         }

         try {
            var0[Material.LEGACY_REDSTONE_BLOCK.ordinal()] = 153;
         } catch (NoSuchFieldError var107) {
         }

         try {
            var0[Material.LEGACY_REDSTONE_COMPARATOR.ordinal()] = 368;
         } catch (NoSuchFieldError var106) {
         }

         try {
            var0[Material.LEGACY_REDSTONE_COMPARATOR_OFF.ordinal()] = 150;
         } catch (NoSuchFieldError var105) {
         }

         try {
            var0[Material.LEGACY_REDSTONE_COMPARATOR_ON.ordinal()] = 151;
         } catch (NoSuchFieldError var104) {
         }

         try {
            var0[Material.LEGACY_REDSTONE_LAMP_OFF.ordinal()] = 124;
         } catch (NoSuchFieldError var103) {
         }

         try {
            var0[Material.LEGACY_REDSTONE_LAMP_ON.ordinal()] = 125;
         } catch (NoSuchFieldError var102) {
         }

         try {
            var0[Material.LEGACY_REDSTONE_ORE.ordinal()] = 74;
         } catch (NoSuchFieldError var101) {
         }

         try {
            var0[Material.LEGACY_REDSTONE_TORCH_OFF.ordinal()] = 76;
         } catch (NoSuchFieldError var100) {
         }

         try {
            var0[Material.LEGACY_REDSTONE_TORCH_ON.ordinal()] = 77;
         } catch (NoSuchFieldError var99) {
         }

         try {
            var0[Material.LEGACY_REDSTONE_WIRE.ordinal()] = 56;
         } catch (NoSuchFieldError var98) {
         }

         try {
            var0[Material.LEGACY_RED_MUSHROOM.ordinal()] = 41;
         } catch (NoSuchFieldError var97) {
         }

         try {
            var0[Material.LEGACY_RED_NETHER_BRICK.ordinal()] = 216;
         } catch (NoSuchFieldError var96) {
         }

         try {
            var0[Material.LEGACY_RED_ROSE.ordinal()] = 39;
         } catch (NoSuchFieldError var95) {
         }

         try {
            var0[Material.LEGACY_RED_SANDSTONE.ordinal()] = 180;
         } catch (NoSuchFieldError var94) {
         }

         try {
            var0[Material.LEGACY_RED_SANDSTONE_STAIRS.ordinal()] = 181;
         } catch (NoSuchFieldError var93) {
         }

         try {
            var0[Material.LEGACY_ROTTEN_FLESH.ordinal()] = 331;
         } catch (NoSuchFieldError var92) {
         }

         try {
            var0[Material.LEGACY_SADDLE.ordinal()] = 293;
         } catch (NoSuchFieldError var91) {
         }

         try {
            var0[Material.LEGACY_SAND.ordinal()] = 13;
         } catch (NoSuchFieldError var90) {
         }

         try {
            var0[Material.LEGACY_SANDSTONE.ordinal()] = 25;
         } catch (NoSuchFieldError var89) {
         }

         try {
            var0[Material.LEGACY_SANDSTONE_STAIRS.ordinal()] = 129;
         } catch (NoSuchFieldError var88) {
         }

         try {
            var0[Material.LEGACY_SAPLING.ordinal()] = 7;
         } catch (NoSuchFieldError var87) {
         }

         try {
            var0[Material.LEGACY_SEA_LANTERN.ordinal()] = 170;
         } catch (NoSuchFieldError var86) {
         }

         try {
            var0[Material.LEGACY_SEEDS.ordinal()] = 259;
         } catch (NoSuchFieldError var85) {
         }

         try {
            var0[Material.LEGACY_SHEARS.ordinal()] = 323;
         } catch (NoSuchFieldError var84) {
         }

         try {
            var0[Material.LEGACY_SHIELD.ordinal()] = 406;
         } catch (NoSuchFieldError var83) {
         }

         try {
            var0[Material.LEGACY_SIGN.ordinal()] = 287;
         } catch (NoSuchFieldError var82) {
         }

         try {
            var0[Material.LEGACY_SIGN_POST.ordinal()] = 64;
         } catch (NoSuchFieldError var81) {
         }

         try {
            var0[Material.LEGACY_SKULL.ordinal()] = 145;
         } catch (NoSuchFieldError var80) {
         }

         try {
            var0[Material.LEGACY_SKULL_ITEM.ordinal()] = 361;
         } catch (NoSuchFieldError var79) {
         }

         try {
            var0[Material.LEGACY_SLIME_BALL.ordinal()] = 305;
         } catch (NoSuchFieldError var78) {
         }

         try {
            var0[Material.LEGACY_SLIME_BLOCK.ordinal()] = 166;
         } catch (NoSuchFieldError var77) {
         }

         try {
            var0[Material.LEGACY_SMOOTH_BRICK.ordinal()] = 99;
         } catch (NoSuchFieldError var76) {
         }

         try {
            var0[Material.LEGACY_SMOOTH_STAIRS.ordinal()] = 110;
         } catch (NoSuchFieldError var75) {
         }

         try {
            var0[Material.LEGACY_SNOW.ordinal()] = 79;
         } catch (NoSuchFieldError var74) {
         }

         try {
            var0[Material.LEGACY_SNOW_BALL.ordinal()] = 296;
         } catch (NoSuchFieldError var73) {
         }

         try {
            var0[Material.LEGACY_SNOW_BLOCK.ordinal()] = 81;
         } catch (NoSuchFieldError var72) {
         }

         try {
            var0[Material.LEGACY_SOIL.ordinal()] = 61;
         } catch (NoSuchFieldError var71) {
         }

         try {
            var0[Material.LEGACY_SOUL_SAND.ordinal()] = 89;
         } catch (NoSuchFieldError var70) {
         }

         try {
            var0[Material.LEGACY_SPECKLED_MELON.ordinal()] = 346;
         } catch (NoSuchFieldError var69) {
         }

         try {
            var0[Material.LEGACY_SPECTRAL_ARROW.ordinal()] = 403;
         } catch (NoSuchFieldError var68) {
         }

         try {
            var0[Material.LEGACY_SPIDER_EYE.ordinal()] = 339;
         } catch (NoSuchFieldError var67) {
         }

         try {
            var0[Material.LEGACY_SPLASH_POTION.ordinal()] = 402;
         } catch (NoSuchFieldError var66) {
         }

         try {
            var0[Material.LEGACY_SPONGE.ordinal()] = 20;
         } catch (NoSuchFieldError var65) {
         }

         try {
            var0[Material.LEGACY_SPRUCE_DOOR.ordinal()] = 194;
         } catch (NoSuchFieldError var64) {
         }

         try {
            var0[Material.LEGACY_SPRUCE_DOOR_ITEM.ordinal()] = 391;
         } catch (NoSuchFieldError var63) {
         }

         try {
            var0[Material.LEGACY_SPRUCE_FENCE.ordinal()] = 189;
         } catch (NoSuchFieldError var62) {
         }

         try {
            var0[Material.LEGACY_SPRUCE_FENCE_GATE.ordinal()] = 184;
         } catch (NoSuchFieldError var61) {
         }

         try {
            var0[Material.LEGACY_SPRUCE_WOOD_STAIRS.ordinal()] = 135;
         } catch (NoSuchFieldError var60) {
         }

         try {
            var0[Material.LEGACY_STAINED_CLAY.ordinal()] = 160;
         } catch (NoSuchFieldError var59) {
         }

         try {
            var0[Material.LEGACY_STAINED_GLASS.ordinal()] = 96;
         } catch (NoSuchFieldError var58) {
         }

         try {
            var0[Material.LEGACY_STAINED_GLASS_PANE.ordinal()] = 161;
         } catch (NoSuchFieldError var57) {
         }

         try {
            var0[Material.LEGACY_STANDING_BANNER.ordinal()] = 177;
         } catch (NoSuchFieldError var56) {
         }

         try {
            var0[Material.LEGACY_STATIONARY_LAVA.ordinal()] = 12;
         } catch (NoSuchFieldError var55) {
         }

         try {
            var0[Material.LEGACY_STATIONARY_WATER.ordinal()] = 10;
         } catch (NoSuchFieldError var54) {
         }

         try {
            var0[Material.LEGACY_STEP.ordinal()] = 45;
         } catch (NoSuchFieldError var53) {
         }

         try {
            var0[Material.LEGACY_STICK.ordinal()] = 244;
         } catch (NoSuchFieldError var52) {
         }

         try {
            var0[Material.LEGACY_STONE.ordinal()] = 2;
         } catch (NoSuchFieldError var51) {
         }

         try {
            var0[Material.LEGACY_STONE_AXE.ordinal()] = 239;
         } catch (NoSuchFieldError var50) {
         }

         try {
            var0[Material.LEGACY_STONE_BUTTON.ordinal()] = 78;
         } catch (NoSuchFieldError var49) {
         }

         try {
            var0[Material.LEGACY_STONE_HOE.ordinal()] = 255;
         } catch (NoSuchFieldError var48) {
         }

         try {
            var0[Material.LEGACY_STONE_PICKAXE.ordinal()] = 238;
         } catch (NoSuchFieldError var47) {
         }

         try {
            var0[Material.LEGACY_STONE_PLATE.ordinal()] = 71;
         } catch (NoSuchFieldError var46) {
         }

         try {
            var0[Material.LEGACY_STONE_SLAB2.ordinal()] = 183;
         } catch (NoSuchFieldError var45) {
         }

         try {
            var0[Material.LEGACY_STONE_SPADE.ordinal()] = 237;
         } catch (NoSuchFieldError var44) {
         }

         try {
            var0[Material.LEGACY_STONE_SWORD.ordinal()] = 236;
         } catch (NoSuchFieldError var43) {
         }

         try {
            var0[Material.LEGACY_STORAGE_MINECART.ordinal()] = 306;
         } catch (NoSuchFieldError var42) {
         }

         try {
            var0[Material.LEGACY_STRING.ordinal()] = 251;
         } catch (NoSuchFieldError var41) {
         }

         try {
            var0[Material.LEGACY_STRUCTURE_BLOCK.ordinal()] = 219;
         } catch (NoSuchFieldError var40) {
         }

         try {
            var0[Material.LEGACY_STRUCTURE_VOID.ordinal()] = 218;
         } catch (NoSuchFieldError var39) {
         }

         try {
            var0[Material.LEGACY_SUGAR.ordinal()] = 317;
         } catch (NoSuchFieldError var38) {
         }

         try {
            var0[Material.LEGACY_SUGAR_CANE.ordinal()] = 302;
         } catch (NoSuchFieldError var37) {
         }

         try {
            var0[Material.LEGACY_SUGAR_CANE_BLOCK.ordinal()] = 84;
         } catch (NoSuchFieldError var36) {
         }

         try {
            var0[Material.LEGACY_SULPHUR.ordinal()] = 253;
         } catch (NoSuchFieldError var35) {
         }

         try {
            var0[Material.LEGACY_THIN_GLASS.ordinal()] = 103;
         } catch (NoSuchFieldError var34) {
         }

         try {
            var0[Material.LEGACY_TIPPED_ARROW.ordinal()] = 404;
         } catch (NoSuchFieldError var33) {
         }

         try {
            var0[Material.LEGACY_TNT.ordinal()] = 47;
         } catch (NoSuchFieldError var32) {
         }

         try {
            var0[Material.LEGACY_TORCH.ordinal()] = 51;
         } catch (NoSuchFieldError var31) {
         }

         try {
            var0[Material.LEGACY_TRAPPED_CHEST.ordinal()] = 147;
         } catch (NoSuchFieldError var30) {
         }

         try {
            var0[Material.LEGACY_TRAP_DOOR.ordinal()] = 97;
         } catch (NoSuchFieldError var29) {
         }

         try {
            var0[Material.LEGACY_TRIPWIRE.ordinal()] = 133;
         } catch (NoSuchFieldError var28) {
         }

         try {
            var0[Material.LEGACY_TRIPWIRE_HOOK.ordinal()] = 132;
         } catch (NoSuchFieldError var27) {
         }

         try {
            var0[Material.LEGACY_VINE.ordinal()] = 107;
         } catch (NoSuchFieldError var26) {
         }

         try {
            var0[Material.LEGACY_WALL_BANNER.ordinal()] = 178;
         } catch (NoSuchFieldError var25) {
         }

         try {
            var0[Material.LEGACY_WALL_SIGN.ordinal()] = 69;
         } catch (NoSuchFieldError var24) {
         }

         try {
            var0[Material.LEGACY_WATCH.ordinal()] = 311;
         } catch (NoSuchFieldError var23) {
         }

         try {
            var0[Material.LEGACY_WATER.ordinal()] = 9;
         } catch (NoSuchFieldError var22) {
         }

         try {
            var0[Material.LEGACY_WATER_BUCKET.ordinal()] = 290;
         } catch (NoSuchFieldError var21) {
         }

         try {
            var0[Material.LEGACY_WATER_LILY.ordinal()] = 112;
         } catch (NoSuchFieldError var20) {
         }

         try {
            var0[Material.LEGACY_WEB.ordinal()] = 31;
         } catch (NoSuchFieldError var19) {
         }

         try {
            var0[Material.LEGACY_WHEAT.ordinal()] = 260;
         } catch (NoSuchFieldError var18) {
         }

         try {
            var0[Material.LEGACY_WOOD.ordinal()] = 6;
         } catch (NoSuchFieldError var17) {
         }

         try {
            var0[Material.LEGACY_WOODEN_DOOR.ordinal()] = 65;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[Material.LEGACY_WOOD_AXE.ordinal()] = 235;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[Material.LEGACY_WOOD_BUTTON.ordinal()] = 144;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[Material.LEGACY_WOOD_DOOR.ordinal()] = 288;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[Material.LEGACY_WOOD_DOUBLE_STEP.ordinal()] = 126;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[Material.LEGACY_WOOD_HOE.ordinal()] = 254;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[Material.LEGACY_WOOD_PICKAXE.ordinal()] = 234;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[Material.LEGACY_WOOD_PLATE.ordinal()] = 73;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[Material.LEGACY_WOOD_SPADE.ordinal()] = 233;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[Material.LEGACY_WOOD_STAIRS.ordinal()] = 54;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[Material.LEGACY_WOOD_STEP.ordinal()] = 127;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[Material.LEGACY_WOOD_SWORD.ordinal()] = 232;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[Material.LEGACY_WOOL.ordinal()] = 36;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[Material.LEGACY_WORKBENCH.ordinal()] = 59;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[Material.LEGACY_WRITTEN_BOOK.ordinal()] = 351;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[Material.LEGACY_YELLOW_FLOWER.ordinal()] = 38;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$org$bukkit$Material = var0;
         return var0;
      }
   }
}
