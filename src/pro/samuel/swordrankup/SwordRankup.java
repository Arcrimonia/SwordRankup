package pro.samuel.swordrankup;

import org.bukkit.Bukkit;
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
        saveDefaultConfig();
        Bukkit.getServer().getPluginManager().registerEvents(new DeathListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new DamageListener(), this);
        this.getCommand("givesword").setExecutor(new SwordCommands());
    }
}
