package me.HeyAwesomePeople.Tycoon.world.plots;

import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.world.plots.plotparts.Region;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;

/**
 * @author HeyAwesomePeople
 * @since Saturday, October 28 2017
 */
public class Business extends Plot {

    Business(Tycoon plugin, World world, Integer id, PlotType type, Location address, HashMap<Integer, Region> regions) {
        super(plugin, world, id, type, address, regions);
    }

    @Override
    public PlotType getPlotType() {
        return PlotType.BUSINESS;
    }

}
