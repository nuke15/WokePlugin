package net.maxmushroom.wokeplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.maxmushroom.wokeplugin.commands.NicknameCommands;
import net.maxmushroom.wokeplugin.commands.PronounsCommands;
import net.maxmushroom.wokeplugin.data.DataManager;
import net.maxmushroom.wokeplugin.nicknames.Nicknames;
import net.maxmushroom.wokeplugin.pronouns.Pronouns;

public class WokePlugin extends JavaPlugin {
    public final DataManager dataManager = new DataManager(this);
    public final Pronouns pronouns = new Pronouns(this);
    public final Nicknames nicknames = new Nicknames(this);

    @Override
    public void onEnable() {
        // register event listeners
        getServer().getPluginManager().registerEvents(new WokeListener(this), this);

        // register commands
        this.getCommand("nickname").setExecutor(new NicknameCommands(this));
        this.getCommand("pronouns").setExecutor(new PronounsCommands(this));

        // update nicks and pronouns for any online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            pronouns.updateTabList(player.getUniqueId());
        }
    }

    @Override
    public void onDisable() {
        pronouns.savePronouns();
        nicknames.saveNicknames();
    }
}
