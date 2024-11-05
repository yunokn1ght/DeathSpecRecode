package org.yuno.deathspec;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;

public class Config {

    // TODO: Replace this SHIT with ConfigAPI. It is necessary, but, kinda good


    // Death category
    @Getter private static boolean togglePlugin;
    @Getter private static boolean toggleDeathMessage;
    @Getter private static int deathMessageType;
    @Getter private static String deathMessage;
    @Getter private static String deathMessageColor;

    // Ban category
    @Getter private static boolean toggleBan;
    @Getter private static int banTime;
    @Getter private static Component banMessage;
    @Getter private static int banMessageType;
    @Getter private static String banReason;
    @Getter private static String banReasonColor;

    // Messages category
    @Getter private static Component noPermissionMessage;
    @Getter private static Component configReloadMessage;
    @Getter private static Component unbannedPlayer;
    @Getter private static Component playerIsntBanned;
    @Getter private static Component listIsEmpty;

    // Experimental category
    @Getter private static boolean removePlayerFromBanlistOnRestart;
    @Getter private static boolean testCommandsEnabled;
    @Getter private static boolean banMessageUsingBossbar;


    public void ConfigCheck() {
        ArrayList<Boolean> experimentalFeatures = new ArrayList<>();

        togglePlugin = DeathSpecRecode.getInstance().getConfig().getBoolean("death.togglePlugin");
        toggleBan = DeathSpecRecode.getInstance().getConfig().getBoolean("ban.toggleBan");

        removePlayerFromBanlistOnRestart = DeathSpecRecode.getInstance().getConfig().getBoolean("experimental.removePlayerFromBanlistOnRestart");
        testCommandsEnabled = DeathSpecRecode.getInstance().getConfig().getBoolean("experimental.testCommands");
        experimentalFeatures.add(removePlayerFromBanlistOnRestart);
        experimentalFeatures.add(testCommandsEnabled);

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
            banMessage = Component.text(DeathSpecRecode.getInstance().getConfig().getString("ban.banMessage", "You'll be banned in {time} seconds"))
                    .color(TextColor.fromHexString(DeathSpecRecode.getInstance().getConfig().getString("ban.banMessageColor", "#FF5733")));

            banMessageType = DeathSpecRecode.getInstance().getConfig().getInt("ban.banMessageType", 1);
            banReason = DeathSpecRecode.getInstance().getConfig().getString("ban.banReason", "You're lost!");
            banReasonColor = DeathSpecRecode.getInstance().getConfig().getString("ban.banReasonColor", "ff3030");

            banMessageUsingBossbar = DeathSpecRecode.getInstance().getConfig().getBoolean("experimental.banMessageUsingBossbar", false);
            experimentalFeatures.add(banMessageUsingBossbar);
        } else {
            DeathSpecRecode.getInstance().getLogger().warning("You have disabled toggleBan, this means you won't be banned when you die.");
        }

        noPermissionMessage = Component.text(DeathSpecRecode.getInstance().getConfig().getString("messages.noPermissionMessage", "You don't have permission to do that!"))
                .color(TextColor.fromHexString(DeathSpecRecode.getInstance().getConfig().getString("messages.noPermissionColor", "#ff3630")));

        configReloadMessage = Component.text(DeathSpecRecode.getInstance().getConfig().getString("messages.configReload", "Config reloaded!"))
                .color(TextColor.fromHexString(DeathSpecRecode.getInstance().getConfig().getString("messages.configReloadColor", "#30ff36")));

        unbannedPlayer = Component.text(DeathSpecRecode.getInstance().getConfig().getString("messages.unbannedPlayer", "Pardoned {playerName}!"))
                .color(TextColor.fromHexString(DeathSpecRecode.getInstance().getConfig().getString("messages.unbannedPlayerColor", "#30ff36")));

        playerIsntBanned = Component.text(DeathSpecRecode.getInstance().getConfig().getString("messages.playerIsntBanned", "{playerName} isn't banned!"))
                .color(TextColor.fromHexString(DeathSpecRecode.getInstance().getConfig().getString("messages.playerIsntBannedColor", "#ff3630")));

        listIsEmpty = Component.text(DeathSpecRecode.getInstance().getConfig().getString("messages.listIsEmpty", "Banlist is empty!"))
                .color(TextColor.fromHexString(DeathSpecRecode.getInstance().getConfig().getString("messages.listIsEmptyColor", "#ff3630")));

        if(experimentalFeatures.stream().anyMatch(feature -> feature)) {
            DeathSpecRecode.getInstance().getLogger().warning("You have enabled experimental features, this means that plugin may not work properly.");
        }
    }
}
