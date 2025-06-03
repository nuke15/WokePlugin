package net.maxmushroom.wokeplugin.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.maxmushroom.wokeplugin.WokePlugin;
import net.maxmushroom.wokeplugin.colors.ColorNames;

public class NicknameCommands implements CommandExecutor, TabCompleter {
    private final WokePlugin plugin;

    public NicknameCommands(WokePlugin wokePlugin) {
        this.plugin = wokePlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // check if the sender is a player
        if (sender instanceof Player player) {
            // if there are no arguments, show usage
            if (args.length == 0) {
                return false;
            } else {
                // set subcommand
                if (args[0].equalsIgnoreCase("set")) {
                    if (args.length < 2) {
                        player.sendMessage(Component.text("Usage: /nickname set <nickname>").color(NamedTextColor.RED));
                        return true;
                    } else if (args.length > 2) {
                        player.sendMessage(
                                Component.text("Nicknames cannot contain spaces.").color(NamedTextColor.RED));
                        return true;
                    } else {
                        plugin.nicknames.setNickname(player.getUniqueId(), args[1]);
                        plugin.pronouns.updateTabList(player.getUniqueId());
                        player.sendMessage(Component.text("Your nickname has been set to: " + args[1])
                                .color(NamedTextColor.GREEN));
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("clear")) {
                    plugin.nicknames.setNickname(player.getUniqueId(), null);
                    player.sendMessage(Component.text("Your nickname has been cleared.").color(NamedTextColor.GREEN));
                    return true;

                    // color subcommand handler
                } else if (args[0].equalsIgnoreCase("color")) {
                    if (args.length < 2 || args.length > 2) {
                        player.sendMessage(Component.text("Usage: /nickname color <color name | reset>")
                                .color(NamedTextColor.RED));
                        return true;
                    } else {
                        if (args[1].equalsIgnoreCase("reset")) {
                            plugin.nicknames.setNicknameColor(player.getUniqueId(), null);
                            plugin.pronouns.updateTabList(player.getUniqueId());
                            player.sendMessage(
                                    Component.text("Your nickname color has been reset.").color(NamedTextColor.GREEN));
                            return true;
                        } else {
                            // handle all color input
                            // Named Colors: aqua, dark_red
                            // Hex Colors: #ABC #AABBCC

                            if (args[1].matches("^#[a-fA-F0-9]{6}$")) {
                                plugin.nicknames.setNicknameColor(player.getUniqueId(), args[1]);
                                plugin.pronouns.updateTabList(player.getUniqueId());
                                player.sendMessage(Component.text("Your nickname color has been set to: ")
                                        .color(NamedTextColor.GREEN)
                                        .append(Component.text(args[1]).color(plugin.nicknames.getNicknameColor(player.getUniqueId()))));
                                return true;
                            } else if (ColorNames.colors.containsKey(args[1].toLowerCase())) {
                                TextColor color = ColorNames.colors.get(args[1].toLowerCase());
                                plugin.nicknames.setNicknameColor(player.getUniqueId(), color.asHexString());
                                plugin.pronouns.updateTabList(player.getUniqueId());
                                player.sendMessage(Component.text("Your nickname color has been set to: ")
                                        .color(NamedTextColor.GREEN)
                                        .append(Component.text(args[1]).color(plugin.nicknames.getNicknameColor(player.getUniqueId()))));
                                return true;
                            } else {
                                player.sendMessage(Component.text("Invalid color name.").color(NamedTextColor.RED));
                                return true;
                            }
                        }
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
            return List.of("set", "clear", "color");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("color")) {
            if (args[1].startsWith("#")) {
                return List.of();
            } else {
                List<String> suggestions = new ArrayList<>();
                for (Map.Entry<String, TextColor> entry : ColorNames.colors.entrySet()) {
                    if (entry.getKey().startsWith(args[1].toLowerCase())) {
                        suggestions.add(entry.getKey());
                    }
                }
                suggestions.add("reset");
                return suggestions;
            }

        } else {
            return List.of();
        }
    }
}
