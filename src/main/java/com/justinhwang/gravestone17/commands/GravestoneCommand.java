package com.justinhwang.gravestone17.commands;

import com.justinhwang.gravestone17.Gravestone17;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GravestoneCommand implements CommandExecutor {
    Gravestone17 plugin;

    public GravestoneCommand(Gravestone17 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(ChatColor.WHITE + "Gravestone17 is currently " + getTextFromBoolean(plugin.getConfig().getBoolean("enabled")));
            sender.sendMessage(ChatColor.WHITE + "To enable/disable the plugin, use " + ChatColor.AQUA + "\"/gravestone <enable/disable>\"");
        } else {
            if(args[0].equals("enable")) {
                this.plugin.getConfig().set("enabled", true);
                this.plugin.saveConfig();
                sender.sendMessage(ChatColor.GREEN + "Gravestone17 is now " + getTextFromBoolean(plugin.getConfig().getBoolean("enabled")));
            } else if(args[0].equals("disable")) {
                this.plugin.getConfig().set("enabled", false);
                this.plugin.saveConfig();
                sender.sendMessage(ChatColor.GREEN + "Gravestone17 is now " + getTextFromBoolean(plugin.getConfig().getBoolean("enabled")));
            } else {
                sender.sendMessage(ChatColor.RED + "Usage: /gravestone <enable/disable>");
            }
        }
        return true;
    }

    private String getTextFromBoolean(boolean b) {
        if(b)
            return "enabled";
        else
            return "disabled";
    }
}
