package dev.mv.showcase.ability;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AbilityData {

    public static void setStats(Player player) {

        double maxHealth = 20;

        ItemStack boots = player.getInventory().getBoots();
        if (boots != null && boots.hasItemMeta() && boots.getItemMeta().hasLore()) {
            ItemMeta bootsMeta = boots.getItemMeta();
            List<String> lore = bootsMeta.getLore();
            maxHealth += getStat(lore, "Health");
        }

        ItemStack leggings = player.getInventory().getLeggings();
        if (leggings != null && leggings.hasItemMeta() && leggings.getItemMeta().hasLore()) {
            ItemMeta leggingsMeta = leggings.getItemMeta();
            List<String> lore = leggingsMeta.getLore();
            maxHealth += getStat(lore, "Health");
        }

        ItemStack chestplate = player.getInventory().getChestplate();
        if (chestplate != null && chestplate.hasItemMeta() && chestplate.getItemMeta().hasLore()) {
            ItemMeta chestplateMeta = chestplate.getItemMeta();
            List<String> lore = chestplateMeta.getLore();
            maxHealth += getStat(lore, "Health");
        }

        ItemStack helmet = player.getInventory().getHelmet();
        if (helmet != null && helmet.hasItemMeta() && helmet.getItemMeta().hasLore()) {
            ItemMeta helmetMeta = helmet.getItemMeta();
            List<String> lore = helmetMeta.getLore();
            maxHealth += getStat(lore, "Health");
        }

        player.setMaxHealth(maxHealth);
    }

    public static float getStat(List<String> lore, String id) {
        for (String stat : lore) {
            if (stat.contains(id + ":")) {
                return getStat(stat);
            }
        }
        return 0;
    }

    public static float getStat(String stat) {
        String[] split = stat.split(":");
        String amount = split[1].replaceAll(" ", "").replaceAll("§[a-z0-9A-Z]", "").replaceAll("[a-zA-Z]", "");
        return Float.parseFloat(amount);
    }

    public static boolean checkAbility(List<String> lore, String name) {
        for (String line : lore) {
            if (line.contains("Ability:")) {
                String[] split = line.split(":");
                if (split[1].contains(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getAbility(List<String> lore, String abilityType) {
        for (String line : lore) {
            if (line.contains("Ability:")) {
                String[] split = line.split(":");
                if (split[1].contains(abilityType)) {
                    String name = split[1].replaceAll(abilityType, "").replaceAll("§[a-z0-9A-Z]", "").replaceAll(" ", "");
                    return (name.charAt(0) + "").toLowerCase() + name.substring(1);
                }
            }
        }
        return null;
    }

    public static boolean checkPassive(List<String> lore, String name) {
        for (String line : lore) {
            if (line.contains("Passive:")) {
                String[] split = line.split(":");
                if (split[1].contains(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkQuality(List<String> lore, String name) {
        for (String line : lore) {
            if (line.contains("Item Quality:")) {
                String[] split = line.split(":");
                if (split[1].contains(name)) {
                    return true;
                }
            }
        }
        return false;
    }

}
