package me.HeyAwesomePeople.Tycoon.players.headsupdisplay;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class BossBarBuilder {

    private static HashMap<UUID, BossBar> bars = new HashMap<>();

    private Player player;
    private BossBar bar;

    private String title;
    private BarColor color;
    private BarStyle style;
    private int progress;
    private BarFlag[] flag = new BarFlag[3];

    public BossBarBuilder(Player player) {
        this.player = player;

        BossBar bar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SEGMENTED_12, BarFlag.PLAY_BOSS_MUSIC);
    }

    public BossBarBuilder title(String title) {
        this.title = title;
        return this;
    }

    public BossBarBuilder color(BarColor color) {
        this.color = color;
        return this;
    }

    public BossBarBuilder style(BarStyle style) {
        this.style = style;
        return this;
    }

    public BossBarBuilder flags(BarFlag... flags) {
        this.flag = flags;
        return this;
    }

    public BossBarBuilder progress(int progress) {
        this.progress = progress;
        return this;
    }

    public void build() {
        BossBar bar = Bukkit.createBossBar(this.title, this.color, this.style, this.flag);
        bar.setVisible(true);
        bar.setProgress(this.progress);

        bars.put(this.player.getUniqueId(), bar);
    }

    public void setProgress(int progress) {
        bars.get(this.player.getUniqueId()).setProgress(progress);
    }

    public static void clear(Player player) {
        if (!bars.containsKey(player.getUniqueId())) return;
        bars.get(player.getUniqueId()).removeAll();
        bars.remove(player.getUniqueId());
    }

}
