package com.mcprohosting.plugins.mindcrack.listeners.serverspecific;

import com.mcprohosting.plugins.mindcrack.utilities.UtilityMethods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SurvivalListener implements Listener {
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player killer = event.getEntity().getKiller();

		event.setDeathMessage(null);

		if (killer != null) {
			UtilityMethods.addPoints(killer.getName(), 15);
		}
	}
}
