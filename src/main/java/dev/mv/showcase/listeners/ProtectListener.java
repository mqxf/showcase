package dev.mv.showcase.listeners;

import dev.mv.showcase.commands.ProtectCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ProtectListener implements Listener {

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        if (ProtectCommand.protect) {
            event.setCancelled(true);
        }
    }

}
