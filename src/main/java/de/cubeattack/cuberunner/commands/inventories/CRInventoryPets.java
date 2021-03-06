package de.cubeattack.cuberunner.commands.inventories;

import de.cubeattack.cuberunner.CRPlayer;
import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.commands.InventoryItem;
import de.cubeattack.cuberunner.utils.ItemStackManager;
import de.cubeattack.cuberunner.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class CRInventoryPets extends CRInventory {

    public CRInventoryPets(CRPlayer crPlayer) {
        super(crPlayer);
        Language local = crPlayer.getLanguage();
        this.title = local.get(Language.Messages.SHOP_PETS_TITLE);
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
        if (Utils.isEqualOnColorStrip(itemStack.getItemMeta().getDisplayName(), local.get(Language.Messages.GUI_Back))) {
            new CRInventoryShop(crPlayer);
        }else if(Utils.isEqualOnColorStrip(itemStack.getItemMeta().getDisplayName(), local.get(Language.Messages.SHOP_BLOCKS_TITLE))) {
            crPlayer.getPlayer().sendMessage("Test2");
        }else if(Utils.isEqualOnColorStrip(itemStack.getItemMeta().getDisplayName(), local.get(Language.Messages.SHOP_PETS_TITLE))) {
            crPlayer.getPlayer().sendMessage("Test2");
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

        icon = new InventoryItem(new ItemStackManager(Material.ARROW));
        icon.setPosition(8);
        icon.getItem().setDisplayName(local.get(Language.Messages.GUI_Back));
        icon.addToInventory(this.inventory);

        icon = new InventoryItem(new ItemStackManager(Material.PIG_SPAWN_EGG));
        icon.setPosition(11);
        icon.addToInventory(this.inventory);

        icon = new InventoryItem(new ItemStackManager(Material.CHICKEN_SPAWN_EGG));
        icon.setPosition(13);
        icon.addToInventory(this.inventory);

        icon = new InventoryItem(new ItemStackManager(Material.TURTLE_SPAWN_EGG));
        icon.setPosition(15);
        icon.addToInventory(this.inventory);

        this.openInventory();
    }
}
