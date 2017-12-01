package me.HeyAwesomePeople.Tycoon.utils;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.World;

@RequiredArgsConstructor
public class Cuboid {
    private final World world;
    private final Integer[] point1;
    private final Integer[] point2;

    public Location getPoint1() {
        return new Location(world, point1[0], point1[1], point1[2]);
    }

    public Location getPoint2() {
        return new Location(world, point2[0], point2[1], point2[2]);
    }
}