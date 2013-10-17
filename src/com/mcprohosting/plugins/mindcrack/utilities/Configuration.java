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
	private int chatTime;

	public Configuration(FileConfiguration config) {
		Mindcrack.getPlugin().saveDefaultConfig();

		spawnLocation = new Location(Bukkit.getWorld("world"), config.getDouble("spawn.x"), config.getDouble("spawn.y"), config.getDouble("spawn.z"), (float) config.getDouble("spawn.pitch"), (float) config.getDouble("spawn.yaw"));

		if (config.getString("servertype").equalsIgnoreCase("mainlobby")) {
			serverType = ServerType.MAINLOBBY;
		} else if (config.getString("servertype").equalsIgnoreCase("otherlobby")) {
			serverType = ServerType.OTHERLOBBY;
		} else if (config.getString("servertype").equalsIgnoreCase("game")) {
			serverType = ServerType.GAME;
		}

		if (getServerType().equals(ServerType.MAINLOBBY)) {
			motd = createMOTD(config.getStringList("motd"));
		}

		chatTime = config.getInt("chattime");
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

	public int getChatTime() {
		return chatTime;
	}
}
