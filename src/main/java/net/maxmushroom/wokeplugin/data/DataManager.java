package net.maxmushroom.wokeplugin.data;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import net.maxmushroom.wokeplugin.WokePlugin;

public class DataManager {
    private final WokePlugin plugin;
    private final File file;
    private final YamlConfiguration config;

    public DataManager(WokePlugin plugin) {
        this.plugin = plugin;

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        this.file = new File(plugin.getDataFolder(), "player-data.yml");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
                plugin.getLogger().info("Created new player-data.yml file.");
            } catch (IOException e) {
                plugin.getLogger().severe("Error while creating player-data.yml: " + e.getMessage());
            }
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);

    }

    public Map<UUID, String> loadSection(String sectionName) {
        Map<UUID, String> output = new ConcurrentHashMap<>();

        ConfigurationSection section = config.getConfigurationSection(sectionName);
        if (section != null) {
            for (String uuidString : section.getKeys(false)) {
                String value = section.getString(uuidString);
                if (value != null && !value.isEmpty()) {
                    try {
                        UUID uuid = UUID.fromString(uuidString);
                        output.put(uuid, value);
                    } catch (IllegalArgumentException e) {
                        plugin.getLogger()
                                .warning("Skipping invalid UUID format in player-data.yml: '" + uuidString + "'");
                    }
                }
            }
            plugin.getLogger()
                    .info("Loaded " + output.size() + " entries from " + sectionName + " in player-data.yml.");
        } else {
            plugin.getLogger().info("No entries from " + sectionName + " were loaded.");
        }

        return output;
    }

    public void saveSection(Map<UUID, String> map, String section) {
        // clear current config
        config.set(section, null);

        // load hashmap into config
        for (Map.Entry<UUID, String> entry : map.entrySet()) {
            config.set(section + "." + entry.getKey().toString(), entry.getValue());
        }

        // save config
        try {
            config.save(file);
            plugin.getLogger().info("Saved " + map.size() + " entries to " + section + " in player-data.yml.");
        } catch (IOException e) { // Catch specific IOException
            plugin.getLogger().severe("Could not save to player-data.yml: " + e.getMessage());
        }
    }
}
