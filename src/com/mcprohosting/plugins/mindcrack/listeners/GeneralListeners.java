package com.mcprohosting.plugins.mindcrack.listeners;

import com.mcprohosting.plugins.mindcrack.Mindcrack;
import com.mcprohosting.plugins.mindcrack.ServerType;
import com.mcprohosting.plugins.mindcrack.database.DatabaseManager;
import com.mcprohosting.plugins.mindcrack.utilities.ChatFilters;
import com.mcprohosting.plugins.mindcrack.utilities.UtilityMethods;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

public class GeneralListeners implements Listener {
	//Self explanatory, handles general operations.

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if (Mindcrack.getPropConfig().getServerType().equals(ServerType.SURVIVAL)) {
			Player killer = event.getEntity().getKiller();
			if (killer != null) {
				UtilityMethods.addPoints(killer.getName(), 15);
			}
		}
	}

	@EventHandler
	public void onRespawnEvent(PlayerRespawnEvent event) {
		event.getPlayer().teleport(Mindcrack.getPropConfig().getSpawnLocation());
	}

	@EventHandler
	public void onHungerEvent(FoodLevelChangeEvent event) {
		if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME) && !Mindcrack.getPropConfig().getServerType().equals(ServerType.SURVIVAL)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME) && !Mindcrack.getPropConfig().getServerType().equals(ServerType.SURVIVAL)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME)  && !Mindcrack.getPropConfig().getServerType().equals(ServerType.SURVIVAL)) {
			event.setCancelled(!UtilityMethods.canChangeBlocks(event.getPlayer()));
		}
	}

	@EventHandler
	public void onPlaceBreakEvent(BlockPlaceEvent event) {
		if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME)  && !Mindcrack.getPropConfig().getServerType().equals(ServerType.SURVIVAL)) {
			event.setCancelled(!UtilityMethods.canChangeBlocks(event.getPlayer()));
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		event.setCancelled(!ChatFilters.chatAllowed(event.getPlayer(), event.getMessage()));
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		Player player = event.getPlayer();

		// Check if the player exists in the database
		if (!DatabaseManager.containsPlayer(event.getPlayer().getName())) {
			DatabaseManager.addPlayer(event.getPlayer().getName());
		}

		//If this is the main lobby display MotD
		if (Mindcrack.getPropConfig().getServerType().equals(ServerType.MAINLOBBY)) {
			player.sendMessage(Mindcrack.getPropConfig().getMotd());
		} else if (Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME) || Mindcrack.getPropConfig().getServerType().equals(ServerType.SURVIVAL)) {
			player.sendMessage(ChatColor.GREEN + "To get back to the main lobby type /server hub or go through the portal!");
		}

		if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME)  && !Mindcrack.getPropConfig().getServerType().equals(ServerType.SURVIVAL)) {
			player.getInventory().clear();
			player.getInventory().setItem(0, new ItemStack(Material.COMPASS));
		}

		if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME)) {
			player.teleport(Mindcrack.getPropConfig().getSpawnLocation());
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		event.setQuitMessage(null);
	}

	@EventHandler
	public void onRainStart(WeatherChangeEvent event) {
		event.setCancelled(true);
	}
}