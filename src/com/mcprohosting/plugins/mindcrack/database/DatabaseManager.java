package com.mcprohosting.plugins.mindcrack.database;

import com.mcprohosting.plugins.mindcrack.Mindcrack;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
	public void init() {
		if (!(Database.setupTables() || Database.isDBSchemaValid())) {
			Mindcrack.getPlugin().getLogger().info("Unable to create tables or validate schema." +
					" Plugin will not load until issue is fixed");
			Bukkit.getPluginManager().disablePlugin(Mindcrack.getPlugin());
		}
	}

	public static Connection getConnection() {
		return Mindcrack.getDB().mysql.getConnection();
	}

	public static boolean containsPlayer(String player) {
		boolean retVal = false;

		Connection connection = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement("SELECT LOWER(player) FROM players WHERE player = LOWER(?);");
			ps.setString(1, player);
			rs = ps.executeQuery();

			retVal = rs.next();

			rs.close();
			ps.close();
			Database.mysql.closeConnection(connection);
		} catch (SQLException e) {
			Mindcrack.getPlugin().getLogger().info("Failed to check if the database contains a player.");
			Database.mysql.closeConnection(connection);
			return false;
		}

		return retVal;
	}

	public static boolean addPlayer(String player) {
		boolean retVal = false;

		Connection connection = getConnection();
		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement("INSERT INTO players (player, global) " +
					"VALUES (?, 0);");
			ps.setString(1, player);
			retVal = ps.executeUpdate() > 0;
			ps.close();
			Database.mysql.closeConnection(connection);
		} catch (SQLException e) {
			Mindcrack.getPlugin().getLogger().info("Failed to add a player to the database.");
			Database.mysql.closeConnection(connection);
			return false;
		}

		return retVal;
	}

	public static boolean addPoints(String player, int points) {
		boolean retVal = false;

		Connection connection = getConnection();
		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement("UPDATE players SET global=global +? WHERE LOWER(player)=(?);");
			ps.setInt(1, points);
			ps.setString(2, player);
			retVal = ps.executeUpdate() > 0;
			ps.close();
			Database.mysql.closeConnection(connection);
		} catch (SQLException e) {
			Mindcrack.getPlugin().getLogger().info("Failed to add points to a players global score in the database.");
			Database.mysql.closeConnection(connection);
			return false;
		}

		return retVal;
	}

	public static int getPoints(String player) {
		int retVal = 0;

		Connection connection = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement("SELECT global FROM players WHERE LOWER(player)=LOWER(?)");
			ps.setString(1, player);
			rs = ps.executeQuery();

			while (rs.next() != false) {
				retVal = rs.getInt(1);
			}

			ps.close();
			rs.close();
			connection.close();
		} catch (SQLException ex) {
			Mindcrack.getPlugin().getLogger().info("Could not retrieve a players points");
		}

		return retVal;
	}
}
