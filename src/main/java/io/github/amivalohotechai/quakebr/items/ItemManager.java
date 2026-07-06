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
        return new ItemBuilder(Material.CROSSBOW, 1)
                .setKey(plugin.getAkKey())
                .setName(MM.deserialize(
                        "<gradient:#8E0E00:#FFF94C><b><!i>AK-47</b></gradient>"
                ))
                .setLore(List.of(MM.deserialize(
                        "<gradient:#8E0E00:#FFF94C><!i>Fires continuously.</gradient>"
                )))
                .addEnchant(Enchantment.FIRE_ASPECT, 2, true)
                .isUnbreakable(true)
                .flags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS)
                .build();
    }

    public static boolean isAK(ItemStack item, QuakeBR plugin) {
        if (item.getItemMeta() == null) return false;
        return item.getItemMeta().getPersistentDataContainer().has(
                plugin.getAkKey(),
                PersistentDataType.BOOLEAN
        );
    }

    public static ItemStack createRailGun(QuakeBR plugin) {
        MiniMessage MM = plugin.getMm();
        return new ItemBuilder(Material.COPPER_HOE, 1)
                .setKey(plugin.getRailgunKey())
                .setName(MM.deserialize(
                        "<gradient:red:blue><b><!i>Rail Gun</b></gradient>"
                ))
                .setLore(List.of(MM.deserialize(
                        "<gradient:red:blue><!i>Fires an instantaneous laser beam.</gradient>"
                )))
                .addEnchant(Enchantment.FIRE_ASPECT, 2, true)
                .isUnbreakable(true)
                .flags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS)
                .build();
    }

    public static boolean isRailgun(ItemStack item, QuakeBR plugin) {
        if (item.getItemMeta() == null) return false;
        return item.getItemMeta().getPersistentDataContainer().has(
                plugin.getRailgunKey(),
                PersistentDataType.BOOLEAN
        );
    }

    public static ItemStack createAssaultRiffleAmmo(QuakeBR plugin, int amount) {

        MiniMessage MM = plugin.getMm();
        return new ItemBuilder(Material.LIGHTNING_ROD, amount)
                .setKey(plugin.getAssaultRiffleAmmoKey())
                .setName(MM.deserialize(
                        "<gradient:#B87333:gold><!i>Assault Riffle Bullet<gold>"
                ))
                .setLore(List.of(MM.deserialize(
                        "<white><!i>Use this for assault riffles</white>"
                )))
                .isUnbreakable(true)
                .flags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE)
                .build();
    }

    public static boolean isAssaultRiffleAmmo(ItemStack item, QuakeBR plugin) {
        if (!item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(
                plugin.getAssaultRiffleAmmoKey(),
                PersistentDataType.BOOLEAN
        );
    }

    public static ItemStack createSniperRiffleAmmo(QuakeBR plugin, int amount) {
        MiniMessage MM = plugin.getMm();
        return new ItemBuilder(Material.COPPER_NUGGET, amount)
                .setKey(plugin.getSniperRiffleAmmoKey())
                .setName(MM.deserialize(
                        "<gradient:purple:gold><!i>Sniper Riffle Bullet</gradient>"
                ))
                .setLore(List.of(MM.deserialize(
                        "<white><!i>Use this for sniper riffles</white>"
                )))
                .isUnbreakable(true)
                .flags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE)
                .build();
    }

    public static boolean isSniperRiffleAmmo(ItemStack item, QuakeBR plugin) {
        if (!item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(
                plugin.getSniperRiffleAmmoKey(),
                PersistentDataType.BOOLEAN
        );
    }

    public static ItemStack createRocketLauncher(QuakeBR plugin) {

        MiniMessage MM = plugin.getMm();
        return new ItemBuilder(Material.COPPER_SHOVEL, 1)
                .setKey(plugin.getRocketLauncherKey())
                .isUnbreakable(true)
                .setName(MM.deserialize(
                        "<gradient:blue:red><!i>Rocket Launcher</gradient>"
                ))
                .setLore(List.of(MM.deserialize(
                        "<white><!i>Launches heavy rockets</white>"
                )))
                .addEnchant(Enchantment.DENSITY, 1, true)
                .flags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
                .build();
    }

    public static boolean isRocketLauncher(ItemStack item, QuakeBR plugin) {

        if (!item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(
                plugin.getRocketLauncherKey(),
                PersistentDataType.BOOLEAN
        );
    }

//    public static ItemStack createRocket(QuakeBR plugin, int amount) {
//
//        MiniMessage MM = plugin.getMm();
//        return new ItemBuilder(Material.SNOWBALL, amount)
//                .setKey(plugin.getRocketKey())
//                .setName(MM.deserialize(
//                        "<gradient:white:gray><!i>Rocket</gradient>"
//                ))
//                .setLore(List.of(MM.deserialize(
//                        "<white><!i>Rocket for Rocket Launcher</white>"
//                )))
//                .addEnchant(Enchantment.DENSITY, 1, true)
//                .flags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS)
//                .build();
//    }

    public static boolean isRocket(ItemStack item, QuakeBR plugin) {
        if (!item.hasItemMeta()) return false;

        return item.getItemMeta().getPersistentDataContainer().has(
                plugin.getRocketKey(),
                PersistentDataType.BOOLEAN
        );
    }

}
