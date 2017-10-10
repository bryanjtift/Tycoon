package me.HeyAwesomePeople.Tycoon.configuration;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

@RequiredArgsConstructor public class ConfigManager {

    private final Tycoon plugin;

    private HashMap<String, FileConfiguration> customConfigs = new HashMap<>();
    private HashMap<String, File> customFiles = new HashMap<>();

    public void newConfig(String name) {
        String ymlName = name + ".yml";
        File file = new File(plugin.getDataFolder(), ymlName);

        if (!file.exists()) {
            saveDefaultConfig(name);
        }

        customFiles.computeIfAbsent(ymlName, n -> file);
        customConfigs.put(ymlName, YamlConfiguration.loadConfiguration(customFiles.get(ymlName)));

        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource(ymlName);
        if (defConfigStream != null) {
            FileConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            customConfigs.get(ymlName).setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig(String name) {
        String ymlName = name + ".yml";
        if (!customConfigs.containsKey(ymlName)) {
            newConfig(name);
        }
        return customConfigs.get(ymlName);
    }

    public void saveConfig(String name) {
        String ymlName = name + ".yml";
        if (!customConfigs.containsKey(ymlName)
                || customFiles.containsKey(ymlName)) {
            return;
        }
        try {
            getConfig(ymlName).save(customFiles.get(ymlName));
        } catch (IOException e) {
            Debug.debug(DebugType.ERROR, "Could not save config '" + ymlName + "' to " + customFiles.get(ymlName));
            e.printStackTrace();
        }
    }

    public void saveDefaultConfig(String name) {
        String ymlName = name + ".yml";
        if (!customFiles.containsKey(ymlName)) {
            customFiles.computeIfAbsent(ymlName, n -> new File(plugin.getDataFolder(), ymlName));
        }
        if (!customFiles.get(ymlName).exists()) {
            plugin.saveResource(ymlName, false);
        }
    }

}
