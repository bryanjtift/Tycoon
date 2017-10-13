package me.HeyAwesomePeople.Tycoon.setup;

import lombok.Getter;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import me.HeyAwesomePeople.Tycoon.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerTutorial implements Listener {

    private final Tycoon plugin;
    @Getter private boolean ready;
    @Getter private Location playerTeleportLocation;

    public PlayerTutorial(Tycoon plugin) {
        this.plugin = plugin;

        if (plugin.getConfigManager().getConfig("config").contains("new_player.location")) {
            playerTeleportLocation = LocationUtils.stringToLocation(plugin.getConfigManager().getConfig("config").getString("new_player.location"));
        }

        if (playerTeleportLocation == null) {
            Debug.debug(DebugType.ERROR, "PlayerTutorial failed to begin since there was no player teleport location!");
            ready = false;
            return;
        }

        ready = true;
    }

    public void startPlayer(Player player) {
        if (!ready) {
            return;
        }
        //TODO
    }

    @EventHandler
    public void playerClick(PlayerInteractEvent e) {

    }

}
