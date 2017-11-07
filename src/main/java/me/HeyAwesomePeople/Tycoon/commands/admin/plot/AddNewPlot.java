package me.HeyAwesomePeople.Tycoon.commands.admin.plot;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.world.plots.Plot;
import me.HeyAwesomePeople.Tycoon.world.plots.PlotType;
import me.HeyAwesomePeople.Tycoon.world.plots.plotparts.Region;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor public class AddNewPlot {

    private final Tycoon plugin;
    private final CommandSender sender;

    public void add(World world, PlotType type, Location location, Region... region) {
        plugin.getPlotManager().addNewPlot(world, type, location, region);

        sender.sendMessage(""); //TODO
    }

}
