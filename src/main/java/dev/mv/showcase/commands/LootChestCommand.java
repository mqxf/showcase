package dev.mv.showcase.commands;

import dev.mv.showcase.lootchest.LootChest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LootChestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                Player p = Bukkit.getPlayer(args[0]);
                if (p != null) {
                    LootChest chest = new LootChest(p, player.getLocation());
                }
                else {
                    LootChest chest = new LootChest(player);
                }
            }
            else {
                LootChest chest = new LootChest(player);
            }
        }

        return false;
    }
}
