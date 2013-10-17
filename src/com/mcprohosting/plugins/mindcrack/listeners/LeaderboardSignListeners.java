package com.mcprohosting.plugins.mindcrack.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.mcprohosting.plugins.mindcrack.LeaderboardSigns;

public class LeaderboardSignListeners implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType().equals(Material.SIGN) && event.getPlayer().hasPermission("mindcrack.editleaderboardsigns")) {
			Sign sign = (Sign) event.getBlock();
			if (sign.getLine(0).equalsIgnoreCase("[lb]")) {
				LeaderboardSigns.addSign(event.getBlock().getLocation());
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		
		if (block.getType().equals(Material.SIGN) && LeaderboardSigns.isRegistered(block.getLocation())) {
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
