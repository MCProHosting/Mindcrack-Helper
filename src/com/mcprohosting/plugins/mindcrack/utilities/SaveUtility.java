package com.mcprohosting.plugins.mindcrack.utilities;

import org.bukkit.Bukkit;

public class SaveUtility implements Runnable {

	@Override
	public void run() {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-all");
	}
}
