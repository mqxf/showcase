package dev.mv.showcase.listeners;

import dev.mv.showcase.lootchest.LootChest;
import dev.mv.showcase.lootchest.LootChests;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class LootChestListener implements Listener {

    @EventHandler
    public void onClickEntity(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            e.setCancelled(true);
            e.getPlayer().updateInventory();
            ArmorStand stand = (ArmorStand) e.getRightClicked();
            if (LootChests.lootChests.containsKey(stand)) {
                LootChest chest = LootChests.lootChests.get(stand);
                chest.open(e.getPlayer());
            }
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player player = (Player) e.getWhoClicked();
            if (LootChests.openLootChests.containsKey(player)) {
                LootChest chest = LootChests.openLootChests.get(player);
                chest.processClick(e);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player player = (Player) e.getPlayer();
            if (LootChests.openLootChests.containsKey(player)) {
                LootChests.openLootChests.remove(player);
            }
        }
    }

}
