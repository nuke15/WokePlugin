package net.maxmushroom.wokeplugin.pronouns;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.maxmushroom.wokeplugin.WokePlugin;

public class Pronouns {
    private final Map<UUID, String> playerPronouns;
    private final Map<UUID, String> pronounColors;
    private final WokePlugin plugin;

    public Pronouns(WokePlugin plugin) {
        this.plugin = plugin;
        this.playerPronouns = new ConcurrentHashMap<>();
        this.pronounColors = new ConcurrentHashMap<>();

        loadPronouns();
    }

    public String getPronouns(UUID uniqueId) {
        return playerPronouns.get(uniqueId);
    }

    public Component getPronounsPrefix(UUID uuid) {
        // instantiate output component
        Component pronounsPrefix = Component.empty();

        // check hashmap for player's pronouns
        String sourcePronouns = getPronouns(uuid);
        if (sourcePronouns != null) {
            // if found, append pronoun component to output
            pronounsPrefix = Component.text("[" + sourcePronouns + "] ");
        }

        return pronounsPrefix.color(getPronounColor(uuid));
    }

    public TextColor getPronounColor(UUID uuid) {
        String color = pronounColors.get(uuid);
        if (color != null) {
            return TextColor.fromHexString(color);
        } else {
            return NamedTextColor.GRAY;
        }
    }

    public void setPronouns(UUID uniqueId, String pronouns) {
        if (pronouns == null || pronouns.trim().isEmpty()) {
            playerPronouns.remove(uniqueId);
        } else {
            playerPronouns.put(uniqueId, pronouns);
        }
    }
    
    public void setPronounColor(UUID uuid, String color) {
        if (color == null || color.trim().isEmpty()) {
            pronounColors.remove(uuid);
        } else {
            pronounColors.put(uuid, color);
        }
    }

    // update the given player's display name in the tab list with pronouns and nick
    public void updateTabList(UUID uuid) {
        Bukkit.getPlayer(uuid).playerListName(
                getPronounsPrefix(uuid)
                        .append(plugin.nicknames.getNickname(uuid)
                                .colorIfAbsent(NamedTextColor.WHITE)));
    }

    public void loadPronouns() {
        // clear current hashmap
        playerPronouns.clear();

        // get new hashmap from data manager
        Map<UUID, String> newPronouns = plugin.dataManager.loadSection("player-pronouns");

        // load new hashmap into this.hashmap
        newPronouns.entrySet().forEach(entry -> {
            playerPronouns.put(entry.getKey(), entry.getValue());
        });

        pronounColors.clear();
        Map<UUID, String> newColors = plugin.dataManager.loadSection("player-pronoun-colors");
        newColors.entrySet().forEach(entry -> {
            pronounColors.put(entry.getKey(), entry.getValue());
        });

    }

    public void savePronouns() {
        plugin.dataManager.saveSection(playerPronouns, "player-pronouns");
        plugin.dataManager.saveSection(pronounColors, "player-pronoun-colors");
    }

}
