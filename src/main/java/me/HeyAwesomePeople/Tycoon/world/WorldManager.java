package me.HeyAwesomePeople.Tycoon.world;

import lombok.Getter;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import me.HeyAwesomePeople.Tycoon.utils.LocationUtils;
import me.HeyAwesomePeople.Tycoon.world.plots.PlotManager;
import org.bukkit.*;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class WorldManager {

    private Tycoon plugin;

    @Getter private World overworld;
    @Getter private Integer overworldBorderRadius;
    @Getter private World underworld;
    @Getter private Integer underworldBorderRadius;

    @Getter private Location spawnPoint;

    @Getter private PlotManager plotManager;

    public WorldManager(Tycoon tycoon) {
        this.plugin = tycoon;

        loadWorldIfExist();
        loadNetherIfExist();

        reloadBorders();
        setupWorldBorders();

        reloadOverworldSpawnPoint();
    }

    public void reloadBorders() {
        this.overworldBorderRadius = plugin.getConfigManager().getConfig("worlds").getInt("overworld.borderRadius", 5000);
        this.underworldBorderRadius = plugin.getConfigManager().getConfig("worlds").getInt("underworld.borderRadius", 5000);

        Debug.debug(DebugType.INFO, "Loaded border radius for overworld with value '" + overworldBorderRadius + "'.");
        Debug.debug(DebugType.INFO, "Loaded border radius for underworld with value '" + underworldBorderRadius + "'.");

        setupWorldBorders();
    }

    private void setupWorldBorders() {
        WorldBorder border = overworld.getWorldBorder();
        border.setCenter(0, 0);
        border.setSize(overworldBorderRadius);
        border.setWarningDistance(10);

        WorldBorder border2 = underworld.getWorldBorder();
        border2.setCenter(0, 0);
        border2.setSize(underworldBorderRadius);
        border2.setWarningDistance(10);
    }

    private void loadWorldIfExist() {
        String worldName = plugin.getConfigManager().getConfig("worlds").getString("overworld.fileName");
        if (Bukkit.getWorld(worldName) == null) {
            Debug.debug(DebugType.INFO, "Tycoon did not find a custom overworld, we'll just use the default.");
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

    public void reloadOverworldSpawnPoint() {
        if (plugin.getConfigManager().getConfig("worlds").contains("overworld.spawnpoint")) {
            spawnPoint = LocationUtils.stringToLocation(plugin.getConfigManager().getConfig("worlds").getString("overworld.spawnpoint"), overworld);
        } else {
            spawnPoint = overworld.getSpawnLocation();
            Debug.debug(DebugType.WARNING, "Unable to find spawn location the overworld. Defaulted to the natural spawn location.");
        }
    }

    public enum Worlds {
        OVERWORLD("overworld"),
        UNDERWORLD("underworld");

        @Getter String worldName;

        Worlds(String w) {
            this.worldName = w;
        }

    }

}
