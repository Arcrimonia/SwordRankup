package pro.samuel.swordrankup;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by Sam on 8/5/2016.
 */
public class SwordRankup extends JavaPlugin{
    public void onEnable() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                saveDefaultConfig();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        FileConfiguration config = getConfig();
        config.addDefault("mobs.bat", 5);
        config.addDefault("mobs.blaze", 12);
        config.addDefault("mobs.cave_spider", 8);
        config.addDefault("mobs.chicken", 4);
        config.addDefault("mobs.cow", 5);
        config.addDefault("mobs.creeper", 14);
        config.addDefault("mobs.ender_dragon", 650);
        config.addDefault("mobs.enderman", 25);
        config.addDefault("mobs.endermite", 9);
        config.addDefault("mobs.ghast", 15);
        config.addDefault("mobs.giant", 125);
        config.addDefault("mobs.guardian", 16);
        config.addDefault("mobs.horse", 5);
        config.addDefault("mobs.iron_golem", 20);
        config.addDefault("mobs.magma_cube", 7);
        config.addDefault("mobs.mushroom_cow", 5);
        config.addDefault("mobs.ocelot", 4);
        config.addDefault("mobs.pig", 5);
        config.addDefault("mobs.pig_zombie", 14);
        config.addDefault("mobs.polar_bear", 12);
        config.addDefault("mobs.player", 100);
        config.addDefault("mobs.rabbit", 4);
        config.addDefault("mobs.sheep", 5);
        config.addDefault("mobs.shulker", 10);
        config.addDefault("mobs.silverfish", 8);
        config.addDefault("mobs.skeleton", 12);
        config.addDefault("mobs.slime", 6);
        config.addDefault("mobs.snowman", 5);
        config.addDefault("mobs.spider", 8);
        config.addDefault("mobs.squid", 5);
        config.addDefault("mobs.villager", 5);
        config.addDefault("mobs.witch", 15);
        config.addDefault("mobs.wither", 450);
        config.addDefault("mobs.zombie", 13);
        config.options().copyDefaults(true);
        saveConfig();
        Bukkit.getServer().getPluginManager().registerEvents(new DeathListener(), this);
        this.getCommand("givesword").setExecutor(new SwordCommands());
    }
}
