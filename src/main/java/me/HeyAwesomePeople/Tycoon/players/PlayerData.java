package me.HeyAwesomePeople.Tycoon.players;

import java.util.UUID;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class PlayerData {

    private String playerName;
    private UUID playerID;

    public PlayerData(UUID playerID) {
        this.playerID = playerID;
    }


}
