package dev.mv.showcase.attribute.attributes;

import com.google.common.collect.Lists;
import dev.mv.showcase.attribute.Attribute;
import dev.mv.showcase.attribute.Stat;
import dev.mv.showcase.attribute.StatType;
import org.bukkit.entity.Player;

import java.util.List;

public class HealthyAttribute implements Attribute {
    @Override
    public String getName() {
        return "Healthy";
    }

    @Override
    public List<String> getDescription(int level) {
        return Lists.newArrayList("&7Gives an extra &c" + (level * 2) + "&7 health.");
    }

    @Override
    public int getMaxLevel() {
        return 10;
    }

    @Override
    public List<Stat> passiveStats(Player player, int level) {
        return Lists.newArrayList(new Stat(StatType.HEALTH, level * 2));
    }
}
