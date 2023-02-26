package me.arman.claimrewards.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.entity.HumanEntity;
import me.arman.claimrewards.Main;

public abstract class Database {
	private Main plugin;
	Connection connection;

	public Database(Main instance) {
		plugin = instance;
	}

	public abstract Connection getSQLConnection();

	public abstract void load();

	public void initialize() {
		String table = plugin.getConfig().getString("tableName").replaceAll("%previous_season%",
				String.valueOf(plugin.getConfig().getInt("currentSeason") - 1));
		connection = getSQLConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE id = ?");
			ResultSet rs = ps.executeQuery();
			close(ps, rs);
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		}
	}

	public Integer getPosition(String id) {
		String table = plugin.getConfig().getString("tableName").replaceAll("%previous_season%",
				String.valueOf(plugin.getConfig().getInt("currentSeason") - 1));
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getSQLConnection();
			ps = conn.prepareStatement("SELECT * FROM " + table + " ORDER BY points DESC;");
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("id").equalsIgnoreCase(id.toLowerCase())) {
					return rs.getRow();
				}
			}
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
			}
		}
		return 0;
	}

	public Integer getPosition(String id, int season) {
		String table = plugin.getConfig().getString("tableName").replaceAll("%previous_season%",
				String.valueOf(season));
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getSQLConnection();
			ps = conn.prepareStatement("SELECT * FROM " + table + " ORDER BY points DESC;");
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("id").equalsIgnoreCase(id.toLowerCase())) {
					return rs.getRow();
				}
			}
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
			}
		}
		return 0;
	}

	public boolean claimedReward(String id) {
		String table = plugin.getConfig().getString("tableName").replaceAll("%previous_season%",
				String.valueOf(plugin.getConfig().getInt("currentSeason") - 1));
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getSQLConnection();
			ps = conn.prepareStatement("SELECT * FROM " + table + " ORDER BY points DESC;");
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("id").equalsIgnoreCase(id.toLowerCase())) {
					if (rs.getBoolean("finished"))
						return true;
				}
			}
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
			}
		}
		return false;
	}

	public void claimDivision(HumanEntity p, int div) {
		String table = plugin.getConfig().getString("tableName").replaceAll("%previous_season%",
				String.valueOf(plugin.getConfig().getInt("currentSeason") - 1));
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getSQLConnection();
			ps = conn.prepareStatement("UPDATE " + table
					+ " SET finished=?, claimedMasters=?, claimedDiamond=?, claimedGold=?, claimedIron=?, claimedStone=?, claimedWood=? WHERE id='"
					+ p.getUniqueId().toString() + "';");
			ps.setBoolean(1, true);
			switch (div) {
			case 1:
				ps.setBoolean(2, true);
				ps.setBoolean(3, false);
				ps.setBoolean(4, false);
				ps.setBoolean(5, false);
				ps.setBoolean(6, false);
				ps.setBoolean(7, false);
				break;
			case 2:
				ps.setBoolean(2, false);
				ps.setBoolean(3, true);
				ps.setBoolean(4, false);
				ps.setBoolean(5, false);
				ps.setBoolean(6, false);
				ps.setBoolean(7, false);
				break;
			case 3:
				ps.setBoolean(2, false);
				ps.setBoolean(3, false);
				ps.setBoolean(4, true);
				ps.setBoolean(5, false);
				ps.setBoolean(6, false);
				ps.setBoolean(7, false);
				break;
			case 4:
				ps.setBoolean(2, false);
				ps.setBoolean(3, false);
				ps.setBoolean(4, false);
				ps.setBoolean(5, true);
				ps.setBoolean(6, false);
				ps.setBoolean(7, false);
				break;
			case 5:
				ps.setBoolean(2, false);
				ps.setBoolean(3, false);
				ps.setBoolean(4, false);
				ps.setBoolean(5, false);
				ps.setBoolean(6, true);
				ps.setBoolean(7, false);
				break;
			case 6:
				ps.setBoolean(2, false);
				ps.setBoolean(3, false);
				ps.setBoolean(4, false);
				ps.setBoolean(5, false);
				ps.setBoolean(6, false);
				ps.setBoolean(7, true);
				break;
			default:
				System.out.println("Invalid division " + div + ". Use 1-6 for masters-wood.");
			}
			ps.executeUpdate();
			return;
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
			}
		}
		return;
	}

	public void close(PreparedStatement ps, ResultSet rs) {
		try {
			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();
		} catch (SQLException ex) {
			Error.close(plugin, ex);
		}
	}
}
