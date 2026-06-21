package io.github.amivalohotechai.quakebr.items;

import io.github.amivalohotechai.quakebr.QuakeBR;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public final class ItemManager {


    public static ItemStack createAk(QuakeBR plugin) {
        MiniMessage MM = plugin.getMm();
        return new ItemBuilder(Material.CROSSBOW)
                .setKey(plugin.getAkKey())
                .setName(MM.deserialize(
                        "<gradient:#8E0E00:#FFF94C><b><!i>AK-47</b></gradient>"
                ))
                .setLore(List.of(MM.deserialize(
                        "<gradient:#8E0E00:#FFF94C><!i>Fires continuously.</gradient>"
                )))
                .addEnchant(Enchantment.FIRE_ASPECT, 2, false)
                .isUnbreakable(true)
                .flags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS)
                .build();
    }

    public static boolean isAK(ItemStack item, QuakeBR plugin) {
        if (item.getItemMeta() == null) return false;
        return item.getItemMeta().getPersistentDataContainer().has(
                plugin.getAkKey(), PersistentDataType.BOOLEAN);
    }

    public static ItemStack createRailGun(QuakeBR plugin) {
        MiniMessage MM = plugin.getMm();
        return new ItemBuilder(Material.COPPER_HOE)
                .setKey(plugin.getRailgunKey())
                .setName(MM.deserialize(
                        "<gradient:red:blue><b><!i>Rail Gun</b></gradient>"
                ))
                .setLore(List.of(MM.deserialize(
                        "<gradient:red:blue><!i>Fires an instantaneous laser beam.</gradient>"
                )))
                .addEnchant(Enchantment.FIRE_ASPECT, 2, false)
                .isUnbreakable(true)
                .flags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS)
                .build();
    }

    public static boolean isRailgun(ItemStack item, QuakeBR plugin) {
        if (item.getItemMeta() == null) return false;
        return item.getItemMeta().getPersistentDataContainer().has(
                plugin.getRailgunKey(), PersistentDataType.BOOLEAN);
    }
}
