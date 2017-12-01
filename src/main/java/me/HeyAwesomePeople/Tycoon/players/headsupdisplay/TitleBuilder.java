package me.HeyAwesomePeople.Tycoon.players.headsupdisplay;

import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_12_R1.ChatComponentText;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class TitleBuilder {

    private Player player;

    public TitleBuilder(Player player) {
        this.player = player;
    }

    private String title = "";
    private String subtitle = "";
    private Integer fadeIn = 1;
    private Integer stay = 2;
    private Integer fadeOut = 1;

    public TitleBuilder title(String title) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        return this;
    }

    public TitleBuilder subtitle(String subtitle) {
        this.subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
        return this;
    }

    public TitleBuilder fadeIn(Integer fadeIn) {
        this.fadeIn = fadeIn;
        return this;
    }

    public TitleBuilder stay(Integer stay) {
        this.stay = stay;
        return this;
    }

    public TitleBuilder fadeOut(Integer fadeOut) {
        this.fadeOut = fadeOut;
        return this;
    }

    public void send() {
        if (!title.isEmpty()) {
            PacketPlayOutTitle bigTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, new ChatComponentText(this.title), fadeIn * 20, stay * 20, fadeOut * 20);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(bigTitlePacket);
        }
        if (!subtitle.isEmpty()) {
            PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, new ChatComponentText(this.title), fadeIn * 20, stay * 20, fadeOut * 20);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitlePacket);
        }
        PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn * 20, stay * 20, fadeOut * 20);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
    }
}
