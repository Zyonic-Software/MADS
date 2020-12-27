package com.zyonicsoftware.minereaper.mads.inventorygui;

import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * A wrapper for an {@link InventoryCloseEvent}
 */
public class InventoryGUICloseEvent extends InventoryCloseEvent implements Cancellable {

    /**
     * If the {@link InventoryCloseEvent} should be cancelled
     *
     * @see Cancellable
     */
    private boolean cancelled;

    /**
     * @param inventoryCloseEvent The {@link InventoryCloseEvent} to be wrapped
     */
    public InventoryGUICloseEvent(final InventoryCloseEvent inventoryCloseEvent) {
        super(inventoryCloseEvent.getView(), inventoryCloseEvent.getReason());
        this.cancelled = false;
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated
     */
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
}
