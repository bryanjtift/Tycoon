package me.HeyAwesomePeople.Tycoon;

import lombok.Getter;
import me.HeyAwesomePeople.Tycoon.players.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class Tycoon extends JavaPlugin {

    @Getter private PlayerManager playerManager;

    @Override
    public void onEnable() {
        playerManager = new PlayerManager(this);
    }

    @Override
    public void onDisable() {
        playerManager.save();
    }

}
