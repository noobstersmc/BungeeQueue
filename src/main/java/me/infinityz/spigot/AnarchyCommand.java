package me.infinityz.spigot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.math.IntRange;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * AnarchyCommand
 */
public class AnarchyCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName().toLowerCase()) {
        case "spawn": {
            if (!(sender instanceof Player)) {
                sender.sendMessage("player cmd only");
                return true;
            }
            final Player player = (Player) sender;
            if (delay.containsKey(player.getUniqueId())) {
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', "&cYou already are in queue to go to spawn"));
                return true;
            }

            teleport(player, 5, "&aYou will be teleported to spawn in &f5 &aseconds!",
                    Bukkit.getWorlds().get(0).getSpawnLocation().add(0.0, 1.5, 0.0), true);

            return true;
        }
        case "randomtp": {
            // Syntax: /randomtp Player X1 Z1 X2 Z2 RADIUS
            if (args.length < 6) {
                sender.sendMessage("Correct Syntax: /randomtp <Player> <x1> <z1> <x2> <z2> <radius>");
            }
            Player player = Bukkit.getPlayer(args[0]);
            if(player == null || !player.isOnline()){
                sender.sendMessage("Player is either null or online");
                return true;
            }
            int x1, z1, x2, z2, radius;
            x1 = Integer.parseInt(args[1]);
            z1 = Integer.parseInt(args[2]);
            x2 = Integer.parseInt(args[3]);
            z2 = Integer.parseInt(args[4]);
            radius = Integer.parseInt(args[5]);
            teleport(player, 5, "&aYou will be randomly teleported in &f5 &aseconds!", getSafeLocation(radius, Bukkit.getWorlds().get(0), x1, z1, x2, z2).add(0.0,1.5,0.0), false);

            return true;
        }
        }
        return false;
    }

    Location getRandomLocation(int radius, World world) {
        Location loc = new Location(world, 0, 0, 0);
        loc.setX(loc.getX() + (Math.random() * radius * 2.0) - radius);
        loc.setZ(loc.getZ() + (Math.random() * radius * 2.0) - radius);
        loc.setY(loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()));
        return loc;
    }

    Location getSafeLocation(int radius, World world, int x1, int z1, int x2, int z2) {
        Location loc = getRandomLocation(radius, world);
        Location topVector = world.getBlockAt(x1, 255, z1).getLocation();
        Location bottomVector = world.getBlockAt(x2, 0, z2).getLocation();
        if (inCuboid(loc, topVector, bottomVector)) {
            return getSafeLocation(radius, world, x1, z1, x2, z2);
        }
        return loc;
    }

    boolean inCuboid(Location origin, Location l1, Location l2) {
        return new IntRange(l1.getX(), l2.getX()).containsDouble(origin.getX())
                && new IntRange(l1.getY(), l2.getY()).containsDouble(origin.getY())
                && new IntRange(l1.getZ(), l2.getZ()).containsDouble(origin.getZ());
    }

    public static Map<UUID, Long> delay = new HashMap<>();
    public static List<UUID> list = new ArrayList<>();

    void teleport(Player player, int seconds, String tpMSG, Location location, boolean checkForMovement) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', tpMSG));
        // Give effects
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 5, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 5, 0));
        // play sound
        player.playSound(player.getLocation(), Sound.BLOCK_CONDUIT_AMBIENT, 100000000.0F, 1.0F);
        if (checkForMovement) {
            delay.put(player.getUniqueId(), System.currentTimeMillis());
        } else {
            list.add(player.getUniqueId());
        }
        Bukkit.getScheduler().runTaskLater(AnarchySpigotPlugin.instance, () -> {
            if (checkForMovement) {
                if (!delay.containsKey(player.getUniqueId()))
                    return;
                delay.remove(player.getUniqueId());
            } else {
                list.remove(player.getUniqueId());
            }
            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100000000.0F, 1.0F);
            player.teleport(location);

        }, 20 * seconds);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!delay.containsKey(e.getPlayer().getUniqueId()))
            return;
        final Player player = e.getPlayer();
        if (e.getTo().distance(e.getFrom()) >= 1) {
            delay.remove(player.getUniqueId());
            player.removePotionEffect(PotionEffectType.CONFUSION);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.GLOWING);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cTeleportation to spawn was cancelled due to movement!"));
        }

    }

}