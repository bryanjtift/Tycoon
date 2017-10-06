package me.HeyAwesomePeople.Tycoon.listeners;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.datamanaging.Statistics;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;

/**
 * @author HeyAwesomePeople
 * @since Saturday, September 30 2017
 */
@RequiredArgsConstructor public class PlayerLeaveListener implements Listener {

    private final Tycoon plugin;

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent e) {
        Debug.debug(DebugType.INFO, "Player quit.");

        // statistics
        Statistics stats = plugin.getPlayerManager().getPlayer(e.getPlayer().getUniqueId()).getDataManager().getStats();

        // last logout
        stats.setString("lastlogout", String.valueOf(new Date().getTime()));

        // update data to mongo
        plugin.getPlayerManager().getPlayer(e.getPlayer().getUniqueId()).getDataManager().updateDocument();
    }

}
