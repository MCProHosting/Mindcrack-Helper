package com.mcprohosting.plugins.mindcrack.utilities;

import com.mcprohosting.plugins.mindcrack.Mindcrack;
import com.mcprohosting.plugins.mindcrack.database.DatabaseManager;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import lilypad.client.connect.api.result.FutureResult;
import lilypad.client.connect.api.result.StatusCode;
import lilypad.client.connect.api.result.impl.RedirectResult;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class UtilityMethods {
	private static Inventory compassInventory;

	//Basic permissions, checks if the player should be able to break/place blocks.
	public static boolean canChangeBlocks(Player player) {
		return (player.isOp() || player.hasPermission("mindcrack.changeblocks"));
	}

	//ONLY RUN THIS ONCE AT STARTUP to create and statically store an instance of Inventory for later use with the compass.
	public static void setupCompassInventory() {
		compassInventory = Mindcrack.getPlugin().getServer().createInventory(null, 9, "Select a Destination:");

		ItemStack hubItem = new ItemStack(Material.BEACON);
		ItemMeta hubItemMeta = hubItem.getItemMeta();
		hubItemMeta.setDisplayName("Hub");
		hubItem.setItemMeta(hubItemMeta);

		ItemStack creativeItem1 = new ItemStack(Material.SAND);
		ItemMeta creativeItem1Meta = creativeItem1.getItemMeta();
		creativeItem1Meta.setDisplayName("Creative #1");
		creativeItem1.setItemMeta(creativeItem1Meta);

		ItemStack creativeItem2 = new ItemStack(Material.SAND);
		ItemMeta creativeItem2Meta = creativeItem2.getItemMeta();
		creativeItem2Meta.setDisplayName("Creative #2");
		creativeItem2.setItemMeta(creativeItem2Meta);

		ItemStack survivalItem1 = new ItemStack(Material.SKULL);
		ItemMeta survivalItem1Meta = survivalItem1.getItemMeta();
		survivalItem1Meta.setDisplayName("Survival #1");
		survivalItem1.setItemMeta(survivalItem1Meta);

		ItemStack survivalItem2 = new ItemStack(Material.SKULL);
		ItemMeta survivalItem2Meta = survivalItem2.getItemMeta();
		survivalItem2Meta.setDisplayName("Survival #2");
		survivalItem2.setItemMeta(survivalItem2Meta);

		ItemStack ladderItem1 = new ItemStack(Material.LADDER);
		ItemMeta ladderItem1Meta = ladderItem1.getItemMeta();
		ladderItem1Meta.setDisplayName("KotL Lobby #1");
		ladderItem1.setItemMeta(ladderItem1Meta);

		ItemStack ladderItem2 = new ItemStack(Material.LADDER);
		ItemMeta ladderItem2Meta = ladderItem2.getItemMeta();
		ladderItem2Meta.setDisplayName("KotL Lobby #2");
		ladderItem2.setItemMeta(ladderItem2Meta);

		compassInventory.setItem(0, hubItem);        //Hub
		compassInventory.setItem(1, creativeItem1);  //Creative1
		compassInventory.setItem(2, creativeItem2);  //Creative2
		compassInventory.setItem(3, survivalItem1);  //Survival
		compassInventory.setItem(4, survivalItem2);  //Survival
		compassInventory.setItem(5, ladderItem1);    //Ladder
		compassInventory.setItem(6, ladderItem2);    //Ladder
	}

	public static void redirectToServer(String server, final Player player) {
		try {
			FutureResult<RedirectResult> futureResult = Mindcrack.getConnect().request(new RedirectRequest(server, player.getName()));
			RedirectResult result = futureResult.await(5000L);
			if (!result.getStatusCode().equals(StatusCode.SUCCESS)) {
				player.sendMessage(ChatColor.RED.toString() + "That server is current not available: !");
				Bukkit.getLogger().warning("RedirectRequest failed for player: " + player.getName());
			}
		} catch (RequestException e) {
			player.sendMessage(ChatColor.RED.toString() + "That server is current not available: " + e.getCause() + "!");
		} catch (InterruptedException e) {
			player.sendMessage(ChatColor.RED.toString() + "That server is current not available: " + e.getCause() + "!");
			Bukkit.getLogger().warning("RedirectRequest interrupted for player: " + player.getName());
		}
	}

	//Getters
	public static Inventory getCompassInventory() {
		return compassInventory;
	}
	
	public static int getPoints(String player) {
		return DatabaseManager.getPoints(player);
	}
	
	public static void addPoints(String player, int points) {
		DatabaseManager.addPoints(player, points);
	}
	
	public static List<String> getTopPlayers(int n) {
		return DatabaseManager.getTopPlayers(n);
	}
}