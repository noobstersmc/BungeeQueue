package me.infinityz.spigot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * QueueEvents
 */
public class QueueEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage("");
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        e.setQuitMessage("");
    }
    // Cancel craft slime/honey blocks

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity().hasPermission("queue.override"))
            return;
        Bukkit.getScheduler().runTaskLater(AnarchySpigotPlugin.instance, () -> {

            e.getEntity().spigot().respawn();
            e.getEntity().kickPlayer(
                    ChatColor.translateAlternateColorCodes('&', "&fYou died, adquired &6rank&f to play freely!"));

        }, 1L);
    }

    @EventHandler
    public void onCraft(BlockPistonExtendEvent e) {
        Block b = e.getBlock();
        List list = getNearbyBlocks(b.getLocation(), 3);
        if (list.size() > 0)
            e.setCancelled(true);

    }

    @EventHandler
    public void onCraft(BlockPistonRetractEvent e) {
        Block b = e.getBlock();
        List list = getNearbyBlocks(b.getLocation(), 3);
        if (list.size() > 0)
            e.setCancelled(true);

    }

    public List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    Block b = location.getWorld().getBlockAt(x, y, z);
                    if (b.getType() == Material.HONEY_BLOCK || b.getType() == Material.SLIME_BLOCK)
                        blocks.add(b);
                }
            }
        }
        return blocks;
    }

}