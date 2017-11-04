package me.HeyAwesomePeople.Tycoon.commands.admin.plot;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.world.plots.Plot;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

/**
 * @author HeyAwesomePeople
 * @since Friday, November 03 2017
 */
@RequiredArgsConstructor public class PlotAddressCmd {

    private final Tycoon plugin;
    private final CommandSender sender;
    private final Plot plot;

    public void set(Location location) {
        plot.setAddress(location);

        sender.sendMessage(""); // TODO
    }

}
