package com.mcprohosting.plugins.mindcrack.listeners.general;

import com.mcprohosting.plugins.mindcrack.Mindcrack;
import com.mcprohosting.plugins.mindcrack.ServerType;
import com.mcprohosting.plugins.mindcrack.database.DatabaseManager;
import com.mcprohosting.plugins.mindcrack.utilities.ChatFilters;
import com.mcprohosting.plugins.mindcrack.utilities.UtilityMethods;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GeneralListeners implements Listener {
	@EventHandler
	public void onRespawnEvent(PlayerRespawnEvent event) {
		if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME)) {
			event.getPlayer().teleport(Mindcrack.getPropConfig().getSpawnLocation());
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		event.setCancelled(!UtilityMethods.canChangeBlocks(event.getPlayer()));
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		event.setCancelled(!UtilityMethods.canChangeBlocks(event.getPlayer()));
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

		//Display instructions for getting out of a non-lobby server.
		if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.LOBBY)) {
			player.sendMessage(ChatColor.GREEN + "To get back to the main lobby type /server hub or go through the portal!");
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