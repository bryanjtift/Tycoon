package me.HeyAwesomePeople.Tycoon.world.plots;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * @author HeyAwesomePeople
 * @since Saturday, October 28 2017
 */
public class Plot {

    private Integer plotId;
    private ArrayList<Region> regions = new ArrayList<>();

    Plot(Integer plotId) {
        this.plotId = plotId;

        //TODO load plot based on ID
    }

    public PlotType getPlotType() {
        return PlotType.UNDEFINED;
    }

    public boolean isPlayerInRegion(Player player) {
        return isLocationInRegion(player.getLocation());
    }

    public boolean isLocationInRegion(Location location) {
        int locX = location.getBlockX();
        int locY = location.getBlockY();
        int locZ = location.getBlockZ();

        for (Region region : regions) {
            int x1 = region.getPoint1().getBlockX();
            int y1 = region.getPoint1().getBlockX();
            int z1 = region.getPoint1().getBlockX();

            int x2 = region.getPoint2().getBlockX();
            int y2 = region.getPoint2().getBlockX();
            int z2 = region.getPoint2().getBlockX();

            ZoneVector curr = new ZoneVector(locX, locY, locZ);
            ZoneVector min = new ZoneVector(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2));
            ZoneVector max = new ZoneVector(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));

            if (curr.isInAABB(min, max)) {
                return true;
            }
        }

        return false;
    }

    public class Region {

        private World world;
        private int[] point1 = new int[3];
        private int[] point2 = new int[3];

        public Region(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
            this.world = world;
            this.point1[0] = x1;
            this.point1[1] = y1;
            this.point1[2] = z1;

            this.point2[0] = x2;
            this.point2[1] = y2;
            this.point2[2] = z2;
        }

        Location getPoint1() {
            return new Location(world, point1[0], point1[1], point1[2]);
        }

        Location getPoint2() {
            return new Location(world, point2[0], point2[1], point2[2]);
        }

    }

    public class ZoneVector {
        private int x;
        private int y;
        private int z;

        ZoneVector(int x, int y, int z) {
            this.x = x;
            this.z = z;
            this.y = y;
        }

        boolean isInAABB(ZoneVector min, ZoneVector max) {
            return ((this.x <= max.x) && (this.x >= min.x) && (this.z <= max.z) && (this.z >= min.z) && (this.y <= max.y) && (this.y >= min.y));
        }
    }

}
