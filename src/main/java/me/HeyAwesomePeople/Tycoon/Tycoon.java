package me.HeyAwesomePeople.Tycoon;

import lombok.Getter;
import me.HeyAwesomePeople.Tycoon.listeners.PlayerJoinListener;
import me.HeyAwesomePeople.Tycoon.mongodb.MongoDBManager;
import me.HeyAwesomePeople.Tycoon.players.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class Tycoon extends JavaPlugin {

    @Getter private PlayerManager playerManager;
    @Getter private MongoDBManager mongoDBManager;

    @Override
    public void onEnable() {
        playerManager = new PlayerManager(this);
        mongoDBManager = new MongoDBManager();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(mongoDBManager), this);
    }

    @Override
    public void onDisable() {
        playerManager.save();
    }

}
