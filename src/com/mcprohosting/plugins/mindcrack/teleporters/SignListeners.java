package com.mcprohosting.plugins.mindcrack.teleporters;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class SignListeners implements Listener {
	ArrayList<Sign> signs = new ArrayList<Sign>();

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (signs.contains(event.getClickedBlock())) {

			}
		}
	}
}
