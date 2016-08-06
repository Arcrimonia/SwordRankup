package pro.samuel.swordrankup;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by Sam on 8/5/2016.
 */
public class SwordLevel {
    public static void AddSwordXP(int XP, Player player) {
        final ItemStack item = player.getInventory().getItemInMainHand();
        final ItemMeta meta = item.getItemMeta();
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            for (int i = 0; i < lore.size(); i = i++) {
                String loreLine = ChatColor.stripColor(lore.get(i));
                if (loreLine.contains("Level")) {
                    int level = Integer.parseInt(loreLine.replaceAll("[^0-9]", ""));
                    int nextLine = i + 1;
                    String xpLine = ChatColor.stripColor(lore.get(nextLine));
                    String xpProgress = xpLine.replaceAll("[^0-9/]", "");
                    String[] splitLore =  xpProgress.split("/");
                    int currentXP = Integer.parseInt(splitLore[0]);
                    int finalXP = Integer.parseInt(splitLore[1]);
                    int updatedXP = currentXP + XP;
                    Bukkit.getConsoleSender().sendMessage(Integer.toString(currentXP) + Integer.toString(finalXP) + Integer.toString(updatedXP));
                    // Player has levelled up sword
                    if (updatedXP >= finalXP) {
                        int nextLevel = level + 1;
                        int leftOverXP = updatedXP - finalXP;
                        int nextXP = nextLevel * 100;
                        if (leftOverXP < nextXP) {
                            AddSwordUpgrade(item, nextLevel);
                            UpdateSwordItem(item, i, nextLine, nextLevel, leftOverXP, nextXP);
                            break;
                        } else {
                            while (leftOverXP > nextXP) {
                                nextXP = nextLevel * 100;
                                leftOverXP = leftOverXP - nextXP;
                                AddSwordUpgrade(item, nextLevel);
                                nextLevel = nextLevel++;
                            }
                            UpdateSwordItem(item, i, nextLine, nextLevel, leftOverXP, nextXP);
                            break;
                        }
                    } else {
                        UpdateSwordItem(item, i, nextLine, level, updatedXP, level * 100);
                        break;
                    }
                }
            }

        }
    }
    public static void AddSwordUpgrade(ItemStack item, int level) {
        final ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        if (level % 5==0 && !(level % 10==0)) {
            if (lore.contains("Damage")) {
                for (int l = 0; l < lore.size(); l = l++) {
                    String loreLine = ChatColor.stripColor(lore.get(l));
                    if (loreLine.contains("Damage")) {
                        int currentDamageBoost = Integer.parseInt(loreLine.replaceAll("[^0-9]", ""));
                        lore.set(l, ChatColor.translateAlternateColorCodes('&', "&9+" + currentDamageBoost++ + " &9Damage"));
                    }
                }
            } else {
                lore.add(ChatColor.translateAlternateColorCodes('&', "&9+1 Damage"));
            }
        } else if (level % 10==0) {
            if (lore.contains("Life Steal")) {
                for (int l = 0; l < lore.size(); l = l++) {
                    String loreLine = ChatColor.stripColor(lore.get(l));
                    if (loreLine.contains("Life Steal")) {
                        int currentHealthGain = Integer.parseInt(loreLine.replaceAll("[^0-9]", ""));
                        lore.set(l, ChatColor.translateAlternateColorCodes('&', "&b+" + currentHealthGain++ + " &bLife Steal"));
                    }
                }
            } else {
                lore.add(ChatColor.translateAlternateColorCodes('&', "&b+1 Life Steal"));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
    }
    public static void UpdateSwordItem(ItemStack item, int i, int nextLine, int level, int progress, int next) {
        final ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.set(i, ChatColor.translateAlternateColorCodes('&', "&7Level " + level));
        lore.set(nextLine, ChatColor.translateAlternateColorCodes('&', "&7Progress " + progress + "/" + next));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2Level&a " + level + " &2[&a" + progress + "&2/&a" + next + "&2]"));
        meta.setLore(lore);
        item.setItemMeta(meta);
    }
}
