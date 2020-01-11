package me.infinityz.bungee;

import me.infinityz.bungee.commands.SlotsCommand;
import me.infinityz.bungee.events.Events;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * QueuePlugin
 */
public class QueuePlugin extends Plugin {

    public static int global_slots = 0;

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerCommand(this, new SlotsCommand());
        getProxy().getPluginManager().registerListener(this, new Events());

    }

}