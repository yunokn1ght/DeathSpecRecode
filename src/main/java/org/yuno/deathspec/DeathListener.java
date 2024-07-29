package org.yuno.deathspec;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements org.bukkit.event.Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);

        if(e.deathMessage() != null)
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(e.deathMessage()));

        p.setGameMode(GameMode.SPECTATOR);
        p.getInventory().forEach(itemStack -> {
            if(itemStack != null)
                p.getWorld().dropItemNaturally(p.getLocation(), itemStack);
        });

        p.getInventory().clear();

        if(Config.isToggleDeathMessage()) {
            Component deathMessage = Component.text(Config.getDeathMessage())
                    .color(TextColor.fromHexString(Config.getDeathMessageColor()));

            switch(Config.getDeathMessageType()) {
                case 0: p.sendMessage(deathMessage); break;
                case 1: p.sendActionBar(deathMessage); break;
                case 2: p.sendTitlePart(TitlePart.TITLE, deathMessage); break;
                case 3: p.sendTitlePart(TitlePart.SUBTITLE, deathMessage); break;
            }
        }

        if(Config.isToggleBan())
            new PlayerBan().banPlayer(p);
    }
}
