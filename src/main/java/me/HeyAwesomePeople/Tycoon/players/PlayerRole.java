package me.HeyAwesomePeople.Tycoon.players;

import lombok.Getter;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public enum PlayerRole {

    CIVILIAN("civilian"),
    COP("cop");

    @Getter private String name;

    PlayerRole(String s) {
        this.name = s;
    }

}
