package dev.mv.showcase.attribute.attributes;

import com.google.common.collect.Lists;
import dev.mv.showcase.attribute.Attribute;

import java.util.List;

public class SuperiorAttribute implements Attribute {

    @Override
    public String getName() {
        return "Superior";
    }

    @Override
    public List<String> getDescription(int level) {
        return Lists.newArrayList("&7Increases all stats of this", "&7weapon by &a" + (level * 2) + "%&7, including", "&7damage of abilities.");
    }

    @Override
    public int getMaxLevel() {
        return 10;
    }

}
