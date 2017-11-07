package me.HeyAwesomePeople.Tycoon.economy.rent;

import me.HeyAwesomePeople.Tycoon.Tycoon;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class RentManager {

    private final Tycoon plugin;
    private HashMap<String, Rent> rents = new HashMap<>();

    public RentManager(Tycoon plugin) {
        this.plugin = plugin;

        loadRentConfigurations();
    }

    private void loadRentConfigurations() {
        ConfigurationSection config = plugin.getConfigManager().getConfig("plots").getConfigurationSection("rent_macros");
        for (String id : config.getKeys(false)) {
            rents.put(id, new Rent(id, config.getLong(id + ".frequency"), config.getInt(id + ".cost")));
        }
    }

}
