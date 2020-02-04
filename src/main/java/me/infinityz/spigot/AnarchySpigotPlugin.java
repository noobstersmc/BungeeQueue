package me.infinityz.spigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * AnarchySpigotPlugin
 */
public class AnarchySpigotPlugin extends JavaPlugin {
    public static AnarchySpigotPlugin instance;
    
    @Override
    public void onEnable() {
        instance =this;
        AnarchyCommand anarchyCommand = new AnarchyCommand();
        Bukkit.getPluginManager().registerEvents(anarchyCommand, this);
        getCommand("randomtp").setExecutor(anarchyCommand);
        getCommand("spawn").setExecutor(anarchyCommand);

    }

    
}