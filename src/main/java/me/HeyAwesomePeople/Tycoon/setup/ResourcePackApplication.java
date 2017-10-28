package me.HeyAwesomePeople.Tycoon.setup;

import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.datamanaging.UserDataManager;
import me.HeyAwesomePeople.Tycoon.players.headsupdisplay.ActionBarBuilder;
import me.HeyAwesomePeople.Tycoon.players.headsupdisplay.TitleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class ResourcePackApplication implements Listener {

    private Tycoon plugin;
    private Player player;

    private UserDataManager userData;

    public ResourcePackApplication(Tycoon plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        userData = plugin.getPlayerManager().getPlayer(player.getUniqueId()).getDataManager();

        String packURL = plugin.getConfigManager().getConfig("config").getString("resource_pack", "");

        if (!userData.getAttributes().getBoolean("resourcePack")) {
            if (!packURL.equals("")) {
                //TODO timer to check if it worked
                player.setResourcePack(packURL);
            }
        } else {
            new ActionBarBuilder(plugin, player).foreverLoop().scrollerSpeed(5).message("&cFailed to load server resource pack. Run /rpack");
            //TODO /rpack command to retry texture pack sending
        }
    }

    @EventHandler
    public void resourceListener(PlayerResourcePackStatusEvent e) {
        if (e.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED || e.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD) {
            //TODO send player a message via big letters that they need to get the resource pack, clickable text in chat
            userData.getAttributes().setBoolean("resourcePack", false);
        } else {
            userData.getAttributes().setBoolean("resourcePack", true);
        }

        if (!userData.getStats().hasKey("lastlogout")) {
            plugin.getPlayerTutorial().startPlayer(e.getPlayer());
        }
    }

}
