package me.HeyAwesomePeople.Tycoon.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    public static String locationToString(Location l) {
        return l.getWorld().getName() + "-" + l.getX() + "-" + l.getY() + "-" + l.getZ() + "-" + l.getYaw() + "-" + l.getPitch();
    }

    public static Location stringToLocation(String l) {
        String[] locs = l.split("-");
        return new Location(Bukkit.getWorld(locs[0]), Double.parseDouble(locs[1]), Double.parseDouble(locs[2]), Double.parseDouble(locs[3]), Float.parseFloat(locs[4]), Float.parseFloat(locs[5]));
    }

}
