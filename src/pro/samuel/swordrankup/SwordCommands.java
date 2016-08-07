package pro.samuel.swordrankup;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 8/6/2016.
 */
public class SwordCommands implements CommandExecutor {

    FileConfiguration config = SwordRankup.getPlugin(SwordRankup.class).getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("givesword")) {
            if (args.length >= 1) {
                if (args[1].equalsIgnoreCase("diamond")) {
                    Player player = Bukkit.getPlayer(args[0]);
                    ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
                    ItemMeta metadata = sword.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&7Level 1"));
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&7Progress 0/100"));
                    metadata.setLore(lore);
                    if (config.getBoolean("sword-level-in-name") == true) {
                        if (!args[2].isEmpty()) {
                            metadata.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2Diamond Sword Level &a1 &2[&a0&2/&a100&2]"));
                        } else {
                            metadata.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2" + args[2] + "Level &a1 &2[&a0&2/&a100&2]"));
                        }
                    } else {
                        if (!args[2].isEmpty()) {
                            metadata.setDisplayName(ChatColor.translateAlternateColorCodes('&', args[2]));
                        }
                    }
                    sword.setItemMeta(metadata);
                    player.getInventory().addItem(sword);
                }
            }
        }
        return true;
    }
}
