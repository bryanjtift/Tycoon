package me.HeyAwesomePeople.Tycoon.world.plots;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import lombok.Setter;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.mongodb.*;
import me.HeyAwesomePeople.Tycoon.mongodb.Collection;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import me.HeyAwesomePeople.Tycoon.utils.LocationUtils;
import me.HeyAwesomePeople.Tycoon.utils.Cuboid;
import me.HeyAwesomePeople.Tycoon.world.plots.plotparts.Region;
import me.HeyAwesomePeople.Tycoon.utils.ZoneVector;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

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
    @Getter @Setter private Location address;
    @Getter private HashMap<Integer, Region> regions = new HashMap<>();

    // from config/set later, default to default
    @Getter private Rent rent;

    // database stuff
    private SyncMongoDBManager manager;
    private Document document;

    // information loaded from database, if present
    @Getter private UUID owner;
    @Getter private List<UUID> members = new ArrayList<>();
    @Getter private long rentlastpaid = -1;

    Plot(Tycoon plugin, World world, Integer id, PlotType type, Location address, HashMap<Integer, Region> regions) {
        this.plugin = plugin;
        this.world = world;
        this.plotId = id;
        this.plotType = type;
        this.address = address;
        this.regions = regions;

        this.manager = plugin.getSyncMongoDBManager();

        Configuration config = plugin.getConfigManager().getConfig("plots");

        loadDocument();
        loadDatabaseInfo();
    }

    private void loadDatabaseInfo() {
        if (document == null) {
            Debug.debug(DebugType.WARNING, "No database entries found for plot '" + plotId + "' in world '" + world.getName() + "'");
            return;
        }
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
        Debug.debug(DebugType.INFO, "[Plot '" + plotId + "'] Owner is '" + this.owner + "', Members are: [" + Arrays.toString(new String[]{Arrays.toString(members.toArray())}) + "], Last Rent Paid Was: " + this.rentlastpaid);
    }

    private void loadDocument() {
        MongoCollection<Document> c = manager.getCollection(Collection.PLOTDATA.getCollName());
        Document doc = c.find().first();

        if (doc == null) {
             this.document = new Document();
             manager.getCollection(Collection.PLOTDATA.getCollName()).insertOne(this.document);
        } else {
            this.document = doc;
        }
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
            for (Cuboid cuboid : region.getCuboids()) {
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

}
