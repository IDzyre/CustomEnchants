package me.Dzyre.customEnchants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class inventories implements Listener{
	public static Inventory anvil = Bukkit.createInventory(null, 9, ChatColor.GOLD + "" + ChatColor.BOLD + "Combine");
	
	public void createAnvilInventory() {
		ItemStack fill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		anvil.setItem(0, fill);
		anvil.setItem(1, fill);
		anvil.setItem(2, null);
		anvil.setItem(3, fill);
		anvil.setItem(4, null);
		ItemStack combine = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
		ItemMeta meta = combine.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Combine");
		combine.setItemMeta(meta);
		anvil.setItem(5, combine);
		anvil.setItem(6, null);
		anvil.setItem(7, fill);
		anvil.setItem(8, fill);
	}

	@EventHandler
	public void removeFromGrindstone(InventoryClickEvent event) {
		if (event.getClickedInventory() instanceof GrindstoneInventory) {
			if (event.getCurrentItem().hasItemMeta()) {
				if (event.getCurrentItem().getItemMeta().hasLore()) {
					event.getWhoClicked().sendMessage("test1");
					ItemMeta meta = event.getCurrentItem().getItemMeta();
					List<String> temp = new ArrayList<String>();
					meta.setLore(temp);
					event.getCurrentItem().setItemMeta(meta);
				}
			}
		}
	}

	@EventHandler
	public void enchanting(EnchantItemEvent event) {
		if (event.getExpLevelCost() < 30) {
			return;
		}
		ItemStack item = event.getItem();
		if ((event.getItem().getType() == Material.DIAMOND_AXE) || (event.getItem().getType() == Material.GOLDEN_AXE)
				|| (event.getItem().getType() == Material.IRON_AXE) || (event.getItem().getType() == Material.STONE_AXE)
				|| (event.getItem().getType() == Material.WOODEN_AXE)) {
			Random rand = new Random();
			int upperbound = 5;
			int int_random = rand.nextInt(upperbound);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			Player player = event.getEnchanter();
			switch (int_random) {
			case 1:
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "TreeFeller");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.TREEFELLER, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been Enchanted with the power to remove whole trees with one swing!");
				return;
			default:
				player.sendMessage("Enchantment Failed, Try Again");

				player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 10, 10);

				return;
			}
		}

		else if ((event.getItem().getType() == Material.BOW)) {
			Random rand = new Random();
			int upperbound = 7;
			int int_random = rand.nextInt(upperbound);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			Player player = event.getEnchanter();
			switch (int_random) {
			case 1:

				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "EnderPort");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.ENDERPORT, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been Enchanted with the power to teleport with a bow");
				return;
			case 2:
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Multishots");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.MULTISHOTS, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been Enchanted with the power to shoot three arrows at once");
				return;
			default:
				player.sendMessage("Enchantment Failed, Try Again");

				player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 10, 10);

				return;
			}
		}

		else if ((event.getItem().getType() == Material.DIAMOND_SWORD)
				|| (event.getItem().getType() == Material.GOLDEN_SWORD)
				|| (event.getItem().getType() == Material.IRON_SWORD)
				|| (event.getItem().getType() == Material.STONE_SWORD)
				|| (event.getItem().getType() == Material.WOODEN_SWORD)) {
			Player player = event.getEnchanter();
			Random rand = new Random();
			int upperbound = 7;
			int int_random = rand.nextInt(upperbound);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			switch (int_random) {
			case 1:
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "ExpBoost");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.EXPBOOST, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been Enchanted with the power to INCREASE exp gain!");
				return;

			case 2:

				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "ExpBoost");
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Lifesteal");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.EXPBOOST, 1);
				item.addUnsafeEnchantment(CustomEnchants.LIFESTEAL, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been Enchanted with the power to INCREASE exp gain, AND a chance to heal when you deal damage!");
				return;
			default:
				player.sendMessage("Enchantment Failed, Try Again");

				player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 10, 10);

				return;

			}

		}

		else if (((item.getType().equals(Material.DIAMOND_HELMET)) || (item.getType().equals(Material.IRON_HELMET))
				|| (item.getType().equals(Material.GOLDEN_HELMET)) || (item.getType().equals(Material.LEATHER_HELMET))
				|| (item.getType().equals(Material.TURTLE_HELMET)))) {

			Random rand = new Random();
			int upperbound = 8;
			int int_random = rand.nextInt(upperbound);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			Player player = event.getEnchanter();
			switch (int_random) {
			case 1:

				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "MineLight");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.MINELIGHT, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been Enchanted with Increased vision in caves!");

				return;
			case 2:
				Player player2 = event.getEnchanter();

				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "MineLight");
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "HealthBoost");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.HEALTHBOOST, 1);
				item.addUnsafeEnchantment(CustomEnchants.MINELIGHT, 1);
				player2.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been Enchanted with increased health, and increased vision in caves!");
				return;
			default:
				player.sendMessage("Enchantment Failed, Try Again");

				player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 10, 10);

				return;

			}
		} else if (((item.getType() == (Material.DIAMOND_BOOTS)) || (item.getType().equals(Material.DIAMOND_LEGGINGS))
				|| (item.getType().equals(Material.DIAMOND_CHESTPLATE)) || (item.getType().equals(Material.IRON_BOOTS))
				|| (item.getType().equals(Material.IRON_LEGGINGS)) || (item.getType().equals(Material.IRON_CHESTPLATE))
				|| (item.getType().equals(Material.GOLDEN_BOOTS)) || (item.getType().equals(Material.GOLDEN_LEGGINGS))
				|| (item.getType().equals(Material.GOLDEN_CHESTPLATE))
				|| (item.getType().equals(Material.LEATHER_BOOTS)) || (item.getType().equals(Material.LEATHER_LEGGINGS))
				|| (item.getType().equals(Material.LEATHER_CHESTPLATE)))) {
			Player player = event.getEnchanter();
			Random rand = new Random();
			int upperbound = 6;
			int int_random = rand.nextInt(upperbound);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			switch (int_random) {
			case 1:

				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "HealthBoost");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.HEALTHBOOST, 1);
				player.sendMessage(
						ChatColor.RED + "" + ChatColor.BOLD + "Your item has been Enchanted with increased health!");
				return;
			default:
				player.sendMessage("Enchantment Failed, Try Again");

				player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 10, 10);

				return;
			}
		} else if (((item.getType() == (Material.DIAMOND_PICKAXE)) || (item.getType().equals(Material.IRON_PICKAXE))
				|| (item.getType().equals(Material.GOLDEN_PICKAXE))
				|| (item.getType().equals(Material.WOODEN_PICKAXE)))) {
			Player player = event.getEnchanter();
			Random rand = new Random();
			int upperbound = 27;
			int int_random = rand.nextInt(upperbound);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			switch (int_random) {
			case 1:
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "VeinMiner");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.VEINMINER, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been Enchanted with the ability to mine all of the same type of ores around the one you mine!");
				return;
			case 2:
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Detonate");
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "VeinMiner");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.VEINMINER, 1);
				item.addUnsafeEnchantment(CustomEnchants.DETONATE, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been Enchanted with the ability to mine all of the same type of ores around the one you mine, and the ability to mine more stone around the block you broke!");
				return;
			case 3:
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Detonate");
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "VeinMiner");
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "ExpBoost");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.VEINMINER, 1);
				item.addUnsafeEnchantment(CustomEnchants.DETONATE, 1);
				item.addUnsafeEnchantment(CustomEnchants.EXPBOOST, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been Enchanted with the ability to mine all of the same type of ores around the one you mine,\n The ability to mine more stone around the block you broke, \n And increased EXP from mining!");
				return;
			case 4:
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Telepathy");
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "VeinMiner");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.VEINMINER, 1);
				item.addUnsafeEnchantment(CustomEnchants.TELEPATHY, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been enchanted with Telepathy, all items you mine will go directly into your inventory, \n And with the ability to mine all of the same type of ores around the one you mine!");
				return;

			case 5:
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Telepathy");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.TELEPATHY, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been enchanted with Telepathy, all items you mine will go directly into your inventory!");
				return;
			case 6:
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Telepathy");
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "AutoSmelt");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.AUTOSMELT, 1);
				item.addUnsafeEnchantment(CustomEnchants.TELEPATHY, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been enchanted with Telepathy, all items you mine will go directly into your inventory, \n And with the the ability to instantly smelt ores you mine!");
				return;

			case 7:
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "AutoSmelt");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.AUTOSMELT, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been enchanted with the power to instantly smelt ores you mine!");
				return;
			case 8:
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Telepathy");
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "AutoSmelt");
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "ExpBoost");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.AUTOSMELT, 1);
				item.addUnsafeEnchantment(CustomEnchants.TELEPATHY, 1);
				item.addUnsafeEnchantment(CustomEnchants.EXPBOOST, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been enchanted with Telepathy, all items you mine will go directly into your inventory, \n With Encreased exp gain while mining, \n And with the the ability to instantly smelt ores you mine!");
				return;

			case 9:
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "AutoSmelt");
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "ExpBoost");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.AUTOSMELT, 1);
				item.addUnsafeEnchantment(CustomEnchants.EXPBOOST, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been enchanted with Encreased exp gain while mining, \n And with the the ability to instantly smelt ores you mine!");
				return;
			default:
				player.sendMessage("Enchantment Failed, Try Again");

				player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 10, 10);

				return;
			}
		} else if (((item.getType() == (Material.DIAMOND_SHOVEL)) || (item.getType().equals(Material.IRON_SHOVEL))
				|| (item.getType().equals(Material.GOLDEN_SHOVEL))
				|| (item.getType().equals(Material.WOODEN_SHOVEL)))) {
			Player player = event.getEnchanter();
			Random rand = new Random();
			int upperbound = 4;
			int int_random = rand.nextInt(upperbound);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			switch (int_random) {
			case 1:
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "VeinMiner");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(CustomEnchants.VEINMINER, 1);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "Your item has been Enchanted with the ability to mine all of the same type of ores around the one you mine!");
				return;
			default:
				player.sendMessage("Enchantment Failed, Try Again");

				player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 10, 10);

				return;
			}
		}

		Player player = event.getEnchanter();
		player.sendMessage("Enchantment Failed, Try Again");
		player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 10, 10);
		return;
	}


	
	@EventHandler
	public void giveAnvilItem(InventoryClickEvent event) {
		if ((event.getInventory().equals(anvil))) {
			Player player = (Player) event.getWhoClicked();
			if (event.getCurrentItem() != null) {
				if (event.getCurrentItem().equals(event.getInventory().getItem(6))) {
					if (player.getLevel() < 30) {
						event.setCancelled(true);
						player.sendMessage("You need 30 levels to do this");
						return;
					}

				}

				if (event.getInventory().equals(anvil) && event.getCurrentItem().equals(event.getInventory().getItem(6))
						&& player.getLevel() > 30) {
					player.setLevel(player.getLevel() - 15);
					player.sendMessage("Successfully Combined");
					player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 10, 10);
					event.getInventory().setItem(2, null);
					event.getInventory().setItem(4, null);
				}

				if (event.getCurrentItem() != null
						&& event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
					event.setCancelled(true);
					return;
				}
				if (event.getCurrentItem() != null
						&& event.getCurrentItem().getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if ((event.getInventory().equals(anvil))) {
			if (event.getCurrentItem() == null) {
				return;
			}
			if(!(event.getCurrentItem().getType().equals(Material.GREEN_STAINED_GLASS_PANE) 
					|| event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE))){
				if(event.getSlot() != 6)
				event.getInventory().setItem(6, null);
			}
			if(!event.getCurrentItem().getType().equals(Material.GREEN_STAINED_GLASS_PANE)){
				return;
			}
			Player player = (Player) event.getWhoClicked();

			if (event.getInventory().getItem(2) != null && event.getInventory().getItem(4) != null) {
				ItemStack item1 = event.getInventory().getItem(2);
				ItemStack item2 = event.getInventory().getItem(4);
				if (item1.getType() != item2.getType()) {
					player.sendMessage("Please use the same item");
					return;
				}
				ItemStack result = new ItemStack(event.getInventory().getItem(2));
				if (item2.hasItemMeta()) {
					result = addEnchants(item1, item2, result);
					result.addUnsafeEnchantments(item2.getEnchantments());
					event.getInventory().setItem(6, result);

					return;
				}
			}

		}
	}

	@EventHandler
	public void closeAnvil(InventoryCloseEvent event) {
		if (event.getInventory().equals(anvil)) {
			ItemStack item1 = event.getInventory().getItem(2);
			ItemStack item2 = event.getInventory().getItem(4);
			if (item1 != null) {
				Player player = (Player) event.getPlayer();
				player.getInventory().addItem(item1);
			}
			if (item2 != null) {
				Player player = (Player) event.getPlayer();
				player.getInventory().addItem(item2);
			}
			createAnvilInventory();
		}
	}

	public ItemStack addEnchants(ItemStack item1, ItemStack item2, ItemStack item) {

		List<String> newLore = new ArrayList<String>();
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		if (item1.getItemMeta().hasLore()) {
			lore = item1.getItemMeta().getLore();
		}
		if (item2.getItemMeta().hasLore()) {
			List<String> Lore1 = item2.getItemMeta().getLore();
			for (String Lore : Lore1) {
				if (!(lore.contains(Lore))) {
					lore.add(Lore);
				}
			}
		}

		for (String Lore : lore) {
			if (Lore.contains("Tree")) {
				newLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "TreeFeller");

			}
			if (Lore.contains("Telepathy")) {
				newLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Telepathy");

			}
			if (Lore.contains("Detonate")) {

				newLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Detonate");

			}
			if (Lore.contains("AutoSmelt")) {
				newLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "AutoSmelt");

			}
			if (Lore.contains("Exp")) {
				newLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "ExpBoost");

			}
			if (Lore.contains("Life")) {
				newLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "LifeSteal");

			}
			if (Lore.contains("Health")) {
				newLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "HealthBoost");

			}
			if (Lore.contains("MineLight")) {
				newLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "MineLight");

			}
			if (Lore.contains("Vein")) {
				newLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Vein Miner");

			}
			if (Lore.contains("Beheading")) {
				newLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Beheading");

			}
		}

		meta.setLore(newLore);
		item.setItemMeta(meta);
		return item;

	}

}