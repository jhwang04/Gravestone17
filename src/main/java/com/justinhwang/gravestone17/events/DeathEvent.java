package com.justinhwang.gravestone17.events;

import com.justinhwang.gravestone17.Gravestone17;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class DeathEvent implements Listener {
    private Gravestone17 plugin;

    public DeathEvent(Gravestone17 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if(plugin.config.getBoolean("enabled")) {

            Player p = e.getEntity();
            Location loc = p.getLocation();

            Block b1 = loc.getBlock();
            Block b2 = loc.getWorld().getBlockAt(loc.getBlockX() + 1, loc.getBlockY(), loc.getBlockZ());

            int numTries = 0;
            while((!isBlockValid(b1) || !isBlockValid(b2)) && numTries < 1000) {
                numTries++;
                Bukkit.getLogger().info("Try #" + numTries + ", " + b1.getLocation().toString());
                if(loc.getBlockY() < 255)
                    loc.setY(loc.getY() + 1.0);
                else
                    loc.setX(loc.getX() + 1.0);

                b1 = loc.getBlock();
                b2 = loc.getWorld().getBlockAt(loc.getBlockX() + 1, loc.getBlockY(), loc.getBlockZ());
            }

            b1.setType(Material.CHEST);
            b2.setType(Material.CHEST);

            Chest targetChest = (Chest) b1.getBlockData();
            Chest secondaryTargetChest = (Chest) b2.getBlockData();

            targetChest.setType(Chest.Type.LEFT);
            secondaryTargetChest.setType(Chest.Type.RIGHT);

            b1.setBlockData(targetChest);
            b2.setBlockData(secondaryTargetChest);

            ArrayList<ItemStack> contents = new ArrayList<ItemStack>();
            ItemStack[] mainContents = p.getInventory().getContents();
            contents.addAll(Arrays.asList(mainContents));
            org.bukkit.block.Chest targetDoubleChest = (org.bukkit.block.Chest) b1.getState();

            ItemStack[] contentsArray = new ItemStack[contents.size()];
            for(int i = 0; i < contentsArray.length; i++) {
                contentsArray[i] = contents.get(i);
            }

            targetDoubleChest.getInventory().setContents(contentsArray);

            Bukkit.broadcastMessage(ChatColor.DARK_RED + p.getName() + " has died!");
            p.sendMessage(ChatColor.AQUA + "You died! Fortunately, the Gravestone17 Plugin has put your items into a chest!");
            p.sendMessage(ChatColor.AQUA + "The chest is at (" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")");

            e.getDrops().clear();
        }
    }

    private boolean isBlockValid(Block b) {
        if(b == null)
            return false;
        else if(b.getType() != Material.AIR)
            return false;
        else if(b.getLocation().getBlockY() < 0)
            return false;
        else
            return true;
    }
}
