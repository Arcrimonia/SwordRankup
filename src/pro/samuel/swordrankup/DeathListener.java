package pro.samuel.swordrankup;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.PlayerInventory;

/**
 * Created by Sam on 8/5/2016.
 */
public class DeathListener implements Listener {
    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        final LivingEntity victim = event.getEntity();
        EntityDamageEvent damageEvent = victim.getLastDamageCause();
        Player killer = victim.getKiller();
        if (killer != null) {
            PlayerInventory inv = killer.getInventory();
            if(inv.getItemInMainHand().getType().toString().toLowerCase().contains("sword") && inv.getItemInMainHand().getItemMeta().getLore().toString().contains("Level")) {
                EntityType type = victim.getType();
                FileConfiguration config = SwordRankup.getPlugin(SwordRankup.class).getConfig();
                Bukkit.getConsoleSender().sendMessage("mobs." + type.toString());
                Bukkit.getConsoleSender().sendMessage(Integer.toString(config.getInt("mobs." + type.toString())));
                SwordLevel.AddSwordXP(config.getInt("mobs." + type.toString().toLowerCase()), killer);
            }
        }
    }

}
