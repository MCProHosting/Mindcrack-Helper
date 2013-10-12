package com.mcprohosting.plugins.mindcrack;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Mindcrack extends JavaPlugin {
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new GeneralListeners(), this);
		getLogger().info("Initialized");
	}

	public void onDisable() {
		getLogger().info("Disabled");
	}
}