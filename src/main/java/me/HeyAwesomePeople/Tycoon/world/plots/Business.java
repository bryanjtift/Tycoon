package me.HeyAwesomePeople.Tycoon.world.plots;

import me.HeyAwesomePeople.Tycoon.Tycoon;
import org.bson.Document;

/**
 * @author HeyAwesomePeople
 * @since Saturday, October 28 2017
 */
public class Business extends Plot {

    Business(Tycoon plugin, String world, Integer plotId, Document document) {
        super(plugin, world, plotId, document);
    }

    @Override
    public PlotType getPlotType() {
        return PlotType.BUSINESS;
    }

}
