package me.HeyAwesomePeople.Tycoon.world.plots;

/**
 * @author HeyAwesomePeople
 * @since Saturday, October 28 2017
 */
public class Business extends Plot {

    public Business(Integer plotId) {
        super(plotId);
    }

    @Override
    public PlotType getPlotType() {
        return PlotType.BUSINESS;
    }

}
