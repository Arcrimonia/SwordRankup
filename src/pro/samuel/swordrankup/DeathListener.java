package pro.samuel.swordrankup;

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

    FileConfiguration config = SwordRankup.getPlugin(SwordRankup.class).getConfig();

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        final LivingEntity victim = event.getEntity();
        EntityDamageEvent damageEvent = victim.getLastDamageCause();
        Player killer = victim.getKiller();
        if (killer != null) {
            PlayerInventory inv = killer.getInventory();
            if(inv.getItemInMainHand().getType().toString().toLowerCase().contains("sword") && inv.getItemInMainHand().getItemMeta().getLore().toString().contains("Level")) {
                EntityType type = victim.getType();
                Sword sword = new Sword(config.getInt("mobs." + type.toString().toLowerCase()), killer);
                sword.addSwordXP();
            }
        }
    }

}
