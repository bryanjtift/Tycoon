package me.HeyAwesomePeople.Tycoon.listeners;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@RequiredArgsConstructor public class FitnessListener implements Listener {

    private final Tycoon plugin;

    @EventHandler
    public void onPlayerWalk(PlayerMoveEvent e) {

    }

}
