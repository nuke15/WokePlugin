package net.maxmushroom.wokeplugin.pronouns;

import org.bukkit.entity.Player;

import io.papermc.paper.chat.ChatRenderer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class PronounsRenderer implements ChatRenderer {
    private final PronounsManager pronouns;

    // constructor
    public PronounsRenderer(PronounsManager pronouns) {
        this.pronouns = pronouns;
    }

    @Override
    public Component render(Player source, Component sourceDisplayName, Component message, Audience viewer) {
        // instantiate output component
        Component pronounsPrefix = Component.empty();

        // check hashmap for player's pronouns
        String sourcePronouns = pronouns.getPronouns(source.getUniqueId());
        if (sourcePronouns != null) {
            // if found, append pronoun component to output
            pronounsPrefix = Component.text("[" + sourcePronouns + "] ");
        }

        // append display name using default <name> format
        Component displayName = Component.text("<")
                .append(sourceDisplayName)
                .append(Component.text("> "));

        return pronounsPrefix.color(NamedTextColor.GRAY).append((displayName).append(message).colorIfAbsent(NamedTextColor.WHITE));
    }
}
