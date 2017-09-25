package me.HeyAwesomePeople.Tycoon.players;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class PlayerPhysical {

    @Getter private UUID playerID;

    @Getter @Setter private Integer stamina;
    @Getter @Setter private Integer fitness;

    public PlayerPhysical(UUID playerID, Integer stamina, Integer fitness) {
        this.playerID = playerID;
        this.stamina = stamina;
        this.fitness = fitness;
    }

    public PlayerPhysical(UUID playerID) {
        this.playerID = playerID;
        this.stamina = 20;
        this.fitness = 20;
    }

}
