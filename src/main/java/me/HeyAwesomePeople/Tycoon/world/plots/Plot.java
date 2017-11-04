package me.HeyAwesomePeople.Tycoon.world.plots;

import com.mongodb.async.client.MongoCollection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.mongodb.MongoDBManager;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import me.HeyAwesomePeople.Tycoon.utils.LocationUtils;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author HeyAwesomePeople
 * @since Saturday, October 28 2017
 */
public class Plot {

    private Tycoon plugin;

    // from creation
    @Getter private World world;
    @Getter private Integer plotId;
    @Getter private PlotType plotType;
    @Getter private Location address;
    @Getter private HashMap<Integer, Region> regions = new HashMap<>();

    // from config/set later, default to default
    @Getter private Rent rent;

    // database stuff
    private MongoDBManager manager;
    private Document document;

    // information loaded from database, if present
    @Getter private UUID owner;
    @Getter private List<UUID> members = new ArrayList<>();
    @Getter private long rentlastpaid;

    Plot(Tycoon plugin, World world, Integer id, PlotType type, Location address, HashMap<Integer, Region> regions) {
        this.plugin = plugin;
        this.world = world;
        this.plotId = id;
        this.plotType = type;
        this.address = address;
        this.regions = regions;

        this.manager = plugin.getMongoDBManager();

        Configuration config = plugin.getConfigManager().getConfig("plots");

        loadDocument();
        loadDatabaseInfo();
    }

    private void loadDatabaseInfo() {
        if (document.containsKey("owner")) {
            this.owner = UUID.fromString(document.getString("owner"));
        }
        if (document.containsKey("members")) {
            for (String uuid : (String[]) document.get("members")) {
                this.members.add(UUID.fromString(uuid));
            }
        }
        if (document.containsKey("rentlastpaid")) {
            this.rentlastpaid = document.getLong("rentlastpaid");
        }
    }

    private void loadDocument() {
        MongoCollection<Document> c = manager.getCollection(MongoDBManager.COLL_PLOTDATA);

        c.find().first((document, throwable) -> {
            if (document == null) {
                Plot.this.document = new Document();
                manager.getCollection(MongoDBManager.COLL_USERDATA).insertOne(this.document,
                        (Void result, final Throwable t) -> Debug.debug(DebugType.INFO, "Successfully inserted document for PlotData."));
            } else {
                Plot.this.document = document;
            }
        });
    }

    public void addCuboidToRegion(Integer id, Integer[] points) {
        //TODO
    }

    public void addRegion(Region region) {
        // TODO
    }

    public void removeRegion(Integer id) {
        this.regions.remove(id);
        //TODO remove from config
    }

    public void reload() {
        //TODO
    }

    public void save() {
        ConfigurationSection config = plugin.getConfigManager().getConfig("plots").getConfigurationSection("world." + world.getName() + ".plots." + plotId);
        config.set("plot_type", plotType.name());
        config.set("rent", rent.getName());
        config.set("address", LocationUtils.locationToStringNoWorld(address));

        for (Integer regionId : regions.keySet()) {
            config.set("regions." + regionId, regions.get(regionId).getCuboidListAsString());
        }

        plugin.getConfigManager().saveConfig("plots");
    }

    public boolean isPlayerInRegion(Player player) {
        return isLocationInRegion(player.getLocation());
    }

    public boolean isLocationInRegion(Location location) {
        int locX = location.getBlockX();
        int locY = location.getBlockY();
        int locZ = location.getBlockZ();

        for (Region region : regions.values()) {
            for (Region.Cuboid cuboid : region.getCuboids()) {
                int x1 = cuboid.getPoint1().getBlockX();
                int y1 = cuboid.getPoint1().getBlockX();
                int z1 = cuboid.getPoint1().getBlockX();

                int x2 = cuboid.getPoint2().getBlockX();
                int y2 = cuboid.getPoint2().getBlockX();
                int z2 = cuboid.getPoint2().getBlockX();

                ZoneVector curr = new ZoneVector(locX, locY, locZ);
                ZoneVector min = new ZoneVector(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2));
                ZoneVector max = new ZoneVector(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));

                if (curr.isInAABB(min, max)) {
                    return true;
                }
            }
        }

        return false;
    }

    @RequiredArgsConstructor
    public class Region {

        @Getter private final World world;
        @Getter private final List<Cuboid> cuboids = new ArrayList<>();

        List<String> getCuboidListAsString() {
            List<String> cuboids = new ArrayList<>();
            for (Cuboid c : this.cuboids) {
                cuboids.add("" + c.getPoint1().getBlockX() + c.getPoint1().getBlockY() + c.getPoint1().getBlockZ() + c.getPoint2().getBlockX() + c.getPoint2().getBlockY() + c.getPoint2().getBlockZ());
            }
            return cuboids;
        }

        public void addCuboid(int[] point1, int[] point2) {
            cuboids.add(new Cuboid(point1, point2));
        }

        @RequiredArgsConstructor class Cuboid {
            private final int[] point1;
            private final int[] point2;

            Location getPoint1() {
                return new Location(world, point1[0], point1[1], point1[2]);
            }

            Location getPoint2() {
                return new Location(world, point2[0], point2[1], point2[2]);
            }
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
