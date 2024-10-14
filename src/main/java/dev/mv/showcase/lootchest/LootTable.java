package dev.mv.showcase.lootchest;

import java.util.List;

public class LootTable {

    private List<BaseItem> itemOptions;

    public LootTable(List<BaseItem> itemOptions) {
        this.itemOptions = itemOptions;
    }

    public List<BaseItem> getItemOptions() {
        return itemOptions;
    }

}
