package com.mcprohosting.plugins.mindcrack;

import com.mcprohosting.plugins.mindcrack.commands.GivePoints;
import com.mcprohosting.plugins.mindcrack.commands.Spawn;
import com.mcprohosting.plugins.mindcrack.database.Database;
import com.mcprohosting.plugins.mindcrack.database.DatabaseManager;
import com.mcprohosting.plugins.mindcrack.listeners.GeneralListeners;
import com.mcprohosting.plugins.mindcrack.listeners.InventoryListeners;
import com.mcprohosting.plugins.mindcrack.listeners.LeaderboardSignListeners;
import com.mcprohosting.plugins.mindcrack.listeners.LilypadMessageListener;
import com.mcprohosting.plugins.mindcrack.teleporters.TeleportSignListeners;
import com.mcprohosting.plugins.mindcrack.teleporters.TeleportSigns;
import com.mcprohosting.plugins.mindcrack.utilities.Configuration;
import com.mcprohosting.plugins.mindcrack.utilities.LeaderboardSigns;
import com.mcprohosting.plugins.mindcrack.utilities.UtilityMethods;

import lilypad.client.connect.api.Connect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Mindcrack extends JavaPlugin {
	private static Plugin plugin;
	private static Connect connect;
	private static Configuration propConfig;
	private static Database database;
	private static DatabaseManager databaseManager;

	public void onEnable() {
		//Allow this to be access statically
		plugin = this;

		//Setup configuration object
		propConfig = new Configuration(this.getConfig());

		//Setup utilities
		connect = plugin.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
		UtilityMethods.setupCompassInventory();

		//Setup listeners
		Bukkit.getPluginManager().registerEvents(new GeneralListeners(), this);
		if (!propConfig.getServerType().equals(ServerType.GAME)) {
			Bukkit.getPluginManager().registerEvents(new InventoryListeners(), this);
		}

		if (propConfig.getServerType().equals(ServerType.OTHERLOBBY)) {
			TeleportSigns.initializeSigns();
			Bukkit.getPluginManager().registerEvents(new TeleportSignListeners(), this);
			connect.registerEvents(new LilypadMessageListener());
		}
		
		if (propConfig.getServerType().equals(ServerType.MAINLOBBY)) {
			LeaderboardSigns.initializeSigns();
			Bukkit.getPluginManager().registerEvents(new LeaderboardSignListeners(), this);
		}

		// Setup database
		database = new Database();
		databaseManager = new DatabaseManager();

		//Setup commands
		getCommand("spawn").setExecutor(new Spawn());
		getCommand("givepoints").setExecutor(new GivePoints());

		//Timerz
		if (propConfig.getServerType().equals(ServerType.MAINLOBBY)) {
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				
				@Override
				public void run() {
					LeaderboardSigns.updateSigns();
				}
			}, 100, 100);
		}
		
		if (propConfig.getServerType().equals(ServerType.OTHERLOBBY)) {
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				
				@Override
				public void run() {
					TeleportSigns.updateSigns();
				}
			}, 20, 20);
		}

		//Done!
		getLogger().info("TYPE: [" + propConfig.getServerType() + "] Initialized");
	}

	public void onDisable() {
		getLogger().info("Disabled");
	}

	//Gets for resources
	public static Plugin getPlugin() {
		return plugin;
	}

	public static Connect getConnect() {
		return connect;
	}

	public static Configuration getPropConfig() {
		return propConfig;
	}

	public static Database getDB() {
		return database;
	}
}