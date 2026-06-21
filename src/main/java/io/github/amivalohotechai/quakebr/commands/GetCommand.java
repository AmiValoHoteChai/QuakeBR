package io.github.amivalohotechai.quakebr.commands;

import io.github.amivalohotechai.quakebr.QuakeBR;
import io.github.amivalohotechai.quakebr.items.ItemManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class GetCommand implements TabExecutor {

    private final QuakeBR plugin;
    private final MiniMessage MM;

    public GetCommand(QuakeBR plugin) {
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
                    "<red><!i><b>You need to choose an item.</b></red>"
            ));
            return true;
        }

        switch (args[0]) {
            case "ak"-> giveItem(player, ItemManager.createAk(plugin));
            case "railgun" -> giveItem(player, ItemManager.createRailGun(plugin));
            default -> player.sendMessage(MM.deserialize(
                    "<red><!i>Invalid item.</red>"
            ));
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length == 1)
            return Stream.of("ak", "railgun")
                    .filter(x -> x.startsWith(args[0]))
                    .toList();

        return List.of();
    }

    private void giveItem(Player player, ItemStack item) {
        Inventory playerInventory = player.getInventory();
        if (playerInventory.firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        } else {
            playerInventory.addItem(item);
        }
    }
}
