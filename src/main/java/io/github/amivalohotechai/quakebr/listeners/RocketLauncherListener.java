package io.github.amivalohotechai.quakebr.listeners;

import io.github.amivalohotechai.quakebr.QuakeBR;
import io.github.amivalohotechai.quakebr.items.ItemManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class RocketLauncherListener implements Listener {

    private final QuakeBR plugin;
    private final MiniMessage MM;

    public RocketLauncherListener(QuakeBR plugin) {
        this.plugin = plugin;
        this.MM = plugin.getMm();
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null) return;
        if (!event.getAction().isRightClick()) return;
        if (!ItemManager.isRocketLauncher(item, plugin)) return;

        long timeLeft = cooldownLeft(player);
        if (timeLeft > 0) {
            player.sendMessage(MM.deserialize(
                    "<red>[Cooldown] </red> Please wait for %d seconds.".formatted(timeLeft)
            ));
            return;
        }

        Snowball rocket = player.launchProjectile(Snowball.class);
        rocket.getPersistentDataContainer().set(plugin.getRocketKey(), PersistentDataType.BOOLEAN, true);

        rocket.setVelocity(player.getLocation().getDirection().multiply(3));

    }

    public long cooldownLeft(Player player) {

        NamespacedKey key = plugin.getRocketLauncherCooldownKey();
        PersistentDataContainer data = player.getPersistentDataContainer();

        long expireTime = data.getOrDefault(key, PersistentDataType.LONG, 0L);

        if (expireTime == 0)
            data.set(key, PersistentDataType.LONG, System.currentTimeMillis() + (5 * 1000));

        else {

            long timeLeft = (expireTime - System.currentTimeMillis());
            timeLeft = (long) Math.ceil(timeLeft / 1000.0);
            if (timeLeft > 0) {

                MiniMessage MM = plugin.getMm();
                player.sendMessage(MM.deserialize(
                        "<red>[Cooldown] </red> Please wait for %d seconds.".formatted(timeLeft)
                ));
                return timeLeft;
            }
        }

        data.set(key, PersistentDataType.LONG, System.currentTimeMillis() + (5 * 1000));
        return 0;
    }

    @EventHandler
    public void onRocketLand(ProjectileHitEvent event) {

        if (!(event.getEntity() instanceof Snowball rocket)) return;

        if (!rocket.getPersistentDataContainer().has(
                plugin.getRocketKey(),
                PersistentDataType.BOOLEAN
        )) return;

        rocket.getWorld().createExplosion(rocket.getLocation(), 3, false);
    }

}
