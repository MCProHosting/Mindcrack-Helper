package com.mcprohosting.plugins.mindcrack;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GeneralListeners implements Listener {
	@EventHandler
	public void onHungerEvent(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		event.setCancelled(!UtilityMethods.canChangeBlocks(event.getPlayer()));
	}

	@EventHandler
	public void onPlaceBreakEvent(BlockPlaceEvent event) {
		event.setCancelled(!UtilityMethods.canChangeBlocks(event.getPlayer()));
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		event.getPlayer().teleport(Mindcrack.spawnLocation);

		if (Mindcrack.serverType.equals(ServerType.MAINLOBBY)) {
			event.getPlayer().sendMessage(Mindcrack.motd);
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