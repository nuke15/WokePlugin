package net.maxmushroom.wokeplugin.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;

public class NicknameCommands implements CommandExecutor, TabCompleter {
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
                        return false;
                    } else {
                        player.displayName(Component.text(args[1]));
                        player.sendMessage(Component.text("Your nickname has been set to: " + args[1]));
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("clear")) {
                    player.displayName(null);
                    player.sendMessage(Component.text("Your nickname has been cleared."));
                    return true;
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
