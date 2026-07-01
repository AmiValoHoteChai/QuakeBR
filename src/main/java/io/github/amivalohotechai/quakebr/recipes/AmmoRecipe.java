package io.github.amivalohotechai.quakebr.recipes;

import io.github.amivalohotechai.quakebr.QuakeBR;
import io.github.amivalohotechai.quakebr.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

// Will add bullet rules later into the game
public class AmmoRecipe {

    public static ShapelessRecipe getAssaultRiffleAmmoRecipe(QuakeBR plugin) {
        ItemStack item = ItemManager.createAssaultRiffleAmmo(plugin, 64);

        ShapelessRecipe recipe = new ShapelessRecipe(plugin.getAssaultRiffleAmmoKey(), item);
        recipe.addIngredient(Material.IRON_INGOT);
        recipe.addIngredient(Material.GUNPOWDER);

        return recipe;
    }

    public static ShapelessRecipe getSniperRiffleAmmoRecipe(QuakeBR plugin) {
        ItemStack item = ItemManager.createSniperRiffleAmmo(plugin, 12);

        ShapelessRecipe recipe = new ShapelessRecipe(plugin.getSniperRiffleAmmoKey(), item);
        recipe.addIngredient(Material.GOLD_INGOT);
        recipe.addIngredient(Material.GUNPOWDER);

        return recipe;
    }

}
