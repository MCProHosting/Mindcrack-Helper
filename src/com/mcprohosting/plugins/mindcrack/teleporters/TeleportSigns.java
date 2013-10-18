package com.mcprohosting.plugins.mindcrack.teleporters;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.mcprohosting.plugins.mindcrack.Mindcrack;

public class TeleportSigns {
	private static HashMap<String, Location> signLocations = new HashMap<String, Location>();
	private static HashMap<String, String> serverStatus = new HashMap<String, String>();
	
	// run this once on startup to load the locations of the leaderboard signs from file
	public static void initializeSigns() {
		ConfigurationSection signs = Mindcrack.getPlugin().getConfig().getConfigurationSection("teleport-signs");
		if (signs == null) {
			Mindcrack.getPlugin().getLogger().severe("No teleport signs set");
		} else {
			for (String key : signs.getKeys(false)) {
				ConfigurationSection section = signs.getConfigurationSection(key);
				signLocations.put(key, new Location(Bukkit.getWorlds().get(0), section.getInt("x"), section.getInt("y"), section.getInt("z")));
				serverStatus.put(key, "0/0");
			}
		}
	}
	
	public static void updateSigns() {
		for (String key : signLocations.keySet()) {
			Block block = signLocations.get(key).getBlock();
			Sign sign = (Sign) block.getState();
			sign.setLine(0, "KotL #" + key.substring(0, 1));
			sign.setLine(2, serverStatus.get(key));
			sign.update(true);
		}
	}
	
	public static void updateStatus(String server, String status) {
		serverStatus.put(server, status);
	}
	
	public static void addSign(String server, Location location) {
		signLocations.put(server, location);
		saveSignsConfig();
	}
	
	public static void removeSign(Location location) {
		signLocations.values().remove(location);
		saveSignsConfig();
	}
	
	public static boolean isRegistered(Location location) {
		for (String key : signLocations.keySet()) {
			if (signLocations.get(key).distance(location) < 1) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isRegistered(String serverName) {
		return signLocations.containsKey(serverName);
	}
	
	public static String getServerName(Location location) {
		for (String key : signLocations.keySet()) {
			if (signLocations.get(key).distance(location) < 1) {
				return key;
			}
		}
		
		return null;
	}
	
	private static void saveSignsConfig() {
		ConfigurationSection signs = new YamlConfiguration();
		
		for (String key : signLocations.keySet()) {
			Location location = signLocations.get(key);
			ConfigurationSection sign = new YamlConfiguration();
			sign.set("x", location.getBlockX());
			sign.set("y", location.getBlockY());
			sign.set("z", location.getBlockZ());
			signs.set(key, sign);
		}
		
		Mindcrack.getPlugin().getConfig().set("teleport-signs", signs);
		Mindcrack.getPlugin().saveConfig();
	}
}
