package io.github.amivalohotechai.quakebr.recipes;

import io.github.amivalohotechai.quakebr.QuakeBR;
import io.github.amivalohotechai.quakebr.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class RocketLauncherRecipe {

    public static ShapedRecipe getRocketLauncherRecipe(QuakeBR plugin) {

        ItemStack item = ItemManager.createRocketLauncher(plugin);

        ShapedRecipe recipe = new ShapedRecipe(plugin.getRocketLauncherKey(), item);

        recipe.shape(" W ", " W ", "WWW");
        recipe.setIngredient('W', Material.COBBLESTONE);

        return recipe;
    }
}
