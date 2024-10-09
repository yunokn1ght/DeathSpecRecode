package org.yuno.deathspec;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.yuno.deathspec.helpers.AutoTypeChoose;
import org.yuno.deathspec.helpers.PlayerBan;

public class DeathListener implements org.bukkit.event.Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();

        if (Config.isTogglePlugin()) {
            e.setCancelled(true);
            if (e.deathMessage() != null) {
                Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(e.deathMessage()));
            }

            p.setGameMode(GameMode.SPECTATOR);
            p.getInventory().forEach(itemStack -> {
                if (itemStack != null)
                    p.getWorld().dropItemNaturally(p.getLocation(), itemStack);
            });

            p.getInventory().clear();

            if (Config.isToggleDeathMessage()) {
                Component deathMessage = Component.text(Config.getDeathMessage())
                        .color(TextColor.fromHexString(Config.getDeathMessageColor()));

                AutoTypeChoose.sendMessage(deathMessage, Config.getDeathMessageType(), p);
            }

            if (Config.isToggleBan())
                new PlayerBan().banPlayer(p);
        }
    }
}
