package io.github.amivalohotechai.quakebr.recipes;

import io.github.amivalohotechai.quakebr.QuakeBR;
import io.github.amivalohotechai.quakebr.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class GunRecipe {

    public static ShapedRecipe getAkRecipe(QuakeBR plugin) {

        ItemStack item = ItemManager.createAk(plugin);

        ShapedRecipe recipe = new ShapedRecipe(plugin.getAkKey(), item);
        recipe.shape("BBB", " CB", "  B");
        recipe.setIngredient('B', Material.DEEPSLATE);
        recipe.setIngredient('C', Material.LEVER);

        return recipe;
    }

    public static ShapedRecipe getRailgunRecipe(QuakeBR plugin) {
        ItemStack item = ItemManager.createRailGun(plugin);

        ShapedRecipe recipe = new ShapedRecipe(plugin.getRailgunKey(), item);
        recipe.shape("BBB", " CB", "  B");
        recipe.setIngredient('B', Material.ANDESITE);
        recipe.setIngredient('C', Material.LEVER);

        return recipe;
    }

}
