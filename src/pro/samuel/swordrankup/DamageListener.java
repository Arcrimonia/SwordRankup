package pro.samuel.swordrankup;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by Sam on 8/6/2016.
 */
public class DamageListener implements Listener{
    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() == EntityType.PLAYER) {
            final Player attacker = (Player) event.getDamager();
            PlayerInventory inv = attacker.getInventory();
            ItemMeta meta = inv.getItemInMainHand().getItemMeta();
            List<String> lore = meta.getLore();
            if (inv.getItemInMainHand().getType().toString().toLowerCase().contains("sword")) {
                if (inv.getItemInMainHand().getItemMeta().getLore().toString().contains("Damage")) {
                    for (int l = 0; l < lore.size(); l++) {
                        String loreLine = ChatColor.stripColor(lore.get(l));
                        if (loreLine.contains("Damage")) {
                            int damageBoost = Integer.parseInt(loreLine.replaceAll("[^0-9]", ""));
                            event.setDamage(event.getDamage() + damageBoost);
                        }
                        if (loreLine.contains("Life Steal")) {
                            int lifeStealLevel = Integer.parseInt(loreLine.replaceAll("[^0-9]", ""));
                            int healthGain = (int) Math.round(event.getDamage() * lifeStealLevel / 100);
                            attacker.setHealth(attacker.getHealth() + healthGain);
                        }
                    }
                }

            }
        }
    }
}
