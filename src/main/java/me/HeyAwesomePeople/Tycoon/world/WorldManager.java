package me.HeyAwesomePeople.Tycoon.world;

import lombok.Getter;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import me.HeyAwesomePeople.Tycoon.utils.LocationUtils;
import me.HeyAwesomePeople.Tycoon.world.plots.PlotManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class WorldManager {

    private Tycoon plugin;

    @Getter private World overworld;
    @Getter private World underworld;

    @Getter private Location spawnPoint;

    @Getter private PlotManager plotManager;

    public WorldManager(Tycoon tycoon) {
        this.plugin = tycoon;

        loadWorldIfExist();
        loadNetherIfExist();

        reloadSpawnPoint();
    }

    private void loadWorldIfExist() {
        String worldName = plugin.getConfigManager().getConfig("worlds").getString("overworld.fileName");
        if (Bukkit.getWorld(worldName) == null) {
            Debug.debug(DebugType.INFO, "Tycoon did not find a custom overworld, we'll just use the default..");
        }
        overworld = Bukkit.createWorld(new WorldCreator(worldName));
    }

    private void loadNetherIfExist() {
        String worldName = plugin.getConfigManager().getConfig("worlds").getString("underworld.fileName");
        if (Bukkit.getWorld(worldName) == null) {
            Debug.debug(DebugType.INFO, "Tycoon did not find a custom underworld, we'll just use the default.");
        }
        underworld = Bukkit.createWorld(new WorldCreator(worldName).environment(World.Environment.NETHER));
    }

    public void reloadSpawnPoint() {
        if (plugin.getConfigManager().getConfig("config").contains("new_player.location")) {
            spawnPoint = LocationUtils.stringToLocation(plugin.getConfigManager().getConfig("config").getString("new_player.location"));
        } else {
            spawnPoint = overworld.getSpawnLocation();
            Debug.debug(DebugType.WARNING, "Unable to find spawn location for new players. Defaulted to the natural spawn location.");
        }
    }

}
