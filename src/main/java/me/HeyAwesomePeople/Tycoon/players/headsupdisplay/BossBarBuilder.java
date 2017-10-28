package me.HeyAwesomePeople.Tycoon.players.headsupdisplay;

import me.HeyAwesomePeople.Tycoon.Tycoon;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class BossBarBuilder {

    private Tycoon plugin;

    private static HashMap<UUID, BukkitTask> tasks = new HashMap<>();
    private static HashMap<UUID, BossBar> bars = new HashMap<>();

    private Player player;
    private BossBar bar;

    private String title;
    private BarColor color;
    private BarStyle style;
    private int segments = -1;
    private double progress = -1;
    private BarFlag[] flag;

    private int seconds = -1;

    public BossBarBuilder(Tycoon plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        bar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SEGMENTED_6, BarFlag.PLAY_BOSS_MUSIC);
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

        if (style.equals(BarStyle.SEGMENTED_6)) {
            segments = 6;
        } else if (style.equals(BarStyle.SEGMENTED_10)) {
            segments = 10;
        } else if (style.equals(BarStyle.SEGMENTED_12)) {
            segments = 12;
        } else if (style.equals(BarStyle.SEGMENTED_20)) {
            segments = 20;
        }

        return this;
    }

    //TODO eventually fix flags
    @Deprecated
    public BossBarBuilder flags(BarFlag... flags) {
        this.flag = flags;
        return this;
    }

    public BossBarBuilder progress(double progress) {
        this.progress = progress;
        return this;
    }

    public BossBarBuilder seconds(int seconds) {
        this.seconds = seconds;
        return this;
    }

    public void build() {
        BossBar bar = Bukkit.getServer().createBossBar(this.title, this.color, this.style);
        bar.setVisible(true);

        if (progress != -1)
            bar.setProgress(this.progress);

        bars.put(this.player.getUniqueId(), bar);
        bar.addPlayer(this.player);

        if (seconds != -1) {
            startTask();
        }
    }

    private void startTask() {
        tasks.put(player.getUniqueId(), new BukkitRunnable() {

            private int loops = BossBarBuilder.this.seconds * 20;
            private int interval = loops / segments;
            private int count = 0;

            @Override
            public void run() {
                if (loops <= 0) {
                    clear(player);
                    return;
                }
                if (count % interval == 0) {
                    double progress = (segments - (count / interval)) / 10.0;
                    Bukkit.broadcastMessage("Set Progress: " + progress + "; segments: " + segments + "; count: " + count + "; interval: " + interval);
                    setProgress(progress);
                }
                count++;
                loops--;
            }
        }.runTaskTimer(plugin, 0L, 1L));

    }

    public void setProgress(double progress) {
        bars.get(this.player.getUniqueId()).setProgress(progress);
    }

    public static void clear(Player player) {
        if (tasks.containsKey(player.getUniqueId())) {
            tasks.get(player.getUniqueId()).cancel();
            tasks.remove(player.getUniqueId());
        }
        if (bars.containsKey(player.getUniqueId())) {
            bars.get(player.getUniqueId()).removeAll();
            bars.remove(player.getUniqueId());
        }
    }

}
