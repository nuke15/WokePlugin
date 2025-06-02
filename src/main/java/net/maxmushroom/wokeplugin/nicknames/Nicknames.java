package net.maxmushroom.wokeplugin.nicknames;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.maxmushroom.wokeplugin.WokePlugin;

public class Nicknames {
    private final Map<UUID, String> playerNicknames;
    // TODO: colors
    private final WokePlugin plugin;

    public Nicknames(WokePlugin plugin) {
        this.plugin = plugin;
        this.playerNicknames = new ConcurrentHashMap<>();

        loadNicknames();
    }

    public Component getNickname(UUID uuid) {
        String nickname = playerNicknames.get(uuid);
        if (nickname != null) {
            return Component.text(nickname)
                    .hoverEvent(HoverEvent.showText(Component.text(Bukkit.getPlayer(uuid).getName())));
        } else {
            return Component.text(Bukkit.getPlayer(uuid).getName());
        }
    }

    public void setNickname(UUID uuid, String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            playerNicknames.remove(uuid);
        } else {
            playerNicknames.put(uuid, nickname);
        }
    }

    public void loadNicknames() {
        // clear current hashmap
        playerNicknames.clear();

        // get new hashmap from data manager
        Map<UUID, String> newNicknames = plugin.dataManager.loadSection("player-nicknames");

        // load new hashmap into this.hashmap
        for (Map.Entry<UUID, String> entry : newNicknames.entrySet()) {
            playerNicknames.put(entry.getKey(), entry.getValue());
        }
    }

    public void saveNicknames() {
        plugin.dataManager.saveSection(playerNicknames, "player-nicknames");
    }

}
