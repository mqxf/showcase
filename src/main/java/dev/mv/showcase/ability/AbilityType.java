package dev.mv.showcase.ability;

public enum AbilityType {

    RIGHT_CLICK("RIGHT CLICK"),
    SNEAK_RIGHT_CLICK("SNEAK RIGHT CLICK"),
    SNEAK("SNEAK"),
    LEFT_CLICK("LEFT CLICK"),
    SNEAK_LEFT_CLICK("SNEAK LEFT CLICK"),
    PASSIVE("PASSIVE");

    private String string;

    AbilityType(String string) {
        this.string = string;
    }

    public String toString() {
        return string;
    }

}
