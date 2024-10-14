package dev.mv.showcase.listeners;

import com.codingforcookies.armorequip.ArmorEquipEvent;
import dev.mv.showcase.Showcase;
import dev.mv.showcase.ability.Abilities;
import dev.mv.showcase.ability.Ability;
import dev.mv.showcase.ability.AbilityData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Method;
import java.util.List;

public class AbilityListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        AbilityData.setStats(player);
    }

    @EventHandler
    public void onEquipmentChange(ArmorEquipEvent e) {
        Player player = e.getPlayer();
        (new BukkitRunnable() {
            public void run () {
                AbilityData.setStats(player);
            }
        }).runTaskLater(Showcase.getInstance(), 1);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if (e.isSneaking()) {
            Player player = e.getPlayer();
            ItemStack boots = player.getInventory().getBoots();
            if (boots != null && boots.hasItemMeta() && boots.getItemMeta().hasLore()) {
                List<String> lore = boots.getItemMeta().getLore();
                String ability = AbilityData.getAbility(lore, "SNEAK");

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
            }
        }
    }

}
