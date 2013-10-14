package com.mcprohosting.plugins.mindcrack;

import com.mcprohosting.plugins.mindcrack.listeners.GeneralListeners;
import com.mcprohosting.plugins.mindcrack.listeners.InventoryListeners;
import lilypad.client.connect.api.Connect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Mindcrack extends JavaPlugin {
	private static Plugin plugin;
	private static Connect connect;
	private static Location spawnLocation;
	private static String motd = ChatColor.GREEN.toString();
	private static ServerType serverType;

	public void onEnable() {
		plugin = this;

		this.saveDefaultConfig();
		spawnLocation = new Location(Bukkit.getWorld("world"), this.getConfig().getDouble("spawn.x"), this.getConfig().getDouble("spawn.y"), this.getConfig().getDouble("spawn.z"), (float) this.getConfig().getDouble("spawn.pitch"), (float) this.getConfig().getDouble("spawn.yaw"));

		if (this.getConfig().getString("servertype").equalsIgnoreCase("mainlobby")) {
			serverType = ServerType.MAINLOBBY;
		} else if (this.getConfig().getString("servertype").equalsIgnoreCase("otherlobby")) {
			serverType = ServerType.OTHERLOBBY;
		} else if (this.getConfig().getString("servertype").equalsIgnoreCase("game")) {
			serverType = ServerType.GAME;
		}

		if (serverType.equals(ServerType.MAINLOBBY)) {
			for (int i = 0; i < this.getConfig().getStringList("motd").size(); i++) {
				if (i == this.getConfig().getStringList("motd").size()-1) { //If it is the last element, do not append an additional line.
					motd = motd + this.getConfig().getStringList("motd").get(i);
				} else {
					motd = motd + this.getConfig().getStringList("motd").get(i) + "\n";
				}
			}
		}

		connect = plugin.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
		UtilityMethods.setupCompassInventory();

		Bukkit.getPluginManager().registerEvents(new GeneralListeners(), this);
		if (!serverType.equals(ServerType.GAME)) {
			Bukkit.getPluginManager().registerEvents(new InventoryListeners(), this);
		}

		getLogger().info("TYPE: [" + serverType + "] Initialized");
	}

	public void onDisable() {
		getLogger().info("Disabled");
	}

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