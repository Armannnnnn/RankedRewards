package me.arman.claimrewards.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import me.arman.claimrewards.Main;

public class ClaimGui implements Listener {

	private final Inventory inv;
	private Main plugin;

	public ClaimGui(Main instance) {
		plugin = instance;
		inv = Bukkit.createInventory(null, 54,
				ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("guiTitle")));
	}

	private ItemStack createItem(Material m, String name) {
		final ItemStack i = new ItemStack(m, 1);
		final ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(name);
		i.setItemMeta(meta);
		return i;
	}

	private ItemStack createItem(Material m, String name, List<String> lore) {
		final ItemStack i = new ItemStack(m, 1);
		final ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(name);
		List<String> finalLore = new ArrayList<String>();
		for (String loreLine : lore) {
			finalLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
		}
		meta.setLore(finalLore);
		i.setItemMeta(meta);
		return i;
	}

	private ItemStack createRedClay(String name, List<String> lore) {
		final ItemStack i = new ItemStack(Material.STAINED_CLAY, 1, (short) 6);
		final ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(name);
		List<String> finalLore = new ArrayList<String>();
		for (String loreLine : lore) {
			finalLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
		}
		meta.setLore(finalLore);
		i.setItemMeta(meta);
		return i;
	}

	private ItemStack createItem(Material m, String name, List<String> lore, boolean glow) {
		final ItemStack i = new ItemStack(m, 1);
		final ItemMeta meta = i.getItemMeta();
		meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.setDisplayName(name);
		List<String> finalLore = new ArrayList<String>();
		for (String loreLine : lore) {
			finalLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
		}
		finalLore.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.expiry")));
		meta.setLore(finalLore);
		i.setItemMeta(meta);
		return i;
	}

	public void openGui(final Player p) {
		int season = plugin.getConfig().getInt("currentSeason");
		int pos = plugin.getRDatabase().getPosition(p.getUniqueId().toString());
		if (season - 1 > 0) {
			inv.setItem(0, createItem(Material.EYE_OF_ENDER,
					ChatColor.translateAlternateColorCodes('&', "&3Season " + (season - 1) + " &7[Previous Season]")));
		} else {
			inv.setItem(0, createItem(Material.EYE_OF_ENDER,
					ChatColor.translateAlternateColorCodes('&', "&3Beta Season &7[Previous Season]")));
		}
		if ((pos >= 1 && pos <= 10) && !plugin.getRDatabase().claimedReward(p.getUniqueId().toString()))
			inv.setItem(2,
					createItem(Material.EMERALD_BLOCK,
							ChatColor.translateAlternateColorCodes('&',
									plugin.getConfig().getString("lores.mastersName")),
							plugin.getConfig().getStringList("lores.mastersLore"), true));
		else
			inv.setItem(2,
					createItem(Material.EMERALD_BLOCK,
							ChatColor.translateAlternateColorCodes('&',
									plugin.getConfig().getString("lores.mastersName")),
							plugin.getConfig().getStringList("lores.mastersLore")));

		if ((pos >= 11 && pos <= 2) && !plugin.getRDatabase().claimedReward(p.getUniqueId().toString()))
			inv.setItem(3,
					createItem(Material.DIAMOND_BLOCK,
							ChatColor.translateAlternateColorCodes('&',
									plugin.getConfig().getString("lores.diamondName")),
							plugin.getConfig().getStringList("lores.diamondLore"), true));
		else
			inv.setItem(3,
					createItem(Material.DIAMOND_BLOCK,
							ChatColor.translateAlternateColorCodes('&',
									plugin.getConfig().getString("lores.diamondName")),
							plugin.getConfig().getStringList("lores.diamondLore")));
		if ((pos >= 26 && pos <= 50) && !plugin.getRDatabase().claimedReward(p.getUniqueId().toString()))
			inv.setItem(4,
					createItem(Material.GOLD_BLOCK,
							ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.goldName")),
							plugin.getConfig().getStringList("lores.goldLore"), true));
		else
			inv.setItem(4,
					createItem(Material.GOLD_BLOCK,
							ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.goldName")),
							plugin.getConfig().getStringList("lores.goldLore")));
		if ((pos >= 51 && pos <= 75) && !plugin.getRDatabase().claimedReward(p.getUniqueId().toString()))
			inv.setItem(5,
					createItem(Material.IRON_BLOCK,
							ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.ironName")),
							plugin.getConfig().getStringList("lores.ironLore"), true));
		else
			inv.setItem(5,
					createItem(Material.IRON_BLOCK,
							ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.ironName")),
							plugin.getConfig().getStringList("lores.ironLore")));
		if ((pos >= 76 && pos <= 100) && !plugin.getRDatabase().claimedReward(p.getUniqueId().toString()))
			inv.setItem(6,
					createItem(Material.STONE,
							ChatColor.translateAlternateColorCodes('&',
									plugin.getConfig().getString("lores.stoneName")),
							plugin.getConfig().getStringList("lores.stoneLore"), true));
		else
			inv.setItem(6,
					createItem(Material.STONE,
							ChatColor.translateAlternateColorCodes('&',
									plugin.getConfig().getString("lores.stoneName")),
							plugin.getConfig().getStringList("lores.stoneLore")));

		if (pos >= 101 && !plugin.getRDatabase().claimedReward(p.getUniqueId().toString()))
			inv.setItem(7,
					createItem(Material.WOOD,
							ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.woodName")),
							plugin.getConfig().getStringList("lores.woodLore"), true));
		else
			inv.setItem(7,
					createItem(Material.WOOD,
							ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.woodName")),
							plugin.getConfig().getStringList("lores.woodLore")));

		inv.setItem(18, createItem(Material.EYE_OF_ENDER,
				ChatColor.translateAlternateColorCodes('&', "&3Season " + (season) + " &7[Current Season]")));
		inv.setItem(20,
				createItem(Material.EMERALD_BLOCK,
						ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.mastersName")),
						plugin.getConfig().getStringList("lores.mastersLore")));
		inv.setItem(21,
				createItem(Material.DIAMOND_BLOCK,
						ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.diamondName")),
						plugin.getConfig().getStringList("lores.diamondLore")));
		inv.setItem(22,
				createItem(Material.GOLD_BLOCK,
						ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.goldName")),
						plugin.getConfig().getStringList("lores.goldLore")));
		inv.setItem(23,
				createItem(Material.IRON_BLOCK,
						ChatColor.translateAlternateColorCodes('&', "&fIron Division &7[#51-#75]"),
						plugin.getConfig().getStringList("lores.ironLore")));
		inv.setItem(24,
				createItem(Material.STONE,
						ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.stoneName")),
						plugin.getConfig().getStringList("lores.stoneLore")));
		inv.setItem(25,
				createItem(Material.WOOD,
						ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.woodName")),
						plugin.getConfig().getStringList("lores.woodLore")));

		inv.setItem(36, createItem(Material.EYE_OF_ENDER,
				ChatColor.translateAlternateColorCodes('&', "&3Season " + (season + 1) + " &7[Next Season]")));
		inv.setItem(38,
				createRedClay(
						ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.mastersName")),
						plugin.getConfig().getStringList("lores.mastersLoreUnavailable")));
		inv.setItem(39,
				createRedClay(
						ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.diamondName")),
						plugin.getConfig().getStringList("lores.diamondLoreUnavailable")));
		inv.setItem(40,
				createRedClay(
						ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.goldName")),
						plugin.getConfig().getStringList("lores.goldLoreUnavailable")));
		inv.setItem(41,
				createRedClay(
						ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.ironName")),
						plugin.getConfig().getStringList("lores.ironLoreUnavailable")));
		inv.setItem(42,
				createRedClay(
						ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.stoneName")),
						plugin.getConfig().getStringList("lores.stoneLoreUnavailable")));
		inv.setItem(43,
				createRedClay(
						ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lores.woodName")),
						plugin.getConfig().getStringList("lores.woodLoreUnavailable")));
		p.openInventory(inv);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (plugin.getConfig().getString("guiTitle").contains(ChatColor.stripColor(e.getInventory().getTitle()))) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)
				return;
			int pos = plugin.getRDatabase().getPosition(e.getWhoClicked().getUniqueId().toString());
			if (pos < 1) {
				e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("messages.noRewards")));
				return;
			}
			if (plugin.getRDatabase().claimedReward(e.getWhoClicked().getUniqueId().toString())) {
				e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("messages.alreadyClaimed")));
				return;
			}
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			if (e.getSlot() == 2) {
				if (!(pos >= 1 && pos <= 10)) {
					e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
							plugin.getConfig().getString("messages.cannotClaim")));
					return;
				}
				for (String command : plugin.getConfig().getStringList("commands.masters")) {
					Bukkit.dispatchCommand(console, command.replaceAll("%player%", e.getWhoClicked().getName()));
				}
				plugin.getRDatabase().claimDivision(e.getWhoClicked(), 1);
				e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("messages.claimedRewards")));
				Firework fw = (Firework) e.getWhoClicked().getWorld().spawnEntity(e.getWhoClicked().getLocation(),
						EntityType.FIREWORK);
				FireworkMeta fwm = fw.getFireworkMeta();
				Random r = new Random();
				int rt = r.nextInt(4) + 1;
				Type type = Type.BALL;
				if (rt == 1)
					type = Type.BALL;
				if (rt == 2)
					type = Type.BALL_LARGE;
				if (rt == 3)
					type = Type.BURST;
				if (rt == 4)
					type = Type.CREEPER;
				if (rt == 5)
					type = Type.STAR;
				FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.GREEN)
						.withFade(Color.GREEN).with(type).trail(r.nextBoolean()).build();
				fwm.addEffect(effect);
				fwm.setPower(r.nextInt(2) + 1);
				fw.setFireworkMeta(fwm);
			} else if (e.getSlot() == 3) {
				if (!(pos >= 11 && pos <= 25)) {
					e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
							plugin.getConfig().getString("messages.cannotClaim")));
					return;
				}
				for (String command : plugin.getConfig().getStringList("commands.diamond")) {
					Bukkit.dispatchCommand(console, command.replaceAll("%player%", e.getWhoClicked().getName()));
				}
				plugin.getRDatabase().claimDivision(e.getWhoClicked(), 2);
				e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("messages.claimedRewards")));
				e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("messages.claimedRewards")));
				Firework fw = (Firework) e.getWhoClicked().getWorld().spawnEntity(e.getWhoClicked().getLocation(),
						EntityType.FIREWORK);
				FireworkMeta fwm = fw.getFireworkMeta();
				Random r = new Random();
				int rt = r.nextInt(4) + 1;
				Type type = Type.BALL;
				if (rt == 1)
					type = Type.BALL;
				if (rt == 2)
					type = Type.BALL_LARGE;
				if (rt == 3)
					type = Type.BURST;
				if (rt == 4)
					type = Type.CREEPER;
				if (rt == 5)
					type = Type.STAR;
				FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.AQUA)
						.withFade(Color.AQUA).with(type).trail(r.nextBoolean()).build();
				fwm.addEffect(effect);
				fwm.setPower(r.nextInt(2) + 1);
				fw.setFireworkMeta(fwm);

			} else if (e.getSlot() == 4) {
				if (!(pos >= 26 && pos <= 50)) {
					e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
							plugin.getConfig().getString("messages.cannotClaim")));
					return;
				}
				for (String command : plugin.getConfig().getStringList("commands.gold")) {
					Bukkit.dispatchCommand(console, command.replaceAll("%player%", e.getWhoClicked().getName()));
				}
				plugin.getRDatabase().claimDivision(e.getWhoClicked(), 3);
				e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("messages.claimedRewards")));
				e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("messages.claimedRewards")));
				Firework fw = (Firework) e.getWhoClicked().getWorld().spawnEntity(e.getWhoClicked().getLocation(),
						EntityType.FIREWORK);
				FireworkMeta fwm = fw.getFireworkMeta();
				Random r = new Random();
				int rt = r.nextInt(4) + 1;
				Type type = Type.BALL;
				if (rt == 1)
					type = Type.BALL;
				if (rt == 2)
					type = Type.BALL_LARGE;
				if (rt == 3)
					type = Type.BURST;
				if (rt == 4)
					type = Type.CREEPER;
				if (rt == 5)
					type = Type.STAR;
				FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.ORANGE)
						.withFade(Color.ORANGE).with(type).trail(r.nextBoolean()).build();
				fwm.addEffect(effect);
				fwm.setPower(r.nextInt(2) + 1);
				fw.setFireworkMeta(fwm);
			} else if (e.getSlot() == 5) {
				if (!(pos >= 51 && pos <= 75)) {
					e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
							plugin.getConfig().getString("messages.cannotClaim")));
					return;
				}
				for (String command : plugin.getConfig().getStringList("commands.iron")) {
					Bukkit.dispatchCommand(console, command.replaceAll("%player%", e.getWhoClicked().getName()));
				}
				plugin.getRDatabase().claimDivision(e.getWhoClicked(), 4);
				e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("messages.claimedRewards")));
				e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("messages.claimedRewards")));
				Firework fw = (Firework) e.getWhoClicked().getWorld().spawnEntity(e.getWhoClicked().getLocation(),
						EntityType.FIREWORK);
				FireworkMeta fwm = fw.getFireworkMeta();
				Random r = new Random();
				int rt = r.nextInt(4) + 1;
				Type type = Type.BALL;
				if (rt == 1)
					type = Type.BALL;
				if (rt == 2)
					type = Type.BALL_LARGE;
				if (rt == 3)
					type = Type.BURST;
				if (rt == 4)
					type = Type.CREEPER;
				if (rt == 5)
					type = Type.STAR;
				FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.WHITE)
						.withFade(Color.WHITE).with(type).trail(r.nextBoolean()).build();
				fwm.addEffect(effect);
				fwm.setPower(r.nextInt(2) + 1);
				fw.setFireworkMeta(fwm);
			} else if (e.getSlot() == 6) {
				if (!(pos >= 76 && pos <= 100)) {
					e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
							plugin.getConfig().getString("messages.cannotClaim")));
					return;
				}
				for (String command : plugin.getConfig().getStringList("commands.stone")) {
					Bukkit.dispatchCommand(console, command.replaceAll("%player%", e.getWhoClicked().getName()));
				}
				e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("messages.claimedRewards")));
				Firework fw = (Firework) e.getWhoClicked().getWorld().spawnEntity(e.getWhoClicked().getLocation(),
						EntityType.FIREWORK);
				FireworkMeta fwm = fw.getFireworkMeta();
				Random r = new Random();
				int rt = r.nextInt(4) + 1;
				Type type = Type.BALL;
				if (rt == 1)
					type = Type.BALL;
				if (rt == 2)
					type = Type.BALL_LARGE;
				if (rt == 3)
					type = Type.BURST;
				if (rt == 4)
					type = Type.CREEPER;
				if (rt == 5)
					type = Type.STAR;
				FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.SILVER)
						.withFade(Color.SILVER).with(type).trail(r.nextBoolean()).build();
				fwm.addEffect(effect);
				fwm.setPower(r.nextInt(2) + 1);
				fw.setFireworkMeta(fwm);
				plugin.getRDatabase().claimDivision(e.getWhoClicked(), 5);
				e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("messages.claimedRewards")));
			} else if (e.getSlot() == 7) {
				if (!(pos >= 101)) {
					e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
							plugin.getConfig().getString("messages.cannotClaim")));
					return;
				}
				for (String command : plugin.getConfig().getStringList("commands.wood")) {
					Bukkit.dispatchCommand(console, command.replaceAll("%player%", e.getWhoClicked().getName()));
				}
				e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("messages.claimedRewards")));
				Firework fw = (Firework) e.getWhoClicked().getWorld().spawnEntity(e.getWhoClicked().getLocation(),
						EntityType.FIREWORK);
				FireworkMeta fwm = fw.getFireworkMeta();
				Random r = new Random();
				int rt = r.nextInt(4) + 1;
				Type type = Type.BALL;
				if (rt == 1)
					type = Type.BALL;
				if (rt == 2)
					type = Type.BALL_LARGE;
				if (rt == 3)
					type = Type.BURST;
				if (rt == 4)
					type = Type.CREEPER;
				if (rt == 5)
					type = Type.STAR;
				FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.OLIVE)
						.withFade(Color.OLIVE).with(type).trail(r.nextBoolean()).build();
				fwm.addEffect(effect);
				fwm.setPower(r.nextInt(2) + 1);
				fw.setFireworkMeta(fwm);
				plugin.getRDatabase().claimDivision(e.getWhoClicked(), 6);
				e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("messages.claimedRewards")));
			}
			e.getWhoClicked().closeInventory();
		}
	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent e) {
		if (plugin.getConfig().getString("guiTitle").contains(ChatColor.stripColor(e.getInventory().getTitle()))) {
			e.setCancelled(true);
			if (e.getOldCursor() != null && e.getOldCursor().getType() != Material.AIR)
				e.getWhoClicked().closeInventory();
		}
	}

}
