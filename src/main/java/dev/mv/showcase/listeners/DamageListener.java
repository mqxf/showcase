package dev.mv.showcase.listeners;

import dev.mv.showcase.ability.Abilities;
import dev.mv.showcase.ability.AbilityData;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class DamageListener implements Listener {

    public static HashMap<Player, Integer> damageReduction = new HashMap<>();

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.CUSTOM) return;

        if (e.getDamager().getType() == EntityType.PLAYER) {
            Player player = (Player) e.getDamager();
            ItemStack weapon = player.getItemInHand();
            if (weapon.getType() == Material.BOW) {
                e.setDamage(0);
                return;
            }
            if (weapon != null && weapon.hasItemMeta() && weapon.getItemMeta().hasLore()) {
                float damage = AbilityData.getStat(weapon.getItemMeta().getLore(), "Damage");
                e.setDamage(damage);
            }
        }
        else if (e.getDamager().getType() == EntityType.ARROW) {
            Arrow arrow = (Arrow) e.getDamager();
            if (arrow.getShooter() instanceof Player) {
                Player player = (Player) arrow.getShooter();
                if (e.getEntity().getType() == EntityType.PLAYER && ((Player) e.getEntity()).equals(player)) {
                    e.setCancelled(true);
                    return;
                }
                ItemStack weapon = player.getItemInHand();
                if (weapon!= null && weapon.hasItemMeta() && weapon.getItemMeta().hasLore()) {
                    List<String> lore = weapon.getItemMeta().getLore();
                    float damage = AbilityData.getStat(lore, "Damage");
                    e.setDamage(damage);
                    if (AbilityData.checkAbility(lore, "Shield Ripper")) {
                        int ticks = Math.round(AbilityData.getStat(lore, "Ticks Removed"));
                        Abilities.shieldRipper(player, e.getEntity(), ticks);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().getType() == EntityType.ARMOR_STAND) {
            e.setCancelled(true);
            return;
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
            return;
        }

        if(e.getEntity().getType() == EntityType.PLAYER) {
            Player player = (Player) e.getEntity();
            boolean tanky = false;
            for (ItemStack item : player.getInventory()) {
                if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                    if (AbilityData.checkPassive(item.getItemMeta().getLore(), "Tanky")) {
                        e.setDamage(e.getDamage() * 0.25f);
                        tanky = true;
                        break;
                    }
                }
            }
            if (!tanky) {
                for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                    if (entity.getType() == EntityType.PLAYER) {
                        Player player2 = (Player) entity;
                        for (ItemStack item : player2.getInventory()) {
                            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                                if (AbilityData.checkAbility(item.getItemMeta().getLore(), "Absorb")) {
                                    player2.damage(e.getFinalDamage());
                                    e.setDamage(e.getDamage() * 0.1f);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (e.getEntity().getType() == EntityType.PLAYER) {
            Player player = (Player) e.getEntity();
            if (!damageReduction.containsKey(player)) damageReduction.put(player, 0);
            int reduction = damageReduction.get(player);
            double damage = e.getDamage();
            double newDamage = damage - (damage * (reduction / 100.0));
            if (newDamage < 0) newDamage = 0;
            e.setDamage(newDamage);
        }

        /*
        if (e.getEntity().getType() != EntityType.ARMOR_STAND && e.getEntity() instanceof LivingEntity) {
            long fDamage = Math.round(e.getDamage());
            if (fDamage < 0) fDamage = 0;
            Random random = new Random();
            int angle = random.nextInt(360);
            double x = Math.sin(angle);
            double z = Math.cos(angle);
            double y = (random.nextDouble() * 2) - 1;
            ArmorStand stand = (ArmorStand) e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation().add(x * 0.5, y * 0.5, z * 0.5), EntityType.ARMOR_STAND);
            stand.setVisible(false);
            stand.setGravity(false);
            stand.setBasePlate(false);
            stand.setCustomNameVisible(true);
            stand.setCustomName(Utils.chat("&7" + ShowStatsCommand.shorten(fDamage, fDamage)));

            new BukkitRunnable() {
                @Override
                public void run() {
                    stand.remove();
                }
            }.runTaskLater(Showcase.getInstance(), 20);
        }
        */
    }

}
