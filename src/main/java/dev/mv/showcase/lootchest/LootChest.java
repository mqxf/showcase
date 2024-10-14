package dev.mv.showcase.lootchest;

import com.google.common.collect.Lists;
import dev.mv.showcase.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class LootChest {

    private Player player;
    private Location location;
    private boolean rerolledItem = false;
    private boolean rerolledAttribute = false;
    private boolean opened = false;
    private LootItem item;
    ArmorStand chest;
    List<ArmorStand> base = new ArrayList<>();

    public LootChest(Player player) {
        this.player = player;
        location = player.getLocation().add(0, -0.7, 0);

        chest = Utils.armorStand(location, new ItemStack(Material.CHEST), null, null, null);
        base.add(Utils.armorStand(location.clone().add(0.2, -0.5, 0.2), new ItemStack(Material.WOOD), null, null, null));
        base.add(Utils.armorStand(location.clone().add(-0.2, -0.5, 0.2), new ItemStack(Material.WOOD), null, null, null));
        base.add(Utils.armorStand(location.clone().add(0.2, -0.5, -0.2), new ItemStack(Material.WOOD), null, null, null));
        base.add(Utils.armorStand(location.clone().add(-0.2, -0.5, -0.2), new ItemStack(Material.WOOD), null, null, null));

        item = new LootItem(LootChests.lootTable);

        LootChests.lootChests.put(chest, this);
    }

    public LootChest(Player player, Location location) {
        this.player = player;
        location = location.add(0, -0.7, 0);

        chest = Utils.armorStand(location, new ItemStack(Material.CHEST), null, null, null);
        base.add(Utils.armorStand(location.clone().add(0.2, -0.5, 0.2), new ItemStack(Material.WOOD), null, null, null));
        base.add(Utils.armorStand(location.clone().add(-0.2, -0.5, 0.2), new ItemStack(Material.WOOD), null, null, null));
        base.add(Utils.armorStand(location.clone().add(0.2, -0.5, -0.2), new ItemStack(Material.WOOD), null, null, null));
        base.add(Utils.armorStand(location.clone().add(-0.2, -0.5, -0.2), new ItemStack(Material.WOOD), null, null, null));

        item = new LootItem(LootChests.lootTable);

        LootChests.lootChests.put(chest, this);
    }

    public void open(Player player) {
        if (player != this.player) {
            player.sendMessage(Utils.chat("&cThis is not your reward chest!"));
            return;
        }

        Inventory inventory = Bukkit.createInventory(null, 54, Utils.chat("&8Reward chest"));

        for (int i = 0; i < 54; i++) {
            ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
            ItemMeta emptyMeta = empty.getItemMeta();
            emptyMeta.setDisplayName(Utils.chat("&8"));
            empty.setItemMeta(emptyMeta);
            inventory.setItem(i, empty);
        }

        ItemStack claim = new ItemStack(Material.CHEST);
        ItemMeta claimMeta = claim.getItemMeta();
        claimMeta.setDisplayName(Utils.chat("&aClaim reward"));
        claimMeta.setLore(Lists.newArrayList(Utils.chat("&eClick to claim the"), Utils.chat("&ereward in this chest.")));
        claim.setItemMeta(claimMeta);
        inventory.setItem(40, claim);


        inventory.setItem(22, item.asItemStack());

        String attributeColor = rerolledAttribute ? "&c" : "&a";
        String itemColor = rerolledItem ? "&c" : "&a";
        byte attributeDamage = rerolledAttribute ? (byte) 14 : (byte) 5;
        byte itemDamage = rerolledItem ? (byte) 14 : (byte) 5;

        int[] attr = new int[] {27, 28, 29, 36, 38, 45, 46, 47};
        int[] item = new int[] {33, 34, 35, 42, 44, 51, 52, 53};

        for (int i : attr) {
            ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, attributeDamage);
            ItemMeta emptyMeta = empty.getItemMeta();
            emptyMeta.setDisplayName(Utils.chat("&8"));
            empty.setItemMeta(emptyMeta);
            inventory.setItem(i, empty);
        }

        for (int i : item) {
            ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, itemDamage);
            ItemMeta emptyMeta = empty.getItemMeta();
            emptyMeta.setDisplayName(Utils.chat("&8"));
            empty.setItemMeta(emptyMeta);
            inventory.setItem(i, empty);
        }

        ItemStack rerollAttribute = new ItemStack(Material.FEATHER);
        ItemMeta rerollAttributeMeta = rerollAttribute.getItemMeta();
        rerollAttributeMeta.setDisplayName(Utils.chat(attributeColor + "Reroll attribute"));
        if (rerolledAttribute) {
            rerollAttributeMeta.setLore(Lists.newArrayList(Utils.chat("&cYou have already rerolled the"), Utils.chat("&cattribute on this item.")));
        }
        else {
            rerollAttributeMeta.setLore(Lists.newArrayList(Utils.chat("&eClick to reroll the"), Utils.chat("&eattribute on this item.")));
        }
        rerollAttribute.setItemMeta(rerollAttributeMeta);

        inventory.setItem(37, rerollAttribute);

        ItemStack rerollItem = new ItemStack(Material.FEATHER);
        ItemMeta rerollItemMeta = rerollItem.getItemMeta();
        rerollItemMeta.setDisplayName(Utils.chat(attributeColor + "Reroll item"));
        if (rerolledItem) {
            rerollItemMeta.setLore(Lists.newArrayList(Utils.chat("&cYou have already rerolled the"), Utils.chat("&citem in this chest.")));
        }
        else {
            rerollItemMeta.setLore(Lists.newArrayList(Utils.chat("&eClick to reroll the"), Utils.chat("&eitem in this chest.")));
        }
        rerollItem.setItemMeta(rerollItemMeta);

        inventory.setItem(43, rerollItem);

        player.openInventory(inventory);
        LootChests.openLootChests.put(player, this);
    }

    public void processClick(InventoryClickEvent e) {
        e.setCancelled(true);
        ItemStack clicked = e.getCurrentItem();
        if (clicked == null) return;
        if (!clicked.hasItemMeta()) return;
        ItemMeta meta = clicked.getItemMeta();
        if (!meta.hasDisplayName()) return;
        String name = meta.getDisplayName();
        if (name.contains("Close")) {
            player.closeInventory();
            return;
        }
        if (name.contains("Claim")) {
            player.closeInventory();
            player.getInventory().addItem(item.asItemStack());
            dispose();
            return;
        }
        if (name.contains("Reroll item")) {
            if (rerolledItem) {
                player.sendMessage(Utils.chat("&cYou have already rerolled this item."));
            }
            else {
                player.closeInventory();
                item.reroll();
                rerolledItem = true;
                rerolledAttribute = false;
                return;
            }
        }
        if (name.contains("Reroll attribute")) {
            if (rerolledAttribute) {
                player.sendMessage(Utils.chat("&cYou have already rerolled the attribute on this item."));
            }
            else {
                player.closeInventory();
                item.rerollAttribute();
                rerolledAttribute = true;
            }
        }
    }

    public void dispose() {
        chest.remove();
        chest = null;
        base.forEach(armorStand -> armorStand.remove());
        base.clear();
        base = null;
        if (LootChests.openLootChests.containsKey(player)) LootChests.openLootChests.remove(player);
        LootChests.lootChests.remove(chest);
    }
}
