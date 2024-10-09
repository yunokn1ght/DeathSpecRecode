package org.yuno.deathspec;

import lombok.Getter;

public class Config {

    // TODO: Replace this SHIT with ConfigAPI. It is necessary, but, kinda good

    @Getter private static boolean togglePlugin;
    @Getter private static boolean toggleDeathMessage;
    @Getter private static int deathMessageType;
    @Getter private static String deathMessage;
    @Getter private static String deathMessageColor;

    @Getter private static boolean toggleBan;
    @Getter private static int banTime;
    @Getter private static String banMessage;
    @Getter private static int banMessageType;
    @Getter private static String banMessageColor;
    @Getter private static String banReason;
    @Getter private static String banReasonColor;

    @Getter private static String noPermissionMessage;
    @Getter private static String noPermissionColor;
    @Getter private static String configReloadMessage;
    @Getter private static String configReloadColor;
    @Getter private static String unbannedPlayer;
    @Getter private static String unbannedPlayerColor;
    @Getter private static String playerIsntBanned;
    @Getter private static String playerIsntBannedColor;
    @Getter private static String listIsEmpty;
    @Getter private static String listIsEmptyColor;

    @Getter private static boolean removePlayerFromBanlistOnRestart;


    public void ConfigCheck() {
        togglePlugin = DeathSpecRecode.getInstance().getConfig().getBoolean("death.togglePlugin");
        toggleBan = DeathSpecRecode.getInstance().getConfig().getBoolean("ban.toggleBan");
        removePlayerFromBanlistOnRestart = DeathSpecRecode.getInstance().getConfig().getBoolean("plugin.removePlayerFromBanlistOnRestart");

        if(togglePlugin) {
            toggleDeathMessage = DeathSpecRecode.getInstance().getConfig().getBoolean("death.toggleDeathMessage", true);
            deathMessageType = DeathSpecRecode.getInstance().getConfig().getInt("death.deathMessageType", 1);
            deathMessage = DeathSpecRecode.getInstance().getConfig().getString("death.deathMessage", "You died, so you were transferred to Spectator Mode");
            deathMessageColor = DeathSpecRecode.getInstance().getConfig().getString("death.deathMessageColor", "#FFC300");
        } else {
            DeathSpecRecode.getInstance().getLogger().warning("You have disabled togglePlugin, this means that plugin wont work.");
        }

        if(toggleBan) {
            banTime = DeathSpecRecode.getInstance().getConfig().getInt("ban.banTime", 8);
            banMessage = DeathSpecRecode.getInstance().getConfig().getString("ban.banMessage", "You'll be banned in {time} seconds");
            banMessageType = DeathSpecRecode.getInstance().getConfig().getInt("ban.banMessageType", 1);
            banMessageColor = DeathSpecRecode.getInstance().getConfig().getString("ban.banMessageColor", "#FF5733");
            banReason = DeathSpecRecode.getInstance().getConfig().getString("ban.banReason", "You're lost!");
            banReasonColor = DeathSpecRecode.getInstance().getConfig().getString("ban.banReasonColor", "ff3030");
        } else {
            DeathSpecRecode.getInstance().getLogger().warning("You have disabled toggleBan, this means you won't be banned when you die.");
        }

        noPermissionMessage = DeathSpecRecode.getInstance().getConfig().getString("messages.noPermissionMessage", "You don't have permission to do that!");
        noPermissionColor = DeathSpecRecode.getInstance().getConfig().getString("messages.noPermissionColor", "#ff3630");

        configReloadColor = DeathSpecRecode.getInstance().getConfig().getString("messages.configReloadColor", "#30ff36");
        configReloadMessage = DeathSpecRecode.getInstance().getConfig().getString("messages.configReload", "Config reloaded!");

        unbannedPlayer = DeathSpecRecode.getInstance().getConfig().getString("messages.unbannedPlayer", "Pardoned {playerName}!");
        unbannedPlayerColor = DeathSpecRecode.getInstance().getConfig().getString("messages.unbannedPlayerColor", "#30ff36");

        playerIsntBanned = DeathSpecRecode.getInstance().getConfig().getString("messages.playerIsntBanned", "{playerName} isn't banned!");
        playerIsntBannedColor = DeathSpecRecode.getInstance().getConfig().getString("messages.playerIsntBannedColor", "#ff3630");

        listIsEmpty = DeathSpecRecode.getInstance().getConfig().getString("messages.listIsEmpty", "Banlist is empty!");
        listIsEmptyColor = DeathSpecRecode.getInstance().getConfig().getString("messages.listIsEmptyColor", "#ff3630");
    }
}
