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
			Player player = (Player) sender;

			if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.SURVIVAL)) {
				if (!Mindcrack.getPropConfig().getServerType().equals(ServerType.GAME)) {
					player.teleport(Mindcrack.getPropConfig().getSpawnLocation());
				} else {
					player.sendMessage(ChatColor.RED + "You cannot run that in a game instance!");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You cannot run /spawn in a survival instance. To leave type /server hub");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
		}

		return true;
	}
}
