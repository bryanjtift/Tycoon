package me.HeyAwesomePeople.Tycoon.players.headsupdisplay;

import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.utils.Scroller;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class ActionBarBuilder {

    private Tycoon plugin;

    private static String nmsver;

    private Player player;
    private static HashMap<UUID, BukkitTask> tasks = new HashMap<>();

    public ActionBarBuilder(Tycoon plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        nmsver = Bukkit.getServer().getClass().getPackage().getName();
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
    }

    private String message = "";
    private boolean foreverTime = false;
    private Integer seconds = -1;

    private boolean foreverLoop = false;
    private Integer charLimit = 32;
    private Integer loops = -1;
    private Integer scrollSpeed = 3;
    private Scroller scroller;


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

    public ActionBarBuilder scrollerSpeed(int speed) {
        this.scrollSpeed = speed;
        return this;
    }

    public ActionBarBuilder charLimit(Integer limit) {
        this.charLimit = limit;
        return this;
    }

    /**
     * Start any loops needed to send the action bar to the player
     */
    public void send() {
        clear(player);

        sendActionBar(this.player, this.message.substring(0, this.charLimit - 2));

        if (foreverLoop || loops != -1) {
            scroller = new Scroller(this.message, this.charLimit, 0, '&');
            startScrollerTask();
            return;
        }

        if (foreverTime || seconds != -1) {
            startTask();
        }
    }

    /**
     * Start task to handle scrollers with time or loop parameters
     */
    private void startScrollerTask() {
        tasks.put(player.getUniqueId(), new BukkitRunnable() {

            private int seconds = ActionBarBuilder.this.seconds * 20 / ActionBarBuilder.this.scrollSpeed;

            @Override
            public void run() {
                String newMsg = scroller.next();
                if (ActionBarBuilder.this.seconds != -1) {
                    if (seconds <= 0) {
                        clear(player);
                    }
                    seconds--;
                }
                if (loops != -1) {
                    if (newMsg.substring(2).equals(message.substring(0, charLimit - 2))) {
                        if (loops == 0) {
                            clear(player);
                        }
                        loops--;
                    }
                }
                sendActionBar(player, newMsg);
            }
        }.runTaskTimer(plugin, 10L, this.scrollSpeed));
    }


    /**
     * Start task with just a time parameter
     */
    private void startTask() {
        tasks.put(player.getUniqueId(), new BukkitRunnable() {
            @Override
            public void run() {
                if (seconds == 0) {
                    clear(player);
                }
                if (seconds != -1) {
                    seconds--;
                }
                sendActionBar(player, message);
            }
        }.runTaskTimer(this.plugin, 20L, 20L));
    }

    /**
     * Remove all action bar tasks from a player
     *
     * @param p Player to remove action bar tasks from
     */
    public static void clear(Player p) {
        if (tasks.containsKey(p.getUniqueId())) {
            tasks.get(p.getUniqueId()).cancel();
            tasks.remove(p.getUniqueId());
        }
    }

    private static void sendActionBar(Player player, String message) {
        if (!player.isOnline()) {
            return; // Player may have logged out
        }

        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);
            Object ppoc;
            Class<?> c4 = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
            Class<?> c5 = Class.forName("net.minecraft.server." + nmsver + ".Packet");
            Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
            Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
            Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nmsver + ".ChatMessageType");
            Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
            Object chatMessageType = null;
            for (Object obj : chatMessageTypes) {
                if (obj.toString().equals("GAME_INFO")) {
                    chatMessageType = obj;
                }
            }
            Object o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(message);
            ppoc = c4.getConstructor(new Class<?>[]{c3, chatMessageTypeClass}).newInstance(o, chatMessageType);
            Method m1 = craftPlayerClass.getDeclaredMethod("getHandle");
            Object h = m1.invoke(craftPlayer);
            Field f1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = f1.get(h);
            Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
            m5.invoke(pc, ppoc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}