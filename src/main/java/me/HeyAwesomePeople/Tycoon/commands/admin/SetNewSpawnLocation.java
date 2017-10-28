package me.HeyAwesomePeople.Tycoon.commands.admin;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import org.bukkit.Location;

@RequiredArgsConstructor public class SetNewSpawnLocation {

    private final Tycoon plugin;
    private final Location l;

    public void set() {
        plugin.getConfigManager().getConfig("config").set("new_player.location", l);
        plugin.getConfigManager().saveConfig("config");
        plugin.getWorldManager().reloadSpawnPoint();
    }

}
