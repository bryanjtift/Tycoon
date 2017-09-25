package me.HeyAwesomePeople.Tycoon.players;

import me.HeyAwesomePeople.Tycoon.Tycoon;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private Tycoon plugin;

    private HashMap<UUID, TycoonPlayer> players = new HashMap<UUID, TycoonPlayer>();

    public PlayerManager(Tycoon plugin) {
        this.plugin = plugin;
    }

    public void save() {

    }
}
