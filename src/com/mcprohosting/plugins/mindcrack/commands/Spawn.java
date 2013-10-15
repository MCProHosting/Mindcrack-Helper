package com.mcprohosting.plugins.mindcrack.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Spawn implements CommandExecutor {
	//Takes the player back to the spawn point from Mindcrack.getSpawn();

	public Spawn() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equals("spawn")) {

		}
	}
}
