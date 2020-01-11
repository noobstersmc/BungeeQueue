package me.infinityz.bungee.events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.infinityz.bungee.QueuePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Events
 */
public class Events implements Listener {

    List<UUID> list = new ArrayList<>();

    @EventHandler
    public void on(PostLoginEvent event) {
        if (ProxyServer.getInstance().getOnlineCount() <= QueuePlugin.global_slots)
            return;
        if (!event.getPlayer().hasPermission("queue.override")) {
            //Send the player to the limbo
            list.add(event.getPlayer().getUniqueId());
            return;
        }
        //Send the player to where they want.

    }
    
    @EventHandler
    public void onser(ServerConnectEvent e) {
        ProxiedPlayer player = e.getPlayer();
        if (!list.contains(player.getUniqueId()))
            return;        
        list.remove(player.getUniqueId());
        if (!player.hasPermission("queue.override")) {
            // Send the player to the limbo
            e.setTarget(ProxyServer.getInstance().getServerInfo("limbo") == null
                    ? ProxyServer.getInstance().getServers().entrySet().iterator().next().getValue()
                    : ProxyServer.getInstance().getServerInfo("limbo"));



                    
            player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cYou've been sent to a limbo server because the maximum player capacity was reached!")));
            return;
        }
        
        
        
    }

    
}