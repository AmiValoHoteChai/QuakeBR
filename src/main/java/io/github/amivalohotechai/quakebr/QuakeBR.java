package io.github.amivalohotechai.quakebr;

import io.github.amivalohotechai.quakebr.commands.GetCommand;
import io.github.amivalohotechai.quakebr.commands.QBRCommand;
import io.github.amivalohotechai.quakebr.enums.GameState;
import io.github.amivalohotechai.quakebr.listeners.GameListener;
import io.github.amivalohotechai.quakebr.listeners.GunListener;
import io.github.amivalohotechai.quakebr.recipes.AmmoRecipe;
import io.github.amivalohotechai.quakebr.recipes.GunRecipe;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.command.PluginCommand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class QuakeBR extends JavaPlugin {

    private GameState gameState = GameState.WAITING;
    private final Map<UUID, ItemStack[]> players = new HashMap<>();
    private final Map<UUID, ItemStack[]> eliminatedPlayers = new HashMap<>();
    private static final MiniMessage MM = MiniMessage.miniMessage();

    // Keys for custom items and recipe's
    private final NamespacedKey railgunKey = new NamespacedKey(this, "railgun");
    private final NamespacedKey akKey = new NamespacedKey(this, "ak");
    private final NamespacedKey railgunCooldownKey = new NamespacedKey(this, "railgun-cooldown");

    // Later features
    private final NamespacedKey assaultRiffleAmmoKey = new NamespacedKey(this, "assault-riffle-ammo");
    private final NamespacedKey sniperRiffleAmmoKey = new NamespacedKey(this, "sniper-riffle-ammo");

    @Override
    public void onEnable() {

        // Event Listeners Registration
        getServer().getPluginManager().registerEvents(new GunListener(this), this);
        getServer().getPluginManager().registerEvents(new GameListener(this), this);

        // Commands registration
        PluginCommand qbr = getCommand("qbr");
        if (qbr != null) qbr.setExecutor(new QBRCommand(this));

        PluginCommand get = getCommand("get");
        if (get != null) get.setExecutor(new GetCommand(this));

        // Recipes registration
        getServer().addRecipe(GunRecipe.getAkRecipe(this));
        getServer().addRecipe(GunRecipe.getRailgunRecipe(this));

        // Bullets will be added later into the game
        getServer().addRecipe(AmmoRecipe.getAssaultRiffleAmmoRecipe(this));
        getServer().addRecipe(AmmoRecipe.getSniperRiffleAmmoRecipe(this));

    }

    @Override
    public void onDisable() {
        // Plugin cleanup when closing
    }

    public GameState getGameState() { return gameState; }

    public void setGameState(GameState gameState) { this.gameState = gameState; }

    public Map<UUID, ItemStack[]> getPlayers() { return players; }

    public NamespacedKey getRailgunKey() {
        return railgunKey;
    }

    public NamespacedKey getAkKey() {
        return akKey;
    }

    public MiniMessage getMm() {
        return MM;
    }

    public NamespacedKey getAssaultRiffleAmmoKey() {
        return assaultRiffleAmmoKey;
    }

    public NamespacedKey getSniperRiffleAmmoKey() {
        return sniperRiffleAmmoKey;
    }

    public NamespacedKey getRailgunCooldownKey() {
        return railgunCooldownKey;
    }

    public Map<UUID, ItemStack[]> getEliminatedPlayers() {
        return eliminatedPlayers;
    }

}
