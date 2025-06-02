package net.maxmushroom.wokeplugin;

import org.bukkit.entity.Player;

import io.papermc.paper.chat.ChatRenderer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class WokeRenderer implements ChatRenderer {
    private final WokePlugin plugin;

    // constructor
    public WokeRenderer(WokePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Component render(Player source, Component sourceDisplayName, Component message, Audience viewer) {
        // append display name using default <name> format
        Component displayName = Component.text("<")
                .append(plugin.nicknames.getNickname(source.getUniqueId()))
                .append(Component.text("> "));

        return plugin.pronouns.getPronounsPrefix(source.getUniqueId()).color(NamedTextColor.GRAY).append((displayName).append(message).colorIfAbsent(NamedTextColor.WHITE));
    }
}
