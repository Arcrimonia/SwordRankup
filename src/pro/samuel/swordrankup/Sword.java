package pro.samuel.swordrankup;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by Sam on 8/5/2016.
 */
public class Sword {

    final private ItemMeta meta;
    final private ItemStack item;
    private int level;
    private int currentXP;
    private int finalXP;
    private int updatedXP;
    private int levelLoreLine;
    private int progressLoreLine;
    private List<String> lore;

    public Sword(int xp, Player player) {

        this.item = player.getInventory().getItemInMainHand();
        this.meta = item.getItemMeta();
        lore = meta.getLore();
        for (levelLoreLine = 0; levelLoreLine < lore.size(); levelLoreLine++) {
            String loreLine = ChatColor.stripColor(lore.get(levelLoreLine));
            if (loreLine.contains("Level")) {
                this.level = Integer.parseInt(loreLine.replaceAll("[^0-9]", ""));
                progressLoreLine = levelLoreLine + 1;
                String xpLine = ChatColor.stripColor(lore.get(progressLoreLine));
                String xpProgress = xpLine.replaceAll("[^0-9/]", "");
                String[] splitLore =  xpProgress.split("/");
                currentXP = Integer.parseInt(splitLore[0]);
                finalXP = Integer.parseInt(splitLore[1]);
                updatedXP = currentXP + xp;
                break;
            }
        }
    }

    public void addSwordXP() {
        // Player has levelled up sword
        if (updatedXP >= finalXP) {
            level = level + 1;
            int leftOverXP = updatedXP - finalXP;
            if (leftOverXP < level * 100) {
                addSwordUpgrade();
                updateSword(leftOverXP);
            } else {
                while (leftOverXP > level * 100) {
                    leftOverXP = leftOverXP - level * 100;
                    addSwordUpgrade();
                    level = level + 1;
                }
                updateSword(leftOverXP);
            }
        } else {
            updateSword(updatedXP);
        }
    }

    public void addSwordUpgrade() {

        if (level % 3==0 && !(level % 6==0)) {
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
        } else if (level % 6==0) {
            if (lore.contains("Life Steal")) {
                for (int l = 0; l < lore.size(); l = l++) {
                    String loreLine = ChatColor.stripColor(lore.get(l));
                    if (loreLine.contains("Life Steal")) {
                        int currentHealthGain = Integer.parseInt(loreLine.replaceAll("[^0-9]", ""));
                        lore.set(l, ChatColor.translateAlternateColorCodes('&', "&b+" + currentHealthGain + 10 + " &bLife Steal"));
                    }
                }
            } else {
                lore.add(ChatColor.translateAlternateColorCodes('&', "&b+30% Life Steal"));
            }
        }
        applyMeta();
    }

    public void updateSword(int progress) {

        lore.set(levelLoreLine, ChatColor.translateAlternateColorCodes('&', "&7Level " + level));
        lore.set(progressLoreLine, ChatColor.translateAlternateColorCodes('&', "&7Progress " + progress + "/" + level * 100));
        FileConfiguration config = SwordRankup.getPlugin(SwordRankup.class).getConfig();
        if (config.getBoolean("sword-level-in-name")) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2Level&a " + level + " &2[&a" + progress + "&2/&a" + level * 100 + "&2]"));
        }
        applyMeta();
    }

    public void applyMeta() {
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

//    public int getSwordInfo(String string) {
//
//    }
//
//    public void setSwordInfo(String string) {
//
//    }
}
