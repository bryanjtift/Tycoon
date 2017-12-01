package me.HeyAwesomePeople.Tycoon.commands.admin.plot;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.world.plots.Plot;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor public class PlotMembersCmd {

    private final Tycoon plugin;
    private final CommandSender sender;
    private final Plot plot;

    public void setOwner(UUID id) {
        plot.setOwner(id);
    }

    public void removeOwner() {
        plot.setOwner(null);
    }

    public void addMember(UUID id) {
        plot.addMember(id);
    }

    public void removeMember(UUID id) {
        plot.removeMember(id);
    }

    public void clearMembers() {
        plot.setMembers(new ArrayList<>());
    }



}
