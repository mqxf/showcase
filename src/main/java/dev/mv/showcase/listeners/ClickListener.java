package dev.mv.showcase.listeners;

import dev.mv.showcase.ability.Abilities;
import dev.mv.showcase.ability.Ability;
import dev.mv.showcase.ability.AbilityData;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.List;

public class ClickListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack item = player.getItemInHand();
        if (item!= null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                List<String> lore = item.getItemMeta().getLore();
                String ability;
                if (player.isSneaking()) {
                    if (AbilityData.getAbility(lore, "SNEAK RIGHT CLICK") != null) {
                        ability = AbilityData.getAbility(lore, "SNEAK RIGHT CLICK");
                    }
                    else {
                        ability = AbilityData.getAbility(lore, "RIGHT CLICK");
                    }
                }
                else {
                    ability = AbilityData.getAbility(lore, "RIGHT CLICK");
                }

                if (ability != null) {
                    try {
                        Method method = Abilities.class.getMethod(ability, Player.class);
                        if (method.isAnnotationPresent(Ability.class)) {
                            method.invoke(null, player);
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if (AbilityData.checkQuality(lore, "Shortbow")) {
                    int cooldown = Math.round(AbilityData.getStat(lore, "Shot Cooldown") * 20);
                    if (AbilityData.checkAbility(lore, "Triple Shot")) {
                        Abilities.tripleShot(player, cooldown);
                    }
                    else {
                        Abilities.singleShot(player, cooldown);
                    }
                    e.setCancelled(true);
                }
            }
        }
    }

}
