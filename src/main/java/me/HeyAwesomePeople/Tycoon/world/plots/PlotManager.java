package me.HeyAwesomePeople.Tycoon.world.plots;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import lombok.experimental.var;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.economy.rent.RentManager;
import me.HeyAwesomePeople.Tycoon.mongodb.Collection;
import me.HeyAwesomePeople.Tycoon.mongodb.SyncMongoDBManager;
import me.HeyAwesomePeople.Tycoon.utils.*;
import me.HeyAwesomePeople.Tycoon.world.plots.plotparts.Region;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class PlotManager {

    private Tycoon plugin;
    private RentManager rentManager;

    private HashMap<Integer, Plot> plots = new HashMap<>();
    private SyncMongoDBManager manager;

    private Document document;

    public PlotManager(Tycoon plugin) {
        this.plugin = plugin;
        this.manager = plugin.getSyncMongoDBManager();
        this.rentManager = new RentManager(plugin);

        loadPlots();
        loadHolograms();
    }

    private void loadPlots() {
        MongoCollection<Document> c = manager.getCollection(Collection.PLOTDATA.getCollName());

        int loaded = 0;
        for (Document d : c.find()) {
            if (Bukkit.getWorld(d.getString("world")) == null) {
                Debug.debug(DebugType.ERROR, "Failed to load plot '" + d.getString("plotid") + "' because world '" + d.getString("world") + "' doesn't exist!");
                continue;
            }

            World world = Bukkit.getWorld(d.getString("world"));
            Integer plotId = d.getInteger("plotid");
            PlotType type = PlotType.valueOf(d.getString("plottype"));
            Location location = LocationUtils.integerListToLocation(world, (List <Integer>) d.get("address"));
            HashMap<Integer, Region> regions = new HashMap<>();
            Rent rent; // TODO
            UUID owner = UUID.fromString(d.getString("owner"));
            List<UUID> members = ListUtils.toUUIDList((List <String>) d.get("members"));



            BasicDBObject regs = (BasicDBObject) d.get("regions");
            for (String id : regs.keySet()) {
                Integer regionid = Integer.parseInt(id);
                BasicDBList listCuboids = (BasicDBList) regs.get(id);

                List<Cuboid> cuboids = new ArrayList<>();

                for (Object listPoints : listCuboids) {
                    BasicDBList cuboid = (BasicDBList) listPoints;

                    Integer[] point1 = {(Integer) cuboid.get(0), (Integer) cuboid.get(1), (Integer) cuboid.get(2)};
                    Integer[] point2 = {(Integer) cuboid.get(3), (Integer) cuboid.get(4), (Integer) cuboid.get(5)};
                    cuboids.add(new Cuboid(world, point1, point2));
                }

                regions.put(regionid, new Region(world, cuboids));

            }

            plots.put(plotId, new Plot(plugin, world, plotId, type, location, regions));
            loaded++;
        }

        Debug.debug(DebugType.INFO, "Loaded " + loaded + " plots from the database.");
    }

    public Plot createPlot(PlotType type, Location address) {
        Integer plotid = NumberUtils.firstMissingNum(plots.keySet());
        Plot plot = new Plot(plugin, address.getWorld(), plotid, type, address, new HashMap<>());
        plots.put(plotid, plot);
        return plot;
    }

    public void uploadPlot(Plot plot) {
        MongoCollection<Document> c = manager.getCollection(Collection.PLOTDATA.getCollName());

        Integer[] address = {plot.getAddress().getBlockX(), plot.getAddress().getBlockY(), plot.getAddress().getBlockZ()};

        BasicDBObject regions = new BasicDBObject();
        for (Integer regionId : plot.getRegions().keySet()) {
            BasicDBList cuboids = new BasicDBList();

            for (Cuboid cub : plot.getRegions().get(regionId).getCuboids()) {
                BasicDBList cuboidArray = new BasicDBList();
                Location p1 = cub.getPoint1();
                Location p2 = cub.getPoint2();
                Integer[] cuboid = {p1.getBlockX(), p1.getBlockY(), p1.getBlockZ(),
                        p2.getBlockX(), p2.getBlockY(), p2.getBlockZ()};
                Collections.addAll(cuboidArray, cuboid);
                cuboids.add(cuboidArray);
            }

            regions.put("" + regionId, cuboids);
        }

        Document doc = new Document("plotid", plot.getPlotId());
        doc.put("type", plot.getPlotType().name());
        doc.put("rent", plot.getRent().getName());
        doc.put("address", Arrays.asList(address));
        doc.put("regions", regions);
        doc.put("owner", plot.getOwner().toString());
        doc.put("members", Collections.singletonList(ListUtils.toStringList(plot.getMembers())));
        doc.put("rentlastpaid", plot.getRentlastpaid());
        doc.put("world", plot.getWorld().getName());

        UpdateOptions option = new UpdateOptions();
        option.upsert(true);
        c.replaceOne(eq("plotid", plot.getPlotId()), doc, option);
    }

    public void loadHolograms() {
        for (Plot p : plots.values()) {
            Location l = p.getAddress();

            Hologram hologram = HologramsAPI.createHologram(plugin, l);
            hologram.appendTextLine(ChatColor.GREEN + "PlotID: " + p.getPlotId());
            hologram.appendTextLine(ChatColor.GREEN + "Plot Type: " + p.getPlotType().name());
            hologram.appendTextLine(ChatColor.GREEN + "Rent: " + p.getRent().getCost() + " per " + p.getRent().getFrequency() + " seconds.");
            hologram.appendTextLine(ChatColor.GREEN + Bukkit.getPlayer(p.getOwner()).getName());

            VisibilityManager visibility = hologram.getVisibilityManager();
            visibility.setVisibleByDefault(false);
            // TODO visibility.showTo();
        }
    }

    public void saveAll() {
        int count = 0;
        for (Plot p : plots.values()) {
            uploadPlot(p);
            count++;
        }
        Debug.debug(DebugType.INFO, "Saved " + count + " plots into the database.");
    }

}
