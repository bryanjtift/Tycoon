package me.HeyAwesomePeople.Tycoon.listeners;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.datamanaging.UserDataManager;
import me.HeyAwesomePeople.Tycoon.mongodb.MongoDBManager;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author HeyAwesomePeople
 * @since Friday, September 29 2017
 */
@RequiredArgsConstructor public class PlayerJoinListener implements Listener {

    private final MongoDBManager mongoDBManager;

    @EventHandler
    public void playerPreJoinEvent(AsyncPlayerPreLoginEvent e) {
        Debug.debug(DebugType.INFO, "Player logged on.");
        UserDataManager manager = new UserDataManager(mongoDBManager, e.getUniqueId(), e.getName(), mongoDBManager.getCollection(MongoDBManager.COLL_USERDATA));
        Debug.debug(DebugType.INFO, "Manager: " + manager.getId());
    }

}
