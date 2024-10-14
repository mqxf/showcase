package dev.mv.showcase.ability;

import dev.mv.showcase.Showcase;
import dev.mv.showcase.listeners.DamageListener;
import dev.mv.showcase.utils.Utils;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static dev.mv.showcase.ability.AbilityType.*;

public class Abilities {

    private static Set<Material> transparent = new HashSet<>();

    static {
        transparent.add(Material.AIR);
        transparent.add(Material.WATER);
        transparent.add(Material.STATIONARY_WATER);
        transparent.add(Material.LAVA);
        transparent.add(Material.STATIONARY_LAVA);
        transparent.add(Material.FIRE);
        transparent.add(Material.TRIPWIRE);
        transparent.add(Material.STRING);
        transparent.add(Material.VINE);
        transparent.add(Material.LONG_GRASS);
        transparent.add(Material.CROPS);
        transparent.add(Material.SNOW);
        transparent.add(Material.SAPLING);
        transparent.add(Material.CARROT);
        transparent.add(Material.POTATO);
        transparent.add(Material.SUGAR_CANE_BLOCK);
        transparent.add(Material.DEAD_BUSH);
    }

    private static List<Player> explodeCooldown = new ArrayList<>();
    private static List<Player> teleportCooldown = new ArrayList<>();
    private static List<Player> teleportSpeedCooldown = new ArrayList<>();
    private static List<Player> etherCooldown = new ArrayList<>();
    private static List<Player> singleShotCooldown = new ArrayList<>();
    private static List<Player> tripleShotCooldown = new ArrayList<>();
    private static List<Player> implosionCooldown = new ArrayList<>();
    private static List<Player> shadowWarpCooldown = new ArrayList<>();
    private static List<Player> witherShieldCooldown = new ArrayList<>();
    private static List<Player> witherImpactCooldown = new ArrayList<>();
    private static List<Player> witherImpactShieldCooldown = new ArrayList<>();
    private static List<Player> instantHealCooldown = new ArrayList<>();

    private static void withCooldown(Player player, List<Player> cooldown, int ticks, Runnable task) {
        if (!cooldown.contains(player)) {
            cooldown.add(player);
            task.run();
            new BukkitRunnable() {
                public void run () {
                    cooldown.remove(player);
                }
            }.runTaskLater(Showcase.getInstance(), ticks);
        }
    }

    @Ability(activation = SNEAK)
    public static void explodingBoots(Player player) {
        withCooldown(player, explodeCooldown, 10, () -> {
            player.getWorld().playSound(player.getLocation().add(0, 0.5, 0), Sound.EXPLODE, 1, 0.7f);
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_LARGE, true, (float) player.getLocation().getX(), (float) player.getLocation().getY() + 0.5f, (float) player.getLocation().getZ(), 0.0F, 0.0F, 0.0F, 3.0F, 6, new int[0]);
            for (Player online : Bukkit.getOnlinePlayers()) {
                (((CraftPlayer) online).getHandle()).playerConnection.sendPacket((Packet) packet);
            }
            player.getNearbyEntities(3, 3, 3).forEach(entity -> {
                if (entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        int noDamageTicks = livingEntity.getNoDamageTicks();
                        livingEntity.setNoDamageTicks(0);
                        livingEntity.damage(10);
                        livingEntity.setNoDamageTicks(noDamageTicks);
                        Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(player, livingEntity, EntityDamageEvent.DamageCause.CUSTOM, 10));
                    }
                }
            });
        });
    }

    @Ability(activation = RIGHT_CLICK)
    public static void instantTransmission(Player player) {
        ItemStack item = player.getInventory().getItemInHand();
        int pRange = 0;
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            pRange = Math.round(AbilityData.getStat(item.getItemMeta().getLore(), "Teleport Range"));
        }
        if (pRange <= 0) return;
        int range = pRange;
        withCooldown(player, teleportSpeedCooldown, 100, () -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2, false, false));
        });
        withCooldown(player, teleportCooldown, 1, () -> {
            Block b = null;
            Block previous = null;
            for (Block block : player.getLineOfSight(transparent, range)) {
                if (transparent.contains(block.getType())) {
                    previous = block;
                    continue;
                }
                b = block;
                break;
            }
            if (b == null) {
                b = player.getLineOfSight(transparent, range).get(player.getLineOfSight(transparent, range).size() - 1);
            } else {
                b = previous;
            }
            Location loc = new Location(b.getWorld(), b.getX(), b.getY(), b.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
            if (!transparent.contains(player.getWorld().getBlockAt(loc.clone().add(0, 1, 0)).getType())) {
                loc = loc.add(0, -1, 0);
            }
            player.teleport(loc.add(0.5, 0, 0.5));
            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        });
    }

    @Ability(activation = SNEAK_RIGHT_CLICK)
    public static void etherTransmission(Player player) {
        ItemStack item = player.getInventory().getItemInHand();
        int pRange = 0;
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            pRange = Math.round(AbilityData.getStat(item.getItemMeta().getLore(), "Ether Range"));
        }
        if (pRange <= 0) return;
        int range = pRange;
        withCooldown(player, etherCooldown, 5, () -> {
            Block b = player.getTargetBlock(transparent, range);
            if (b.isEmpty() || b.isLiquid() || b.getType() == Material.AIR) return;
            Block above = b.getWorld().getBlockAt(b.getLocation().add(0, 1, 0));
            if (above.getType() != Material.AIR) return;
            above = b.getWorld().getBlockAt(b.getLocation().add(0, 2, 0));
            if (above.getType() != Material.AIR) return;
            Location loc = new Location(b.getWorld(), b.getX() + 0.5, b.getY() + 1, b.getZ() + 0.5, player.getLocation().getYaw(), player.getLocation().getPitch());
            player.teleport(loc);
            player.playSound(loc, Sound.ENDERMAN_TELEPORT, 1.0f, 0.7f);
        });
    }

    public static void singleShot(Player player, int cooldown) {
        withCooldown(player, singleShotCooldown, cooldown, () -> {
            Arrow arrow = player.getWorld().spawnArrow(player.getEyeLocation(), player.getEyeLocation().getDirection(), 2, 0);
            arrow.setShooter(player);
        });
    }

    public static void tripleShot(Player player, int cooldown) {
        withCooldown(player, tripleShotCooldown, cooldown, () -> {
            Location loc = player.getEyeLocation();
            int angle = 15;
            Arrow arrow = player.getWorld().spawnArrow(loc, loc.getDirection(), 2, 0);
            Arrow arrow1 = player.getWorld().spawnArrow(loc, new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw() + angle, loc.getPitch()).getDirection(), 2, 0);
            Arrow arrow2 = player.getWorld().spawnArrow(loc, new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw() - angle, loc.getPitch()).getDirection(), 2, 0);
            arrow.setShooter(player);
            arrow1.setShooter(player);
            arrow2.setShooter(player);
        });
    }

    public static void shieldRipper(Player player, Entity entity, int amount) {
        if (amount <= 0) return;
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;
            int ticks = living.getMaximumNoDamageTicks();
            if (ticks >= amount) {
                living.setMaximumNoDamageTicks(ticks - amount);
                living.setNoDamageTicks(ticks - amount);
            }
            else if (ticks > 0) {
                living.setMaximumNoDamageTicks(0);
                living.setNoDamageTicks(0);
            }
            else {
                player.sendMessage(Utils.chat("&cThis entity's shield has already been removed."));
            }
        }
    }

    @Ability(activation = RIGHT_CLICK)
    public static void implosion(Player player) {
        withCooldown(player, implosionCooldown, 200, () -> {
            player.getWorld().playSound(player.getLocation().add(0, 0.5, 0), Sound.EXPLODE, 1, 0.7f);
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_LARGE, true, (float) player.getLocation().getX(), (float) player.getLocation().getY() + 0.5f, (float) player.getLocation().getZ(), 0.0F, 0.0F, 0.0F, 7.0F, 6, new int[0]);
            for (Player online : Bukkit.getOnlinePlayers()) {
                (((CraftPlayer) online).getHandle()).playerConnection.sendPacket((Packet) packet);
            }
            player.getNearbyEntities(5, 5, 5).forEach(entity -> {
                if (entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        int noDamageTicks = livingEntity.getNoDamageTicks();
                        livingEntity.setNoDamageTicks(0);
                        livingEntity.damage(20);
                        livingEntity.setNoDamageTicks(noDamageTicks);
                        Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(player, livingEntity, EntityDamageEvent.DamageCause.CUSTOM, 20));
                    }
                }
            });
        });
    }

    @Ability(activation = RIGHT_CLICK)
    public static void shadowWarp(Player player) {
        withCooldown(player, shadowWarpCooldown, 200, () -> {
            Block b = null;
            Block previous = null;
            for (Block block : player.getLineOfSight(transparent, 10)) {
                if (transparent.contains(block.getType())) {
                    previous = block;
                    continue;
                }
                b = block;
                break;
            }
            if (b == null) {
                b = player.getLineOfSight(transparent, 10).get(player.getLineOfSight(transparent, 10).size() - 1);
            } else {
                b = previous;
            }
            Location loc = new Location(b.getWorld(), b.getX(), b.getY(), b.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
            if (!transparent.contains(player.getWorld().getBlockAt(loc.clone().add(0, 1, 0)).getType())) {
                loc = loc.add(0, -1, 0);
            }
            player.teleport(loc.add(0.5, 0, 0.5));
        });
    }

    @Ability(activation = RIGHT_CLICK)
    public static void witherShield(Player player) {
        withCooldown(player, witherShieldCooldown, 200, () -> {
            if (!DamageListener.damageReduction.containsKey(player)) DamageListener.damageReduction.put(player, 0);
            DamageListener.damageReduction.put(player, DamageListener.damageReduction.get(player) + 20);
            EntityLiving NMSPlayer = ((CraftPlayer) player).getHandle();
            NMSPlayer.setAbsorptionHearts((float) (NMSPlayer.getAbsorptionHearts() + (player.getMaxHealth() * 0.5f)));
            new BukkitRunnable() {
                @Override
                public void run() {
                    DamageListener.damageReduction.put(player, DamageListener.damageReduction.get(player) - 20);
                    float absorption = NMSPlayer.getAbsorptionHearts();
                    if (absorption > player.getMaxHealth() * 0.5f) {
                        absorption = (float) (player.getMaxHealth() * 0.5f);
                        NMSPlayer.setAbsorptionHearts(NMSPlayer.getAbsorptionHearts() - absorption);
                    }
                    else {
                        NMSPlayer.setAbsorptionHearts(0);
                    }
                    double newHealth = player.getHealth() + (absorption * 0.5f);
                    if (newHealth > player.getMaxHealth()) {
                        player.setHealth(player.getMaxHealth());
                    }
                    else {
                        player.setHealth(newHealth);
                    }
                }
            }.runTaskLater(Showcase.getInstance(), 100);
        });
    }

    public static void witherImpactShield(Player player) {
        withCooldown(player, witherImpactShieldCooldown, 100, () -> {
            if (!DamageListener.damageReduction.containsKey(player)) DamageListener.damageReduction.put(player, 0);
            DamageListener.damageReduction.put(player, DamageListener.damageReduction.get(player) + 20);
            EntityLiving NMSPlayer = ((CraftPlayer) player).getHandle();
            NMSPlayer.setAbsorptionHearts((float) (NMSPlayer.getAbsorptionHearts() + (player.getMaxHealth() * 0.75f)));
            new BukkitRunnable() {
                @Override
                public void run() {
                    DamageListener.damageReduction.put(player, DamageListener.damageReduction.get(player) - 20);
                    float absorption = NMSPlayer.getAbsorptionHearts();
                    if (absorption > player.getMaxHealth() * 0.75f) {
                        absorption = (float) (player.getMaxHealth() * 0.75f);
                        NMSPlayer.setAbsorptionHearts(NMSPlayer.getAbsorptionHearts() - absorption);
                    }
                    else {
                        NMSPlayer.setAbsorptionHearts(0);
                    }
                    double newHealth = player.getHealth() + (absorption * 0.75f);
                    if (newHealth > player.getMaxHealth()) {
                        player.setHealth(player.getMaxHealth());
                    }
                    else {
                        player.setHealth(newHealth);
                    }
                }
            }.runTaskLater(Showcase.getInstance(), 100);
        });
    }

    @Ability(activation = RIGHT_CLICK)
    public static void witherImpact(Player player) {
        witherImpactShield(player);
        withCooldown(player, witherImpactCooldown, 2, () -> {
            Block b = null;
            Block previous = null;
            for (Block block : player.getLineOfSight(transparent, 10)) {
                if (transparent.contains(block.getType())) {
                    previous = block;
                    continue;
                }
                b = block;
                break;
            }
            if (b == null) {
                b = player.getLineOfSight(transparent, 10).get(player.getLineOfSight(transparent, 10).size() - 1);
            } else {
                b = previous;
            }
            Location loc = new Location(b.getWorld(), b.getX(), b.getY(), b.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
            if (!transparent.contains(player.getWorld().getBlockAt(loc.clone().add(0, 1, 0)).getType())) {
                loc = loc.add(0, -1, 0);
            }
            player.teleport(loc.add(0.5, 0, 0.5));
            player.getWorld().playSound(player.getLocation().add(0, 0.5, 0), Sound.EXPLODE, 1, 0.7f);
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_LARGE, true, (float) player.getLocation().getX(), (float) player.getLocation().getY() + 0.5f, (float) player.getLocation().getZ(), 0.0F, 0.0F, 0.0F, 7.0F, 6, new int[0]);
            for (Player online : Bukkit.getOnlinePlayers()) {
                (((CraftPlayer) online).getHandle()).playerConnection.sendPacket((Packet) packet);
            }
            ItemStack hyp = player.getItemInHand();
            int hypDamage;
            if (hyp != null && hyp.hasItemMeta() && hyp.getItemMeta().hasDisplayName()) {
                if (hyp.getItemMeta().getDisplayName().contains("➎")) {
                    hypDamage = 10000;
                } else {
                    hypDamage = 40;
                }
            } else {
                hypDamage = 40;
            }
            player.getNearbyEntities(7, 7, 7).forEach(entity -> {
                if (entity.getType() != EntityType.ARMOR_STAND && entity.getType() != EntityType.PLAYER) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        int noDamageTicks = livingEntity.getNoDamageTicks();
                        livingEntity.setNoDamageTicks(0);
                        livingEntity.damage(hypDamage);
                        livingEntity.setNoDamageTicks(noDamageTicks);
                        Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(player, livingEntity, EntityDamageEvent.DamageCause.CUSTOM, hypDamage));
                    }
                }
            });
        });
    }

    @Ability(activation = RIGHT_CLICK)
    public static void instantHeal(Player player) {
        withCooldown(player, instantHealCooldown, 40, () -> {
            double health = player.getMaxHealth() * 0.25f;
            double oldHealth = player.getHealth();
            if (player.getHealth() + health > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }
            else {
                player.setHealth(player.getHealth() + health);
            }
            player.sendMessage(Utils.chat("&a&lHealed yourself for " + String.format("%.1f", player.getHealth() - oldHealth) + " health."));
            for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                if (entity.getType() == EntityType.PLAYER) {
                    Player p = (Player) entity;
                    double pHealth = p.getMaxHealth() * 0.1f;
                    double pOldHealth = p.getHealth();
                    if (p.getHealth() + pHealth > p.getMaxHealth()) {
                        p.setHealth(p.getMaxHealth());
                    }
                    else {
                        p.setHealth(p.getHealth() + pHealth);
                    }
                    p.sendMessage(Utils.chat("&a&l" + player.getDisplayName() + " has healed you for " + String.format("%.1f", p.getHealth() - pOldHealth) + " health."));
                }
            }
        });
    }

}
