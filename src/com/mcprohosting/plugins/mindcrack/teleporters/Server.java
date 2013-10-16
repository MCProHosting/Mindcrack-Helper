package com.mcprohosting.plugins.mindcrack.teleporters;

import org.bukkit.block.Sign;

public class Server {
	private Sign associatedSign;
	private String teleportServer;
	private int playersInServer;
	private int serverMaxPlayers;

	public Server(Sign associatedSign, String teleportServer) {
		this.associatedSign = associatedSign;
		this.teleportServer = teleportServer;
	}

	public Sign getAssociatedSign() {
		return associatedSign;
	}

	public String getTeleportServer() {
		return teleportServer;
	}

	public int getPlayersInServer() {
		return playersInServer;
	}

	public void setPlayersInServer(int playersInServer) {
		this.playersInServer = playersInServer;
	}

	public int getServerMaxPlayers() {
		return serverMaxPlayers;
	}
}
