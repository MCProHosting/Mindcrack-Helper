package com.mcprohosting.plugins.mindcrack;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class CreativeListeners {
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.getPlayer().setGameMode(GameMode.CREATIVE);
	}
}