package me.HeyAwesomePeople.Tycoon.world.plots;

import me.HeyAwesomePeople.Tycoon.Tycoon;

/**
 * @author HeyAwesomePeople
 * @since Saturday, October 28 2017
 */
public class Business extends Plot {

    Business(Tycoon plugin, String world, Integer plotId) {
        super(plugin, world, plotId);
    }

    @Override
    public PlotType getPlotType() {
        return PlotType.BUSINESS;
    }

}
