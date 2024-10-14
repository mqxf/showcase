package dev.mv.showcase.utils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class Utils {

    public static String chat(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static ArmorStand armorStand(Location location, String name) {
        ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setBasePlate(false);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setArms(false);
        stand.setCustomName(chat(name));
        stand.setCustomNameVisible(true);
        return stand;
    }

    public static ArmorStand armorStand(Location location, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setBasePlate(false);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setArms(false);
        stand.setHelmet(helmet);
        stand.setChestplate(chestplate);
        stand.setLeggings(leggings);
        stand.setBoots(boots);
        return stand;
    }

    public static ArmorStand armorStand(Location location, String name, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setBasePlate(false);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setArms(false);
        stand.setHelmet(helmet);
        stand.setChestplate(chestplate);
        stand.setLeggings(leggings);
        stand.setBoots(boots);
        stand.setCustomName(chat(name));
        stand.setCustomNameVisible(true);
        return stand;
    }

}
