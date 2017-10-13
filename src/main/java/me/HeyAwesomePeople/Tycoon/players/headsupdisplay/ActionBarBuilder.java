package me.HeyAwesomePeople.Tycoon.players.headsupdisplay;

import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.utils.ReflectionAPI;
import net.minecraft.server.v1_12_R1.Tuple;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.UUID;

public class ActionBarBuilder {

    private Tycoon plugin;

    private Player player;
    private static HashMap<UUID, BukkitTask> tasks = new HashMap<>();

    public ActionBarBuilder(Tycoon plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    private String message = "";
    private boolean foreverTime = false;
    private Integer seconds = -1;

    private boolean foreverLoop = false;
    private Integer charLimit = 32;
    private Integer loops = -1;

    public ActionBarBuilder message(String msg) {
        this.message = msg;
        return this;
    }

    public ActionBarBuilder forever() {
        this.foreverTime = true;
        return this;
    }

    public ActionBarBuilder time(Integer seconds) {
        this.seconds = seconds;
        return this;
    }

    public ActionBarBuilder foreverLoop() {
        this.foreverLoop = true;
        return this;
    }

    public ActionBarBuilder loops(Integer loops) {
        this.loops = loops;
        return this;
    }

    public ActionBarBuilder charLimit(Integer limit) {
        this.charLimit = limit;
        return this;
    }

    public void send() {
        if (foreverTime || seconds != -1) {
            startTask();
        }
        sendActionBar(this.player, this.message);
    }

    private void startTask() {
        tasks.put(player.getUniqueId(), new BukkitRunnable() {
            @Override
            public void run() {
                if (seconds == 0) {
                    tasks.get(player.getUniqueId()).cancel();
                }
                if (seconds != -1) {
                    seconds--;
                }
                sendActionBar(player, message);
            }
        }.runTaskTimer(this.plugin, 20L, 20L));
    }

    public static void clear(Player p) {
        tasks.get(p.getUniqueId()).cancel();
        sendActionBar(p, "");
    }

    private static void sendActionBar(Player p, String msg) {
        try {
            Class<?> packetClass = ReflectionAPI.getNmsClass("PacketPlayOutChat");
            Class<?> componentClass = ReflectionAPI.getNmsClass("IChatBaseComponent");
            Class<?> serializerClass = ReflectionAPI.getNmsClass("IChatBaseComponent$ChatSerializer");
            Constructor<?> packetConstructor = packetClass.getConstructor(componentClass, byte.class);
            Object BaseComponent = serializerClass.getMethod("a", String.class).invoke(null, "{\"text\": \"" + msg + "\"}");
            Object packet = packetConstructor.newInstance(BaseComponent, (byte) 2);
            ReflectionAPI.sendPacket(p, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}