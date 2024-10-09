package org.yuno.deathspec.helpers;

import io.papermc.paper.ban.BanListType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.yuno.deathspec.Config;
import org.yuno.deathspec.DeathSpecRecode;

import java.util.Date;
import java.util.List;

public class PlayerBan {


    public void banPlayer(Player p) {
        int[] timer = {8};

        Bukkit.getScheduler().runTaskTimer(DeathSpecRecode.getInstance(), task -> {
            Component banMessage = Component.text(Config.getBanMessage())
                    .color(TextColor.fromHexString(Config.getBanMessageColor()));

            banMessage = banMessage.replaceText(
                    TextReplacementConfig.builder().matchLiteral("{time}")
                            .replacement(String.valueOf(timer[0])).build());

            AutoTypeChoose.sendMessage(banMessage, Config.getBanMessageType(), p);

            if(timer[0] <= 0) {
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
                timer[0] = 8;
            }

            timer[0] -= 1;
        }, 0, 20);
    }
}
