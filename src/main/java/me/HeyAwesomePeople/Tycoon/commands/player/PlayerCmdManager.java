package me.HeyAwesomePeople.Tycoon.commands.player;

import me.HeyAwesomePeople.Tycoon.Tycoon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlayerCmdManager implements CommandExecutor {

    private final Tycoon plugin;

    public PlayerCmdManager(Tycoon plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        return false;
    }

}
