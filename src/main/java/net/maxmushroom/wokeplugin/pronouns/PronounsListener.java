package net.maxmushroom.wokeplugin.pronouns;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.papermc.paper.event.player.AsyncChatEvent;

public class PronounsListener implements Listener {
    PronounsManager pronouns;

    // constructor
    public PronounsListener(PronounsManager pronouns) {
        this.pronouns = pronouns;
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer(new PronounsRenderer(pronouns));
    }
}
