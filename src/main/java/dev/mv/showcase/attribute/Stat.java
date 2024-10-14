package dev.mv.showcase.attribute;


import lombok.Getter;

public class Stat {

    @Getter
    private StatType type;
    @Getter
    private double value;

    public Stat(StatType type, double value) {
        this.type = type;
        this.value = value;
    }

}
