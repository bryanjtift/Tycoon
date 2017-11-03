package me.HeyAwesomePeople.Tycoon.world.plots;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author HeyAwesomePeople
 * @since Saturday, October 28 2017
 */
public class Plot {

    private Tycoon plugin;

    @Getter private Integer plotId;

    @Getter private PlotType plotType;
    @Getter private Rent rent;
    @Getter private Location address;
    private List<Region> regions = new ArrayList<>();

    Plot(Tycoon plugin, String world, Integer plotId) {
        this.plugin = plugin;
        this.plotId = plotId;

        Configuration config = plugin.getConfigManager().getConfig("plots");

        plotType = PlotType.valueOf(config.getString("worlds." + world + ".plots." + plotId + ".plot_type"));
        rent = new Rent(config.getLong("rent_macros." + config.getString("worlds." + world + ".plots." + plotId + ".rent") + ".frequency"), config.getInt("rent_macros." + config.getString("worlds." + world + ".plots." + plotId + ".rent") + ".cost"));
        address = LocationUtils.stringToLocation(config.getString("worlds." + world + ".plots." + plotId + ".address"), Bukkit.getWorld(world));

        for (String s : config.getStringList("worlds." + world + ".plots." + plotId + ".regions")) {
            int[] locs = Arrays.stream(s.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();

            regions.add(new Region(Bukkit.getWorld(world),
                    Arrays.copyOfRange(locs, 0, 3),
                    Arrays.copyOfRange(locs, 3, 6)));
        }

        loadDatabaseInfo();
    }

    private void loadDatabaseInfo() {
        //TODO get owner, members, and lastrentpaid time
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

    @RequiredArgsConstructor
    public class Region {

        private final World world;
        private final int[] point1;
        private final int[] point2;

        Location getPoint1() {
            return new Location(world, point1[0], point1[1], point1[2]);
        }

        Location getPoint2() {
            return new Location(world, point2[0], point2[1], point2[2]);
        }

    }

    @RequiredArgsConstructor
    public class ZoneVector {
        private final int x;
        private final int y;
        private final int z;

        boolean isInAABB(ZoneVector min, ZoneVector max) {
            return ((this.x <= max.x) && (this.x >= min.x) && (this.z <= max.z) && (this.z >= min.z) && (this.y <= max.y) && (this.y >= min.y));
        }
    }

}
