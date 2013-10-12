package com.mcprohosting.plugins.mindcrack;

import org.bukkit.entity.Player;

public class HelperMethods {
	public static boolean canChangeBlocks(Player player) {
		return (player.isOp() || player.hasPermission("mindcrack.changeblocks"));
	}
}