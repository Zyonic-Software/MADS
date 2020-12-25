package com.zyonicsoftware.mineraper.mads.inventorygui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * An dynamic inventory gui
 */
public abstract class InventoryGUI {

    /**
     * The {@link JavaPlugin} used for initialising the internal listener
     */
    private final JavaPlugin javaPlugin;
    /**
     * The {@link Player} viewing this {@link InventoryGUI}
     */
    private final Player player;
    /**
     * The {@link Inventory} used for this gui
     */
    private final Inventory inventory;

    /**
     * All {@link ItemStack}s used in this gui
     */
    private final List<ItemStack> itemStacks;
    /**
     * The internal {@link Listener}
     */
    private final Listener listener;
    private VerticalAlignment verticalAlignment;
    private HorizontalAlignment horizontalAlignment;

    /**
     * @param javaPlugin The {@link JavaPlugin} used for initialising the internal listener
     * @param player     The {@link Player} viewing this {@link InventoryGUI}
     * @param inventory  The {@link Inventory} used for this gui
     */
    public InventoryGUI(final JavaPlugin javaPlugin, final Player player, final Inventory inventory) {
        this.javaPlugin = javaPlugin;
        this.player = player;
        this.inventory = inventory;

        this.itemStacks = new ArrayList<>();
        this.verticalAlignment = VerticalAlignment.TOP;
        this.horizontalAlignment = HorizontalAlignment.LEFT;

        this.listener = new Listener() {
            @EventHandler
            public void onInventoryOpen(final InventoryOpenEvent inventoryOpenEvent) {
                if (inventoryOpenEvent.getViewers().equals(inventory.getViewers())) {
                    InventoryGUI.this.onInventoryOpen(new InventoryGUIOpenEvent(inventoryOpenEvent));
                }
            }

            @EventHandler
            public void onInventoryClick(final InventoryClickEvent inventoryClickEvent) {
                if (inventoryClickEvent.getViewers().equals(inventory.getViewers())) {
                    InventoryGUI.this.onInventoryClick(new InventoryGUIClickEvent(inventoryClickEvent));
                }
            }

            @EventHandler
            public void onInventoryClose(final InventoryCloseEvent inventoryCloseEvent) {
                if (inventoryCloseEvent.getViewers().equals(inventory.getViewers())) {
                    final InventoryGUICloseEvent inventoryGUICloseEvent = new InventoryGUICloseEvent(inventoryCloseEvent);
                    InventoryGUI.this.onInventoryClose(inventoryGUICloseEvent);
                    if (inventoryGUICloseEvent.isCancelled()) {
                        InventoryGUI.this.open();
                    } else {
                        InventoryGUI.this.unregisterEvents();
                    }
                }
            }
        };
        this.registerEvents();
    }

    /**
     * @return The {@link JavaPlugin} used for initialising the internal listener
     */
    public JavaPlugin getJavaPlugin() {
        return this.javaPlugin;
    }

    /**
     * @return The {@link Player} viewing this {@link InventoryGUI}
     */
    public Player getPlayer() {
        return this.player;
    }

    public VerticalAlignment getVerticalAlignment() {
        return this.verticalAlignment;
    }

    public void setVerticalAlignment(final VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return this.horizontalAlignment;
    }

    public void setHorizontalAlignment(final HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    /**
     * Add an {@link ItemStack} to the gui and update it
     *
     * @param itemStack The {@link ItemStack} to be added
     * @see InventoryGUI#updateDisplay()
     */
    public void addItemStack(final ItemStack itemStack) {
        this.itemStacks.add(itemStack);

        this.updateDisplay();
    }

    /**
     * Remove an {@link ItemStack} from the gui and update it
     *
     * @param itemStack The {@link ItemStack} to be removed
     * @see InventoryGUI#updateDisplay()
     */
    public void removeItemStack(final ItemStack itemStack) {
        this.itemStacks.remove(itemStack);

        this.updateDisplay();
    }

    /**
     * Remove all {@link ItemStack}s from the gui and update it
     *
     * @see InventoryGUI#updateDisplay()
     */
    public void clearItemStacks() {
        this.itemStacks.clear();

        this.updateDisplay();
    }

    /**
     * Sort all {@link ItemStack}s and update the gui
     *
     * @param itemStackComparator The {@link Comparator} the {@link ItemStack}s should be sorted after
     * @see InventoryGUI#updateDisplay()
     */
    public void sortItemStacks(final Comparator<? super ItemStack> itemStackComparator) {
        this.itemStacks.sort(itemStackComparator);

        this.updateDisplay();
    }

    /**
     * Open the {@link InventoryGUI} for the {@link Player} and update the display
     *
     * @see InventoryGUI#updateDisplay()
     */
    private void open() {
        this.player.openInventory(this.inventory);

        this.updateDisplay();
    }

    /**
     * Clear the display and rerender it
     *
     * @see InventoryGUI#clearDisplay()
     */
    private void updateDisplay() {
        this.clearDisplay();

        this.itemStacks.forEach(this.inventory::addItem);
    }

    /**
     * Clear the display
     */
    private void clearDisplay() {
        this.inventory.clear();
    }

    /**
     * Register the internal {@link Listener}
     */
    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(this.listener, this.javaPlugin);
    }

    /**
     * Unregister the internal {@link Listener}
     */
    private void unregisterEvents() {
        HandlerList.unregisterAll(this.listener);
    }

    /**
     * Used for custom implementations when the gui opens
     *
     * @param inventoryGUIOpenEvent The {@link InventoryGUIOpenEvent} to be handled
     */
    protected abstract void onInventoryOpen(InventoryGUIOpenEvent inventoryGUIOpenEvent);

    /**
     * Used for custom implementations when the gui gets clicked
     *
     * @param inventoryGUIClickEvent The {@link InventoryGUIClickEvent} to be handled
     */
    protected abstract void onInventoryClick(InventoryGUIClickEvent inventoryGUIClickEvent);

    /**
     * Used for custom implementations when the gui closes
     *
     * @param inventoryGUICloseEvent The {@link InventoryGUICloseEvent} to be handled
     */
    protected abstract void onInventoryClose(InventoryGUICloseEvent inventoryGUICloseEvent);

}
