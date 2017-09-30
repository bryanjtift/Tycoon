package me.HeyAwesomePeople.Tycoon.utils;

import org.bukkit.ChatColor;

public enum DebugType {

    INFO("&2[Info]"), WARNING("&6[Warning]"), ERROR("&4[Error]");

    protected String name;

    DebugType(String name) {
        this.name = name;
    }

    public String getName() {
        return ChatColor.translateAlternateColorCodes('&', this.name);
    }

}
