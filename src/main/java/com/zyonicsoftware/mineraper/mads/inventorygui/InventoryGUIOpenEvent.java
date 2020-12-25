package com.zyonicsoftware.mineraper.mads.inventorygui;

import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * A wrapper for an {@link InventoryOpenEvent}
 */
public class InventoryGUIOpenEvent extends InventoryOpenEvent {
    /**
     * @param inventoryOpenEvent The {@link InventoryOpenEvent} to be wrapped
     */
    public InventoryGUIOpenEvent(final InventoryOpenEvent inventoryOpenEvent) {
        super(inventoryOpenEvent.getView());
    }
}
