package me.HeyAwesomePeople.Tycoon.utils;

import org.bukkit.Bukkit;

public class Debug {

    public static void debug(DebugType type, String value) {
        Bukkit.getConsoleSender().sendMessage(type.getName() + " " + value);
    }

}
