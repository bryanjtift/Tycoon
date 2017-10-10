package me.HeyAwesomePeople.Tycoon;

import me.HeyAwesomePeople.Tycoon.setup.PlayerTutorial;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;

public class GameManager {

    private final Tycoon plugin;

    public GameManager(Tycoon plugin) {
        this.plugin = plugin;

        // Check Starter Setup

        Debug.debug(DebugType.INFO, "Player tutorial loaded!");
    }

    public enum ComponentReady {
        FAILED, SUCCESS;
    }

    public enum GameReady {
        NOT_READY, EDIT, READY;
    }

}
