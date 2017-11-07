package me.HeyAwesomePeople.Tycoon.world.plots.plotparts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Region {

    @Getter private final World world;
    @Getter private final List<Cuboid> cuboids;

    public List<String> getCuboidListAsString() {
        List<String> cuboids = new ArrayList<>();
        for (Cuboid c : this.cuboids) {
            cuboids.add("" + c.getPoint1().getBlockX() + c.getPoint1().getBlockY() + c.getPoint1().getBlockZ() + c.getPoint2().getBlockX() + c.getPoint2().getBlockY() + c.getPoint2().getBlockZ());
        }
        return cuboids;
    }

    public void addCuboid(int[] point1, int[] point2) {
        cuboids.add(new Cuboid(world, point1, point2));
    }


}