package me.infinityz.spigot;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * QueueEvents
 */
public class QueueEvents implements Listener {
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage("joinMessage");
        e.getPlayer().setGameMode(GameMode.SPECTATOR);
        e.getPlayer().sendTitle("Welcome to the Limbo!", "Have fun doing nothing");
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        e.setQuitMessage("quitMessage");
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(e.getTo().getBlockX() != e.getFrom().getBlockX() ||  e.getTo().getBlockZ() != e.getFrom().getBlockZ()){
            e.setCancelled(true);
        }
        if(e.getTo().getBlockY() < 1){
            e.getPlayer().teleport(e.getFrom().add(0.0, 40, 0.0));
        }
    }
    

    
}