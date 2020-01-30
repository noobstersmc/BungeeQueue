package me.infinityz.spigot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * QueueEvents
 */
public class QueueEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage("");
        if (e.getPlayer().hasPlayedBefore())
            return;

        e.getPlayer().teleport(getRandomLocation(1000, Bukkit.getWorlds().get(0)));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        e.setQuitMessage("");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity().hasPermission("queue.override"))
            return;
        Bukkit.getScheduler().runTaskLater(AnarchySpigotPlugin.instance, () -> {
            e.getEntity().kickPlayer(
                    ChatColor.translateAlternateColorCodes('&', "&fYou died, adquire &6rank&f to play freely!"));

        }, 10L);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        final Player player = e.getPlayer();
        e.setRespawnLocation(getSpawnLocation(player));
    }
    
    //Function that allows for the player to be spawn either randomly or at their bed spawn.
    Location getSpawnLocation(Player player){
        return player.getBedSpawnLocation() == null ? getRandomLocation(1000, Bukkit.getWorlds().get(0)) : player.getBedSpawnLocation();
    }

    Location getRandomLocation(int radius, World world){
        Location loc = new Location(world, 0, 0, 0);
        loc.setX(loc.getX() + (Math.random() * radius * 2.0) - radius);
        loc.setZ(loc.getZ() + (Math.random() * radius * 2.0) - radius);
        loc.setY(loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()));
        loc = loc.add(0.0, 2.0, 0.0);
        return loc;      
    }

    @EventHandler
    public void onCraft(BlockPistonExtendEvent e) {
        Block b = e.getBlock();
        List<Block> list = getNearbyBlocks(b.getLocation(), 3);
        if (list.size() > 0)
            e.setCancelled(true);

    }

    @EventHandler
    public void onCraft(BlockPistonRetractEvent e) {
        Block b = e.getBlock();
        List<Block> list = getNearbyBlocks(b.getLocation(), 3);
        if (list.size() > 0)
            e.setCancelled(true);

    }

    public List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    Block b = location.getWorld().getBlockAt(x, y, z);
                    if (b.getType() == Material.SLIME_BLOCK)
                        blocks.add(b);
                }
            }
        }
        return blocks;
    }

}