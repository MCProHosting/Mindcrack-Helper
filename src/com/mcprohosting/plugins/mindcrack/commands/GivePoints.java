package com.mcprohosting.plugins.mindcrack.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.mcprohosting.plugins.mindcrack.utilities.UtilityMethods;

public class GivePoints implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!cs.hasPermission("mindcrack.givepoints")) {
			cs.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return true;
		}
		
		if (args.length == 2) {
			int points = 0;
			
			try {
				points = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				cs.sendMessage(ChatColor.RED + args[1] + " is not a valid number!");
				return false;
			}
			
			UtilityMethods.addPoints(args[0], points);
			return true;
		} else {
			return false;
		}
	}
}
