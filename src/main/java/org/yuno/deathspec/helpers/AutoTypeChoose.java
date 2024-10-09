package org.yuno.deathspec.helpers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.entity.Player;

public class AutoTypeChoose {

    public static void sendMessage(Component message, int type, Player player) {
        switch(type) {
            case 0: player.sendMessage(message); break;
            case 1: player.sendActionBar(message); break;
            case 2: player.sendTitlePart(TitlePart.TITLE, message); break;
            case 3: player.sendTitlePart(TitlePart.SUBTITLE, message); break;
        }
    }

    public static void sendMessage(String message, int type, Player player) {
        switch(type) {
            case 0: player.sendMessage(message); break;
            case 1: player.sendActionBar(Component.text(message)); break;
            case 2: player.sendTitlePart(TitlePart.TITLE, Component.text(message)); break;
            case 3: player.sendTitlePart(TitlePart.SUBTITLE, Component.text(message)); break;
        }
    }
}
