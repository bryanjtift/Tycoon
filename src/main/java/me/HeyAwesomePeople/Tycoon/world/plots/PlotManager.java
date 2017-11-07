package me.HeyAwesomePeople.Tycoon.world.plots;

import com.mongodb.async.client.MongoCollection;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.mongodb.MongoDBManager;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import me.HeyAwesomePeople.Tycoon.utils.LocationUtils;
import me.HeyAwesomePeople.Tycoon.utils.NumberUtils;
import me.HeyAwesomePeople.Tycoon.world.plots.plotparts.Cuboid;
import me.HeyAwesomePeople.Tycoon.world.plots.plotparts.Region;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class PlotManager {

    private Tycoon plugin;

    private HashMap<World, HashMap<Integer, Plot>> plots = new HashMap<>();
    private MongoDBManager manager;

    private Document document;

    public PlotManager(Tycoon plugin) {
        this.plugin = plugin;
        this.manager = plugin.getMongoDBManager();

        loadDocument();

        for (String world : plugin.getConfigManager().getConfig("plots").getConfigurationSection("world").getKeys(false)) {
            if (Bukkit.getWorld(world) == null) {
                Debug.debug(DebugType.ERROR, "World '" + world + "' not found, but plots are contained within it!");
                continue;
            }
            plots.put(Bukkit.getWorld(world), new HashMap<>());
            loadPlots(Bukkit.getWorld(world));
        }
    }

    private void loadPlots(World world) {
        ConfigurationSection config = plugin.getConfigManager().getConfig("plots").getConfigurationSection("world." + world.getName() + ".plots");
        for (String num : config.getKeys(false)) {
            Integer id = Integer.parseInt(num);
            PlotType type = PlotType.valueOf(config.getString(id + ".plot_type"));
            Location address = LocationUtils.stringToLocation(config.getString(id + ".address"), world);
            HashMap<Integer, Region> regions = new HashMap<>();
            List<Cuboid> cuboids = new ArrayList<>();

            for (String regionNum : config.getConfigurationSection(id + ".regions").getKeys(false)) {
                Integer regionID = Integer.parseInt(regionNum);

                for (String cuboid : config.getStringList(id + ".regions." + regionID)) {
                    int[] ints = Arrays.stream(cuboid.split(",")).mapToInt(Integer::parseInt).toArray();
                    cuboids.add(new Cuboid(world, Arrays.copyOfRange(ints, 0, 3), Arrays.copyOfRange(ints, 3, 6)));
                }

                regions.put(regionID, new Region(world, cuboids));
            }


            plots.get(world).put(id, new Plot(plugin, world, id, type, address, regions));
            Debug.debug(DebugType.INFO, "[PlotLoader][w:" + world.getName() + "] Loaded plot '" + id + "' with data {" + type.name() + ", " + LocationUtils.locationToStringNoWorld(address) + ", [" + Arrays.toString(cuboids.toArray()) + "]}");
        }
    }

    public void addNewPlot(World world, PlotType type, Location location, Region... region) {
        int newId = NumberUtils.firstMissingNum(new ArrayList<>(plots.get(world).keySet()));
        HashMap<Integer, Region> regions = new HashMap<>();
        int count = 1;
        for (Region r : region)
            regions.put(count++, r);

        Plot plot = new Plot(plugin, world, newId, type, location, regions);
        plots.get(world).put(newId, plot);
        plot.save();
    }

    private void loadDocument() {
        MongoCollection<Document> c = manager.getCollection(MongoDBManager.COLL_PLOTDATA);

        c.find().first((document, throwable) -> {
            if (document == null) {
                createNewDocument();
            } else {
                PlotManager.this.document = document;
            }
        });
    }

    private void createNewDocument() {
        document = new Document();
        manager.getCollection(MongoDBManager.COLL_USERDATA).insertOne(this.document,
                (Void result, final Throwable t) -> Debug.debug(DebugType.INFO, "Successfully inserted document for PlotData."));
    }

    public void savePlotsIntoConfig() {
        //TODO
    }

}
