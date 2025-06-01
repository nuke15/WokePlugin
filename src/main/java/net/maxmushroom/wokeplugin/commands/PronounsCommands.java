package net.maxmushroom.wokeplugin.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.maxmushroom.wokeplugin.pronouns.PronounsManager;

public class PronounsCommands implements CommandExecutor, TabCompleter {
    private final PronounsManager pronouns;

    private final int MAX_PRONOUN_LENGTH = 8;

    public PronounsCommands(PronounsManager pronouns) {
        this.pronouns = pronouns;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // check if the sender is a player
        if (sender instanceof Player player) {
            // if there are no arguments, show usage
            if (args.length == 0) {
                return false;
            } else {
                // check if the first command argument is one of our subcommands
                if (args[0].equalsIgnoreCase("set")) {
                    if (args.length < 2 || args.length > 2) {
                        player.sendMessage(Component.text("Usage: /pronouns set <pronouns>"));
                        return true;
                    } else {
                        String formatted = args[1].toLowerCase();
                        if ((formatted.matches("[a-z]+/[a-z]+") && formatted.length() < (MAX_PRONOUN_LENGTH * 2 + 1)) || formatted.equals("any")) {
                            pronouns.setPronouns(player.getUniqueId(), formatted);
                            player.sendMessage(Component.text("Your pronouns have been set to: " + formatted));
                            return true;
                        } else {
                            player.sendMessage(Component.text("Pronouns must follow the format <subject/object>, with the exception of \"any.\""));
                            return true;
                        }
                    }
                } else if (args[0].equalsIgnoreCase("clear")) {
                    if (args.length > 1) {
                        player.sendMessage(Component.text("Usage: /pronouns clear"));
                        return true;
                    } else {
                        pronouns.setPronouns(player.getUniqueId(), null);
                        player.sendMessage(Component.text("Your pronouns have been cleared."));
                        return true;
                    }
                } else {
                    return false;
                }
            }

        } else {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return List.of("set", "clear");
        } else {
            return List.of();
        }
    }
}
