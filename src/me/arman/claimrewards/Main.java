package me.arman.claimrewards;

import org.bukkit.plugin.java.JavaPlugin;

import me.arman.claimrewards.commands.ClaimRewards;
import me.arman.claimrewards.gui.ClaimGui;
import me.arman.claimrewards.sqlite.Database;
import me.arman.claimrewards.sqlite.SQLite;

public class Main extends JavaPlugin {

	private Database db;
	private ClaimGui gui;

	@Override
	public void onEnable() {
		this.db = new SQLite(this);
		this.db.load();
		this.gui = new ClaimGui(this);
		this.saveDefaultConfig();
		this.getCommand("claimrewards").setExecutor(new ClaimRewards(this));
		this.getServer().getPluginManager().registerEvents(new ClaimGui(this), this);
	}

	@Override
	public void onDisable() {
	}

	public Database getRDatabase() {
		return this.db;
	}

	public ClaimGui getGui() {
		return this.gui;
	}

}
