package me.HeyAwesomePeople.Tycoon.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

public class LocationUtils {

    public static String locationToStringNoWorld(Location l) {
        return l.getX() + "," + l.getY() + "," + l.getZ();
    }

    public static Location stringToLocation(String l, World world) {
        String[] locs = l.split(",");
        return new Location(world, Double.parseDouble(locs[0]), Double.parseDouble(locs[1]), Double.parseDouble(locs[2]));
    }

    public static String locationToString(Location l) {
        return l.getWorld().getName() + "-" + l.getX() + "-" + l.getY() + "-" + l.getZ() + "-" + l.getYaw() + "-" + l.getPitch();
    }

    public static Location stringToLocation(String l) {
        String[] locs = l.split("-");
        return new Location(Bukkit.getWorld(locs[0]), Double.parseDouble(locs[1]), Double.parseDouble(locs[2]), Double.parseDouble(locs[3]), Float.parseFloat(locs[4]), Float.parseFloat(locs[5]));
    }

    public static Location integerListToLocation(World world, List<Integer> ints) {
        return new Location(world, ints.get(0), ints.get(1), ints.get(2));
    }

}
