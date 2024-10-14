package dev.mv.showcase.lootchest;

import dev.mv.showcase.attribute.Attribute;
import dev.mv.showcase.attribute.attributes.*;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LootChests {

    public static LootTable lootTable;

    static {
        List<BaseItem> items = new ArrayList<>();
        List<Attribute> armorAttributes = new ArrayList<>();
        List<Attribute> weaponsAttributes = new ArrayList<>();

        armorAttributes.add(new HealthyAttribute());
        armorAttributes.add(new TankyAttribute());
        armorAttributes.add(new BossLuckAttribute());

        weaponsAttributes.add(new DamagerAttribute());
        weaponsAttributes.add(new SuperiorAttribute());
        weaponsAttributes.add(new MobLuckAttribute());

        items.add(new BaseItem(Material.DIAMOND_HELMET, "Boss Helmet", Rarity.LEGENDARY, 10, 5, 0, armorAttributes));
        items.add(new BaseItem(Material.DIAMOND_CHESTPLATE, "Boss Chestplate", Rarity.LEGENDARY, 20, 10, 0, armorAttributes));
        items.add(new BaseItem(Material.DIAMOND_LEGGINGS, "Boss Leggings", Rarity.LEGENDARY, 15, 7, 0, armorAttributes));
        items.add(new BaseItem(Material.DIAMOND_BOOTS, "Boss Boots", Rarity.LEGENDARY, 5, 2, 0, armorAttributes));
        items.add(new BaseItem(Material.DIAMOND_SWORD, "Boss Sword", Rarity.LEGENDARY, 0, 0, 20, weaponsAttributes));
        lootTable = new LootTable(items);
    }

    public static Map<ArmorStand, LootChest> lootChests = new HashMap<>();
    public static Map<Player, LootChest> openLootChests = new HashMap<>();

}
