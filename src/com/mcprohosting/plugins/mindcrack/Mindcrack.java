package com.mcprohosting.plugins.mindcrack;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class Mindcrack extends JavaPlugin {
	protected static Location spawnLocation;
	protected static String motd = ChatColor.GREEN.toString();
	protected static ServerType serverType;

	public void onEnable() {
		this.saveDefaultConfig();

		spawnLocation = new Location(Bukkit.getWorld("world"), this.getConfig().getDouble("spawn.x"), this.getConfig().getDouble("spawn.y"), this.getConfig().getDouble("spawn.z"), (float) this.getConfig().getDouble("spawn.pitch"), (float) this.getConfig().getDouble("spawn.yaw"));

		if (this.getConfig().getString("servertype").equalsIgnoreCase("mainlobby")) {
			serverType = ServerType.MAINLOBBY;
		} else if (this.getConfig().getString("servertype").equalsIgnoreCase("gamelobby")) {
			serverType = ServerType.GAMELOBBY;
		}else if (this.getConfig().getString("servertype").equalsIgnoreCase("other")) {
			serverType = ServerType.OTHER;
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

		Bukkit.getPluginManager().registerEvents(new GeneralListeners(), this);
		getLogger().info("TYPE: [" + serverType + "] Initialized");
	}

	public void onDisable() {
		getLogger().info("Disabled");
	}
}