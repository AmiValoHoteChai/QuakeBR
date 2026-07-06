package io.github.amivalohotechai.quakebr.listeners;

import io.github.amivalohotechai.quakebr.QuakeBR;
import io.github.amivalohotechai.quakebr.items.ItemManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class GunListener implements Listener {

    private final QuakeBR plugin;

    public GunListener(QuakeBR plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // If player right clicks with bare hands or item does not have meta
        if (item == null) return;

        // If the item is not the Rail Gun
        if (!event.getAction().isRightClick()) return;
        event.setCancelled(true);

        if (ItemManager.isRailgun(item, plugin)) handleRailGunDamage(player, 25);
        else if (ItemManager.isAK(item, plugin)) handleGunDamage(player, 5);

    }

    public void handleRailGunDamage(Player player, int amount) {

        NamespacedKey key = plugin.getRailgunCooldownKey();
        PersistentDataContainer data = player.getPersistentDataContainer();

        long expireTime = data.getOrDefault(key, PersistentDataType.LONG, 0L);

        if (expireTime == 0)
            data.set(key, PersistentDataType.LONG, System.currentTimeMillis() + (3 * 1000));
        else {
            long timeLeft = (expireTime - System.currentTimeMillis());
            timeLeft = (long) Math.ceil(timeLeft / 1000.0);
            if (timeLeft > 0) {

                MiniMessage MM = plugin.getMm();
                player.sendMessage(MM.deserialize(
                        "<red>[Cooldown] </red> Please wait for %d seconds.".formatted(timeLeft)
                ));
                return;
            } else data.set(key, PersistentDataType.LONG, System.currentTimeMillis() + (3 * 1000));
        }

        handleGunDamage(player, amount);
    }

    private void handleGunDamage(Player player, int amount) {

        Location start = player.getEyeLocation();
        Vector direction = start.getDirection();
        laser(player, start, direction);
        World playerWorld = player.getWorld();

        playerWorld.playSound(player.getLocation().add(0, 2, 0), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 16, .5f);

        RayTraceResult result = playerWorld.rayTrace(
                start,
                direction,
                200,
                FluidCollisionMode.NEVER,
                true,
                0.1,
                entity -> entity != player && entity instanceof Damageable
        );
        if (result == null) return;
        Entity entity = result.getHitEntity();

        if (entity instanceof Damageable target) damageTarget(target, player, amount);
    }

    private void damageTarget(Damageable target, Player player, int amount) {
        Location targetLocation = target.getLocation();

        if (target instanceof LivingEntity livingTarget) livingTarget.setNoDamageTicks(0);
        target.damage(amount, player);
        World targetWorld = target.getWorld();
        if (target.getHealth() == 0) {
            targetWorld.spawnParticle(Particle.FIREWORK, targetLocation, 50, 2, 2, 2);
            targetWorld.playSound(targetLocation, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, 200, 0.5f);
        }
        targetWorld.playEffect(targetLocation.add(0, 1.5, 0), Effect.ELECTRIC_SPARK, 12);
    }

    private void laser(Player player, Location start, Vector direction) {
        Location temp = start.clone().add(0, -0.2, 0);
        for (int i = 0; i < 50; i++) {
            player.getWorld().spawnParticle(Particle.DUST, temp, 5, 0, 0, 0, new Particle.DustOptions(Color.GRAY, 0.5f));
            temp.add(direction);
        }
    }

}
