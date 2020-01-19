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
        Bukkit.getPluginManager().registerEvents(new QueueEvents(), this);
    }

    
}