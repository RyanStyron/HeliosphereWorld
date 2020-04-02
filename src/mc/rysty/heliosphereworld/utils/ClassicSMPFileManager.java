package mc.rysty.heliosphereworld.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class ClassicSMPFileManager {

    private ClassicSMPFileManager() {
    }

    static ClassicSMPFileManager instance = new ClassicSMPFileManager();

    public static ClassicSMPFileManager getInstance() {
        return instance;
    }

    Plugin plugin;

    FileConfiguration config;
    File configFile;

    FileConfiguration data;
    File dataFile;

    public void setup(Plugin plugin) {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        config = plugin.getConfig();

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        dataFile = new File(plugin.getDataFolder(), "classicsmp.yml");

        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create classicsmp.yml!");
            }
        }

        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public FileConfiguration getData() {
        return data;
    }

    public void saveData() {
        try {
            data.save(dataFile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save classicsmp.yml!");
        }
    }

    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public PluginDescriptionFile getDesc() {
        return plugin.getDescription();
    }

}