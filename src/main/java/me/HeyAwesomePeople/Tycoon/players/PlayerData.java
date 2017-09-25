package me.HeyAwesomePeople.Tycoon.players;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class PlayerData {

    @Getter @Setter private String playerName;
    @Getter private UUID playerID;

    @Getter @Setter private PlayerRole role;

    public PlayerData(UUID playerID, String playerName, PlayerRole role) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.role = role;
    }

}
