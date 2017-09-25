package me.HeyAwesomePeople.Tycoon.listeners;

import me.HeyAwesomePeople.Tycoon.Tycoon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StaminaListener implements Listener {

    private Tycoon plugin;

    private BukkitTask task;
    private List<UUID> players = new ArrayList<UUID>();

    public StaminaListener(Tycoon plugin) {
        this.plugin = plugin;
        task = new SprintingTimer(plugin).runTaskTimer(plugin, 20L, 20L);
    }

    public void destroyTask() {
        task.cancel();
    }

    @EventHandler
    public void playerSprint(PlayerToggleSprintEvent e) {
        if (e.getPlayer().isSprinting()) {
            players.add(e.getPlayer().getUniqueId());
        } else {
            players.remove(e.getPlayer().getUniqueId());
        }
    }

    public class SprintingTimer extends BukkitRunnable {

        private Tycoon plugin;

        public SprintingTimer(Tycoon plugin) {
            this.plugin = plugin;
        }

        public void run() {
            for (UUID id : players) {

            }
        }

    }

}
