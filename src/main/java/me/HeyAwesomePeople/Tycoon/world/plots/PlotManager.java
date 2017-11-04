package me.HeyAwesomePeople.Tycoon.world.plots;

import com.mongodb.async.client.MongoCollection;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.mongodb.MongoDBManager;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import me.HeyAwesomePeople.Tycoon.utils.NumberUtils;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;

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
            loadPlots(Bukkit.getWorld(world));
        }
    }

    private void loadPlots(World world) {
        for (String num : plugin.getConfigManager().getConfig("plots").getConfigurationSection("world." + world + ".plots").getKeys(false)) {
            Integer id = Integer.parseInt(num);
            //TODO
            plots.get(world).put(id, new Plot(plugin, world, id, type, location, region));

        }
    }

    public void addNewPlot(World world, PlotType type, Location location, Plot.Region... region) {
        int newId = NumberUtils.firstMissingNum(new ArrayList<>(plots.get(world).keySet()));
        Plot plot = new Plot(plugin, world, newId, type, location, region);
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
