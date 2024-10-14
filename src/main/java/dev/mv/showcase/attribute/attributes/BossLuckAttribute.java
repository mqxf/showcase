package dev.mv.showcase.attribute.attributes;

import com.google.common.collect.Lists;
import dev.mv.showcase.attribute.Attribute;
import dev.mv.showcase.attribute.Stat;
import dev.mv.showcase.attribute.StatType;
import org.bukkit.entity.Player;

import java.util.List;

public class BossLuckAttribute implements Attribute {
    @Override
    public String getName() {
        return "Boss Luck";
    }

    @Override
    public List<String> getDescription(int level) {
        return Lists.newArrayList("&7When rolling an item in a reward chest", "&7get a &a" + (level * 10) + "% &7chance to make", "&7the attribute level 2.");
    }

    @Override
    public int getMaxLevel() {
        return 10;
    }
}
