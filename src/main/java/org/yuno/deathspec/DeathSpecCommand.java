package org.yuno.deathspec;

import io.papermc.paper.ban.BanListType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DeathSpecCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(strings.length == 0) {
            sender.sendMessage(Component.text("/deathspec reload")
                    .color(NamedTextColor.GOLD)
                    .append(Component.text(" - Reloads config")
                            .color(NamedTextColor.WHITE)));
            return true;
        }

        if(strings[0].equalsIgnoreCase("reload")) {
            if(sender.hasPermission("deathspec.reload") || sender.isOp()) {
                DeathSpecRecode.getInstance().reloadConfig();
                new Config().ConfigCheck();

                sender.sendMessage(Component.text(Config.getConfigReloadMessage())
                        .color(TextColor.fromHexString(Config.getConfigReloadColor())));
                return true;
            } else {
                sender.sendMessage(Component.text(Config.getNoPermissionMessage())
                        .color(TextColor.fromHexString(Config.getNoPermissionColor())));
                return true;
            }
        }

        if(strings[0].equalsIgnoreCase("pardon")) {
            if(sender.hasPermission("deathspec.pardon") || sender.isOp()) {

                if(strings.length < 2) {
                    sender.sendMessage(Component.text("Usage: /deathspec pardon <playerCount | playerName>")
                            .color(NamedTextColor.GOLD));
                    return true;
                }

                if(strings.length == 2) {
                    int playerCount = Integer.parseInt(strings[1]);
                    if(playerCount >= 0) {
                        if(playerCount > 0 && playerCount <= DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers").size()) {

                            for(int x = 0; x < playerCount; x++) {
                                String playerName = DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers").get(x);
                                OfflinePlayer bannedPlayer = Bukkit.getOfflinePlayer(playerName);

                                if(Bukkit.getBanList(BanListType.PROFILE).isBanned(bannedPlayer.getPlayerProfile())) {
                                    Bukkit.getBanList(BanListType.PROFILE).pardon(bannedPlayer.getPlayerProfile());
                                    DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers").remove(x);

                                    DeathSpecRecode.getInstance().getLogger().info(bannedPlayer.getName() + " unbanned by" + sender.getName());
                                }
                            }
                            sender.sendMessage(Component.text("Pardoned " + playerCount + " players")
                                    .color(NamedTextColor.GOLD));
                        } else {
                            sender.sendMessage(Component.text(Config.getZeroPlayerCount())
                                    .color(TextColor.fromHexString(Config.getZeroPlayerCountColor())));
                        }
                        return true;
                    } else {
                        String playerName = strings[1];
                        OfflinePlayer bannedPlayer = Bukkit.getOfflinePlayer(playerName);

                        if(Bukkit.getBanList(BanListType.PROFILE).isBanned(bannedPlayer.getPlayerProfile())) {
                            Bukkit.getBanList(BanListType.PROFILE).pardon(bannedPlayer.getPlayerProfile());
                            DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers").remove(playerName);

                            DeathSpecRecode.getInstance().getLogger().info(bannedPlayer.getName() + " unbanned by" + sender.getName());

                            String unbannedPlayerMessage = Config.getUnbannedPlayer().replace("{playerName}", playerName);
                            sender.sendMessage(Component.text(unbannedPlayerMessage).color(TextColor.fromHexString(Config.getUnbannedPlayerColor())));
                            return true;
                        }

                        String playerNotBannedMessage = Config.getPlayerIsntBanned().replace("{playerName}", playerName);
                        sender.sendMessage(Component.text(playerNotBannedMessage).color(TextColor.fromHexString(Config.getPlayerIsntBannedColor())));
                        return true;
                    }
                }
            } else {
                sender.sendMessage(Component.text(Config.getNoPermissionMessage())
                        .color(TextColor.fromHexString(Config.getNoPermissionColor())));
                return true;
            }
        }

        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 1) return List.of("pardon", "reload");
        if(strings.length == 2) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();

        return null;
    }
}
