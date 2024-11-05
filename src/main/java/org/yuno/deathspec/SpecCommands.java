package org.yuno.deathspec;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@CommandAlias("deathspec")
public class SpecCommands extends BaseCommand {

    @CommandPermission("deathspec.reload")
    @Subcommand("reload")
    public void reload(CommandSender p) {
        DeathSpecRecode.getInstance().reloadConfig();
        new Config().ConfigCheck();

        Component configReloadMessage = Config.getConfigReloadMessage();
        p.sendMessage(configReloadMessage);
    }

    @CommandPermission("deathspec.pardon")
    @Subcommand("pardon")
    public void pardon(CommandSender p, @Single String target) {
        if (!target.equalsIgnoreCase("all")) {
            if (Bukkit.getBanList(BanList.Type.NAME).isBanned(target)) {
                Bukkit.getBanList(BanList.Type.NAME).pardon(target);
                ArrayList<String> banlist = (ArrayList<String>) DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers");
                banlist.remove(target);
                DeathSpecRecode.getInstance().getConfig().set("bannedPlayers", banlist);
                DeathSpecRecode.getInstance().saveConfig();


                Component unbannedPlayerMessage = Config.getUnbannedPlayer();
                unbannedPlayerMessage = unbannedPlayerMessage.replaceText(
                        TextReplacementConfig.builder().matchLiteral("{playerName}")
                                .replacement(target).build());
                p.sendMessage(unbannedPlayerMessage);
            } else {
                ArrayList<String> banlist = (ArrayList<String>) DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers");
                banlist.remove(target);
                DeathSpecRecode.getInstance().getConfig().set("bannedPlayers", banlist);
                DeathSpecRecode.getInstance().saveConfig();

                Component playerIsntBannedMessage = Config.getPlayerIsntBanned();
                playerIsntBannedMessage = playerIsntBannedMessage.replaceText(
                        TextReplacementConfig.builder().matchLiteral("{playerName}")
                                .replacement(target).build());
                p.sendMessage(playerIsntBannedMessage);
            } if(!p.getName().equals("CONSOLE")) DeathSpecRecode.getInstance().getLogger().info(target + " unbanned by " + p.getName());
        } else {
            if(DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers").isEmpty()) {
                Component listIsEmptyMessage = Config.getListIsEmpty();
                p.sendMessage(listIsEmptyMessage);
                return;
            }

            ArrayList<String> banlist = (ArrayList<String>) DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers");
            DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers").forEach(playerName -> {
                OfflinePlayer bannedPlayer = Bukkit.getOfflinePlayer(playerName);
                banlist.remove(playerName);

                if(Bukkit.getBanList(BanList.Type.NAME).isBanned(playerName)) {
                    Bukkit.getBanList(BanList.Type.NAME).pardon(playerName);

                    Component unbannedPlayerMessage = Config.getUnbannedPlayer();
                    unbannedPlayerMessage = unbannedPlayerMessage.replaceText(
                            TextReplacementConfig.builder().matchLiteral("{playerName}")
                                    .replacement(playerName).build());
                    p.sendMessage(unbannedPlayerMessage);
                } else {
                    Component playerIsntBannedMessage = Config.getPlayerIsntBanned();
                    playerIsntBannedMessage = playerIsntBannedMessage.replaceText(
                            TextReplacementConfig.builder().matchLiteral("{playerName}")
                                    .replacement(playerName).build());
                    p.sendMessage(playerIsntBannedMessage);
                } if(!p.getName().equals("CONSOLE")) DeathSpecRecode.getInstance().getLogger().info(bannedPlayer.getName() + " unbanned by " + p.getName());
            });

            DeathSpecRecode.getInstance().getConfig().set("bannedPlayers", banlist);
            DeathSpecRecode.getInstance().saveConfig();
        }
    }

    @CommandPermission("deathspec.check")
    @Subcommand("check")
    public void check(CommandSender p) {
        List<String> banlist = DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers");
        if(!banlist.isEmpty()) for (Iterator<String> iterator = banlist.iterator(); iterator.hasNext();) { // i also dont know why this doesnt work
            String playerName = iterator.next();
            if (!Bukkit.getBanList(BanList.Type.NAME).isBanned(playerName)) {
                iterator.remove();
                if(!p.getName().equals("CONSOLE")) DeathSpecRecode.getInstance().getLogger().info("Deleted " + playerName + " from banlist!");
                p.sendMessage(Component.text("Removed " + playerName + " from banlist!")
                        .color(Config.getUnbannedPlayer().color()));

                DeathSpecRecode.getInstance().getConfig().set("bannedPlayers", banlist);
                DeathSpecRecode.getInstance().saveConfig();
            }
        } else {
            Component listIsEmptyMessage = Config.getListIsEmpty();
            p.sendMessage(listIsEmptyMessage);
        }
    }
}
