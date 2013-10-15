package com.mcprohosting.plugins.mindcrack.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcprohosting.plugins.mindcrack.Mindcrack;
import com.mcprohosting.plugins.mindcrack.ServerType;

public class Spawn implements CommandExecutor {
	//Takes the player back to the spawn point from Mindcrack.getSpawn();

	public Spawn() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (!Mindcrack.getServerType().equals(ServerType.GAME)) {
				Player player = (Player) sender;
				
				player.teleport(Mindcrack.getSpawnLocation());
			}
		} else {
			sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
		}
		
		return true;
	}
}
