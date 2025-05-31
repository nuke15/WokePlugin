package net.maxmushroom.wokeplugin;

import org.bukkit.plugin.java.JavaPlugin;

import net.maxmushroom.wokeplugin.commands.NicknameCommands;
import net.maxmushroom.wokeplugin.commands.PronounsCommands;
import net.maxmushroom.wokeplugin.pronouns.PronounsListener;
import net.maxmushroom.wokeplugin.pronouns.PronounsManager;

public class WokePlugin extends JavaPlugin {
    private final PronounsManager pronouns = new PronounsManager(this);

    @Override
    public void onEnable() {
        // register event listeners
        getServer().getPluginManager().registerEvents(new PronounsListener(pronouns), this);

        // register commands
        this.getCommand("pronouns").setExecutor(new PronounsCommands(pronouns));
        this.getCommand("nickname").setExecutor(new NicknameCommands());
    }

    @Override
    public void onDisable() {
        pronouns.savePronouns();
    }
}
