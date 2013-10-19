package com.mcprohosting.plugins.mindcrack.listeners.serverspecific;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class CreativeListener implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		//Send instructions
		player.sendMessage(ChatColor.GREEN + "Type /p auto to automatically be assigned to a creative plot.");
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onProjectile(ProjectileLaunchEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onHungerEvent(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onSplashPotion(PotionSplashEvent event) {
		event.setCancelled(true);
	}

	@EventHandler void onFirework(PlayerInteractEvent event) {
		if (event.getMaterial() == Material.FIREWORK ) {
			event.setCancelled(true);
		}
		if (event.getMaterial() == Material.POTION) {
			event.setCancelled(true);
		}
	}
}
