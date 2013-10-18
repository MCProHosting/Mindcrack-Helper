package com.mcprohosting.plugins.mindcrack.database;

import com.gmail.favorlock.bonesqlib.MySQL;
import com.mcprohosting.plugins.mindcrack.Mindcrack;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.SQLException;

public class Database {

	public static MySQL mysql;
	private String host;
	private int port;
	private String database;
	private String username;
	private String password;

	public Database() {
		init();
	}

	private void init() {
		loadConfig();
		mysql = new MySQL(Mindcrack.getPlugin().getLogger(),
				"KoTL",
				host,
				port,
				database,
				username,
				password);
		mysql.open();
	}

	private void loadConfig() {
		ConfigurationSection databaseConfig = Mindcrack.getPlugin().getConfig().getConfigurationSection("database");

		for (int i = 1; i <= databaseConfig.getKeys(false).size(); i++) {
			host = databaseConfig.getString("host");
			port = databaseConfig.getInt("port");
			database = databaseConfig.getString("database");
			username = databaseConfig.getString("username");
			password = databaseConfig.getString("password");
		}
	}

	public static boolean isDBSchemaValid() {
		boolean retVal = false;

		retVal = mysql.isTable("players");

		return retVal;
	}

	public static boolean setupTables() {
		boolean retVal = false;

		try {
			if (!mysql.isTable("players")) {
				mysql.query("CREATE TABLE IF NOT EXISTS players (player VARCHAR(16) PRIMARY KEY, global Integer);");
				retVal = true;
			}
		} catch (SQLException e) {
			try {
				if (mysql.isTable("players")) {
					mysql.query("DROP TABLE players;");
				}
			} catch(SQLException ex) {
				Mindcrack.getPlugin().getLogger().severe("Error dropping tables!");
				retVal = false;
			}
		}

		return retVal;
	}

}
