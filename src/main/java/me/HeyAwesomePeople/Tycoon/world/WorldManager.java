package me.HeyAwesomePeople.Tycoon.world;

import me.HeyAwesomePeople.Tycoon.Tycoon;
import org.bukkit.World;

import java.io.File;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class WorldManager {

    private Tycoon plugin;

    private World overworld;
    private World underworld;

    public WorldManager(Tycoon tycoon) {
        this.plugin = tycoon;

        loadWorldsIfExist();
    }

    private void loadWorldsIfExist() {
        File file = new File(plugin.getDataFolder() + File.separator);

    }

}
