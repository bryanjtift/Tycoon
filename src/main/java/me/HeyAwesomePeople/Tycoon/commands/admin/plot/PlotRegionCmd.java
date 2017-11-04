package me.HeyAwesomePeople.Tycoon.commands.admin.plot;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.world.plots.Plot;
import me.HeyAwesomePeople.Tycoon.world.plots.PlotType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor public class PlotRegionCmd {

    private final Tycoon plugin;
    private final CommandSender sender;
    private final Plot plot;

    public void add(Plot.Region region) {
        plot.addRegion(region);

        sender.sendMessage(""); // TODO
    }

    public void addCuboidToRegion(Integer regionID, Integer[] points) {
        plot.addCuboidToRegion(regionID, points);

        sender.sendMessage(""); // TODO
    }

    public void remove(Integer regionID) {
        plot.removeRegion(regionID);

        sender.sendMessage(""); // TODO
    }

}
