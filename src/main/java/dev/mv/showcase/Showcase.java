package dev.mv.showcase;

import dev.mv.showcase.attribute.Attribute;
import dev.mv.showcase.commands.*;
import dev.mv.showcase.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Showcase extends JavaPlugin {

    private static Showcase instance;

    public static Showcase getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        getCommand("item").setExecutor(new ItemCommand());
        getCommand("showStats").setExecutor(new ShowStatsCommand());
        getCommand("lootchest").setExecutor(new LootChestCommand());
        getCommand("protect").setExecutor(new ProtectCommand());
        getCommand("speed").setExecutor(new SpeedCommand());
        Bukkit.getPluginManager().registerEvents(new ClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new AbilityListener(), this);
        Bukkit.getPluginManager().registerEvents(new LootChestListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProtectListener(), this);
        Attribute.registerAttributes();
    }
}
