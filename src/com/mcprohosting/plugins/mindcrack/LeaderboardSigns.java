package com.mcprohosting.plugins.mindcrack;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class LeaderboardSigns {
	private static ArrayList<Location> signLocations = new ArrayList<Location>();
	
	// run this once on startup to load the locations of the leaderboard signs from file
	public static void initializeSigns() {
		ConfigurationSection signs = Mindcrack.getPlugin().getConfig().getConfigurationSection("leaderboard-signs");
		if (signs == null) {
			Mindcrack.getPlugin().getLogger().severe("No leaderboard signs set");
		} else {
			for (String key : signs.getKeys(false)) {
				ConfigurationSection section = signs.getConfigurationSection(key);
				signLocations.add(new Location(Bukkit.getWorlds().get(0), section.getInt("x"), section.getInt("y"), section.getInt("z")));
			}
		}
	}
	
	public static void updateSigns() {
		HashMap<String, Integer> topPlayers = UtilityMethods.getTopPlayers(signLocations.size());
		int i = 1;
		
		for (String player : topPlayers.keySet()) {
			Block block = signLocations.get(i - 1).getBlock();
			if (block instanceof Sign) {
				Sign sign = (Sign) block;
				sign.setLine(0, "[#" + i + "]");
				sign.setLine(1, player);
				sign.setLine(3, topPlayers.get(player) + " Points");
				i++;
			}
		}
	}
	
	public static void addSign(Location location) {
		signLocations.add(location);
		saveSignsConfig();
	}
	
	public static void removeSign(Location location) {
		signLocations.remove(location);
		saveSignsConfig();
	}
	
	private static void saveSignsConfig() {
		ConfigurationSection signs = new YamlConfiguration();
		int i = 0;
		
		for (Location location : signLocations) {
			ConfigurationSection sign = new YamlConfiguration();
			sign.set("x", location.getBlockX());
			sign.set("y", location.getBlockY());
			sign.set("z", location.getBlockZ());
			signs.set("sign" + i, sign);
			i++;
		}
		
		Mindcrack.getPlugin().getConfig().set("leaderboard-signs", signs);
		Mindcrack.getPlugin().saveConfig();
	}
}
