package dev.mv.showcase.attribute.attributes;

import com.google.common.collect.Lists;
import dev.mv.showcase.attribute.Attribute;
import dev.mv.showcase.attribute.Stat;
import dev.mv.showcase.attribute.StatType;
import org.bukkit.entity.Player;

import java.util.List;

public class TankyAttribute implements Attribute {
    @Override
    public String getName() {
        return "Tanky";
    }

    @Override
    public List<String> getDescription(int level) {
        return Lists.newArrayList("&7Gives an extra &a" + (level * 2) + "&7 defence.");
    }

    @Override
    public int getMaxLevel() {
        return 10;
    }

    @Override
    public List<Stat> passiveStats(Player player, int level) {
        return Lists.newArrayList(new Stat(StatType.DEFENCE, level * 2));
    }
}
