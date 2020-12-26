package com.zyonicsoftware.minereaper.mads.inventory;

import com.zyonicsoftware.minereaper.mads.inventorygui.InventoryGUI;
import com.zyonicsoftware.minereaper.mads.inventorygui.InventoryGUIClickEvent;
import com.zyonicsoftware.minereaper.mads.inventorygui.InventoryGUICloseEvent;
import com.zyonicsoftware.minereaper.mads.inventorygui.InventoryGUIOpenEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class TestInventory extends InventoryGUI {
    /**
     * {@inheritDoc}
     */
    public TestInventory(final JavaPlugin javaPlugin, final Player player, final Inventory inventory) {
        super(javaPlugin, player, inventory);
    }

    @Override
    protected void onInventoryOpen(final InventoryGUIOpenEvent inventoryGUIOpenEvent) {

    }

    @Override
    protected void onInventoryClick(final InventoryGUIClickEvent inventoryGUIClickEvent) {

    }

    @Override
    protected void onInventoryClose(final InventoryGUICloseEvent inventoryGUICloseEvent) {

    }
}
