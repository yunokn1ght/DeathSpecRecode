package org.yuno.deathspec;

import lombok.Getter;

public class Config {

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

    public void ConfigCheck() {
        togglePlugin = DeathSpecRecode.getInstance().getConfig().getBoolean("death.togglePlugin");
        toggleBan = DeathSpecRecode.getInstance().getConfig().getBoolean("ban.toggleBan");

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
    }
}
