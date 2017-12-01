package me.HeyAwesomePeople.Tycoon.listeners;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author HeyAwesomePeople
 * @since Thursday, November 09 2017
 */
@RequiredArgsConstructor public class BuildListener implements Listener {

    private final Tycoon plugin;

    @EventHandler
    public void onBuildListener(BlockPlaceEvent e) {
        if (e.getPlayer().getLocation().getWorld().equals(plugin.getWorldManager().getUnderworld())) {
            if (e.getBlock().getType() != Material.OBSIDIAN) return;
            Integer maxBuildDistance = plugin.getWorldManager().getOverworldBorderRadius() / 8;
            if (Math.abs(e.getBlock().getLocation().getBlockX()) >= maxBuildDistance
                    || Math.abs(e.getBlock().getLocation().getBlockZ()) >= maxBuildDistance) {
                //TODO message cannot build portal 1/8 from border
                e.setCancelled(true);
            }
        }
    }

}
