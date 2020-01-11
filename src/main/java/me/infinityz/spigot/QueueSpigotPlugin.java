package me.infinityz.spigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * QueueSpigotPlugin
 */
public class QueueSpigotPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new QueueEvents(), this);
    }

    
}