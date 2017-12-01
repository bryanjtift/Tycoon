package me.HeyAwesomePeople.Tycoon.commands.admin;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author HeyAwesomePeople
 * @since Thursday, November 09 2017
 */
@RequiredArgsConstructor
class TeleportToWorld {

    private final Tycoon plugin;

    void teleport(Player p, WorldManager.Worlds world) {
        if (world == WorldManager.Worlds.OVERWORLD)
            p.teleport(plugin.getWorldManager().getOverworld().getSpawnLocation());
        else
            p.teleport(plugin.getWorldManager().getUnderworld().getSpawnLocation());
    }

    void teleport(Player p, String worldname) {
        p.teleport(Bukkit.getWorld(worldname).getSpawnLocation());
    }

}
