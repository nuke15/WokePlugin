package net.maxmushroom.wokeplugin.colors;

import java.util.HashMap;
import java.util.Map;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public final class ColorNames {
    public static final Map<String, TextColor> colors = new HashMap<>();

    static {
        // vanilla colors
        colors.put("aqua", NamedTextColor.AQUA);
        colors.put("black", NamedTextColor.BLACK);
        colors.put("blue", NamedTextColor.BLUE);
        colors.put("dark_aqua", NamedTextColor.DARK_AQUA);
        colors.put("dark_blue", NamedTextColor.DARK_BLUE);
        colors.put("dark_gray", NamedTextColor.DARK_GRAY);
        colors.put("dark_grey", NamedTextColor.DARK_GRAY);
        colors.put("dark_green", NamedTextColor.DARK_GREEN);
        colors.put("dark_purple", NamedTextColor.DARK_PURPLE);
        colors.put("dark_red", NamedTextColor.DARK_RED);
        colors.put("gold", NamedTextColor.GOLD);
        colors.put("gray", NamedTextColor.GRAY);
        colors.put("grey", NamedTextColor.GRAY);
        colors.put("green", NamedTextColor.GREEN);
        colors.put("light_purple", NamedTextColor.LIGHT_PURPLE);
        colors.put("red", NamedTextColor.RED);
        colors.put("white", NamedTextColor.WHITE);
        colors.put("yellow", NamedTextColor.YELLOW);

        // custom colors
        colors.put("pink", TextColor.fromHexString("#fc9fa9"));
        colors.put("dark_pink", TextColor.fromHexString("#ba5974"));
    }
}
