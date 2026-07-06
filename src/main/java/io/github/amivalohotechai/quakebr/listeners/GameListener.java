package io.github.amivalohotechai.quakebr.listeners;

import io.github.amivalohotechai.quakebr.QuakeBR;
import io.github.amivalohotechai.quakebr.enums.GameState;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;


public class GameListener implements Listener {

    private final QuakeBR plugin;

    public GameListener(QuakeBR plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        if (plugin.getGameState() != GameState.RUNNING) return;
        Player player = event.getPlayer();

        if (!plugin.getPlayers().containsKey(player.getUniqueId())) return;
        eliminatePlayer(player);
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        if (plugin.getGameState() != GameState.RUNNING) return;
        Player player = event.getPlayer();
        if (!plugin.getPlayers().containsKey(player.getUniqueId())) return;
        eliminatePlayer(player);
    }

//    @EventHandler
//    public void onFoodLevelChange(FoodLevelChangeEvent event) {
//
//        if (!(event.getEntity() instanceof Player player)) return;
//
//        if (plugin.getGameState() != GameState.RUNNING) return;
//
//        if (!plugin.getPlayers().containsKey(player.getUniqueId())) return;
//
//        event.setCancelled(true);
//    }

    private void eliminatePlayer(Player player) {
        UUID id = player.getUniqueId();

        Map<UUID, ItemStack[]> joinedPlayers = plugin.getPlayers();
        plugin.getEliminatedPlayers().put(id, joinedPlayers.get(id));
        joinedPlayers.remove(id);

        Bukkit.getScheduler().runTask(plugin, () -> {
            Location loc = player.getLocation();
            for (ItemStack item: player.getInventory().getContents()) {
                if (item!= null) player.getWorld().dropItemNaturally(loc, item);
            }
        });

        player.setGameMode(GameMode.SPECTATOR);
        Bukkit.broadcast(plugin.getMm().deserialize(
                "<red><b>" + player.getName() + "</b> has been eliminated!</red>"
        ));

        checkWinCondition();
    }

    private void checkWinCondition() {
        if (plugin.getPlayers().size() != 1) return;

        MiniMessage MM = plugin.getMm();
        Map<UUID, ItemStack[]> joinedPlayers = plugin.getPlayers();
        UUID winnerUUID = joinedPlayers.keySet().iterator().next();
        Player winner = Bukkit.getPlayer(winnerUUID);
        if (winner == null) {
            Bukkit.broadcast(MM.deserialize(
                    "<gold><b>No winner Player left the game!</b> </gold>"
            ));
            return;
        }

        Bukkit.broadcast(MM.deserialize(
                "<gold><b>" + winner.getName() + "</b> has won the Battle Royal!</gold>"
        ));

        Location spawnLocation = winner.getWorld().getSpawnLocation();
        winner.setGameMode(GameMode.SURVIVAL);
        winner.getInventory().setContents(joinedPlayers.get(winnerUUID));
        joinedPlayers.clear();
        winner.teleport(spawnLocation);

        Bukkit.getScheduler().runTask(plugin, () -> {
            for (int i = 0; i < 3; i++) {
                winner.getWorld().playEffect(winner.getLocation(), Effect.FIREWORK_SHOOT, 20);

            }
        });

        endGame(spawnLocation);

    }

    private void endGame(Location spawnLocation) {
        plugin.setGameState(GameState.WAITING);

        Bukkit.getScheduler().runTask(plugin, () -> {
            Map<UUID, ItemStack[]> eliminatedPlayers = plugin.getEliminatedPlayers();
            for (UUID id: eliminatedPlayers.keySet()) {
                Player player = Bukkit.getPlayer(id);
                if (player == null) continue;
                player.teleport(spawnLocation);
                player.setGameMode(GameMode.SURVIVAL);
                player.getInventory().setContents(eliminatedPlayers.get(id));
            }
            eliminatedPlayers.clear();
        });

        plugin.getPlayers().clear();

        Bukkit.broadcast(plugin.getMm().deserialize(
                "<green>Game lobby has reset. You can now join again!</green>"
        ));
    }

}
