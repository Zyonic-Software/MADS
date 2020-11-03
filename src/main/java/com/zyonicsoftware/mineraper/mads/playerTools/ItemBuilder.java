/*
 * 2020. Zyonic Software - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used in compliance with the attached license.
 *
 * Minecraft Application Development System is developed by Tobias Rempe for MineReaperDE
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.mineraper.mads.playerTools;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(final Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(final Material material, final int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder setName(final String name) {
        final ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(final Enchantment enchantment, final int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addUnsafeEnchantments(final Map<Enchantment, Integer> enchantments) {
        this.itemStack.addUnsafeEnchantments(enchantments);
        return this;
    }

    public ItemBuilder addEnchantment(final Enchantment enchantment, final int level) {
        this.itemStack.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addEnchantments(final Map<Enchantment, Integer> enchantments) {
        this.itemStack.addEnchantments(enchantments);
        return this;
    }

    public ItemBuilder removeEnchantment(final Enchantment enchantment) {
        this.itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder setUnbreakable() {
        final ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setBreakable() {
        final ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setUnbreakable(false);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(final String... lore) {
        final ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(Arrays.asList(lore));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(final List<String> lore) {
        final ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }
}
