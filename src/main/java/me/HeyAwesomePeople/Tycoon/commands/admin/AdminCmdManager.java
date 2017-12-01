package me.HeyAwesomePeople.Tycoon.commands.admin;

import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.world.WorldManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AdminCmdManager implements CommandExecutor {

    private final Tycoon plugin;

    private String adminPerm;
    private String deniedCmdUse;

    public AdminCmdManager(Tycoon plugin) {
        this.plugin = plugin;
        this.adminPerm = plugin.getConfigManager().getConfig("permissions").getString("admincmds");
        this.deniedCmdUse = plugin.getConfigManager().getConfig("messages").getString("commands.deniedCommandUse");
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String commandLabel, final String[] args) {
        if (!sender.hasPermission(adminPerm)) {
            sender.sendMessage(deniedCmdUse);
            return false;
        }

        if (commandLabel.equalsIgnoreCase("atycoon")) {
            if (args.length == 0) {
                //TODO information
            } else {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("setspawnlocation")) {
                        if (!(sender instanceof ConsoleCommandSender)) {
                            new SetNewSpawnLocation(plugin, ((Player) sender).getLocation()).set();
                        } else {
                            //TODO console cannot run this command
                        }
                    }
                    if (args[0].equalsIgnoreCase("worldinfo")) {
                        if (!(sender instanceof ConsoleCommandSender)) {
                            Player p = (Player) sender;
                            p.sendMessage(ChatColor.AQUA + "World: " + p.getLocation().getWorld().getName());
                        } else {
                            //TODO console cannot run this command
                        }
                    }
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("tpworld")) {
                        if (!(sender instanceof ConsoleCommandSender)) {
                            if (!args[1].equalsIgnoreCase("overworld")
                                    || !args[1].equalsIgnoreCase("underworld")) {
                                new TeleportToWorld(plugin).teleport(((Player) sender), args[1]);
                            } else {
                                new TeleportToWorld(plugin).teleport(((Player) sender), WorldManager.Worlds.valueOf(args[1]));
                            }
                        } else {
                            //TODO console cannot run this command
                        }
                    }
                }
                if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("setworldborder")) {
                        new SetNewWorldBorder(plugin, Integer.parseInt(args[2])).set(WorldManager.Worlds.valueOf(args[1]));
                    }
                }
            }
        }

        return false;
    }
}
