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
		this.getServer().getPluginManager().registerEvents(this, new pickaxes());
		this.getServer().getPluginManager().registerEvents(this, new weapons());
		this.getServer().getPluginManager().registerEvents(this, new other());
		this.getServer().getPluginManager().registerEvents(this, new inventories());
		
		CustomEnchants.register();
		createAnvilInventory();
		this.getCommand("enchants").setExecutor(new commands());
		this.getCommand("telepathy").setExecutor(new commands());
		this.getCommand("autosmelt").setExecutor(new commands());
		this.getCommand("detonate").setExecutor(new commands());
		this.getCommand("minelight").setExecutor(new commands());
		this.getCommand("veinmine").setExecutor(new commands());
		this.getCommand("treefeller").setExecutor(new commands());
		this.getCommand("enderport").setExecutor(new commands());
		this.getCommand("lifesteal").setExecutor(new commands());
		this.getCommand("instantgrow").setExecutor(new commands());
		this.getCommand("cultivate").setExecutor(new commands());
		this.getCommand("multishot").setExecutor(new commands());
		this.getCommand("expboost").setExecutor(new commands());
		this.getCommand("beheading").setExecutor(new commands());
		this.getCommand("healthboost").setExecutor(new commands());
		this.getCommand("bunnyhop").setExecutor(new commands());
	}

	@Override
	public void onDisable() {

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
	public void stopJoshy(BlockPlaceEvent event) {
		if (event.getBlock().getType().equals(Material.DIAMOND_ORE)
				|| event.getBlock().getType().equals(Material.EMERALD_ORE)) {
			stopJosh.put(event.getBlock().getLocation(), event.getPlayer().getName());
//			event.getPlayer().sendMessage("PLACE BLUCK");
		}
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


}
