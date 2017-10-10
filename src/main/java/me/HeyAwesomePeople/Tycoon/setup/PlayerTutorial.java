package me.HeyAwesomePeople.Tycoon.setup;

import lombok.Getter;
import me.HeyAwesomePeople.Tycoon.GameManager;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerTutorial implements Listener {

    private final Tycoon plugin;
    @Getter private Location playerTeleportLocation;

    public PlayerTutorial(Tycoon plugin) {
        this.plugin = plugin;

        if (plugin.getConfigManager().getConfig("config").contains("new_player.location")) {

        }
    }

    @EventHandler
    public void playerClick(PlayerInteractEvent e) {

    }

}
