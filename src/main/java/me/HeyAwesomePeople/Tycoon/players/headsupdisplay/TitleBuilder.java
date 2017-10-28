package me.HeyAwesomePeople.Tycoon.players.headsupdisplay;

import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

@RequiredArgsConstructor public class TitleBuilder {

    private Player player;

    public TitleBuilder(Player player) {
        this.player = player;
    }

    private String title = "";
    private String subtitle = "";
    private Integer fadeIn = 20;
    private Integer stay = 20;
    private Integer fadeOut = 20;

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
            IChatBaseComponent title = IChatBaseComponent.ChatSerializer.a("{\" text \": \" " + this.title + " \"}");
            PacketPlayOutTitle bigTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, title);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(bigTitlePacket);
        }
        if (!subtitle.isEmpty()) {
            IChatBaseComponent subtitle = IChatBaseComponent.ChatSerializer.a("{\" text \": \" " + this.subtitle + " \"}");
            PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitle);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitlePacket);
        }
        PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn * 20, stay * 20, fadeOut * 20);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
    }
}
