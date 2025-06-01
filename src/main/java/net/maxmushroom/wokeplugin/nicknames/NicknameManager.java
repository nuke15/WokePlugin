package net.maxmushroom.wokeplugin.nicknames;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.maxmushroom.wokeplugin.WokePlugin;

public class NicknameManager {
    private final Map<UUID, String> playerNicknames;
    // TODO: colors
    private final WokePlugin plugin;

    private final File file;
    private final YamlConfiguration config;

    public NicknameManager(WokePlugin plugin) {
        this.plugin = plugin;
        this.playerNicknames = new HashMap<>();

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        this.file = new File(plugin.getDataFolder(), "nicknames.yml");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
                plugin.getLogger().info("Created new nicknames.yml file.");
            } catch (IOException e) {
                plugin.getLogger().severe("Error while creating nicknames.yml: " + e.getMessage());
            }
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);

        loadNicknames();
    }

    public void updateNickname(UUID uuid) {
        // get nickname from hashmap
        String nickname = playerNicknames.get(uuid);
        // build component
        if (nickname != null) {
            Component nicknameComponent = Component.text(nickname)
                    .hoverEvent(HoverEvent.showText(Component.text(Bukkit.getPlayer(uuid).getName())));
            Bukkit.getPlayer(uuid).displayName(nicknameComponent);
        } else {
            Bukkit.getPlayer(uuid).displayName();
        }
    }

    public void setNickname(UUID uuid, String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            playerNicknames.remove(uuid);

        } else {
            playerNicknames.put(uuid, nickname);
        }
        updateNickname(uuid);
    }

    public void loadNicknames() {
        // clear current hashmap
        playerNicknames.clear();

        // get pronouns section of pronouns.yml
        ConfigurationSection section = config.getConfigurationSection("player-nicknames");
        if (section != null) {
            for (String uuidString : section.getKeys(false)) {
                String pronounsValue = section.getString(uuidString);
                if (pronounsValue != null && !pronounsValue.isEmpty()) {
                    try {
                        UUID uuid = UUID.fromString(uuidString);
                        playerNicknames.put(uuid, pronounsValue);
                    } catch (IllegalArgumentException e) {
                        plugin.getLogger()
                                .warning("Skipping invalid UUID format in nicknames.yml: '" + uuidString + "'");
                    }
                }
            }
            plugin.getLogger().info("Loaded " + playerNicknames.size() + " nickname entries from nicknames.yml.");
        } else {
            plugin.getLogger().info("No nicknames loaded: nicknames.yml is empty.");
        }
    }

    public void saveNicknames() {
        // clear current config
        config.set("player-nicknames", null);

        // load hashmap into config
        for (Map.Entry<UUID, String> entry : playerNicknames.entrySet()) {
            config.set("player-nicknames." + entry.getKey().toString(), entry.getValue());
        }

        // save config
        try {
            config.save(file);
            plugin.getLogger().info("Saved " + playerNicknames.size() + " nickname entries to nicknames.yml.");
        } catch (IOException e) { // Catch specific IOException
            plugin.getLogger().severe("Could not save nicknames to nicknames.yml: " + e.getMessage());
        }
    }

}
