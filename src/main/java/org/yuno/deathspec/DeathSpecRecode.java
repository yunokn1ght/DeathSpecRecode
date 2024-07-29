package org.yuno.deathspec;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathSpecRecode extends JavaPlugin {

    @Getter static DeathSpecRecode instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        // TODO: Add source code at modrinth page
        // TODO: Add unban command
        instance = this;
        saveDefaultConfig();
        new Config().ConfigCheck();

        if(Config.isTogglePlugin()) {
            getServer().getPluginManager().registerEvents(new DeathListener(), this);
            getLogger().info("DeathSpec 2.0 has been enabled!");
        }



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
