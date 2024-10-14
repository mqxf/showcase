package dev.mv.showcase.listeners;

import dev.mv.showcase.Showcase;
import dev.mv.showcase.utils.Utils;
import dev.mv.showcase.commands.ShowStatsCommand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static dev.mv.showcase.commands.ShowStatsCommand.calcLevel;
import static dev.mv.showcase.commands.ShowStatsCommand.shorten;

public class SpawnListener implements Listener {

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent e) {
        Entity entity = e.getEntity();
        if (entity.getType() == EntityType.PLAYER || entity.getType() == EntityType.ARMOR_STAND) return;
        if (ShowStatsCommand.registered.contains(entity)) return;
        if (!(entity instanceof LivingEntity)) return;
        LivingEntity living = (LivingEntity) entity;
        ShowStatsCommand.registered.add(entity);
        double offset = living.getEyeHeight(false) - 1.8 + (living.getType() == EntityType.WITHER ? 0.3 : 0);
        ArmorStand stand = (ArmorStand) living.getWorld().spawnEntity(living.getLocation().add(0, offset, 0), EntityType.ARMOR_STAND);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setVisible(false);
        stand.setCustomNameVisible(true);

        String type = living.getType().toString().charAt(0) + living.getType().toString().toLowerCase().substring(1);
        final double[] health = {living.getHealth()};
        final double[] max = {living.getMaxHealth()};
        final String[] maxHealth = {shorten(max[0], max[0])};

        stand.setCustomName(Utils.chat("&8[&7Lvl " + calcLevel(living) + "&8] &c" + type + " &a" + shorten(living.getHealth(), living.getHealth()) + "&7/&a" + maxHealth[0]));

        new BukkitRunnable() {
            public void run() {
                if (living.isDead() || living == null) {
                    stand.remove();
                    this.cancel();
                }
                stand.teleport(living.getLocation().add(0, offset, 0));
                if (health[0] == living.getHealth() && max[0] == living.getMaxHealth()) return;
                if (living.getMaxHealth() != max[0]) {
                    max[0] = living.getMaxHealth();
                    maxHealth[0] = shorten(max[0], max[0]);
                }
                health[0] = living.getHealth();
                stand.setCustomName(Utils.chat("&8[&7Lvl " + calcLevel(living) + "&8] &c" + type + " &a" + shorten(living.getHealth(), living.getHealth()) + "&7/&a" + maxHealth[0]));
            }
        }.runTaskTimer(Showcase.getInstance(), 0, 1);
    }

}
