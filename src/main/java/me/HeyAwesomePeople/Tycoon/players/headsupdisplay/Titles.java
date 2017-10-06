package me.HeyAwesomePeople.Tycoon.players.headsupdisplay;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor public class Titles {

    public void send(Player player, TitleType type, String value, int stay) {
        //TODO titles
    }

    public enum TitleType {
        BIG_TITLE,
        SMALL_TITLE,
        ACTION_BAR;
    }

}
