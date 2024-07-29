package org.yuno.deathspec;

import io.papermc.paper.ban.BanListType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerBan {


    public void banPlayer(Player p) {
        AtomicInteger timer = new AtomicInteger(8);

        Bukkit.getScheduler().runTaskTimer(DeathSpecRecode.getInstance(), task -> {
            Component banMessage = Component.text(Config.getBanMessage())
                    .color(TextColor.fromHexString(Config.getBanMessageColor()));

            banMessage = banMessage.replaceText(
                    TextReplacementConfig.builder().matchLiteral("{time}")
                            .replacement(timer.toString()).build());

            switch(Config.getDeathMessageType()) {
                case 0: p.sendMessage(banMessage); break;
                case 1: p.sendActionBar(banMessage); break;
                case 2: p.sendTitlePart(TitlePart.TITLE, banMessage); break;
                case 3: p.sendTitlePart(TitlePart.SUBTITLE, banMessage); break;
            }

            if(timer.get() <= 0) {
                task.cancel();

                String banReason = ChatColor.valueOf(Config.getBanReasonColor()) + Config.getBanReason();



                Bukkit.getBanList(BanListType.PROFILE).addBan(
                        p.getPlayerProfile(),
                        banReason, (Date) null, null);


                p.kick(Component.text(banReason));

                List<String> bannedPlayers = DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers");

                if(!bannedPlayers.contains(PlainTextComponentSerializer.plainText().serialize(p.displayName())))
                    bannedPlayers.add(PlainTextComponentSerializer.plainText().serialize(p.displayName()));

                DeathSpecRecode.getInstance().getConfig().set("bannedPlayers", bannedPlayers);
                DeathSpecRecode.getInstance().saveConfig();
                timer.set(0);
            }

            timer.getAndDecrement();
        }, 0, 20);
    }
}
