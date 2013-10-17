package com.mcprohosting.plugins.mindcrack.utilities;

import com.mcprohosting.plugins.mindcrack.Mindcrack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ChatFilters {
	private static Character[] checkedCharacters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '!', '.', ',', };
	private static LinkedHashMap<Character, Integer> characterMap = new LinkedHashMap<Character, Integer>();
	private static ArrayList<String> recentChatters = new ArrayList<String>();

	public static boolean chatAlowed(final Player player, final String message) {
		if (!player.hasPermission("mindcrack.chat.bypass")) {
			if (messageIsCaps(message)) {
				player.sendMessage(ChatColor.RED + "Your message cannot contains that many caps!");
				return false;
			} else if (messageIsPrimarlyOneCharacter(message)) {
				player.sendMessage(ChatColor.RED + "Your message contains too many of the same characters.");
				return false;
			}

			if (recentChatters.contains(player.getName())) {
				player.sendMessage(ChatColor.RED + "Please wait " + Mindcrack.getPropConfig().getChatTime() + "s between messages.");
				return false;
			} else {
				recentChatters.add(player.getName());

				Bukkit.getScheduler().scheduleSyncRepeatingTask(Mindcrack.getPlugin(), new Runnable() {
					@Override
					public void run() {
						recentChatters.remove(player.getName());
					}
				}, Mindcrack.getPropConfig().getChatTime()*20, Mindcrack.getPropConfig().getChatTime()*20);
			}
		}

		return true;
	}

	private static boolean messageIsPrimarlyOneCharacter(String message) {
		message = message.toLowerCase();

		if (message.length() > 3) {
			for (int i = 0; i < checkedCharacters.length; i++) {
				int count = message.length() - message.replace(checkedCharacters[i].toString(), "").length();
				characterMap.put(checkedCharacters[i], count);
			}

			for (int x = 0; x < characterMap.size(); x++) {
				double characterPercentOccurrence = (((Integer) characterMap.values().toArray()[x]) * 100F)/message.length();

				return characterPercentOccurrence >= 60;
			}
		}

		return false;
	}

	private static Boolean messageIsCaps(String message){
		if (message.length() > 3) {
			int count = 0;
			for(Character c : message.toCharArray()){
				if(Character.isUpperCase(c)){
					count++;
				}
			}

			return (count/message.length()*100) > 60;
		}

		return false;
	}
}
