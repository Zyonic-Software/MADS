package com.zyonicsoftware.minereaper.mads;

import com.zyonicsoftware.minereaper.mads.inventory.TestInventory;
import com.zyonicsoftware.minereaper.mads.inventorygui.HorizontalAlignment;
import com.zyonicsoftware.minereaper.mads.inventorygui.VerticalAlignment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class MADS extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        System.out.println("hiadad");
        System.out.println("hiadad");
        System.out.println("hiadad");
        System.out.println("hiadad");
        System.out.println("hiadad");
    }

    @EventHandler
    public void onAsyncPlayerChat(final AsyncPlayerChatEvent asyncPlayerChatEvent) {
        final Player player = asyncPlayerChatEvent.getPlayer();
        final String message = asyncPlayerChatEvent.getMessage();
        if (message.startsWith("inv")) {
            final String[] args = message.replace("inv", "").split(" ");
            final TestInventory testInventory = new TestInventory(this, player, Bukkit.createInventory(player, 9 * 6, "TestInventory"));

            if (args.length >= 1) {
                for (int i = 0; i < Integer.parseInt(args[0]); i++) {
                    final ItemStack itemStack = new ItemStack(Material.REDSTONE);
                    final ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(String.valueOf(i));
                    itemStack.setItemMeta(itemMeta);
                    testInventory.addItemStack(itemStack);
                }
            }

            if (args.length >= 2) {
                testInventory.setVerticalAlignment(VerticalAlignment.valueOf(args[1]));
            }

            if (args.length >= 3) {
                testInventory.setHorizontalAlignment(HorizontalAlignment.valueOf(args[2]));
            }

            testInventory.open();
        }
    }
}
