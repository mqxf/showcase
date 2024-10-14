package dev.mv.showcase.commands;

import dev.mv.showcase.Showcase;
import dev.mv.showcase.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ShowStatsCommand implements CommandExecutor {

    public static List<Entity> registered = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            for (Entity entity : player.getNearbyEntities(200, 200, 200)) {
                if (entity.getType() == EntityType.PLAYER || entity.getType() == EntityType.ARMOR_STAND) continue;
                if (registered.contains(entity)) continue;
                if (!(entity instanceof LivingEntity)) continue;
                LivingEntity living = (LivingEntity) entity;
                registered.add(entity);
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
        return false;
    }

    public static int calcLevel(LivingEntity living) {
        int level = 0;

        level += Math.round(Math.sqrt(5 * living.getMaxHealth()));

        if (living.getType() == EntityType.ENDERMAN) level *= 1.5;

        return level;
    }

    public static String shorten(double standard, double number) {
        long rounded = Math.round(number);

        if (standard >= 1000000000000000000L) {
            double digits = rounded / 1000000000000000000f;
            return String.format("%.1fP", digits);
        }
        else if (standard >= 1000000000000000L) {
            double digits = rounded / 1000000000000000f;
            if (number > 10000000000000000L) {
                return Math.round(digits) + "Q";
            }
            else {
                return String.format("%.1fQ", digits);
            }
        }
        else if (standard >= 1000000000000L) {
            double digits = rounded / 1000000000000f;
            if (number > 10000000000000L) {
                return Math.round(digits) + "T";
            }
            else {
                return String.format("%.1fT", digits);
            }
        }
        else if (standard >= 1000000000) {
            double digits = rounded / 1000000000f;
            if (number >= 10000000000L) {
                return Math.round(digits) + "B";
            }
            else {
                return String.format("%.1fB", digits);
            }
        }
        else if (standard >= 1000000) {
            double digits = rounded / 1000000f;
            if (number >= 10000000) {
                return Math.round(digits) + "M";
            }
            else {
                return String.format("%.1fM", digits);
            }
        }
        else if (standard >= 1000) {
            double digits = rounded / 1000f;
            if (number >= 10000) {
                return Math.round(digits) + "k";
            }
            else {
                return String.format("%.1fk", digits);
            }
        }
        return rounded + "";
    }

}
