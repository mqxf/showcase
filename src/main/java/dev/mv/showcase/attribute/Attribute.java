package dev.mv.showcase.attribute;

import dev.mv.showcase.attribute.attributes.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Attribute {

    String getName();
    List<String> getDescription(int level);
    int getMaxLevel();

    default void onDamaged(Player player, int level) {}

    default void onHit(Player player, int level) {}

    default List<Stat> passiveStats(Player player, int level) {
        return null;
    }

    Map<String, Attribute> registered = new HashMap<>();

    static void register(Attribute attribute) {
        registered.put(attribute.getName(), attribute);
    }

    static Attribute get(String name) {
        return registered.get(name);
    }

    static List<ActiveAttribute> getAttributes(ItemStack item) {
        List<ActiveAttribute> attributes = new ArrayList<>();
        int count = 0;

        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> lore = item.getItemMeta().getLore();
            for (String line : lore) {
                if (line.startsWith("§b")) {
                    String name = line.replaceAll("§b", "").replaceAll(" [1-9]", "");
                    int level = Integer.parseInt(line.replaceAll("§b", "").replaceAll(name, "").replaceAll(" ", ""));
                    if (registered.containsKey(name)) {
                        attributes.add(new ActiveAttribute(registered.get(name), level));
                        count++;
                    }
                    if (count == 2) break;
                }
            }
        }

        return attributes;
    }

    static void registerAttributes() {
        register(new HealthyAttribute());
        register(new TankyAttribute());
        register(new BossLuckAttribute());
        register(new DamagerAttribute());
        register(new SuperiorAttribute());
        register(new MobLuckAttribute());
    }

}
