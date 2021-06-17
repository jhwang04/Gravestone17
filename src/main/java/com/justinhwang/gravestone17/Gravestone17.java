package com.justinhwang.gravestone17;

import com.justinhwang.gravestone17.commands.GravestoneCommand;
import com.justinhwang.gravestone17.events.DeathEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Gravestone17 extends JavaPlugin {
    public boolean isEnabled;
    public FileConfiguration config;

    @Override
    public void onEnable() {
        // ensures default config exists
        this.saveDefaultConfig();

        config = this.getConfig();

        isEnabled = config.getBoolean("enabled");

        getCommand("gravestone").setExecutor(new GravestoneCommand(this));

        getServer().getPluginManager().registerEvents(new DeathEvent(this), this);

        getLogger().info("Gravestone has been loaded!");
        getLogger().info("testing #2");
    }
}
