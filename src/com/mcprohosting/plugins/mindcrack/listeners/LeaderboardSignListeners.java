package com.mcprohosting.plugins.mindcrack.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

import com.mcprohosting.plugins.mindcrack.utilities.LeaderboardSigns;

public class LeaderboardSignListeners implements Listener {

	@EventHandler
	public void onSignEdit(SignChangeEvent event) {
		if (event.getPlayer().hasPermission("mindcrack.editleaderboardsigns")) {
			if (event.getLine(0).equalsIgnoreCase("[lb]")) {
				LeaderboardSigns.addSign(event.getBlock().getLocation());
				event.getPlayer().sendMessage("Leaderboard sign added.");
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		
		if (block.getType().equals(Material.WALL_SIGN) && LeaderboardSigns.isRegistered(block.getLocation())) {
			if (!event.getPlayer().hasPermission("mindcrack.editleaderboardsigns")) {
				event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to remove leaderboard signs.");
				event.setCancelled(true);
			} else {
				LeaderboardSigns.removeSign(block.getLocation());
				event.getPlayer().sendMessage("Leaderboard sign removed.");
			}
		}
	}
}
