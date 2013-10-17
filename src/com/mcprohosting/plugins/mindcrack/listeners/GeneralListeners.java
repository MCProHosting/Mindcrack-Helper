package com.mcprohosting.plugins.mindcrack.listeners;

import com.mcprohosting.plugins.mindcrack.utilities.ChatFilters;
import com.mcprohosting.plugins.mindcrack.Mindcrack;
import com.mcprohosting.plugins.mindcrack.ServerType;
import com.mcprohosting.plugins.mindcrack.utilities.UtilityMethods;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

public class GeneralListeners implements Listener {
	//Self explanatory, handles general operations.

	@EventHandler
	public void onHungerEvent(FoodLevelChangeEvent event) {
		if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME)) {
			event.setCancelled(!UtilityMethods.canChangeBlocks(event.getPlayer()));
		}
	}

	@EventHandler
	public void onPlaceBreakEvent(BlockPlaceEvent event) {
		if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME)) {
			event.setCancelled(!UtilityMethods.canChangeBlocks(event.getPlayer()));
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		event.setCancelled(ChatFilters.chatAlowed(event.getPlayer(), event.getMessage()));
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		Player player = event.getPlayer();

		//If this is the main lobby display MotD
		if (Mindcrack.getPropConfig().getServerType().equals(ServerType.MAINLOBBY)) {
			player.sendMessage(Mindcrack.getPropConfig().getMotd());
		}

		//If this is NOT a game instance then give them a compass so they can teleport.
		if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME)) {
			player.getInventory().clear();
			player.getInventory().setItem(0, new ItemStack(Material.COMPASS));
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