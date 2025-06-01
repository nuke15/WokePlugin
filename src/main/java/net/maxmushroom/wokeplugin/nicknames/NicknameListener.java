package net.maxmushroom.wokeplugin.nicknames;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NicknameListener implements Listener {
    private final NicknameManager nicknames;

    // constructor
    public NicknameListener(NicknameManager nicknames) {
        this.nicknames = nicknames;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        nicknames.updateNickname(event.getPlayer().getUniqueId());
    }
}
