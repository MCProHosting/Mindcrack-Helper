package com.mcprohosting.plugins.mindcrack;

import com.mcprohosting.plugins.mindcrack.commands.GivePoints;
import com.mcprohosting.plugins.mindcrack.commands.Spawn;
import com.mcprohosting.plugins.mindcrack.database.Database;
import com.mcprohosting.plugins.mindcrack.database.DatabaseManager;
import com.mcprohosting.plugins.mindcrack.listeners.general.GeneralListeners;
import com.mcprohosting.plugins.mindcrack.listeners.general.LeaderboardSignListeners;
import com.mcprohosting.plugins.mindcrack.listeners.general.LilypadMessageListener;
import com.mcprohosting.plugins.mindcrack.listeners.serverspecific.CreativeListener;
import com.mcprohosting.plugins.mindcrack.listeners.serverspecific.LobbyListener;
import com.mcprohosting.plugins.mindcrack.listeners.serverspecific.SurvivalListener;
import com.mcprohosting.plugins.mindcrack.teleporters.TeleportSignListeners;
import com.mcprohosting.plugins.mindcrack.teleporters.TeleportSigns;
import com.mcprohosting.plugins.mindcrack.utilities.Configuration;
import com.mcprohosting.plugins.mindcrack.utilities.LeaderboardSigns;
import com.mcprohosting.plugins.mindcrack.utilities.SaveUtility;
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
		getLogger().info("Starting Helper");
		//Allow this to be access statically
		plugin = this;

		//Setup configuration object
		propConfig = new Configuration(this.getConfig());

		//Setup utilities
		connect = plugin.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
		UtilityMethods.setupCompassInventory();

		//Setup listeners
		Bukkit.getPluginManager().registerEvents(new GeneralListeners(), this);
		if (getPropConfig().getServerType().equals(ServerType.LOBBY)) {
			Bukkit.getPluginManager().registerEvents(new LobbyListener(), this);
		} else if (getPropConfig().getServerType().equals(ServerType.CREATIVE)) {
			Bukkit.getPluginManager().registerEvents(new CreativeListener(), this);
		} else if (getPropConfig().getServerType().equals(ServerType.SURVIVAL)) {
			Bukkit.getPluginManager().registerEvents(new SurvivalListener(), this);
		}


		if (propConfig.getServerType().equals(ServerType.LOBBY)) {
			LeaderboardSigns.initializeSigns();
			Bukkit.getPluginManager().registerEvents(new LeaderboardSignListeners(), this);
			Bukkit.getPluginManager().registerEvents(new TeleportSignListeners(), this);
			connect.registerEvents(new LilypadMessageListener());
		}

		// Setup database
		database = new Database();
		databaseManager = new DatabaseManager();

		//Setup commands
		getCommand("spawn").setExecutor(new Spawn());
		getCommand("givepoints").setExecutor(new GivePoints());

		//Setup updaters
		if (propConfig.getServerType().equals(ServerType.LOBBY)) {
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				
				@Override
				public void run() {
					LeaderboardSigns.updateSigns();
					TeleportSigns.updateSigns();
				}
			}, 60, 60);
		}

		// Setup auto save on creative
		if (propConfig.getServerType().equals(ServerType.CREATIVE)) {
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new SaveUtility(), 20, 20 * 60 * 15);
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