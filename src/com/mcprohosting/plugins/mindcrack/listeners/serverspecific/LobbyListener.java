package com.mcprohosting.plugins.mindcrack.listeners.serverspecific;

import com.mcprohosting.plugins.mindcrack.Mindcrack;
import com.mcprohosting.plugins.mindcrack.ServerType;
import com.mcprohosting.plugins.mindcrack.utilities.UtilityMethods;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class LobbyListener implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		//Send MotD
		player.sendMessage(Mindcrack.getPropConfig().getMotd());

		//Setup inventory
		player.getInventory().clear();
		player.getInventory().setItem(0, new ItemStack(Material.COMPASS));

		//Teleport to spawn
		player.teleport(Mindcrack.getPropConfig().getSpawnLocation());
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onHungerEvent(FoodLevelChangeEvent event) {
		if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME) && !Mindcrack.getPropConfig().getServerType().equals(ServerType.SURVIVAL)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (player.getItemInHand().getType().equals(Material.COMPASS)) {
			player.openInventory(UtilityMethods.getCompassInventory());
		}
	}

	//For interacting with the compass inventory to teleport players to other servers
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked().getItemInHand().getType().equals(Material.COMPASS) || event.getWhoClicked().getItemInHand().getType().equals(Material.AIR)) {
			String displayName = "";

			try {
				displayName = event.getCurrentItem().getItemMeta().getDisplayName();
			} catch (NullPointerException e) {
				displayName = "";
			}
			if (displayName.equals("Hub")) {
				UtilityMethods.redirectToServer("hub", (Player) event.getWhoClicked());
			} else if (displayName.equals("Creative #1")) {
				UtilityMethods.redirectToServer("1-creative", (Player) event.getWhoClicked());
			} else if (displayName.equals("Creative #2")) {
				UtilityMethods.redirectToServer("2-creative", (Player) event.getWhoClicked());
			} else if (displayName.equals("Survival #1")) {
				UtilityMethods.redirectToServer("1-survival", (Player) event.getWhoClicked());
			} else if (displayName.equals("Survival #2")) {
				UtilityMethods.redirectToServer("2-survival", (Player) event.getWhoClicked());
			} else if (displayName.equals("KotL Lobby #1")) {
				UtilityMethods.redirectToServer("1-kotl-lobby", (Player) event.getWhoClicked());
			} else if (displayName.equals("KotL Lobby #2")) {
				UtilityMethods.redirectToServer("2-kotl-lobby", (Player) event.getWhoClicked());
			}

			if (!event.getWhoClicked().isOp()) {
				event.setCancelled(true);
			}
		}
	}
}
