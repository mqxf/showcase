package dev.mv.showcase.attribute.attributes;

import com.google.common.collect.Lists;
import dev.mv.showcase.attribute.Attribute;

import java.util.List;

public class DamagerAttribute implements Attribute {

    @Override
    public String getName() {
        return "Damager";
    }

    @Override
    public List<String> getDescription(int level) {
        return Lists.newArrayList("&7Increases damage by &c" + (level * 5) + "%&7.");
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

}
