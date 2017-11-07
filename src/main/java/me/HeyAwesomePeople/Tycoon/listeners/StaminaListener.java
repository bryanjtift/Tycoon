package me.HeyAwesomePeople.Tycoon.listeners;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class StaminaListener implements Listener {

    private final Tycoon plugin;

    private BukkitTask task;
    private ArrayList<UUID> players = new ArrayList<>();
    private HashMap<UUID, Integer> times = new HashMap<>();

    public StaminaListener(Tycoon plugin) {
        this.plugin = plugin;

        task = new SprintingTimer().runTaskTimer(plugin, 20L, 20L);
    }

    public void destroyTask() {
        if (task != null)
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

    @RequiredArgsConstructor private class SprintingTimer extends BukkitRunnable {

        public void run() {
            //TODO show stamina information to play HUD
            for (UUID id : players) {
                if (!times.containsKey(id)) {
                    times.put(id, 1);
                } else {
                    times.put(id, times.get(id) + 1);
                }
            }

        }

    }

}
