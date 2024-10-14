package dev.mv.showcase.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                if (!player.isFlying()) {
                    player.setWalkSpeed(Float.parseFloat(args[0]));
                }
                else {
                    player.setFlySpeed(Float.parseFloat(args[0]));
                }
            }
            else if (args.length == 2) {
                Player player = Bukkit.getPlayer(args[0]);
                if (!player.isFlying()) {
                    player.setWalkSpeed(Float.parseFloat(args[1]));
                }
                else {
                    player.setFlySpeed(Float.parseFloat(args[2]));
                }
            }
        }
        return false;
    }

}
