package com.mcprohosting.plugins.mindcrack.listeners;

import com.mcprohosting.plugins.mindcrack.UtilityMethods;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class InventoryListeners implements Listener {
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (player.getItemInHand().getType().equals(Material.COMPASS)) {
			player.openInventory(UtilityMethods.getCompassInventory());
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getContents().equals(UtilityMethods.getCompassInventory().getContents())) { //Better way to do this?
			if (event.getCurrentItem().getType().equals(Material.BEACON)) {
				UtilityMethods.redirectToServer("hub", (Player) event.getWhoClicked());
			}

			event.setCancelled(true);
		}
	}
}
