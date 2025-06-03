package net.maxmushroom.wokeplugin.nicknames;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.maxmushroom.wokeplugin.WokePlugin;

public class Nicknames {
    private final Map<UUID, String> playerNicknames;
    private final Map<UUID, String> nicknameColors;
    private final WokePlugin plugin;

    public Nicknames(WokePlugin plugin) {
        this.plugin = plugin;
        this.playerNicknames = new ConcurrentHashMap<>();
        this.nicknameColors = new ConcurrentHashMap<>();

        loadNicknames();
    }

    public Component getNickname(UUID uuid) {
        String nickname = playerNicknames.get(uuid);
        if (nickname != null) {
            return Component.text(nickname)
                    .hoverEvent(HoverEvent.showText(Component.text(Bukkit.getPlayer(uuid).getName())))
                    .color(getNicknameColor(uuid));
        } else {
            return Component.text(Bukkit.getPlayer(uuid).getName()).color(getNicknameColor(uuid));
        }
    }

    public TextColor getNicknameColor(UUID uuid) {
        String color = nicknameColors.get(uuid);
        if (color != null) {
            return TextColor.fromHexString(color);
        } else {
            return null;
        }
    }

    public void setNickname(UUID uuid, String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            playerNicknames.remove(uuid);
        } else {
            playerNicknames.put(uuid, nickname);
        }
    }

    public void setNicknameColor(UUID uuid, String color) {
        if (color == null || color.trim().isEmpty()) {
            nicknameColors.remove(uuid);
        } else {
            nicknameColors.put(uuid, color);
        }
    }

    public void loadNicknames() {
        // clear current hashmap
        playerNicknames.clear();

        // get new hashmap from data manager
        Map<UUID, String> newNicknames = plugin.dataManager.loadSection("player-nicknames");

        // load new hashmap into this.hashmap
        newNicknames.entrySet().forEach(entry -> {
            playerNicknames.put(entry.getKey(), entry.getValue());
        });

        // now do all the same for the colors
        nicknameColors.clear();
        Map<UUID, String> newColors = plugin.dataManager.loadSection("player-nickname-colors");
        newColors.entrySet().forEach(entry -> {
            nicknameColors.put(entry.getKey(), entry.getValue());
        });
    }

    public void saveNicknames() {
        plugin.dataManager.saveSection(playerNicknames, "player-nicknames");
        plugin.dataManager.saveSection(nicknameColors, "player-nickname-colors");
    }

}
