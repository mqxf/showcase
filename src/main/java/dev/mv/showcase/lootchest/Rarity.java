package dev.mv.showcase.lootchest;

public enum Rarity {
    COMMON("COMMON", "&f"),
    UNCOMMON("UNCOMMON", "&a"),
    RARE("RARE", "&9"),
    EPIC("EPIC", "&5"),
    LEGENDARY("LEGENDARY", "&6"),
    MYTHIC("MYTHIC", "&d");

    private String name;
    private String symbol;
    Rarity(String name, String code) {
        this.name = name;
        this.symbol = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return symbol;
    }
}
