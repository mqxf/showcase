package dev.mv.showcase.attribute;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.List;

public class ActiveAttribute {

    @Getter
    private Attribute attribute;
    @Getter
    private int level;

    public ActiveAttribute(Attribute attribute, int level) {
        this.attribute = attribute;
        this.level = level;
        if (level < 1) level = 1;
        if (level > attribute.getMaxLevel()) level = attribute.getMaxLevel();
    }

    public void onDamaged(Player player) {
        attribute.onDamaged(player, level);
    }

    public void onHit(Player player) {
        attribute.onHit(player, level);
    }

    public List<Stat> passiveStats(Player player) {
        return attribute.passiveStats(player, level);
    }

    public List<String> getDescription() {
        return attribute.getDescription(level);
    }

}
