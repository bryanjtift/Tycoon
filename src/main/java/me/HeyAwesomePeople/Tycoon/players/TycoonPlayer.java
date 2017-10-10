package me.HeyAwesomePeople.Tycoon.players;

import lombok.Getter;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.datamanaging.UserDataManager;
import me.HeyAwesomePeople.Tycoon.mongodb.MongoDBManager;
import org.bukkit.Bukkit;
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

    @Getter private List<String> achievements = new ArrayList<String>();

    public TycoonPlayer(Tycoon plugin, UUID playerID, String username) {
        this.plugin = plugin;
        this.playerID = playerID;
        this.username = username;

        this.dataManager = new UserDataManager(plugin, playerID, username, plugin.getMongoDBManager().getCollection(MongoDBManager.COLL_USERDATA));

        this.skills = new PlayerSkills(this);
        // TODO setSkills

        this.physical = new PlayerPhysical(playerID, 0, 0);
        // TODO setStamina and Fitness

        this.role = PlayerRole.CIVILIAN;
    }



}
