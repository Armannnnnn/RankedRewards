package me.arman.claimrewards.sqlite;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import me.arman.claimrewards.Main;

public class SQLite extends Database {

	private Main plugin;

	String dbname;

	public SQLite(Main instance) {
		super(instance);
		plugin = instance;
		dbname = plugin.getConfig().getString("dbName");
	}

	public Connection getSQLConnection() {
		File dataFolder = new File(Bukkit.getPluginManager().getPlugin("SkyWars").getDataFolder(),
				dbname + ".db");
		if (!dataFolder.exists()) {
			try {
				dataFolder.createNewFile();
			} catch (IOException e) {
				plugin.getLogger().log(Level.SEVERE, "File write error: " + dbname + ".db");
			}
		}
		try {
			if (connection != null && !connection.isClosed()) {
				return connection;
			}
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
			return connection;
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
		} catch (ClassNotFoundException ex) {
			plugin.getLogger().log(Level.SEVERE, "Missing the SQLite JBDC library.");
		}
		return null;
	}

	public void load() {
		connection = getSQLConnection();
		try {
			Statement s = connection.createStatement();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		initialize();
	}
}
