package me.arman.claimrewards.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.arman.claimrewards.Main;

public class ClaimRewards implements CommandExecutor {

	private Main plugin;

	public ClaimRewards(Main instance) {
		plugin = instance;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("claimrewards")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(
						ChatColor.translateAlternateColorCodes('&', "&cYou must be a player to run this command!"));
				return true;
			}
			Player p = (Player) sender;
			if (!p.hasPermission("claimrewards.use")) {
				p.sendMessage(
						ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.noPerms")));
				return true;
			}
			plugin.getGui().openGui(p);
		}

		return false;
	}

}
