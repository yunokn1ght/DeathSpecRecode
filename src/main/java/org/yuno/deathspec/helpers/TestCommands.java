package org.yuno.deathspec.helpers;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.yuno.deathspec.Config;
import org.yuno.deathspec.DeathSpecRecode;

import java.util.Date;
import java.util.List;

@CommandPermission("minecraft.command.op")
@CommandAlias("deathspectest")
public class TestCommands extends BaseCommand {

    @Subcommand("ban")
    public void ban(Player p, String target) {
        String banReason = ChatColor.valueOf(Config.getBanReasonColor()) + Config.getBanReason();
        Bukkit.getBanList(BanList.Type.NAME).addBan(
                target,
                banReason, (Date) null, null);


        if(Bukkit.getPlayer(target) != null) {
            Bukkit.getPlayer(target).kick(Component.text(banReason));
        }

        List<String> bannedPlayers = DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers");

        if(!bannedPlayers.contains(target)) {
            bannedPlayers.add(target);
        }

        DeathSpecRecode.getInstance().getConfig().set("bannedPlayers", bannedPlayers);
        DeathSpecRecode.getInstance().saveConfig();

        p.sendMessage(Component.text(target, NamedTextColor.GOLD).append(Component.text(" successfully banned!")));
    }

    @Subcommand("add")
    public void addToBanlist(Player p, String target) {
        List<String> bannedPlayers = DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers");
        if(!bannedPlayers.contains(target)) {
            bannedPlayers.add(target);
        }

        DeathSpecRecode.getInstance().getConfig().set("bannedPlayers", bannedPlayers);
        DeathSpecRecode.getInstance().saveConfig();

        p.sendMessage(Component.text(target, NamedTextColor.GOLD).append(Component.text(" successfully added to banlist!")));
    }

    @Subcommand("fill")
    public void fillBanlist(Player p, int count) {
        List<String> bannedPlayers = DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers");
        for (int i = 0; i < count; i++) {
            bannedPlayers.add("test" + i);
        }

        DeathSpecRecode.getInstance().getConfig().set("bannedPlayers", bannedPlayers);
        DeathSpecRecode.getInstance().saveConfig();
        p.sendMessage(Component.text("Successfully filled banlist!"));
    }

    @Subcommand("banFill")
    public void banFill(Player p, int count) {
        List<String> bannedPlayers = DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers");
        for (int i = 0; i < count; i++) {
            bannedPlayers.add("test" + i);
            String banReason = ChatColor.valueOf(Config.getBanReasonColor()) + Config.getBanReason();
            Bukkit.getBanList(BanList.Type.NAME).addBan(
                    "test" + 1,
                    banReason, null, null);
        }

        DeathSpecRecode.getInstance().getConfig().set("bannedPlayers", bannedPlayers);
        DeathSpecRecode.getInstance().saveConfig();
        p.sendMessage(Component.text("Successfully filled banlist!"));
    }
}
