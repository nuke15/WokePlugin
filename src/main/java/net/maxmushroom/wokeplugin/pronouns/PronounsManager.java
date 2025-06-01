package net.maxmushroom.wokeplugin.pronouns;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import net.maxmushroom.wokeplugin.WokePlugin;

public class PronounsManager {
    private final Map<UUID, String> playerPronouns;
    // TODO: colors
    private final WokePlugin plugin;

    private final File file;
    private final YamlConfiguration config;

    public PronounsManager(WokePlugin plugin) {
        this.plugin = plugin;
        this.playerPronouns = new HashMap<>();

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        this.file = new File(plugin.getDataFolder(), "pronouns.yml");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
                plugin.getLogger().info("Created new pronouns.yml file.");
            } catch (IOException e) {
                plugin.getLogger().severe("Error while creating pronouns.yml: " + e.getMessage());
            }
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
        loadPronouns();
    }

    public String getPronouns(UUID uniqueId) {
        return playerPronouns.get(uniqueId);
    }
    
    public void setPronouns(UUID uniqueId, String pronouns) {
        if (pronouns == null || pronouns.trim().isEmpty()) {
            playerPronouns.remove(uniqueId);
        } else {
            playerPronouns.put(uniqueId, pronouns);
        }
    }

    public void loadPronouns() {
        // clear current hashmap
        playerPronouns.clear();

        // get pronouns section of pronouns.yml
        ConfigurationSection section = config.getConfigurationSection("player-pronouns");
        if (section != null) {
            for (String uuidString : section.getKeys(false)) {
                String pronounsValue = section.getString(uuidString);
                if (pronounsValue != null && !pronounsValue.isEmpty()) {
                    try {
                        UUID uuid = UUID.fromString(uuidString);
                        playerPronouns.put(uuid, pronounsValue);
                    } catch (IllegalArgumentException e) {
                        plugin.getLogger().warning("Skipping invalid UUID format in pronouns.yml: '" + uuidString + "'");
                    }
                }
            }
            plugin.getLogger().info("Loaded " + playerPronouns.size() + " pronoun entries from pronouns.yml.");
        } else {
            plugin.getLogger().info("No pronouns loaded: pronouns.yml is empty.");
        }
    }

    public void savePronouns() {
        // clear current config
        config.set("player-pronouns", null);

        // load hashmap into config
        for (Map.Entry<UUID, String> entry : playerPronouns.entrySet()) {
            config.set("player-pronouns." + entry.getKey().toString(), entry.getValue());
        }

        // save config
        try {
            config.save(file);
            plugin.getLogger().info("Saved " + playerPronouns.size() + " pronoun entries to pronouns.yml.");
        } catch (IOException e) { // Catch specific IOException
            plugin.getLogger().severe("Could not save pronouns to pronouns.yml: " + e.getMessage());
        }
    }
}
