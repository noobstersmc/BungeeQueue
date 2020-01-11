package me.infinityz.bungee.commands;

import me.infinityz.bungee.QueuePlugin;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * SlotsCommand
 */
public class SlotsCommand extends Command {

    public SlotsCommand() {
        super("globalslots", "queue.slots");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Correct command usage: /globalslots <int:current>");
            return;
        }
        if (args[0].equalsIgnoreCase("current")) {
            sender.sendMessage("Current max capacity: " + QueuePlugin.global_slots);
            return;
        }
        int integer = Integer.parseInt(args[0]);
        sender.sendMessage("New max capacity is: " + integer);
        QueuePlugin.global_slots = integer;

    }

}