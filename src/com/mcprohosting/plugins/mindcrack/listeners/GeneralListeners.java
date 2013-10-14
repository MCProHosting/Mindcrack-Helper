package com.mcprohosting.plugins.mindcrack.listeners;

import com.mcprohosting.plugins.mindcrack.Mindcrack;
import com.mcprohosting.plugins.mindcrack.ServerType;
import com.mcprohosting.plugins.mindcrack.UtilityMethods;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

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
		Player player = event.getPlayer();

		player.teleport(Mindcrack.getSpawnLocation());

		if (Mindcrack.getServerType().equals(ServerType.MAINLOBBY)) {
			player.sendMessage(Mindcrack.motd);
		}

		if (!Mindcrack.getServerType().equals(ServerType.GAME)) {
			player.getInventory().clear();
			player.getInventory().setItem(0, new ItemStack(Material.COMPASS));

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