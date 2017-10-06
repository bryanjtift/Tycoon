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

    public void loadPlayer(UUID id, String username) {
        players.put(id, new TycoonPlayer(plugin, id, username));
    }

    public TycoonPlayer getPlayer(UUID id) {
        return players.get(id);
    }

}
