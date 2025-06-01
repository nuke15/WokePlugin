package net.maxmushroom.wokeplugin.nicknames;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
// import org.bukkit.event.player.PlayerQuitEvent;

// import net.kyori.adventure.text.Component;
// import net.kyori.adventure.text.TextReplacementConfig;
// import net.kyori.adventure.text.format.NamedTextColor;

public class NicknameListener implements Listener {
    private final NicknameManager nicknames;

    // constructor
    public NicknameListener(NicknameManager nicknames) {
        this.nicknames = nicknames;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        nicknames.updateNickname(event.getPlayer().getUniqueId());

        // event.joinMessage(event.joinMessage());
    }

    // @EventHandler
    // public void onPlayerLeave(PlayerQuitEvent event) {

    //     Component quitMessage = event.getPlayer().displayName()
    //             .append(Component.text(" left the game"))
    //             .colorIfAbsent(NamedTextColor.YELLOW);

    //     event.quitMessage(quitMessage);
    // }
}
