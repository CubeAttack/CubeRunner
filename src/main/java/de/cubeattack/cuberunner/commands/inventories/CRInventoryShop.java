package de.cubeattack.cuberunner.commands.inventories;

import de.cubeattack.cuberunner.*;
import de.cubeattack.cuberunner.commands.InventoryItem;
import de.cubeattack.cuberunner.utils.ItemStackManager;
import de.cubeattack.cuberunner.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CRInventoryShop extends CRInventory {

    public CRInventoryShop(CRPlayer crPlayer) {
        super(crPlayer);
        Language local = crPlayer.getLanguage();
        this.title = local.get(Language.Messages.SHOP_GUI_TITLE);
        this.amountOfRows = 3;
        this.createInventory();

        try {
            this.fillInventory();
        } catch (CRPlayer.PlayerStatsException var4) {
            var4.printStackTrace();
        }
    }

    public void update(ItemStack itemStack, InventoryAction action) {
        Language local = this.crPlayer.getLanguage();
        if (Utils.isEqualOnColorStrip(itemStack.getItemMeta().getDisplayName(), local.get(Language.Messages.SHOP_WEAPONS_TITLE))) {
            new CRInventoryWeapons(crPlayer);
        }else if(Utils.isEqualOnColorStrip(itemStack.getItemMeta().getDisplayName(), local.get(Language.Messages.SHOP_BLOCKS_TITLE))) {
            new CRInventoryBlocks(crPlayer);
        }else if(Utils.isEqualOnColorStrip(itemStack.getItemMeta().getDisplayName(), local.get(Language.Messages.SHOP_PETS_TITLE))) {
            new CRInventoryPets(crPlayer);
        }
    }

    public void fillInventory() throws CRPlayer.PlayerStatsException {
        Language local = this.crPlayer.getLanguage();
        InventoryItem icon = new InventoryItem(new ItemStackManager(Material.GRAY_STAINED_GLASS_PANE));
        icon.getItem().setData((short)10);
        icon.getItem().setDisplayName("" + ChatColor.RED);

        for (int i = 0; i < inventory.getSize(); i++) {
            icon.setPosition(i);
            icon.addToInventory(this.inventory);
        }

        icon = new InventoryItem(new ItemStackManager(Material.BOW));
        icon.setPosition(11);
        icon.getItem().setDisplayName(local.get(Language.Messages.SHOP_WEAPONS_TITLE));
        icon.addToInventory(this.inventory);

        icon = new InventoryItem(new ItemStackManager(Material.SANDSTONE));
        icon.setPosition(13);
        icon.getItem().setDisplayName(local.get(Language.Messages.SHOP_BLOCKS_TITLE));
        icon.addToInventory(this.inventory);

        icon = new InventoryItem(new ItemStackManager(Material.VILLAGER_SPAWN_EGG));
        icon.setPosition(15);
        icon.getItem().setDisplayName(local.get(Language.Messages.SHOP_PETS_TITLE));
        icon.addToInventory(this.inventory);

        this.openInventory();
    }
}
