package me.HeyAwesomePeople.Tycoon;

import lombok.Getter;
import me.HeyAwesomePeople.Tycoon.commands.admin.AdminCmdManager;
import me.HeyAwesomePeople.Tycoon.commands.player.PlayerCmdManager;
import me.HeyAwesomePeople.Tycoon.configuration.ConfigManager;
import me.HeyAwesomePeople.Tycoon.listeners.PlayerJoinListener;
import me.HeyAwesomePeople.Tycoon.listeners.PlayerLeaveListener;
import me.HeyAwesomePeople.Tycoon.listeners.StaminaListener;
import me.HeyAwesomePeople.Tycoon.mongodb.MongoDBManager;
import me.HeyAwesomePeople.Tycoon.players.PlayerManager;
import me.HeyAwesomePeople.Tycoon.setup.PlayerTutorial;
import me.HeyAwesomePeople.Tycoon.world.WorldManager;
import me.HeyAwesomePeople.Tycoon.world.plots.PlotManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class Tycoon extends JavaPlugin {

    @Getter private ConfigManager configManager;

    @Getter private WorldManager worldManager;
    @Getter private PlotManager plotManager;
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
        configManager.newConfig("permissions");
        configManager.newConfig("messages");
        mongoDBManager = new MongoDBManager();

        worldManager = new WorldManager(this);
        plotManager = new PlotManager(this);
        playerTutorial = new PlayerTutorial(this);
        playerManager = new PlayerManager(this);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(this), this);
        staminaListener = new StaminaListener(this);
        getServer().getPluginManager().registerEvents(staminaListener, this);

        getCommand("atycoon").setExecutor(new AdminCmdManager(this));
        getCommand("tycoon").setExecutor(new PlayerCmdManager(this));
    }

    @Override
    public void onDisable() {
        staminaListener.destroyTask();
    }

}
