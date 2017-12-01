package me.HeyAwesomePeople.Tycoon.commands.admin.plot;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.utils.LocationUtils;
import me.HeyAwesomePeople.Tycoon.world.plots.Plot;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor public class PlotInfoCmd {

    private final Tycoon plugin;
    private final CommandSender sender;
    private final Plot plot;

    public void getInfo() {
        sender.sendMessage(ChatColor.GREEN + "Plot: " + plot.getPlotId());
        sender.sendMessage(ChatColor.GREEN + "Type: " + plot.getPlotType().name());
        sender.sendMessage(ChatColor.GREEN + "Address: " + LocationUtils.locationToStringNoWorld(plot.getAddress()));
    }

}
