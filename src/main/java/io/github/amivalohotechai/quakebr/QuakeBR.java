package io.github.amivalohotechai.quakebr;

import io.github.amivalohotechai.quakebr.commands.GetCommand;
import io.github.amivalohotechai.quakebr.commands.QBRCommand;
import io.github.amivalohotechai.quakebr.enums.GameState;
import io.github.amivalohotechai.quakebr.listeners.GunListener;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class QuakeBR extends JavaPlugin {

    private GameState gameState = GameState.WAITING;
    private final Set<UUID> players = new HashSet<>();
    private static final MiniMessage MM = MiniMessage.miniMessage();

    // Keys for custom items
    private final NamespacedKey railgunKey = new NamespacedKey(this, "railgun");
    private final NamespacedKey akKey = new NamespacedKey(this, "ak");

    @Override
    public void onEnable() {

        // Event Listeners Registration
        getServer().getPluginManager().registerEvents(new GunListener(this), this);

        // Commands registration
        PluginCommand qbr = getCommand("qbr");
        if (qbr != null) qbr.setExecutor(new QBRCommand(this));

        PluginCommand get = getCommand("get");
        if (get != null) get.setExecutor(new GetCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin cleanup when closing
    }

    public GameState getGameState() { return gameState; }

    public void setGameState(GameState gameState) { this.gameState = gameState; }

    public Set<UUID> getPlayers() { return players; }

    public NamespacedKey getRailgunKey() {
        return railgunKey;
    }

    public NamespacedKey getAkKey() {
        return akKey;
    }

    public MiniMessage getMm() {
        return MM;
    }

}
