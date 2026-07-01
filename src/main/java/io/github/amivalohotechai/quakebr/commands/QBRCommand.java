package io.github.amivalohotechai.quakebr.commands;

import io.github.amivalohotechai.quakebr.QuakeBR;
import io.github.amivalohotechai.quakebr.enums.GameState;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

public class QBRCommand implements TabExecutor {

    private final QuakeBR plugin;
    private final MiniMessage MM;

    public QBRCommand(QuakeBR plugin) {
        this.plugin = plugin;
        this.MM = plugin.getMm();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(MM.deserialize(
                    "<red><!i><b>Only players are allowed to use this command.</b></red>"
            ));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(MM.deserialize(
                    "<green><!i>Usage: /qbr join | leave | start</green>"
            ));
            return true;
        }

        switch (args[0]) {
            case "join" -> handleJoin(player);
            case "leave" -> handleLeave(player);
            case "start" -> handleStart(player);
            default -> player.sendMessage(MM.deserialize("<red><!i>Unknown subcommand."));

        }

        return true;
    }

    private void handleJoin(Player player) {
        if (plugin.getGameState() == GameState.RUNNING) {
            player.sendMessage(MM.deserialize(
                    "<red><!i>The game has already begun.</red>"
            ));
            return;
        }
        Map<UUID, ItemStack[]> joinedPlayers = plugin.getPlayers();
        if (joinedPlayers.containsKey(player.getUniqueId())) {
            player.sendMessage(MM.deserialize(
                    "<red><!i>You are already in the game.</red>"
            ));
            return;
        }
        Inventory playerInventory = player.getInventory();
        joinedPlayers.put(player.getUniqueId(), playerInventory.getContents());
        playerInventory.clear();

        player.sendMessage(MM.deserialize(
                "<gold><!i>Welcome to the game.</gold>"
        ));
    }

    private void handleLeave(Player player) {
        Map<UUID, ItemStack[]> joinedPlayers = plugin.getPlayers();
        UUID playerID = player.getUniqueId();

        if (!joinedPlayers.containsKey(playerID)) {
            player.sendMessage(MM.deserialize(
                    "<red><!i>You are not in the game.</red>"
            ));
            return;
        }
        if (plugin.getGameState() == GameState.RUNNING) {
            player.sendMessage(MM.deserialize(
                    "<red><!i>You cannot leave the game now.</red>"
            ));
            return;
        }

        player.getInventory().setContents(joinedPlayers.get(playerID));
        joinedPlayers.remove(playerID);
        player.sendMessage(MM.deserialize("<yellow><!i>You left the game."));
    }

    private void handleStart(Player player) {
        if (!player.isOp()) {
            player.sendMessage(MM.deserialize(
                    "<red><!i>You don't have permission to use that command.</red>"
            ));
            return;
        }
        if (plugin.getGameState() == GameState.RUNNING) {
            player.sendMessage(MM.deserialize(
                    "<red><!i>There is already a game running.</red>"
            ));
            return;
        }
        Map<UUID, ItemStack[]> joinedPlayers = plugin.getPlayers();
        if (joinedPlayers.size() < 2) {
            player.sendMessage(MM.deserialize(
                    "<red><!i>Need at least 2 players to start the game.</red>"
            ));
            return;
        }
        Location spawnLocation = player.getWorld().getSpawnLocation();
        Bukkit.getScheduler().runTask(plugin, () -> {
            for (UUID id: joinedPlayers.keySet()) {
                Player p = Bukkit.getPlayer(id);
                if (p == null) continue;
                p.teleport(spawnLocation.add(0, 300, 0));
                p.getInventory().setChestplate(ItemStack.of(Material.ELYTRA));
                p.setHealth(20);
            }
        });
        Bukkit.getServer().sendMessage(MM.deserialize(
                "<gold><!i><b>Game has started</b></gold>"
        ));
        plugin.setGameState(GameState.RUNNING);


    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length == 1)
            return Stream.of("join", "leave", "start")
                    .filter(s -> s.startsWith(args[0]))
                    .toList();

        return List.of();
    }
}