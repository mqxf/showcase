package dev.mv.showcase.attribute.attributes;

import com.google.common.collect.Lists;
import dev.mv.showcase.attribute.Attribute;

import java.util.List;

public class MobLuckAttribute implements Attribute {

    @Override
    public String getName() {
        return "Mob Luck";
    }

    @Override
    public List<String> getDescription(int level) {
        return Lists.newArrayList("&7When killing a mob,", "&7get a &a" + (level * 10) + "% &7chance to", "&7double the drops.");
    }

    @Override
    public int getMaxLevel() {
        return 10;
    }

}
