package net.maxmushroom.wokeplugin;

import org.bukkit.plugin.java.JavaPlugin;

import net.maxmushroom.wokeplugin.commands.NicknameCommands;
import net.maxmushroom.wokeplugin.commands.PronounsCommands;
import net.maxmushroom.wokeplugin.nicknames.NicknameListener;
import net.maxmushroom.wokeplugin.nicknames.NicknameManager;
import net.maxmushroom.wokeplugin.pronouns.PronounsListener;
import net.maxmushroom.wokeplugin.pronouns.PronounsManager;

public class WokePlugin extends JavaPlugin {
    private final PronounsManager pronouns = new PronounsManager(this);
    private final NicknameManager nicknames = new NicknameManager(this);

    @Override
    public void onEnable() {
        // register event listeners
        getServer().getPluginManager().registerEvents(new PronounsListener(pronouns), this);
        getServer().getPluginManager().registerEvents(new NicknameListener(nicknames), this);

        // register commands
        this.getCommand("pronouns").setExecutor(new PronounsCommands(pronouns));
        this.getCommand("nickname").setExecutor(new NicknameCommands(nicknames));
    }

    @Override
    public void onDisable() {
        pronouns.savePronouns();
        nicknames.saveNicknames();
    }
}
