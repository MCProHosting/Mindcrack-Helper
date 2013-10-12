package com.mcprohosting.plugins.mindcrack;

import org.bukkit.entity.Player;

public class UtilityMethods {
	public static boolean canChangeBlocks(Player player) {
		return (player.isOp() || player.hasPermission("mindcrack.changeblocks"));
	}
}