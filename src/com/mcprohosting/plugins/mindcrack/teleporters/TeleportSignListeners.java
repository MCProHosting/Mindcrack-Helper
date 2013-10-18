package com.mcprohosting.plugins.mindcrack.teleporters;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.mcprohosting.plugins.mindcrack.utilities.UtilityMethods;

public class TeleportSignListeners implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.WALL_SIGN)) {
			if (TeleportSigns.isRegistered(event.getClickedBlock().getLocation())) {
				String serverName = TeleportSigns.getServerName(event.getClickedBlock().getLocation());
				if (serverName != null) {
					UtilityMethods.redirectToServer(serverName, event.getPlayer());
				}
			}
		}
	}
	
	@EventHandler
	public void onSignEdit(SignChangeEvent event) {
		if (event.getPlayer().hasPermission("mindcrack.editteleportsigns")) {
			if (event.getLine(0).matches("\\[tp [1-8]\\]")) {
				TeleportSigns.addSign(event.getLine(0).substring(4, 5) + "-kotl", event.getBlock().getLocation());
				event.getPlayer().sendMessage("Teleport sign added.");
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		
		if (block.getType().equals(Material.WALL_SIGN) && TeleportSigns.isRegistered(block.getLocation())) {
			if (!event.getPlayer().hasPermission("mindcrack.editteleportsigns")) {
				event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to remove teleport signs.");
				event.setCancelled(true);
			} else {
				TeleportSigns.removeSign(block.getLocation());
				event.getPlayer().sendMessage("Teleport sign removed.");
			}
		}
	}
}
