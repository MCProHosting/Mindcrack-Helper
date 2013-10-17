package com.mcprohosting.plugins.mindcrack;

import com.mcprohosting.plugins.mindcrack.commands.GivePoints;
import com.mcprohosting.plugins.mindcrack.commands.Spawn;
import com.mcprohosting.plugins.mindcrack.listeners.GeneralListeners;
import com.mcprohosting.plugins.mindcrack.listeners.InventoryListeners;
import com.mcprohosting.plugins.mindcrack.teleporters.SignListeners;

import lilypad.client.connect.api.Connect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Mindcrack extends JavaPlugin {
	private static Plugin plugin;
	private static Connect connect;
	private static Location spawnLocation;
	private static String motd;
	private static ServerType serverType;

	public void onEnable() {
		//Allow this to be access statically.
		plugin = this;

		//Setup configuration values.
		this.saveDefaultConfig();
		spawnLocation = new Location(Bukkit.getWorld("world"), this.getConfig().getDouble("spawn.x"), this.getConfig().getDouble("spawn.y"), this.getConfig().getDouble("spawn.z"), (float) this.getConfig().getDouble("spawn.pitch"), (float) this.getConfig().getDouble("spawn.yaw"));

		//Declare server type
		if (this.getConfig().getString("servertype").equalsIgnoreCase("mainlobby")) {
			serverType = ServerType.MAINLOBBY;
		} else if (this.getConfig().getString("servertype").equalsIgnoreCase("otherlobby")) {
			serverType = ServerType.OTHERLOBBY;
		} else if (this.getConfig().getString("servertype").equalsIgnoreCase("game")) {
			serverType = ServerType.GAME;
		}

		//Create message of the day
		if (serverType.equals(ServerType.MAINLOBBY)) {
			motd = UtilityMethods.createMOTD(this.getConfig().getStringList("motd"));
		}

		//Setup utilities
		connect = plugin.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
		UtilityMethods.setupCompassInventory();

		//Setup listeners
		Bukkit.getPluginManager().registerEvents(new GeneralListeners(), this);
		if (!serverType.equals(ServerType.GAME)) {
			Bukkit.getPluginManager().registerEvents(new InventoryListeners(), this);
		}

		if (serverType.equals(ServerType.OTHERLOBBY)) {
			Bukkit.getPluginManager().registerEvents(new SignListeners(), this);
		}

		//Setup commands
		getCommand("spawn").setExecutor(new Spawn());
		getCommand("givepoints").setExecutor(new GivePoints());

		getLogger().info("TYPE: [" + serverType + "] Initialized");
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

	public static Location getSpawnLocation() {
		return spawnLocation;
	}

	public static String getMotD() {
		return motd;
	}

	public static ServerType getServerType() {
		return serverType;
	}
}