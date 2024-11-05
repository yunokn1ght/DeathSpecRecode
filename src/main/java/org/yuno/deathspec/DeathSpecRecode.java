package org.yuno.deathspec;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.yuno.deathspec.helpers.TestCommands;

import java.util.Iterator;
import java.util.List;

public final class DeathSpecRecode extends JavaPlugin {

    @Getter static DeathSpecRecode instance;


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();
        new Config().ConfigCheck();

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new SpecCommands());
        if(Config.isTestCommandsEnabled()) {
            manager.registerCommand(new TestCommands());
        }

        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getLogger().info("DeathSpec 2.0 has been enabled!");

        if(Config.isRemovePlayerFromBanlistOnRestart()) {
            List<String> banlist = DeathSpecRecode.getInstance().getConfig().getStringList("bannedPlayers");
            if(!banlist.isEmpty()) for (Iterator<String> iterator = banlist.iterator(); iterator.hasNext();) { // idk why banlist.remove() doesn't work
                String playerName = iterator.next();
                if (!Bukkit.getBanList(BanList.Type.NAME).isBanned(playerName)) {
                    iterator.remove();
                    getLogger().info("Deleted " + playerName + " from banlist!");
                    DeathSpecRecode.getInstance().getConfig().set("bannedPlayers", banlist);
                    DeathSpecRecode.getInstance().saveConfig();
                }
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
