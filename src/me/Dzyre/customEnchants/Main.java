package me.Dzyre.customEnchants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World.Environment;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.Ageable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

@SuppressWarnings("deprecation")
public class Main extends JavaPlugin implements Listener, CommandExecutor {
	public Inventory anvil = Bukkit.createInventory(null, 9, ChatColor.GOLD + "" + ChatColor.BOLD + "Combine");
	public static Map<Location, String> stopJosh = new HashMap<Location, String>();

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		CustomEnchants.register();
		createAnvilInventory();
	}

	@Override
	public void onDisable() {

	}

	@EventHandler
	public void setFly(PlayerMoveEvent e) {
		if (e.getPlayer().getInventory().getBoots() != null && e.getPlayer().getInventory().getBoots().hasItemMeta()) {
			if (e.getPlayer().getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.BUNNYHOP)) {
				if (e.getPlayer().getLocation().getBlock().getRelative(0, -1, 0).getType() != Material.AIR) {
					e.getPlayer().setAllowFlight(true);
				}
			}
		} else if (e.getPlayer().getAllowFlight() && e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
			e.getPlayer().setAllowFlight(false);
		}
	}

	@EventHandler
	public void removeFallDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			if (event.getCause().equals(DamageCause.FALL)) {
				if (((Player) event.getEntity()).getInventory().getBoots() != null
						&& ((Player) event.getEntity()).getInventory().getBoots().hasItemMeta()) {
					if (((Player) event.getEntity()).getInventory().getBoots().getItemMeta()
							.hasEnchant(CustomEnchants.BUNNYHOP)) {
						if(!((Player) event.getEntity()).getAllowFlight())
							event.setCancelled(true);

					}
				}
			}
		}

	}

	@EventHandler
	public void doubleJump(PlayerToggleFlightEvent event) {
		if (event.getPlayer().getInventory().getBoots() != null
				&& event.getPlayer().getInventory().getBoots().hasItemMeta()) {
			if (event.getPlayer().getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.BUNNYHOP)) {
				if (!(event.getPlayer().getGameMode() == GameMode.CREATIVE
						|| event.getPlayer().getGameMode() == GameMode.SPECTATOR)) {
					Player p = event.getPlayer();
					event.setCancelled(true);
					p.setFlying(false);
					p.setAllowFlight(false);
					event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1);
					event.getPlayer().getWorld().spawnParticle(Particle.LAVA, event.getPlayer().getLocation(), 25);
					event.getPlayer().setVelocity(p.getLocation().getDirection().setY(1.4));
				}
			}
		}
	}

	public void createAnvilInventory() {
		ItemStack fill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		anvil.setItem(0, fill);
		anvil.setItem(1, fill);
		anvil.setItem(2, null);
		anvil.setItem(3, fill);
		anvil.setItem(4, null);
		ItemStack combine = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		ItemMeta meta = combine.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Combine");
		combine.setItemMeta(meta);
		anvil.setItem(5, combine);
		anvil.setItem(6, null);
		anvil.setItem(7, fill);
		anvil.setItem(8, fill);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equals("anvil")) {
			Player player = (Player) sender;
			if (anvil.getViewers().isEmpty()) {
				player.openInventory(anvil);
				return true;
			} else
				player.sendMessage("Please wait for " + anvil.getViewers().get(0).getName());
			return true;
		}

		if (label.equalsIgnoreCase("enchants")) {
			if (!(sender instanceof Player)) {
				return true;
			}
			Player player = (Player) sender;
			player.sendMessage(ChatColor.UNDERLINE + "" + ChatColor.GOLD + "Telepathy: " + ChatColor.GREEN
					+ "Mined items go directly into your inventory\n \n" + ChatColor.GOLD + "Detonate: "
					+ ChatColor.GREEN + " Mines a 3x3x1 area around the block you broke(Only breaks stone)\n \n"
					+ ChatColor.GOLD + "AutoSmelt: " + ChatColor.GREEN
					+ " When mining Iron or Gold ore, the ingot drops instead of the ore\n \n" + ChatColor.GOLD
					+ "InstantGrow:  " + ChatColor.GREEN
					+ "Right click farmland under wheat seeds to instantly grow them, at the cost of durability\n \n"
					+ ChatColor.GOLD + "Cultitvator:  " + ChatColor.GREEN
					+ "Makes a 4x4 plot of farmland with water block in the middle(if the conditions are met)\n \n"
					+ ChatColor.GOLD + "TreeFeller: " + ChatColor.GREEN
					+ "Mining a log will break all logs in the tree (that touch a previously broken log)"
					+ ChatColor.RED + " JUNGLE LOGS EXCEPTED\n \n" + ChatColor.GOLD + "MultiShot: " + ChatColor.GREEN
					+ "Shoots 3 arrows in a vertical line(Costs 3 arrows)\n \n" + ChatColor.GOLD + "EnderPort: "
					+ ChatColor.GREEN + " Shooting a bow shoots an enderpearl instead(Costs 1 enderpearl)\n \n"
					+ ChatColor.GOLD + "MineLight: " + ChatColor.GREEN
					+ "Gives night vision when in the dark, and spawns torches at your feet when the light level gets too low\n \n"
					+ ChatColor.GOLD + "VeinMiner: " + ChatColor.GREEN
					+ "Mines all same-type blocks that connect with the original block\n \n" + ChatColor.GOLD
					+ "Exp Boost: " + ChatColor.GREEN + "Increases the amount of exp you gain \n \n" + ChatColor.GOLD
					+ "HealthBoost: " + ChatColor.GREEN + "Increases your health (STACKABLE) \n \n" + ChatColor.GOLD
					+ "Lifesteal: " + ChatColor.GREEN + "Chance to gain health from attacking \n \n" + ChatColor.GOLD
					+ "Beheading: " + ChatColor.GREEN + "Chance to get a player head from killing them");
			return true;
		}
		if (sender.hasPermission("custom.enchants.give")) {
			if (label.equalsIgnoreCase("explevels")) {

				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;

				player.sendMessage("" + player.getExp());
				return true;
			}
			if (label.equalsIgnoreCase("telepathy")) {

				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;

				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.TELEPATHY)) {
					player.sendMessage(ChatColor.RED + "You already have that enchant!");
					return true;
				}
				ItemStack item = player.getInventory().getItemInMainHand();
				item.addUnsafeEnchantment(CustomEnchants.TELEPATHY, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Telepathy I");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;
			}
			if (label.equalsIgnoreCase("beheading")) {

				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;

				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.BEHEADING)) {
					player.sendMessage(ChatColor.RED + "You already have that enchant!");
					return true;
				}
				ItemStack item = player.getInventory().getItemInMainHand();
				item.addUnsafeEnchantment(CustomEnchants.BEHEADING, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Beheading I");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;
			}
			if (label.equalsIgnoreCase("ExpBoost")) {

				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;

				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.EXPBOOST)) {
					player.sendMessage(ChatColor.RED + "You already have that enchant!");
					return true;
				}
				ItemStack item = player.getInventory().getItemInMainHand();
				item.addUnsafeEnchantment(CustomEnchants.EXPBOOST, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Exp Boost");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;
			}
			if (label.equalsIgnoreCase("instantgrow")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.INSTANTGROW)) {
					player.sendMessage(ChatColor.RED + "You already have that enchant!");
					return true;
				}
				ItemStack item = player.getInventory().getItemInMainHand();
				item.addUnsafeEnchantment(CustomEnchants.INSTANTGROW, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "InstantGrow I");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;
			}
			if (label.equalsIgnoreCase("veinmine")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.VEINMINER)) {
					player.sendMessage(ChatColor.RED + "You already have that enchant!");
					return true;
				}
				ItemStack item = player.getInventory().getItemInMainHand();
				item.addUnsafeEnchantment(CustomEnchants.VEINMINER, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Vein Miner");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;
			}
			if (label.equalsIgnoreCase("SilkSpawners")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;
				player.sendMessage("This enchant has not been created");
//			if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.SILKSPAWNERS)) {
//				player.sendMessage(ChatColor.RED + "You already have that enchant!");
//				return true;
//			}
//			ItemStack item = player.getInventory().getItemInMainHand();
//			item.addUnsafeEnchantment(CustomEnchants.SILKSPAWNERS, 1);
//			ItemMeta meta = item.getItemMeta();
//			List<String> lore = new ArrayList<String>();
//			lore.add(ChatColor.GOLD + "" + ChatColor.BOLD  + "SilkTouch II");
//			if (meta.hasLore()) {
//				for (String l : meta.getLore())
//					lore.add(l);
//			}
//			meta.setLore(lore);
//			item.setItemMeta(meta);
//			return true;
			}
			if (label.equalsIgnoreCase("cultivate")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;
				ItemStack item = player.getInventory().getItemInMainHand();
				if (!(item.getType().name().contains("HOE"))) {
					player.sendMessage("You cannot enchant this item with that enchant");
					return true;
				}
				item.addUnsafeEnchantment(CustomEnchants.CULTIVATOR, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GRAY + "Cultivator");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				player.getInventory().addItem(item);
				return true;
			}
			if (label.equalsIgnoreCase("detonate")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.DETONATE)) {
					player.sendMessage(ChatColor.RED + "You already have that enchant!");
					return true;
				}
				ItemStack item = player.getInventory().getItemInMainHand();
				item.addUnsafeEnchantment(CustomEnchants.DETONATE, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Detonate I");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;
			}
			if (label.equalsIgnoreCase("autosmelt")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.AUTOSMELT)) {
					player.sendMessage(ChatColor.RED + "You already have that enchant!");
					return true;
				}
				ItemStack item = (player.getInventory().getItemInMainHand());
				item.addUnsafeEnchantment(CustomEnchants.AUTOSMELT, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "AutoSmelt");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;
			}
			if (label.equalsIgnoreCase("treefeller")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.TREEFELLER)) {
					player.sendMessage(ChatColor.RED + "You already have that enchant!");
					return true;
				}
				ItemStack item = (player.getInventory().getItemInMainHand());
				item.addUnsafeEnchantment(CustomEnchants.TREEFELLER, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "TreeFeller");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;
			}
			if (label.equalsIgnoreCase("bunnyhop")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.BUNNYHOP)) {
					player.sendMessage(ChatColor.RED + "You already have that enchant!");
					return true;
				}
				ItemStack item = (player.getInventory().getItemInMainHand());
				item.addUnsafeEnchantment(CustomEnchants.BUNNYHOP, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Bunnyhop");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;
			}

			if (label.equalsIgnoreCase("swordeffects")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.SWORDEFFECTS)) {
					player.sendMessage(ChatColor.RED + "You already have that enchant!");
					return true;
				}
				ItemStack item = (player.getInventory().getItemInMainHand());
				item.addUnsafeEnchantment(CustomEnchants.SWORDEFFECTS, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "SwordEffects");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;
			}
			if (label.equalsIgnoreCase("lifesteal")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.LIFESTEAL)) {
					player.sendMessage(ChatColor.RED + "You already have that enchant!");
					return true;
				}
				ItemStack item = (player.getInventory().getItemInMainHand());
				item.addUnsafeEnchantment(CustomEnchants.LIFESTEAL, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "LifeSteal");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;
			}
			if (label.equalsIgnoreCase("minelight")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.MINELIGHT)) {
					player.sendMessage(ChatColor.RED + "You already have that enchant!");
					return true;
				}
				ItemStack item = (player.getInventory().getItemInMainHand());
				item.addUnsafeEnchantment(CustomEnchants.MINELIGHT, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "MineLight");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;
			}
			if (label.equalsIgnoreCase("entityshooter")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.ENTITYSHOOTER)) {
					player.sendMessage(ChatColor.RED + "You already have that enchant!");
					return true;
				}
				ItemStack item = (player.getInventory().getItemInMainHand());
				item.addUnsafeEnchantment(CustomEnchants.ENTITYSHOOTER, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "EntityShooter");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;
			}
			if (label.equalsIgnoreCase("place")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				Player player = (Player) sender;
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.HEALTHBOOST)) {
					player.sendMessage(ChatColor.RED + "You already have that enchant!");
					return true;
				}
				ItemStack item = (player.getInventory().getItemInMainHand());
				item.addUnsafeEnchantment(CustomEnchants.HEALTHBOOST, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "HealthBoost");
				if (meta.hasLore()) {
					for (String l : meta.getLore())
						lore.add(l);
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;

			}
		}
		return false;
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
			if (event.getCursor() == null) {
				event.setCancelled(true);
				return;
			}
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
					player.getInventory().addItem(event.getCurrentItem());
					player.closeInventory();
				}

				if (event.getCurrentItem() != null
						&& event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
					event.setCancelled(true);
					return;
				}
				if (event.getCurrentItem() != null
						&& event.getCurrentItem().getType().equals(Material.RED_STAINED_GLASS_PANE)) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	public void onClick(InventoryDragEvent event) {
		if ((event.getInventory().equals(anvil))) {
			if (event.getOldCursor() == null) {
				event.setCancelled(true);
				return;
			}
			Player player = (Player) event.getWhoClicked();

			if (event.getInventory().getItem(2) != null && event.getInventorySlots().contains(4)) {
				ItemStack item1 = event.getInventory().getItem(2);
				ItemStack item2 = event.getOldCursor();
				if (item1.getType() != item2.getType()) {
					player.sendMessage("Please use the same item");
					return;
				}
				ItemStack result = new ItemStack(event.getInventory().getItem(2));
				if (item2.hasItemMeta()) {
					result = addEnchants(item1, item2, result);
					result.addUnsafeEnchantments(event.getOldCursor().getEnchantments());
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

	@EventHandler
	public void entityShooter(EntityShootBowEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();

		if (!player.getInventory().getItemInMainHand().hasItemMeta()) {
			return;
		}
		if (!player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.ENTITYSHOOTER))
			return;
		if (!player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
			return;
		}

		Random rand = new Random();
		int upperBound = 5;
		int myNum = rand.nextInt(upperBound);
		if (myNum == 4) {
			event.setCancelled(true);
			Entity myEnt2 = player.getWorld().spawn(player.getEyeLocation(), Cat.class);
			myEnt2.setVelocity(event.getProjectile().getVelocity().multiply(1.1));
			return;
		}
		switch (myNum) {
		case 0:
			Snowball snow = player.launchProjectile(Snowball.class, event.getProjectile().getVelocity());
			Entity myEnt = player.getWorld().spawn(player.getLocation(), Creeper.class);
			((Creeper) myEnt).setPowered(true);
			((Creeper) myEnt).ignite();
			event.setProjectile(snow);
			event.getProjectile().addPassenger(myEnt);
			return;

		case 1:
			event.setCancelled(true);
			Entity myEnt1 = player.getWorld().spawn(player.getEyeLocation(), Chicken.class);
			myEnt1.setVelocity(event.getProjectile().getVelocity().multiply(1));
			return;

		case 3:
			event.setCancelled(true);
			Snowball snow2 = player.launchProjectile(Snowball.class, event.getProjectile().getVelocity().multiply(12));
			player.launchProjectile(snow2.getClass(), event.getProjectile().getVelocity()).addPassenger(player);
			player.sendMessage(ChatColor.GOLD + "WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
		case 4:
//			event.setProjectile(snow);
			Entity myEnt2 = player.getWorld().spawn(player.getLocation(), Cat.class);
			event.setProjectile(myEnt2);
//			event.getProjectile().addPassenger(myEnt2);
			return;
		case 2:
			Snowball snow1 = player.launchProjectile(Snowball.class, event.getProjectile().getVelocity());
			event.setProjectile(snow1);
			Entity myEnt3 = player.getWorld().spawn(player.getLocation(), TNTPrimed.class);
			event.getProjectile().addPassenger(myEnt3);
			return;
		}

	}

	@EventHandler
	public void healthBoost(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		int count = -1;
		if (player.getInventory().getBoots() != null
				&& player.getInventory().getBoots().getItemMeta().hasEnchant(CustomEnchants.HEALTHBOOST)) {
			count++;
		}
		if (player.getInventory().getHelmet() != null
				&& player.getInventory().getHelmet().getItemMeta().hasEnchant(CustomEnchants.HEALTHBOOST)) {
			count++;

		}
		if (player.getInventory().getChestplate() != null
				&& player.getInventory().getChestplate().getItemMeta().hasEnchant(CustomEnchants.HEALTHBOOST)) {
			count++;

		}
		if (player.getInventory().getLeggings() != null
				&& player.getInventory().getLeggings().getItemMeta().hasEnchant(CustomEnchants.HEALTHBOOST)) {
			count++;

		}
		if (count == -1)
			player.removePotionEffect(PotionEffectType.HEALTH_BOOST);

		if (count >= 3)
			count = 4;
		if (count < 0)
			return;
		if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {

			if (player.getPotionEffect(PotionEffectType.HEALTH_BOOST).getAmplifier() == count)
				return;
			if (player.getPotionEffect(PotionEffectType.HEALTH_BOOST).getAmplifier() > count)
				player.removePotionEffect(PotionEffectType.HEALTH_BOOST);

			else if (player.getPotionEffect(PotionEffectType.HEALTH_BOOST).getAmplifier() < count)
				player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
		}
		player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, count));
		return;
	}

	@EventHandler
	public void expBoost(PlayerExpChangeEvent event) {
		Player player = event.getPlayer();
		if (player.getInventory().getItemInMainHand().equals(null))
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
			return;
		}
		if ((event.getPlayer().getGameMode().equals(GameMode.CREATIVE)
				|| (event.getPlayer().getGameMode().equals(GameMode.SPECTATOR))))
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.EXPBOOST))
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
			return;
		}
		int xp = event.getAmount();
		event.setAmount(xp * 3);
	}

	@EventHandler
	public void getLight(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (player.getInventory().getHelmet() == null) {
			return;
		}
		if (!event.getPlayer().getInventory().getHelmet().hasItemMeta()) {
			return;
		}
		if (event.getPlayer().getWorld().getEnvironment().equals(Environment.NETHER)) {
			return;
		}
		if (!event.getPlayer().getInventory().getHelmet().getItemMeta().hasEnchant(CustomEnchants.MINELIGHT))
			return;
		if (player.getLocation().getBlock().getRelative(0, 1, 0).getLightFromSky() > 10) {
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
			return;
		}
		if (!event.getPlayer().getInventory().getHelmet().getItemMeta().hasLore()) {
			return;
		}
		if (player.getLocation().getBlock().getRelative(0, 1, 0).getLightFromBlocks() > 5)
			
			return;
		if (player.getLocation().getBlock().getRelative(0, -1, 0).getType().equals(Material.AIR)) {
			return;
		}
		if ((player.getLocation().getBlock().getRelative(0, -1, 0).getType().equals(Material.WATER))
				|| (player.getLocation().getBlock().getType().equals(Material.WATER))) {
			return;
		}
		if ((event.getPlayer().getGameMode().equals(GameMode.CREATIVE)
				|| (event.getPlayer().getGameMode().equals(GameMode.SPECTATOR))))
			return;
		
		player.getLocation().getBlock().setType(Material.TORCH);
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 600, 100));
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getPlayer().getInventory().getItemInMainHand() == null)
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta())
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.TELEPATHY))
			return;
//		if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.VEINMINER))
//			return;
		if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.AUTOSMELT)
				&& (event.getBlock().getType() == Material.IRON_ORE || event.getBlock().getType() == Material.GOLD_ORE))
			return;
		if ((event.getPlayer().getGameMode().equals(GameMode.CREATIVE)
				|| (event.getPlayer().getGameMode().equals(GameMode.SPECTATOR))))
			return;
		if (event.getPlayer().getInventory().firstEmpty() == -1)
			return;
		if (event.getBlock().getState() instanceof Container)
			return;
		event.setDropItems(false);
		Player player = event.getPlayer();
		Block block = event.getBlock();
		Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
		if (drops.isEmpty())
			return;
		player.getInventory().addItem(drops.iterator().next());

	}

	@EventHandler
	public void plantSeeds(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			return;
		}
		if (!(event.getClickedBlock().getType() == Material.FARMLAND)) {
			return;
		}
		if (event.getPlayer().getInventory().getItemInMainHand() == null) {
			return;
		}
		if (!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
			return;
		}
		if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.INSTANTGROW))
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
			return;
		}
		if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_HOE)) {
			Block block = event.getClickedBlock().getRelative(0, 1, 0);
			Ageable age = (Ageable) event.getClickedBlock().getRelative(0, 1, 0).getBlockData();
			BlockState bs = block.getState();
			age.setAge(age.getMaximumAge());
			bs.setBlockData(age);
			bs.update();

			ItemStack itemStack = new ItemStack(player.getInventory().getItemInMainHand());
			Damageable itemDamage = (Damageable) itemStack.getItemMeta();
			ItemMeta itemMeta = itemStack.getItemMeta();
			if (itemMeta instanceof Damageable) {
				int damage = itemDamage.getDamage();
				int subdmg = 0;
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
					if (player.getInventory().getItemInMainHand().getItemMeta()
							.getEnchantLevel(Enchantment.DURABILITY) == 1) {
						subdmg = 1;
					} else if (player.getInventory().getItemInMainHand().getItemMeta()
							.getEnchantLevel(Enchantment.DURABILITY) == 2) {
						subdmg = 2;
					} else if (player.getInventory().getItemInMainHand().getItemMeta()
							.getEnchantLevel(Enchantment.DURABILITY) == 3) {
						subdmg = 3;
					}
				}
				((Damageable) itemMeta).setDamage(damage + 4 - subdmg);
			}
			player.getInventory().getItemInMainHand().setItemMeta(itemMeta);

		}
	}

	@EventHandler
	public void stopJoshy(BlockPlaceEvent event) {
		if (event.getBlock().getType().equals(Material.DIAMOND_ORE)
				|| event.getBlock().getType().equals(Material.EMERALD_ORE)) {
			stopJosh.put(event.getBlock().getLocation(), event.getPlayer().getName());
//			event.getPlayer().sendMessage("PLACE BLUCK");
		}
	}

	public boolean isPlaced(Location loc, Player player) {

		if (stopJosh.get(loc) == null) {
//			player.sendMessage("is null");
			return false;
		}
		if (stopJosh.get(loc) != null) {
//			player.sendMessage("is nut null");
			stopJosh.remove(loc);
			return true;
		}

		return true;
	}

	public static HashMap<Block, Integer> nearBlocks = new HashMap<Block, Integer>();

	public int checkNearBlocks(Block block, ItemStack pickaxe, Block originalBlock, Player player) {

		Damageable itemDamage = (Damageable) pickaxe.getItemMeta();
		ItemMeta itemMeta = pickaxe.getItemMeta();
		if (itemDamage.getDamage() > pickaxe.getType().getMaxDurability()) {
			return 0;
		}
		if (block.getType() != originalBlock.getType()) {
			return 0;
		}
		if (block.getType() == Material.AIR) {
			return 0;
		}
		if (nearBlocks.get(block) != null) {
			return 0;
		}
		nearBlocks.put(block, 1);

		checkNearBlocks(block.getRelative(BlockFace.UP), pickaxe, originalBlock, player);

		checkNearBlocks(block.getRelative(BlockFace.DOWN), pickaxe, originalBlock, player);

		checkNearBlocks(block.getRelative(BlockFace.NORTH), pickaxe, originalBlock, player);

		checkNearBlocks(block.getRelative(BlockFace.SOUTH), pickaxe, originalBlock, player);

		checkNearBlocks(block.getRelative(BlockFace.EAST), pickaxe, originalBlock, player);

		checkNearBlocks(block.getRelative(BlockFace.WEST), pickaxe, originalBlock, player);

		block.breakNaturally(pickaxe);
		itemDamage = (Damageable) pickaxe.getItemMeta();
		itemMeta = pickaxe.getItemMeta();

		int damage = itemDamage.getDamage();
		int subdmg = 0;
		if (itemMeta instanceof Damageable) {
			if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
				if (player.getInventory().getItemInMainHand().getItemMeta()
						.getEnchantLevel(Enchantment.DURABILITY) == 3) {
					subdmg = 1;
				}
			}
		}
		((Damageable) itemMeta).setDamage((damage + (1 - subdmg)));
		player.getInventory().getItemInMainHand().setItemMeta(itemMeta);
		return 0;
	}

	@EventHandler
	public void veinMiner(BlockBreakEvent event) {

		if (event.getPlayer().getInventory().getItemInMainHand() == null)
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta())
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.VEINMINER))
			return;
		if (isPlaced(event.getBlock().getLocation(), event.getPlayer())) {
			return;
		}
		if ((event.getBlock().getType().equals(Material.STONE)) || (event.getBlock().getType().equals(Material.GRASS))
				|| (event.getBlock().getType().equals(Material.DIRT))
				|| (event.getBlock().getType().equals(Material.ICE))
				|| (event.getBlock().getType().equals(Material.BLUE_ICE))
				|| event.getBlock().getType().equals(Material.PACKED_ICE)
				|| event.getBlock().getType().equals(Material.COBBLESTONE)
				|| event.getBlock().getType().equals(Material.GLASS)
				|| event.getBlock().getType().equals(Material.GRAY_STAINED_GLASS)
				|| event.getBlock().getType().equals(Material.NETHERRACK))
			return;
		if ((event.getPlayer().getGameMode().equals(GameMode.CREATIVE)
				|| (event.getPlayer().getGameMode().equals(GameMode.SPECTATOR))))
			return;
		if (event.getBlock().getState() instanceof Container)
			return;
		if (!(event.getBlock().getType().equals(Material.IRON_ORE)
				|| (event.getBlock().getType().equals(Material.GOLD_ORE))
				|| (event.getBlock().getType().equals(Material.COAL_ORE))
				|| (event.getBlock().getType().equals(Material.DIAMOND_ORE))
				|| (event.getBlock().getType().equals(Material.EMERALD_ORE))
				|| (event.getBlock().getType().equals(Material.LAPIS_ORE))
				|| (event.getBlock().getType().equals(Material.NETHER_QUARTZ_ORE))
				|| (event.getBlock().getType().equals(Material.COPPER_ORE))))
			return;
		event.setCancelled(true);
		Player player = (Player) event.getPlayer();
		Block block = event.getBlock();

		checkNearBlocks(block, event.getPlayer().getInventory().getItemInMainHand(), block, player);

		block.breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
		int count = nearBlocks.size();
		nearBlocks.clear();

		ItemStack itemStack = new ItemStack(player.getInventory().getItemInMainHand());
		Damageable itemDamage = (Damageable) itemStack.getItemMeta();
		ItemMeta itemMeta = itemStack.getItemMeta();
		int damage = itemDamage.getDamage();
		int subdmg = 1;

		if (itemMeta instanceof Damageable) {

			if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
				if (player.getInventory().getItemInMainHand().getItemMeta()
						.getEnchantLevel(Enchantment.DURABILITY) == 1) {
					subdmg = 2;
				} else if (player.getInventory().getItemInMainHand().getItemMeta()
						.getEnchantLevel(Enchantment.DURABILITY) == 2) {
					subdmg = 3;
				} else if (player.getInventory().getItemInMainHand().getItemMeta()
						.getEnchantLevel(Enchantment.DURABILITY) == 3) {
					subdmg = 4;
				}
			}
		}
		((Damageable) itemMeta).setDamage((damage + (count / subdmg)));
		player.getInventory().getItemInMainHand().setItemMeta(itemMeta);
		return;

	}

	public static String getCardinalDirection(Player player) {
		double rotation = (player.getLocation().getYaw() - 180) % 360;
		if (rotation < 0) {
			rotation += 360.0;
		}
		if (0 <= rotation && rotation < 44) {
			return "N";

		} else if (44 <= rotation && rotation < 130) {
			return "E";
		} else if (130 <= rotation && rotation < 222.5) {
			return "S";
		} else if (222.5 <= rotation && rotation < 305) {
			return "W";
		} else if (305 <= rotation && rotation < 360.0) {
			return "N";
		} else {
			return null;
		}
	}

	public BlockFace getBlockFace(Player player) {
		List<Block> lastTwoTargetBlocks = player.getLastTwoTargetBlocks(null, 100);
		if (lastTwoTargetBlocks.size() != 2 || !lastTwoTargetBlocks.get(1).getType().isOccluding())
			return null;
		Block targetBlock = lastTwoTargetBlocks.get(1);
		Block adjacentBlock = lastTwoTargetBlocks.get(0);
		return targetBlock.getFace(adjacentBlock);
	}

	public void squareDetonate(String face, Block block, int number, int count, ItemStack pickaxe) {
		if (count >= 3) {
			return;

		}

		if (block != null) {
			switch (face) {
			case "West":
				if (block.getType() != Material.STONE && number < 3) {
					squareDetonate("West", block.getRelative(0, 0, 1), number + 1, count, pickaxe);
				}
				if (number < 3) {
					squareDetonate("West", block.getRelative(BlockFace.SOUTH), number + 1, count, pickaxe);
				}
				if (number >= 3) {
					squareDetonate("West", block.getRelative(0, -1, -2), 1, count + 1, pickaxe);
				}

				break;
			case "East":
				if (block.getType() != Material.STONE && number < 3) {
					squareDetonate("East", block.getRelative(0, 0, -1), number + 1, count, pickaxe);
				}
				if (number < 3) {
					squareDetonate("East", block.getRelative(BlockFace.NORTH), number + 1, count, pickaxe);
				}
				if (number >= 3) {
					squareDetonate("East", block.getRelative(0, -1, 2), 1, count + 1, pickaxe);
				}
				break;
			case "South":
				if (block.getType() != Material.STONE && number < 3) {
					squareDetonate("South", block.getRelative(1, 0, 0), number + 1, count, pickaxe);
				}
				if (number < 3) {
					squareDetonate("South", block.getRelative(BlockFace.EAST), number + 1, count, pickaxe);
				}
				if (number >= 3) {
					squareDetonate("South", block.getRelative(-2, -1, 0), 1, count + 1, pickaxe);
				}
				break;
			case "North":
				if (block.getType() != Material.STONE && number < 3) {
					squareDetonate("North", block.getRelative(-1, 0, 0), number + 1, count, pickaxe);
				}
				if (number < 3) {
					squareDetonate("North", block.getRelative(BlockFace.WEST), number + 1, count, pickaxe);
				}
				if (number >= 3) {
					squareDetonate("North", block.getRelative(2, -1, 0), 1, count + 1, pickaxe);
				}
				break;
			case "Up":
				if (block.getType() != Material.STONE && number < 3) {
					squareDetonate("Up", block.getRelative(0, 0, 1), number + 1, count, pickaxe);
				}
				if (number < 3) {
					squareDetonate("Up", block.getRelative(BlockFace.SOUTH), number + 1, count, pickaxe);
				}
				if (number >= 3) {
					squareDetonate("Up", block.getRelative(-1, 0, -2), 1, count + 1, pickaxe);
				}
				break;
			case "Down":
				if (number < 3) {
					squareDetonate("Down", block.getRelative(BlockFace.SOUTH), number++, count, pickaxe);
				}
				if (number >= 3) {
					squareDetonate("Down", block.getRelative(-1, 0, -2), 1, count++, pickaxe);
				}
				break;

			}
			if (block.getType() != Material.STONE) {
				return;
			}
			nearBlocks.put(block, 1);
			block.breakNaturally(pickaxe);
		}
	}

	@EventHandler
	public void detonate(BlockBreakEvent event) {

		if (event.getPlayer().getInventory().getItemInMainHand() == null)
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta())
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.DETONATE))
			return;
		if ((event.getPlayer().getGameMode().equals(GameMode.CREATIVE)
				|| (event.getPlayer().getGameMode().equals(GameMode.SPECTATOR))))
			return;
		if (event.getBlock().getState() instanceof Container)
			return;
		if (!(event.getBlock().getType().equals(Material.STONE)))
			return;
		event.setCancelled(true);
		Player player = event.getPlayer();
		ItemStack itemStack = new ItemStack(player.getInventory().getItemInMainHand());
		Damageable itemDamage = (Damageable) itemStack.getItemMeta();
		ItemMeta itemMeta = itemStack.getItemMeta();
		switch (getBlockFace(player).name()) {

		case "EAST":
			squareDetonate("East", event.getBlock().getRelative(0, 1, 1), 1, 0,
					player.getInventory().getItemInMainHand());
			break;
		case "WEST":
			squareDetonate("West", event.getBlock().getRelative(0, 1, -1), 1, 0,
					player.getInventory().getItemInMainHand());
			break;
		case "NORTH":
			squareDetonate("North", event.getBlock().getRelative(1, 1, 0), 1, 0,
					player.getInventory().getItemInMainHand());
			break;
		case "SOUTH":
			squareDetonate("South", event.getBlock().getRelative(-1, 1, 0), 1, 0,
					player.getInventory().getItemInMainHand());
			break;
		case "UP":
		case "DOWN":
			squareDetonate("Up", event.getBlock().getRelative(1, 0, -1), 1, 0,
					player.getInventory().getItemInMainHand());
			break;
		default:
			break;
		}

		int damage = itemDamage.getDamage();
		int subdmg = 1;
		int count = nearBlocks.size();
		nearBlocks.clear();
		if (itemMeta instanceof Damageable) {

			if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
				if (player.getInventory().getItemInMainHand().getItemMeta()
						.getEnchantLevel(Enchantment.DURABILITY) == 1) {
					subdmg = 2;
				} else if (player.getInventory().getItemInMainHand().getItemMeta()
						.getEnchantLevel(Enchantment.DURABILITY) == 2) {
					subdmg = 3;
				} else if (player.getInventory().getItemInMainHand().getItemMeta()
						.getEnchantLevel(Enchantment.DURABILITY) == 3) {
					subdmg = 4;
				}
			}
		}
		((Damageable) itemMeta).setDamage((damage + (count / subdmg)));
		player.getInventory().getItemInMainHand().setItemMeta(itemMeta);
		return;
	}

	@EventHandler
	public void smeltOre(BlockBreakEvent event) {
		Player player = (Player) event.getPlayer();
		if (event.getPlayer().getInventory().getItemInMainHand() == null)
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta())
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.AUTOSMELT))
			return;
		if ((event.getPlayer().getGameMode().equals(GameMode.CREATIVE)
				|| (event.getPlayer().getGameMode().equals(GameMode.SPECTATOR))))
			return;
		if (event.getBlock().getState() instanceof Container)
			return;
		if (!((event.getBlock().getType().equals(Material.IRON_ORE))
				|| (event.getBlock().getType().equals(Material.GOLD_ORE)))) {
			return;
		}

		event.setDropItems(false);
		Block block = event.getBlock();
		if ((!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.TELEPATHY))
				|| (event.getPlayer().getInventory().firstEmpty() == -1)) {
			if ((event.getBlock().getType().equals(Material.IRON_ORE))) {
				String s = (event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand()).toArray()[0]
						.toString().split(" ")[2]).charAt(0) + "";
				event.getPlayer().getWorld().dropItemNaturally(block.getLocation(),
						(new ItemStack(Material.IRON_INGOT, Integer.parseInt(s))));

				return;
			}
			if (event.getBlock().getType().equals(Material.GOLD_ORE)) {
				String s = (event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand()).toArray()[0]
						.toString().split(" ")[2]).charAt(0) + "";
				event.getPlayer().getWorld().dropItemNaturally(block.getLocation(),
						(new ItemStack(Material.GOLD_INGOT, Integer.parseInt(s))));
				return;
			}
		} else if (event.getBlock().getType().equals(Material.GOLD_ORE)) {
			String s = (event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand()).toArray()[0]
					.toString().split(" ")[2]).charAt(0) + "";
			player.getInventory().addItem((new ItemStack(Material.GOLD_INGOT, Integer.parseInt(s))));
			return;
		} else if ((event.getBlock().getType().equals(Material.IRON_ORE))) {
			String s = (event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand()).toArray()[0]
					.toString().split(" ")[2]).charAt(0) + "";
			player.getInventory().addItem((new ItemStack(Material.IRON_INGOT, Integer.parseInt(s))));
			return;
		}

	}

	@EventHandler
	public void treeFeller(BlockBreakEvent event) {
		Player player = (Player) event.getPlayer();
		if (event.getPlayer().getInventory().getItemInMainHand() == null)
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta())
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.TREEFELLER))
			return;
		if ((event.getPlayer().getGameMode().equals(GameMode.CREATIVE)
				|| (event.getPlayer().getGameMode().equals(GameMode.SPECTATOR))))
			return;
		if (!((event.getBlock().getType().equals(Material.ACACIA_LOG))
				|| (event.getBlock().getType().equals(Material.SPRUCE_LOG))
				|| (event.getBlock().getType().equals(Material.OAK_LOG))
				|| (event.getBlock().getType().equals(Material.DARK_OAK_LOG))
				|| (event.getBlock().getType().equals(Material.BIRCH_LOG)))) {
			return;
		}
		if ((!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.TELEPATHY))
				|| (event.getPlayer().getInventory().firstEmpty() == -1)) {
			checkNearBlocks(event.getBlock(), event.getPlayer().getInventory().getItemInMainHand(), event.getBlock(),
					player);
		} else {
			checkNearBlocks(event.getBlock(), event.getPlayer().getInventory().getItemInMainHand(), event.getBlock(),
					player);

		}
		int count = nearBlocks.size();
		nearBlocks.clear();
		ItemStack itemStack = new ItemStack(player.getInventory().getItemInMainHand());
		Damageable itemDamage = (Damageable) itemStack.getItemMeta();
		ItemMeta itemMeta = itemStack.getItemMeta();
		int damage = itemDamage.getDamage();
		int subdmg = 1;
		if (itemMeta instanceof Damageable) {

			if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
				if (player.getInventory().getItemInMainHand().getItemMeta()
						.getEnchantLevel(Enchantment.DURABILITY) == 1) {
					subdmg = 2;
				} else if (player.getInventory().getItemInMainHand().getItemMeta()
						.getEnchantLevel(Enchantment.DURABILITY) == 2) {
					subdmg = 3;
				} else if (player.getInventory().getItemInMainHand().getItemMeta()
						.getEnchantLevel(Enchantment.DURABILITY) == 3) {
					subdmg = 4;
				}
			}
		}
		((Damageable) itemMeta).setDamage((damage + (count / subdmg)));
		player.getInventory().getItemInMainHand().setItemMeta(itemMeta);
	}

	@EventHandler
	public static void lifeSteal(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player))
			return;
		Player player = (Player) event.getDamager();
		if (player.getInventory().getItemInMainHand() == null)
			return;
		if (!player.getInventory().getItemInMainHand().hasItemMeta())
			return;
		if (!player.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.LIFESTEAL))
			return;
		if (((player.getPlayer().getGameMode().equals(GameMode.SPECTATOR)))
				|| (player.getPlayer().getGameMode().equals(GameMode.CREATIVE)))
			return;
		Random rand = new Random();
		int upperbound = 9;
		int int_random = rand.nextInt(upperbound);
		switch (int_random) {
		case 1:
			if (player.getHealth() < player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue() - 1.0)
				player.setHealth(player.getHealth() + 0.5);
			else
				player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
			return;
		case 2:
			if (player.getHealth() < player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue() - 2.0)
				player.setHealth(player.getHealth() + 2.0);
			else
				player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
			return;
		case 3:
			if (player.getHealth() < player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue() - 3.0)
				player.setHealth(player.getHealth() + 3.0);
			else
				player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
			return;

		default:
			return;
		}
	}

	@EventHandler
	public static void beheading(EntityDeathEvent event) {
		if (!(event.getEntity().getKiller() instanceof Player))
			return;
		if (event.getEntity().getType() == EntityType.PLAYER) {
			Player player = (Player) event.getEntity().getKiller();
			if (player.getInventory().getItemInMainHand() == null)
				return;
			if (!player.getInventory().getItemInMainHand().hasItemMeta())
				return;
			if (!player.getPlayer().getInventory().getItemInMainHand().getItemMeta()
					.hasEnchant(CustomEnchants.BEHEADING))
				return;
			if (((player.getPlayer().getGameMode().equals(GameMode.SPECTATOR)))) {
				return;
			}
			Random rand = new Random();
			int upperbound = 20;
			int int_random = rand.nextInt(upperbound);
			switch (int_random) {
			case 10:
				Player player2 = (Player) event.getEntity();
				ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
				SkullMeta meta = (SkullMeta) head.getItemMeta();
				meta.setOwner(player2.getName());
				meta.setDisplayName(ChatColor.RED + player2.getName() + "'s Head");
				head.setItemMeta(meta);
				event.getDrops().add(head);
			}
		}

		return;
	}

	@EventHandler
	public static void swordEffects(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player))
			return;
		if (!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getDamager();
		if (player.getInventory().getItemInMainHand() == null)
			return;
		if (!player.getInventory().getItemInMainHand().hasItemMeta())
			return;
		if (!player.getPlayer().getInventory().getItemInMainHand().getItemMeta()
				.hasEnchant(CustomEnchants.SWORDEFFECTS))
			return;
		if (((player.getPlayer().getGameMode().equals(GameMode.SPECTATOR))))
			return;
		Player player2 = (Player) event.getEntity();
		Random rand = new Random();
		int upperbound = 35;
		int int_random = rand.nextInt(upperbound);
		switch (int_random) {
		case 0:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 100));
			return;
		case 1:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 5));
			return;
		case 3:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 5));
			return;
		case 4:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 1, 100));
			return;
		case 5:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100, 2));
			return;
		case 6:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 100));
			return;
		case 7:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 100));
			return;
		case 8:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 1, 1));
			return;
		case 9:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 100));
			return;
		case 10:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 100));
			return;
		case 11:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 100));
			return;
		case 12:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 5));
			return;
		case 13:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 100));
			return;
		case 14:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 100));
			return;
		case 15:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 2));
			return;
		case 16:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
			return;
		case 17:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 5));
			return;
		case 18:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100, 5));
			return;
		case 19:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 100, 10));
			return;
		case 20:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100, 1));
			return;
		case 21:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 5));
			return;
		case 22:
			player2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100, 5));
			return;
		}

	}

	@EventHandler
	public void bowTeleport(EntityShootBowEvent event) {
		if (event.getEntity().getType().equals(EntityType.PLAYER)) {
			Player player = (Player) event.getEntity();
			Arrow arrow = (Arrow) event.getProjectile();
			if (player.getInventory().getItemInMainHand() == null) {
				return;
			}
			if (!player.getInventory().getItemInMainHand().hasItemMeta()) {
				return;
			}
			if (!player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.ENDERPORT))
				return;
			if (!player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
				return;
			}
			ItemStack itemStack = new ItemStack(player.getInventory().getItemInMainHand());
			Damageable itemDamage = (Damageable) itemStack.getItemMeta();
			ItemMeta itemMeta = itemStack.getItemMeta();
			int damage = itemDamage.getDamage();
			int subdmg = 1;
			if (itemMeta instanceof Damageable) {

				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
					if (player.getInventory().getItemInMainHand().getItemMeta()
							.getEnchantLevel(Enchantment.DURABILITY) == 1) {
						subdmg = 0;
					} else if (player.getInventory().getItemInMainHand().getItemMeta()
							.getEnchantLevel(Enchantment.DURABILITY) == 2) {
						subdmg = 1;
					} else if (player.getInventory().getItemInMainHand().getItemMeta()
							.getEnchantLevel(Enchantment.DURABILITY) == 3) {
						subdmg = 2;
					}
				}
			}

			int count = 0;
			for (ItemStack stack : player.getInventory().getContents()) {
				if (stack != null && stack.getType() == Material.ARROW) {
					count += stack.getAmount();
				}
			}

			if (!player.getInventory().containsAtLeast(new ItemStack(Material.ENDER_PEARL), 1)) {
				player.sendMessage("Not enough Ammo");
				return;
			}

			event.setCancelled(true);

			player.launchProjectile(EnderPearl.class).setVelocity(arrow.getVelocity());
			String cobblestone = "ENDER PEARL";
			player.getInventory().removeItem(new ItemStack(Material.matchMaterial(cobblestone), 1));
			player.getInventory().removeItem(new ItemStack(Material.ARROW, count - 1));
			player.getInventory().addItem(new ItemStack(Material.ARROW, count - 1));
			((Damageable) itemMeta).setDamage((damage + (3 - subdmg)));
			player.getInventory().getItemInMainHand().setItemMeta(itemMeta);
			return;

		}
	}

	@EventHandler
	public void arrowVolley(EntityShootBowEvent event) {
		if (event.getEntity().getType().equals(EntityType.PLAYER)) {
			Player player = (Player) event.getEntity();
			if (player.getInventory().getItemInMainHand() == null) {
				return;
			}
			if (!player.getInventory().getItemInMainHand().hasItemMeta()) {
				return;
			}
			if (!player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.MULTISHOTS))
				return;
			if (!player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
				return;
			}

			//
			ItemStack itemStack = new ItemStack(player.getInventory().getItemInMainHand());
			Damageable itemDamage = (Damageable) itemStack.getItemMeta();
			ItemMeta itemMeta = itemStack.getItemMeta();
			int damage = itemDamage.getDamage();
			int arrowAmount = 3;
			if (event.getBow().containsEnchantment(Enchantment.ARROW_INFINITE)) {
				arrowAmount = 0;
			}

			if (player.getInventory().containsAtLeast(new ItemStack(Material.ARROW), arrowAmount)) {
				String cobblestone = "ARROW";
				player.getInventory().removeItem(new ItemStack(Material.matchMaterial(cobblestone), 3));
				int subdmg = 1;
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
					if (player.getInventory().getItemInMainHand().getItemMeta()
							.getEnchantLevel(Enchantment.DURABILITY) == 1) {
						subdmg = 0;
					} else if (player.getInventory().getItemInMainHand().getItemMeta()
							.getEnchantLevel(Enchantment.DURABILITY) == 2) {
						subdmg = 1;
					} else if (player.getInventory().getItemInMainHand().getItemMeta()
							.getEnchantLevel(Enchantment.DURABILITY) == 3) {
						subdmg = 2;
					}
				}
				//
				Arrow arrow1 = player.launchProjectile(Arrow.class);
				Arrow arrow2 = player.launchProjectile(Arrow.class);
				Vector velocity = event.getProjectile().getVelocity();
				arrow1.setVelocity(velocity.add(new Vector(0, 0.1, 0)));
				arrow1.setShooter(player);
				arrow2.setVelocity(velocity.add(new Vector(0, .1, 0)));
				arrow2.setShooter(player);
				((Damageable) itemMeta).setDamage(damage + 4 - subdmg);
				player.getInventory().getItemInMainHand().setItemMeta(itemMeta);
			}

		}
	}

	@EventHandler
	public void hoeGround(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			return;
		}
		if (!(block.getType().equals(Material.DIRT) || (block.getType().equals(Material.GRASS_BLOCK)))) {
			;
			return;
		}
		if (!(event.getPlayer().getInventory().getItemInMainHand().hasItemMeta())) {
			return;
		}
		if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.CULTIVATOR)) {
			return;
		}
		Block block1 = block.getRelative(1, 0, 0);
		Block block2 = block.getRelative(-1, 0, 0);
		Block block3 = block.getRelative(0, 0, 1);
		Block block4 = block.getRelative(0, 0, -1);
		Block block5 = block.getRelative(1, 0, 1);
		Block block6 = block.getRelative(1, 0, -1);
		Block block7 = block.getRelative(-1, 0, 1);
		Block block8 = block.getRelative(-1, 0, -1);
		Block block9 = block.getRelative(2, 0, 0);
		Block block10 = block.getRelative(-2, 0, 0);
		Block block11 = block.getRelative(0, 0, 2);
		Block block12 = block.getRelative(0, 0, -2);
		Block block13 = block.getRelative(2, 0, 2);
		Block block14 = block.getRelative(2, 0, -2);
		Block block15 = block.getRelative(-2, 0, 2);
		Block block16 = block.getRelative(-2, 0, -2);
		Block block17 = block.getRelative(2, 0, 1);
		Block block18 = block.getRelative(2, 0, -1);
		Block block19 = block.getRelative(-2, 0, 1);
		Block block20 = block.getRelative(-2, 0, -1);
		Block block21 = block.getRelative(1, 0, 2);
		Block block22 = block.getRelative(1, 0, -2);
		Block block23 = block.getRelative(-1, 0, 2);
		Block block24 = block.getRelative(-1, 0, -2);

		if (!(block1.getType().equals(Material.DIRT) || (block1.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block2.getType().equals(Material.DIRT) || (block2.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block3.getType().equals(Material.DIRT) || (block3.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block4.getType().equals(Material.DIRT) || (block4.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block5.getType().equals(Material.DIRT) || (block5.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block6.getType().equals(Material.DIRT) || (block6.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block7.getType().equals(Material.DIRT) || (block7.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block8.getType().equals(Material.DIRT) || (block8.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block9.getType().equals(Material.DIRT) || (block9.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block10.getType().equals(Material.DIRT) || (block10.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block11.getType().equals(Material.DIRT) || (block11.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block12.getType().equals(Material.DIRT) || (block12.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block13.getType().equals(Material.DIRT) || (block13.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block14.getType().equals(Material.DIRT) || (block14.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block15.getType().equals(Material.DIRT) || (block15.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block16.getType().equals(Material.DIRT) || (block16.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block17.getType().equals(Material.DIRT) || (block17.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block18.getType().equals(Material.DIRT) || (block18.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block19.getType().equals(Material.DIRT) || (block19.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block20.getType().equals(Material.DIRT) || (block20.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block21.getType().equals(Material.DIRT) || (block21.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block22.getType().equals(Material.DIRT) || (block22.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block23.getType().equals(Material.DIRT) || (block23.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}
		if (!(block24.getType().equals(Material.DIRT) || (block24.getType().equals(Material.GRASS_BLOCK)))) {
			player.sendMessage(ChatColor.RED
					+ "There is not enough space here, Consider clearing out area, and changing any non-grass or non-dirt blocks to dirt");
			event.setCancelled(true);
			return;
		}

		block.setType(Material.WATER);
		block1.setType(Material.FARMLAND);
		block2.setType(Material.FARMLAND);
		block3.setType(Material.FARMLAND);
		block4.setType(Material.FARMLAND);
		block5.setType(Material.FARMLAND);
		block6.setType(Material.FARMLAND);
		block7.setType(Material.FARMLAND);
		block8.setType(Material.FARMLAND);
		block9.setType(Material.FARMLAND);
		block10.setType(Material.FARMLAND);
		block11.setType(Material.FARMLAND);
		block12.setType(Material.FARMLAND);
		block13.setType(Material.FARMLAND);
		block14.setType(Material.FARMLAND);
		block15.setType(Material.FARMLAND);
		block16.setType(Material.FARMLAND);
		block17.setType(Material.FARMLAND);
		block18.setType(Material.FARMLAND);
		block19.setType(Material.FARMLAND);
		block20.setType(Material.FARMLAND);
		block21.setType(Material.FARMLAND);
		block22.setType(Material.FARMLAND);
		block23.setType(Material.FARMLAND);
		block24.setType(Material.FARMLAND);

		ItemStack itemStack = new ItemStack(player.getInventory().getItemInMainHand());
		Damageable itemDamage = (Damageable) itemStack.getItemMeta();
		ItemMeta itemMeta = itemStack.getItemMeta();
		if (itemMeta instanceof Damageable) {
			int damage = itemDamage.getDamage();
			int subdmg = 0;
			if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
				if (player.getInventory().getItemInMainHand().getItemMeta()
						.getEnchantLevel(Enchantment.DURABILITY) == 1) {
					subdmg = 12;
				} else if (player.getInventory().getItemInMainHand().getItemMeta()
						.getEnchantLevel(Enchantment.DURABILITY) == 2) {
					subdmg = 16;
				} else if (player.getInventory().getItemInMainHand().getItemMeta()
						.getEnchantLevel(Enchantment.DURABILITY) == 3) {
					subdmg = 18;
				}
			}
			((Damageable) itemMeta).setDamage(damage + 24 - subdmg);
		}
		player.getInventory().getItemInMainHand().setItemMeta(itemMeta);

	}

	@EventHandler
	public void trySilkSpawners(BlockBreakEvent event) {
		if(!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))){
		return;
		}
		if (event.getBlock().getType().equals(Material.SPAWNER)) {
			event.setCancelled(true);
			ItemStack newSpawner = new ItemStack(Material.SPAWNER);
			ItemMeta meta = newSpawner.getItemMeta();
			CreatureSpawner cs = (CreatureSpawner) event.getBlock().getState();

			List<String> lore = new ArrayList<String>();
			lore.add(cs.getSpawnedType() + " Spawner");
			meta.setDisplayName(cs.getSpawnedType() + " Spawner");
			meta.setLore(lore);
			newSpawner.setItemMeta(meta);
			event.getBlock().setType(Material.AIR);
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), newSpawner);
			;
		}
	}

	@EventHandler
	public void trySetSpawner(BlockPlaceEvent event) {
		if (event.getBlock().getType().equals(Material.SPAWNER)) {

			event.getBlock().setType(Material.SPAWNER);
			CreatureSpawner cs = (CreatureSpawner) event.getBlock().getState();
			cs.setSpawnedType(EntityType.valueOf(
					event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().split(" ")[0]));
		}
	}

	@EventHandler
	public void setSpawnerType(PlayerInteractEvent event) {
		if (!(event.getItem().getType().toString().contains("SPAWN_EGG"))) {
			return;
		}
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if (event.getClickedBlock().getType() != Material.SPAWNER) {
				event.getPlayer().sendMessage("Spawn eggs can only be used to change spawner types");
				event.setCancelled(true);
				return;
			} else {
				CreatureSpawner cs = (CreatureSpawner) (event.getClickedBlock().getState());
				String type = event.getItem().getType().toString();
				String checkSecondWord = event.getItem().getType().toString().split("_")[1];
				if(checkSecondWord.equalsIgnoreCase( "SPAWN")) {
					cs.setSpawnedType(EntityType.valueOf(type.split("_")[0]));
				}else {
					String newType = type.split("_")[0] + "_" + type.split("_")[1];
					cs.setSpawnedType(EntityType.valueOf(newType));
				}
				
			}
		}
	}

}
