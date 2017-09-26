package me.HeyAwesomePeople.Tycoon.players;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class TycoonPlayer {

    @Getter private UUID playerID;

    @Getter private PlayerSkills skills;
    @Getter private PlayerPhysical physical;
    @Getter private PlayerData data;

    @Getter private List<String> achievements = new ArrayList<String>();

    public TycoonPlayer(UUID playerID) {
        this.playerID = playerID;
    }



}
