package org.yuno.deathspec;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import io.papermc.paper.ban.BanListType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("deathspec")
public class SpecCommands extends BaseCommand {

    @CommandPermission("deathspec.reload")
    @Subcommand("reload")
    public void reload(Player p) {
        DeathSpecRecode.getInstance().reloadConfig();
        new Config().ConfigCheck();

        p.sendMessage(Component.text(Config.getConfigReloadMessage())
                .color(TextColor.fromHexString(Config.getConfigReloadColor())));
        p.sendMessage(Component.text("wekdfksdkf"));
    }

    @CommandPermission("deathspec.pardon")
    @Subcommand("pardon")
    public void pardon(Player p, @Single String target) {
        if (!target.equalsIgnoreCase("all")) {
            OfflinePlayer bannedPlayer = Bukkit.getOfflinePlayer(target);

            if (Bukkit.getBanList(BanListType.PROFILE).isBanned(bannedPlayer.getPlayerProfile())) {
                Bukkit.getBanList(BanListType.PROFILE).pardon(bannedPlayer.getPlayerProfile());
                ArrayList<String> banlist = (ArrayList<String>) DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers");
                banlist.remove(target);
                DeathSpecRecode.getInstance().getConfig().set("bannedPlayers", banlist);
                DeathSpecRecode.getInstance().saveConfig();

                String output = Config.getUnbannedPlayer().replace("{playerName}", target);
                p.sendMessage(Component.text(output).color(TextColor.fromHexString(Config.getUnbannedPlayerColor())));
                DeathSpecRecode.getInstance().getLogger().info(bannedPlayer.getName() + " unbanned by" + p.getName());
            } else {
                ArrayList<String> banlist = (ArrayList<String>) DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers");
                banlist.remove(target);
                DeathSpecRecode.getInstance().getConfig().set("bannedPlayers", banlist);
                DeathSpecRecode.getInstance().saveConfig();

                String output = Config.getPlayerIsntBanned().replace("{playerName}", target);
                p.sendMessage(Component.text(output).color(TextColor.fromHexString(Config.getPlayerIsntBannedColor())));
                DeathSpecRecode.getInstance().getLogger().info(bannedPlayer.getName() + " unbanned by" + p.getName());
            }
        } else {
            DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers").forEach(playerName -> {
                OfflinePlayer bannedPlayer = Bukkit.getOfflinePlayer(playerName);

                ArrayList<String> banlist = (ArrayList<String>) DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers");
                banlist.remove(playerName);
                DeathSpecRecode.getInstance().getConfig().set("bannedPlayers", banlist);
                DeathSpecRecode.getInstance().saveConfig();

                if(Bukkit.getBanList(BanListType.PROFILE).isBanned(bannedPlayer.getPlayerProfile())) {
                    Bukkit.getBanList(BanListType.PROFILE).pardon(bannedPlayer.getPlayerProfile());
                    String output = Config.getUnbannedPlayer().replace("{playerName}", playerName);
                    p.sendMessage(Component.text(output).color(TextColor.fromHexString(Config.getUnbannedPlayerColor())));
                } else {
                    String output = Config.getPlayerIsntBanned().replace("{playerName}", playerName);
                    p.sendMessage(Component.text(output).color(TextColor.fromHexString(Config.getPlayerIsntBannedColor())));
                } DeathSpecRecode.getInstance().getLogger().info(bannedPlayer.getName() + " unbanned by" + p.getName());
            });
        }
    }
}
