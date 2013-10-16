package com.mcprohosting.plugins.mindcrack;

import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class UtilityMethods {
	private static Inventory compassInventory;

	//Basic permissions, checks if the player should be able to break/place blocks.
	public static boolean canChangeBlocks(Player player) {
		return (player.isOp() || player.hasPermission("mindcrack.changeblocks"));
	}

	//ONLY RUN THIS ONCE AT STARTUP to create and statically store an instance of Inventory for later use with the compass.
	public static void setupCompassInventory() {
		compassInventory = Mindcrack.getPlugin().getServer().createInventory(null, 9, "Teleport!");

		ItemStack hubItem = new ItemStack(Material.BEACON);
		hubItem.getItemMeta().setDisplayName("Hub");

		ItemStack creativeItem1 = new ItemStack(Material.SAND);
		creativeItem1.getItemMeta().setDisplayName("Creative #1");

		ItemStack creativeItem2 = new ItemStack(Material.SAND);
		creativeItem2.getItemMeta().setDisplayName("Creative #2");

		ItemStack survivalItem1 = new ItemStack(Material.SKULL);
		survivalItem1.getItemMeta().setDisplayName("Survival #1");

		ItemStack survivalItem2 = new ItemStack(Material.SKULL);
		survivalItem2.getItemMeta().setDisplayName("Survival #2");

		ItemStack ladderItem1 = new ItemStack(Material.LADDER);
		ladderItem1.getItemMeta().setDisplayName("KotL Lobby #1");

		ItemStack ladderItem2 = new ItemStack(Material.LADDER);
		ladderItem2.getItemMeta().setDisplayName("KotL Lobby #2");

		compassInventory.setItem(0, hubItem);        //Hub
		compassInventory.setItem(2, creativeItem1);  //Creative1
		compassInventory.setItem(2, creativeItem2);  //Creative2
		compassInventory.setItem(3, survivalItem1);  //Survival
		compassInventory.setItem(3, survivalItem2);  //Survival
		compassInventory.setItem(4, ladderItem1);    //Ladder
		compassInventory.setItem(4, ladderItem2);    //Ladder
	}

	public static void redirectToServer(String server, final Player player) {
		try {
			Mindcrack.getConnect().request(new RedirectRequest(server, player.getName()));
		} catch (RequestException e) {
			player.sendMessage(ChatColor.RED.toString() + "That server is current not available: " + e.getCause() + "!");
		}
	}

	//Compiles a string for the message of the day based on a list of configuration values
	public static String createMOTD(List<String> input) {
		String motd = ChatColor.GREEN.toString();

		for (int i = 0; i < input.size(); i++) {
			if (i == input.size()-1) { //If it is the last element, do not append an additional line.
				motd = motd + input.get(i);
			} else {
				motd = motd + input.get(i) + "\n";
			}
		}

		return motd;
	}

	//Getters
	public static Inventory getCompassInventory() {
		return compassInventory;
	}
}