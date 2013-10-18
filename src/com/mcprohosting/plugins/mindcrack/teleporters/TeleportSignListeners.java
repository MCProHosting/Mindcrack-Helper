package com.mcprohosting.plugins.mindcrack.teleporters;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.mcprohosting.plugins.mindcrack.utilities.UtilityMethods;

public class TeleportSignListeners implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.SIGN)) {
			if (TeleportSigns.isRegistered(event.getClickedBlock().getLocation())) {
				String serverName = TeleportSigns.getServerName(event.getClickedBlock().getLocation());
				if (serverName != null) {
					UtilityMethods.redirectToServer(serverName, event.getPlayer());
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType().equals(Material.SIGN) && event.getPlayer().hasPermission("mindcrack.editteleportsigns")) {
			Sign sign = (Sign) event.getBlock();
			if (sign.getLine(0).matches("\\[tp [1-8]\\]")) {
				TeleportSigns.addSign(sign.getLine(0).substring(4, 5) + "-kotl", sign.getLocation());
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		
		if (block.getType().equals(Material.SIGN) && TeleportSigns.isRegistered(block.getLocation())) {
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
