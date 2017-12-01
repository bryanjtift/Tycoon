package me.HeyAwesomePeople.Tycoon.players;

import lombok.Getter;
import lombok.Setter;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.datamanaging.UserDataManager;
import me.HeyAwesomePeople.Tycoon.mongodb.Collection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class TycoonPlayer {

    private final Tycoon plugin;

    @Getter private Player player;
    @Getter private UUID playerID;
    @Getter private String username;
    @Getter private UserDataManager dataManager;

    @Getter private PlayerSkills skills;
    @Getter private PlayerPhysical physical;
    @Getter private PlayerRole role;

    @Getter @Setter private Integer karma;

    @Getter private List<String> achievements = new ArrayList<>();

    public TycoonPlayer(Tycoon plugin, UUID playerID, String username) {
        this.plugin = plugin;
        this.playerID = playerID;
        this.username = username;

        this.dataManager = new UserDataManager(plugin, playerID, username, plugin.getASyncMongoDBManager().getCollection(Collection.USERDATA.getCollName()));

        this.skills = new PlayerSkills(this);
        skills.saveSkillsData();

        this.physical = new PlayerPhysical(this);
        physical.savePhysicalData();

        setupRole();
        setupKarma();
    }

    private void setupRole() {
        if (dataManager.getAttributes().hasKey("playerrole")) {
            this.role = PlayerRole.valueOf(dataManager.getAttributes().getString("playerrole"));
        } else {
            this.role = PlayerRole.CIVILIAN;
        }
    }

    private void addKarma(int value) {
        this.karma += value;
    }

    private void removeKarma(int value) {
        this.karma -= value;
    }

    private void setupKarma() {
        if (dataManager.getStats().hasKey("karma")) {
            this.karma = dataManager.getStats().getInt("karma");
        } else {
            this.karma = 0;
            dataManager.getStats().setInt("karma", 0);
        }
    }

    public void unloadPlayer() {
        dataManager.getStats().setInt("karma", karma);
        dataManager.getStats().setString("playerrole", role.getName());
        skills.saveSkillsData();
        physical.savePhysicalData();

        dataManager.updateDocument();
    }

}
