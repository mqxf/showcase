package dev.mv.showcase.lootchest;

import dev.mv.showcase.attribute.Attribute;
import dev.mv.showcase.utils.Utils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LootItem {

    private LootTable table;
    private BaseItem base;
    private Attribute attribute;

    public LootItem(LootTable table) {
        this.table = table;
        Random random = new Random();
        base = table.getItemOptions().get(random.nextInt(table.getItemOptions().size()));
        attribute = base.getAttributeOptions().get(random.nextInt(base.getAttributeOptions().size()));
    }

    public void rerollAttribute() {
        Random random = new Random();
        attribute = base.getAttributeOptions().get(random.nextInt(base.getAttributeOptions().size()));
    }

    public void reroll() {
        Random random = new Random();
        base = table.getItemOptions().get(random.nextInt(table.getItemOptions().size()));
        attribute = base.getAttributeOptions().get(random.nextInt(base.getAttributeOptions().size()));
    }

    public ItemStack asItemStack() {
        ItemStack item = new ItemStack(base.getMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chat(base.getRarity().getCode() + base.getName()));
        List<String> lore = new ArrayList<>();
        if (base.getHealth() > 0) lore.add("&7Health: &a+" + base.getHealth());
        if (base.getDefence() > 0) lore.add("&7Defence: &a+" + base.getDefence());
        if (base.getDamage() > 0) lore.add("&7Damage: &c+" + base.getDamage());

        if (attribute != null) {
            lore.add("");
            lore.add("&b" + attribute.getName() + " 1");
            lore.addAll(attribute.getDescription(1));
        }

        if (base.getAbilityName() != null) {
            lore.add("");
            lore.add("&6Ability: " + base.getAbilityName() + " &e&l" + base.getAbilityType().toString());
            lore.addAll(base.getAbilityDescription());
        }

        lore.add("");
        lore.add(base.getRarity().getCode() + "&l" + base.getRarity().getName());

        meta.setLore(lore.stream().map(Utils::chat).collect(Collectors.toList()));
        item.setItemMeta(meta);

        return item;
    }

}
