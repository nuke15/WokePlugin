package net.maxmushroom.wokeplugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.papermc.paper.event.player.AsyncChatEvent;

public class WokeListener implements Listener {
    private final WokePlugin plugin;

    public WokeListener(WokePlugin wokePlugin) {
        this.plugin = wokePlugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncChatEvent event) {
        event.renderer(new WokeRenderer(plugin));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.pronouns.updateTabList(event.getPlayer().getUniqueId());
    }
}