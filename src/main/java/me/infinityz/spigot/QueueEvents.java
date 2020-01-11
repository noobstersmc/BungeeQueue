package me.infinityz.spigot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * QueueEvents
 */
public class QueueEvents implements Listener {
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (e.getPlayer().hasPermission("queue"))
            return;
        
    }

    
}