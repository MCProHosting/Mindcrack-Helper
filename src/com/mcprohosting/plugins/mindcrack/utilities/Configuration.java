package com.mcprohosting.plugins.mindcrack.utilities;

import com.mcprohosting.plugins.mindcrack.Mindcrack;
import com.mcprohosting.plugins.mindcrack.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Configuration {
	private ServerType serverType;
	private Location spawnLocation;
	private String motd;

	public Configuration(FileConfiguration config) {
		Mindcrack.getPlugin().saveDefaultConfig();

		this.spawnLocation = new Location(Bukkit.getWorld("world"), config.getDouble("spawn.x"), config.getDouble("spawn.y"), config.getDouble("spawn.z"), (float) config.getDouble("spawn.pitch"), (float) config.getDouble("spawn.yaw"));

		if (config.getString("servertype").equalsIgnoreCase("lobby")) {
			this.serverType = ServerType.LOBBY;
		} else if (config.getString("servertype").equalsIgnoreCase("game")) {
			this.serverType = ServerType.GAME;
		} else if (config.getString("servertype").equalsIgnoreCase("survival")) {
			this.serverType = ServerType.SURVIVAL;
		} else if (config.getString("servertype").equalsIgnoreCase("creative")) {
			this.serverType = ServerType.CREATIVE;
		}

		if (getServerType().equals(ServerType.LOBBY)) {
			this.motd = createMOTD(config.getStringList("motd"));
		}
	}

	//Compiles a string for the message of the day based on a list of configuration values
	private static String createMOTD(List<String> input) {
		String motd = ChatColor.GREEN.toString();

		for (int i = 0; i < input.size(); i++) {
			if (i == input.size()-1) { //If it is the last element, do not append an additional line.
				motd = motd + input.get(i);
			} else {
				motd = motd + input.get(i) + "\n";
			}
		}

		return motd;
	}

	public ServerType getServerType() {
		return serverType;
	}

	public Location getSpawnLocation() {
		return spawnLocation;
	}

	public String getMotd() {
		return motd;
	}
}
