package com.zyonicsoftware.mineraper.mads.inventorygui;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * A wrapper for an {@link InventoryClickEvent}
 */
public class InventoryGUIClickEvent extends InventoryClickEvent {

    /**
     * @param inventoryClickEvent The {@link InventoryClickEvent} to be wrapped
     */
    public InventoryGUIClickEvent(final InventoryClickEvent inventoryClickEvent) {
        super(inventoryClickEvent.getView(), inventoryClickEvent.getSlotType(), inventoryClickEvent.getSlot(), inventoryClickEvent.getClick(), inventoryClickEvent.getAction(), inventoryClickEvent.getHotbarButton());
    }
}
