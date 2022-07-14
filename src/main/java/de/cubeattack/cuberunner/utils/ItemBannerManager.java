package de.cubeattack.cuberunner.utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class ItemBannerManager extends ItemStackManager {
   private DyeColor baseColor;
   private List<Pattern> patterns;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$me$poutineqc$cuberunner$utils$ItemBannerManager$CustomPattern;

   public ItemBannerManager() {
      super(Material.GRAY_BANNER);
      this.patterns = new ArrayList();
   }

   public ItemBannerManager(ItemStack itemStack) {
      super(itemStack);
      this.patterns = new ArrayList();
      BannerMeta meta = (BannerMeta)itemStack.getItemMeta();
      this.baseColor = meta.getBaseColor();
      this.patterns = meta.getPatterns();
   }

   public ItemBannerManager(ItemBannerManager.CustomPattern pattern) {
      this(pattern, DyeColor.BLACK);
   }

   public ItemBannerManager(ItemBannerManager.CustomPattern pattern, DyeColor dyeColor) {
      super(Material.GRAY_BANNER);
      this.patterns = new ArrayList();
      this.durability = 0;
      this.setPattern(pattern, dyeColor);
   }

   public ItemStack getItem() {
      ItemStack itemStack = super.getItem();
      BannerMeta meta = (BannerMeta)itemStack.getItemMeta();
      meta.setBaseColor(this.baseColor);
      meta.setPatterns(this.patterns);
      itemStack.setItemMeta(meta);
      return itemStack;
   }

   public boolean isSame(ItemStack itemStack) {
      if (!super.isSame(itemStack)) {
         return false;
      } else {
         BannerMeta meta = (BannerMeta)itemStack.getItemMeta();
         if (meta.getBaseColor() != this.baseColor) {
            return false;
         } else if (meta.getPatterns().size() != this.patterns.size()) {
            return false;
         } else {
            for(int i = 0; i < this.patterns.size(); ++i) {
               if (((Pattern)this.patterns.get(i)).getColor() != ((Pattern)meta.getPatterns().get(i)).getColor() || ((Pattern)this.patterns.get(i)).getPattern() != ((Pattern)meta.getPatterns().get(i)).getPattern()) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public void add(Pattern pattern) {
      this.patterns.add(pattern);
   }

   public void setPatterns(List<Pattern> patterns) {
      this.patterns = patterns;
   }

   public void clearPatterns() {
      this.patterns.clear();
   }

   public List<Pattern> getPatterns() {
      return this.patterns;
   }

   private void setPattern(ItemBannerManager.CustomPattern pattern, DyeColor dyeColor) {
      this.patterns.clear();
      switch($SWITCH_TABLE$me$poutineqc$cuberunner$utils$ItemBannerManager$CustomPattern()[pattern.ordinal()]) {
      case 1:
         this.baseColor = dyeColor;
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE));
         this.patterns.add(new Pattern(dyeColor, PatternType.HALF_VERTICAL));
         break;
      case 2:
         this.baseColor = dyeColor;
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE));
         this.patterns.add(new Pattern(dyeColor, PatternType.HALF_VERTICAL_MIRROR));
         break;
      case 3:
         this.baseColor = dyeColor;
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));
         break;
      case 4:
         this.baseColor = DyeColor.WHITE;
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_TOP));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_DOWNLEFT));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.BORDER));
         break;
      case 5:
         this.baseColor = DyeColor.WHITE;
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_TOP));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_CENTER));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.CURLY_BORDER));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.BORDER));
         break;
      case 6:
         this.baseColor = DyeColor.WHITE;
         this.patterns.add(new Pattern(dyeColor, PatternType.TRIANGLE_TOP));
         this.patterns.add(new Pattern(dyeColor, PatternType.TRIANGLE_BOTTOM));
         this.patterns.add(new Pattern(dyeColor, PatternType.SQUARE_TOP_LEFT));
         this.patterns.add(new Pattern(dyeColor, PatternType.SQUARE_BOTTOM_RIGHT));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_DOWNLEFT));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.BORDER));
         break;
      case 7:
         this.baseColor = DyeColor.WHITE;
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_MIDDLE));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_TOP));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.BORDER));
         break;
      case 8:
         this.baseColor = dyeColor;
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.HALF_HORIZONTAL));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_MIDDLE));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.BORDER));
         break;
      case 9:
         this.baseColor = dyeColor;
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.HALF_VERTICAL_MIRROR));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.HALF_HORIZONTAL_MIRROR));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.DIAGONAL_RIGHT_MIRROR));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_DOWNRIGHT));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_TOP));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.BORDER));
         break;
      case 10:
         this.baseColor = DyeColor.WHITE;
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.HALF_HORIZONTAL));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_MIDDLE));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_TOP));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.BORDER));
         break;
      case 11:
         this.baseColor = DyeColor.WHITE;
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_TOP));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.SQUARE_BOTTOM_LEFT));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_DOWNLEFT));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.BORDER));
         break;
      case 12:
         this.baseColor = DyeColor.WHITE;
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_TOP));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_MIDDLE));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.BORDER));
         break;
      case 13:
         this.baseColor = DyeColor.WHITE;
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.HALF_HORIZONTAL_MIRROR));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_MIDDLE));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_TOP));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.BORDER));
         break;
      case 14:
         this.baseColor = DyeColor.WHITE;
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_CENTER));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.BORDER));
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_MIDDLE));
         break;
      case 15:
         this.baseColor = DyeColor.WHITE;
         this.patterns.add(new Pattern(dyeColor, PatternType.STRIPE_MIDDLE));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.BORDER));
         break;
      case 16:
         this.baseColor = DyeColor.WHITE;
         this.patterns.add(new Pattern(dyeColor, PatternType.TRIANGLE_BOTTOM));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.BORDER));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT));
         this.patterns.add(new Pattern(DyeColor.WHITE, PatternType.CREEPER));
      }

   }

   public DyeColor getBaseColor() {
      return this.baseColor;
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$me$poutineqc$cuberunner$utils$ItemBannerManager$CustomPattern() {
      int[] var10000 = $SWITCH_TABLE$me$poutineqc$cuberunner$utils$ItemBannerManager$CustomPattern;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[ItemBannerManager.CustomPattern.values().length];

         try {
            var0[ItemBannerManager.CustomPattern.ARROW_ACTUAL.ordinal()] = 3;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.ARROW_LEFT.ordinal()] = 2;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.ARROW_RIGHT.ordinal()] = 1;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.DOT.ordinal()] = 16;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.EIGHT.ordinal()] = 12;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.FIVE.ordinal()] = 9;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.FOUR.ordinal()] = 8;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.NINE.ordinal()] = 13;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.ONE.ordinal()] = 5;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.SEVEN.ordinal()] = 11;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.SIX.ordinal()] = 10;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.SYMBOL_MINUS.ordinal()] = 15;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.SYMBOL_PLUS.ordinal()] = 14;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.THREE.ordinal()] = 7;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.TWO.ordinal()] = 6;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[ItemBannerManager.CustomPattern.ZERO.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$me$poutineqc$cuberunner$utils$ItemBannerManager$CustomPattern = var0;
         return var0;
      }
   }

   public static enum CustomPattern {
      ARROW_RIGHT,
      ARROW_LEFT,
      ARROW_ACTUAL,
      ZERO,
      ONE,
      TWO,
      THREE,
      FOUR,
      FIVE,
      SIX,
      SEVEN,
      EIGHT,
      NINE,
      SYMBOL_PLUS,
      SYMBOL_MINUS,
      DOT;

      public static ItemBannerManager.CustomPattern getPattern(int number) {
         switch(number) {
         case 0:
            return ZERO;
         case 1:
            return ONE;
         case 2:
            return TWO;
         case 3:
            return THREE;
         case 4:
            return FOUR;
         case 5:
            return FIVE;
         case 6:
            return SIX;
         case 7:
            return SEVEN;
         case 8:
            return EIGHT;
         case 9:
            return NINE;
         default:
            return null;
         }
      }
   }
}
