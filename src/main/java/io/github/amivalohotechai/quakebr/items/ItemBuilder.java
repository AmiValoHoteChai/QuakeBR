package io.github.amivalohotechai.quakebr.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public final class ItemBuilder {

    private NamespacedKey key;
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(Material material, int amount) {
        this.item = ItemStack.of(material, amount);
        this.meta = item.getItemMeta();
    }

    public ItemStack build() {

        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key, PersistentDataType.BOOLEAN, true);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);

        return item;
    }

    public ItemBuilder isUnbreakable(boolean isUnbreakable) {
        meta.setUnbreakable(isUnbreakable);
        return this;
    }

    public ItemBuilder flags(ItemFlag... itemFlags) {
        meta.addItemFlags(itemFlags);
        return this;
    }

    public ItemBuilder setName(Component name) {
        meta.displayName(name);
        return this;
    }

    public ItemBuilder setKey(NamespacedKey key) {
        this.key = key;
        return this;
    }

    public ItemBuilder setLore(List<Component> lore) {
        meta.lore(lore);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level, boolean ignoreLevelRestrictions) {
        meta.addEnchant(enchantment, level, ignoreLevelRestrictions);
        return this;
    }

}
