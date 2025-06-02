package net.maxmushroom.wokeplugin.pronouns;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.maxmushroom.wokeplugin.WokePlugin;

public class Pronouns {
    private final Map<UUID, String> playerPronouns;
    // TODO: colors
    private final WokePlugin plugin;

    public Pronouns(WokePlugin plugin) {
        this.plugin = plugin;
        this.playerPronouns = new ConcurrentHashMap<>();

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

        return pronounsPrefix.color(NamedTextColor.GRAY);
    }

    public void setPronouns(UUID uniqueId, String pronouns) {
        if (pronouns == null || pronouns.trim().isEmpty()) {
            playerPronouns.remove(uniqueId);
        } else {
            playerPronouns.put(uniqueId, pronouns);
        }
    }

    // update the given player's display name in the tab list with pronouns and nick
    public void updateTabList(UUID uuid) {
        Bukkit.getPlayer(uuid).playerListName(
                getPronounsPrefix(uuid)
                        .append(Bukkit.getPlayer(uuid).displayName()
                                .colorIfAbsent(NamedTextColor.WHITE)));
    }

    public void loadPronouns() {
        // clear current hashmap
        playerPronouns.clear();

        // get new hashmap from data manager
        Map<UUID, String> newNicknames = plugin.dataManager.loadSection("player-pronouns");

        // load new hashmap into this.hashmap
        for (Map.Entry<UUID, String> entry : newNicknames.entrySet()) {
            playerPronouns.put(entry.getKey(), entry.getValue());
        }
    }

    public void savePronouns() {
        plugin.dataManager.saveSection(playerPronouns, "player-pronouns");
    }

}
