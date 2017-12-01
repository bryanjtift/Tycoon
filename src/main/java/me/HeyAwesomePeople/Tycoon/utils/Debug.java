package me.HeyAwesomePeople.Tycoon.utils;

import me.HeyAwesomePeople.Tycoon.Tycoon;
import org.bukkit.Bukkit;

public class Debug {

    public static void debug(DebugType type, String value) {
        if (Tycoon.debug)
            Bukkit.getConsoleSender().sendMessage(type.getName() + " " + value);
    }

}
