package com.mcprohosting.plugins.mindcrack;

import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UtilityMethods {
	private static Inventory compassInventory;

	public static boolean canChangeBlocks(Player player) {
		return (player.isOp() || player.hasPermission("mindcrack.changeblocks"));
	}

	public static void setupCompassInventory() {
		compassInventory = Mindcrack.getPlugin().getServer().createInventory(null, 9, "Teleport!");
		compassInventory.setItem(0, new ItemStack(Material.BEACON));
	}

	public static void redirectToServer(String server, final Player player) {
		try {
			Mindcrack.getConnect().request(new RedirectRequest(server, player.getName()));
		} catch (RequestException e) {
			player.sendMessage(ChatColor.RED.toString() + "That server is current not available: " + e.getCause() + "!");
		}
	}

	public static Inventory getCompassInventory() {
		return compassInventory;
	}
}