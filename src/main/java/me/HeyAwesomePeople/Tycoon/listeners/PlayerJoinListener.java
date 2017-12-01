package me.HeyAwesomePeople.Tycoon.listeners;

import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.datamanaging.Statistics;
import me.HeyAwesomePeople.Tycoon.players.headsupdisplay.ActionBarBuilder;
import me.HeyAwesomePeople.Tycoon.players.headsupdisplay.BossBarBuilder;
import me.HeyAwesomePeople.Tycoon.players.headsupdisplay.TitleBuilder;
import me.HeyAwesomePeople.Tycoon.setup.ResourcePackApplication;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Date;

/**
 * @author HeyAwesomePeople
 * @since Friday, September 29 2017
 */
@RequiredArgsConstructor public class PlayerJoinListener implements Listener {

    private final Tycoon plugin;

    @EventHandler
    public void playerPreJoinEvent(AsyncPlayerPreLoginEvent e) {
        // load player from mongodb, or create player if not there already
        plugin.getPlayerManager().loadPlayer(e.getUniqueId(), e.getName());
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Statistics stats = plugin.getPlayerManager().getPlayer(e.getPlayer().getUniqueId()).getDataManager().getStats();

        // last login
        stats.setString("lastlogin", String.valueOf(new Date().getTime()));

        // first login, new player
        if (!stats.hasKey("firstlogin")) {
            e.getPlayer().teleport(plugin.getWorldManager().getSpawnPoint());

            stats.setString("firstlogin", String.valueOf(new Date().getTime()));

            new TitleBuilder(e.getPlayer()).title(plugin.getConfigManager().getConfig("config").getString("new_player.joinMsg")).stay(2).send();
        } else {
            new TitleBuilder(e.getPlayer()).title(plugin.getConfigManager().getConfig("config").getString("returning_player.joinMsg")).stay(2).send();
        }

        // make sure they have downloaded resource pack
        // new ResourcePackApplication(plugin, e.getPlayer());
    }

}
