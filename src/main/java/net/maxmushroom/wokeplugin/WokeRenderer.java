package net.maxmushroom.wokeplugin;

import org.bukkit.entity.Player;

import io.papermc.paper.chat.ChatRenderer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.maxmushroom.wokeplugin.pronouns.PronounsManager;

public class WokeRenderer implements ChatRenderer {
    private final PronounsManager pronouns;

    // constructor
    public WokeRenderer(PronounsManager pronouns) {
        this.pronouns = pronouns;
    }

    @Override
    public Component render(Player source, Component sourceDisplayName, Component message, Audience viewer) {
        // append display name using default <name> format
        Component displayName = Component.text("<")
                .append(sourceDisplayName)
                .append(Component.text("> "));

        return pronouns.getPronounsPrefix(source.getUniqueId()).color(NamedTextColor.GRAY).append((displayName).append(message).colorIfAbsent(NamedTextColor.WHITE));
    }
}
