package me.HeyAwesomePeople.Tycoon;

import lombok.Getter;
import me.HeyAwesomePeople.Tycoon.configuration.ConfigManager;
import me.HeyAwesomePeople.Tycoon.listeners.PlayerJoinListener;
import me.HeyAwesomePeople.Tycoon.listeners.PlayerLeaveListener;
import me.HeyAwesomePeople.Tycoon.listeners.StaminaListener;
import me.HeyAwesomePeople.Tycoon.mongodb.MongoDBManager;
import me.HeyAwesomePeople.Tycoon.players.PlayerManager;
import me.HeyAwesomePeople.Tycoon.setup.PlayerTutorial;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class Tycoon extends JavaPlugin {

    @Getter private ConfigManager configManager;

    @Getter private PlayerManager playerManager;
    @Getter private MongoDBManager mongoDBManager;

    private StaminaListener staminaListener;

    @Getter private PlayerTutorial playerTutorial;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        configManager.newConfig("config");
        configManager.newConfig("plots");
        configManager.newConfig("worlds");

        playerTutorial = new PlayerTutorial(this);

        playerManager = new PlayerManager(this);
        mongoDBManager = new MongoDBManager();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(this), this);
        staminaListener = new StaminaListener(this);
        getServer().getPluginManager().registerEvents(staminaListener, this);
    }

    @Override
    public void onDisable() {
        staminaListener.destroyTask();
    }

}
