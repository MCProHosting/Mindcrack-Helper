package com.mcprohosting.plugins.mindcrack;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class GeneralListeners implements Listener {
	@EventHandler
	public void onHungerEvent(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onEntityDamageEvent(EntityDamageByEntityEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onBlockDamageEvent(EntityDamageByBlockEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		event.setCancelled(HelperMethods.canChangeBlocks(event.getPlayer()));
	}

	@EventHandler
	public void onPlaceBreakEvent(BlockPlaceEvent event) {
		event.setCancelled(HelperMethods.canChangeBlocks(event.getPlayer()));
	}
}