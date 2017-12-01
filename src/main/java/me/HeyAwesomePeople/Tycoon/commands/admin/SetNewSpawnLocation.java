package me.HeyAwesomePeople.Tycoon.commands.admin;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.utils.LocationUtils;
import me.HeyAwesomePeople.Tycoon.world.WorldManager;
import org.bukkit.Location;

@RequiredArgsConstructor public class SetNewSpawnLocation {

    private final Tycoon plugin;
    private final Location l;

    public void set() {
        plugin.getConfigManager().getConfig("worlds").set("overworld.spawnpoint", LocationUtils.locationToStringNoWorld(l));
        plugin.getConfigManager().saveConfig("worlds");
        plugin.getWorldManager().reloadOverworldSpawnPoint();
    }

}
