package net.maxmushroom.wokeplugin.pronouns;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.maxmushroom.wokeplugin.WokeRenderer;

public class PronounsListener implements Listener {
    private final PronounsManager pronouns;

    // constructor
    public PronounsListener(PronounsManager pronouns) {
        this.pronouns = pronouns;
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer(new WokeRenderer(pronouns));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        pronouns.updateTabList(event.getPlayer().getUniqueId());
    }
}
