package me.HeyAwesomePeople.Tycoon.world;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.TravelAgent;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.PortalCreateEvent;

@RequiredArgsConstructor public class PortalListener implements Listener {

    private final Tycoon plugin;

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        Bukkit.broadcastMessage("Portal Event Happened.");
    }

    @EventHandler
    public void enterPortal(EntityPortalEnterEvent e) {
    }

    @EventHandler
    public void portal(PortalCreateEvent e) {
        if (e.getWorld().equals(plugin.getWorldManager().getUnderworld())) {
            Integer maxBuildDistance = plugin.getWorldManager().getOverworldBorderRadius() / 8;
            Block b = e.getBlocks().get(0);
            if (Math.abs(b.getLocation().getBlockX()) >= maxBuildDistance
                    || Math.abs(b.getLocation().getBlockZ()) >= maxBuildDistance) {
                for (Entity entity : b.getWorld().getEntities()) {
                    if (b.getLocation().distance(entity.getLocation()) <= 4) {
                        if (entity instanceof Player) {
                            entity.sendMessage(""); // TODO message
                        }
                    }
                }
                e.setCancelled(true);
            }
        }
    }

}
