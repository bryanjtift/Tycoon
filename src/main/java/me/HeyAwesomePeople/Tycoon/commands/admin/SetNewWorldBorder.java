package me.HeyAwesomePeople.Tycoon.commands.admin;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.utils.LocationUtils;
import me.HeyAwesomePeople.Tycoon.world.WorldManager;
import org.bukkit.Location;

@RequiredArgsConstructor public class SetNewWorldBorder {

    private final Tycoon plugin;
    private final Integer radius;

    public void set(WorldManager.Worlds world) {
        plugin.getConfigManager().getConfig("worlds").set(world.getWorldName() + ".borderRadius", radius);
        plugin.getConfigManager().saveConfig("worlds");

        plugin.getWorldManager().reloadBorders();
    }

}
