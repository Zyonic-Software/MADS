package com.zyonicsoftware.minereaper.mads.inventorygui;

import com.zyonicsoftware.minereaper.mads.util.MathUtil;
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

//    private static final String INVENTORY_GUI_CLOSING_META_DATA_KEY = "inventoryGUIClosing";
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
    private InventoryGUIAlignment verticalAlignment;
    private InventoryGUIAlignment horizontalAlignment;
    private ItemStack backgroundItemStack;

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
        this.verticalAlignment = InventoryGUIAlignment.TOP;
        this.horizontalAlignment = InventoryGUIAlignment.LEFT;

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
//                System.out.println("inventoryCloseEvent: " + inventoryCloseEvent.getViewers());
//                System.out.println("inventory: " + inventory.getViewers());
                if (inventoryCloseEvent.getViewers().equals(inventory.getViewers())) {
                    final InventoryGUICloseEvent inventoryGUICloseEvent = new InventoryGUICloseEvent(inventoryCloseEvent);
                    InventoryGUI.this.onInventoryClose(inventoryGUICloseEvent);
//                    System.out.println(inventoryCloseEvent.getPlayer().hasMetadata(InventoryGUI.INVENTORY_GUI_CLOSING_META_DATA_KEY));
                    /*if (inventoryCloseEvent.getPlayer().hasMetadata(InventoryGUI.INVENTORY_GUI_CLOSING_META_DATA_KEY)) {
                        inventoryCloseEvent.getPlayer().removeMetadata(InventoryGUI.INVENTORY_GUI_CLOSING_META_DATA_KEY, javaPlugin);
                    } else {*/
                    if (inventoryGUICloseEvent.isCancelled()) {
                        InventoryGUI.this.open();
                    } else {
                        InventoryGUI.this.unregisterEvents();
                    }
//                    }
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

    public InventoryGUIAlignment getVerticalAlignment() {
        return this.verticalAlignment;
    }

    public void setVerticalAlignment(final InventoryGUIAlignment inventoryGUIAlignment) {
        this.verticalAlignment = inventoryGUIAlignment;

        this.updateDisplay();
    }

    public InventoryGUIAlignment getHorizontalAlignment() {
        return this.horizontalAlignment;
    }

    public void setHorizontalAlignment(final InventoryGUIAlignment inventoryGUIAlignment) {
        this.horizontalAlignment = inventoryGUIAlignment;

        this.updateDisplay();
    }

    public ItemStack getBackgroundItemStack() {
        return this.backgroundItemStack;
    }

    public void setBackgroundItemStack(final ItemStack backgroundItemStack) {
        this.backgroundItemStack = backgroundItemStack;

        this.updateDisplay();
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
    public void open() {
        this.close();
        this.player.openInventory(this.inventory);

        this.updateDisplay();
    }

    /**
     * Close the {@link InventoryGUI} for the {@link Player}
     */
    public void close() {
//        this.player.setMetadata(InventoryGUI.INVENTORY_GUI_CLOSING_META_DATA_KEY, new FixedMetadataValue(this.javaPlugin, true));
        this.player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
    }

    /**
     * Clear the display and rerender it
     *
     * @see InventoryGUI#clearDisplay()
     */
    private void updateDisplay() {
        this.clearDisplay();

        int row = this.getOriginSlot(this.verticalAlignment);
        int column = this.getOriginSlot(this.horizontalAlignment);

        for (final ItemStack itemStack : this.itemStacks) {
            this.setItem(row, column, itemStack);
            column += this.horizontalAlignment.equals(InventoryGUIAlignment.RIGHT) ? -1 : 1;
            if (column == (this.horizontalAlignment.equals(InventoryGUIAlignment.RIGHT) ? 0 - 1 : this.getMaxColumns())) {
                column = this.horizontalAlignment.equals(InventoryGUIAlignment.RIGHT) ? this.getMaxColumns() - 1 : 0;
                row += this.verticalAlignment.equals(InventoryGUIAlignment.BOTTOM) ? -1 : 1;
            }
        }

        if (this.backgroundItemStack != null) {
            final ItemStack[] contents = this.inventory.getContents();
            for (int i = 0; i < contents.length; i++) {
                if (contents[i] == null) {
                    this.inventory.setItem(i, this.backgroundItemStack);
                }
            }
        }
    }

    private int getOriginSlot(final InventoryGUIAlignment inventoryGUIAlignment) {
        final int itemStackMaxRows = (int) Math.ceil(this.itemStacks.size() / 9f);
        switch (inventoryGUIAlignment) {
            case TOP:
            case LEFT:
                return 0;
            case VERTICAL_MIDDLE:
                if (MathUtil.isEven(itemStackMaxRows)) {
                    return (this.getMaxRows() / 2 + 1 - itemStackMaxRows / 2) - 1;
                } else {
                    return (int) ((int) Math.ceil(this.getMaxRows() / 2d) - Math.floor(itemStackMaxRows / 2d) - 1);
                }
            case HORIZONTAL_MIDDLE:
                if (MathUtil.isEven(itemStackMaxRows)) {
                    return (this.getMaxColumns() / 2 + 1 - itemStackMaxRows / 2) - 1;
                } else {
                    return (int) ((int) Math.ceil(this.getMaxColumns() / 2d) - Math.floor(itemStackMaxRows / 2d) - 1);
                }
            case BOTTOM:
                return this.getMaxRows() - 1;
            case RIGHT:
                return this.getMaxColumns() - 1;
        }
        return -1;
    }

    private int getMaxColumns() {
        return 9;
    }

    private int getMaxRows() {
        return this.inventory.getSize() / 9;
    }

    private void setItem(final int row, final int column, final ItemStack itemStack) {
        this.inventory.setItem(9 * row + column, itemStack);
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
