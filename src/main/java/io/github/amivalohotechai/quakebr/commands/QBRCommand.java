package io.github.amivalohotechai.quakebr.commands;

import io.github.amivalohotechai.quakebr.QuakeBR;
import io.github.amivalohotechai.quakebr.enums.GameState;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
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
        Set<UUID> joinedPlayers = plugin.getPlayers();
        if (joinedPlayers.contains(player.getUniqueId())) {
            player.sendMessage(MM.deserialize(
                    "<red><!i>You are already in the game.</red>"
            ));
            return;
        }
        joinedPlayers.add(player.getUniqueId());
        player.sendMessage(MM.deserialize(
                "<gold><!i>Welcome to the game.</gold>"
        ));
    }

    private void handleLeave(Player player) {
        Set<UUID> joinedPlayers = plugin.getPlayers();
        UUID playerID = player.getUniqueId();

        if (!joinedPlayers.contains(playerID)) {
            player.sendMessage(MM.deserialize(
                    "<red><!i>You are not in the game.</red>"
            ));
            return;
        }
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
        Set<UUID> joinedPlayers = plugin.getPlayers();
        if (joinedPlayers.size() < 2) {
            player.sendMessage(MM.deserialize(
                    "<red><!i>Need at least 2 players to start the game.</red>"
            ));
            return;
        }
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