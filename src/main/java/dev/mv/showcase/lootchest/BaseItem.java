package dev.mv.showcase.lootchest;

import dev.mv.showcase.ability.AbilityType;
import dev.mv.showcase.attribute.Attribute;
import lombok.Getter;
import org.bukkit.Material;

import java.util.List;

public class BaseItem {

    @Getter
    private Material material;
    @Getter
    private String name;
    @Getter
    private Rarity rarity;
    @Getter
    private int health;
    @Getter
    private int defence;
    @Getter
    private int damage;
    @Getter
    private String abilityName;
    @Getter
    private AbilityType abilityType;
    @Getter
    private List<String> abilityDescription;
    @Getter
    private List<Attribute> attributeOptions;

    public BaseItem(Material material, String name, Rarity rarity, int health, int defence, int damage, String abilityName, AbilityType abilityType, List<String> abilityDescription, List<Attribute> attributeOptions) {
        this.material = material;
        this.name = name;
        this.rarity = rarity;
        this.health = health;
        this.defence = defence;
        this.damage = damage;
        this.abilityName = abilityName;
        this.abilityType = abilityType;
        this.abilityDescription = abilityDescription;
        this.attributeOptions = attributeOptions;
    }

    public BaseItem(Material material, String name, Rarity rarity, int health, int defence, int damage, List<Attribute> attributeOptions) {
        this.material = material;
        this.name = name;
        this.rarity = rarity;
        this.health = health;
        this.defence = defence;
        this.damage = damage;
        this.attributeOptions = attributeOptions;
    }
}
