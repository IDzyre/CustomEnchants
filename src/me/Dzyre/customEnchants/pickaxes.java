package me.Dzyre.customEnchants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class pickaxes implements Listener {
	public static Map<Location, String> stopJosh = new HashMap<Location, String>();

	public static HashMap<Block, Integer> nearBlocks = new HashMap<Block, Integer>();

	@EventHandler
	public void telepathy(BlockBreakEvent event) {
		if (event.getPlayer().getInventory().getItemInMainHand() == null)
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta())
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.TELEPATHY))
			return;
//		if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.VEINMINER))
//			return;
		if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.DETONATE))
			return;
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

	public static int checkNearBlocks(Block block, ItemStack pickaxe, Block originalBlock, Player player) {

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

		if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.AUTOSMELT)) {
			if (block.getType().equals(Material.GOLD_ORE)) {
				String s = (block.getDrops(player.getInventory().getItemInMainHand()).toArray()[0].toString()
						.split(" ")[2]).charAt(0) + "";
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.TELEPATHY)) {
					if (!(player.getInventory().firstEmpty() == -1)) {
						player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, Integer.parseInt(s)));
						block.getWorld().spawnParticle(Particle.BLOCK_DUST, block.getLocation(), 12,
								block.getBlockData());
						block.setType(Material.AIR);
					}
				} else {
					player.getWorld().dropItemNaturally(block.getLocation(),
							(new ItemStack(Material.GOLD_INGOT, Integer.parseInt(s))));
					block.getWorld().spawnParticle(Particle.BLOCK_DUST, block.getLocation(), 12, block.getBlockData());
					block.setType(Material.AIR);
				}
			} else if (block.getType().equals(Material.IRON_ORE)) {
				String s = (block.getDrops(player.getInventory().getItemInMainHand()).toArray()[0].toString()
						.split(" ")[2]).charAt(0) + "";
				if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.TELEPATHY)) {
					if (!(player.getInventory().firstEmpty() == -1)) {
						player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, Integer.parseInt(s)));
						block.getWorld().spawnParticle(Particle.BLOCK_DUST, block.getLocation(), 12,
								block.getBlockData());
						block.setType(Material.AIR);
					}
				} else {
					player.getWorld().dropItemNaturally(block.getLocation(),
							(new ItemStack(Material.IRON_INGOT, Integer.parseInt(s))));
					block.getWorld().spawnParticle(Particle.BLOCK_DUST, block.getLocation(), 12, block.getBlockData());
					block.setType(Material.AIR);
				}
			}
		} else if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.TELEPATHY)) {
			if ((player.getInventory().firstEmpty() != -1)) {
				player.getInventory()
						.addItem(block.getDrops(player.getInventory().getItemInMainHand()).iterator().next());
				block.getWorld().spawnParticle(Particle.BLOCK_DUST, block.getLocation(), 12, block.getBlockData());
				block.setType(Material.AIR);
			}
		} else {
			block.breakNaturally(pickaxe);
		}

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
		Block targetBlock = lastTwoTargetBlocks.get(1);
		Block adjacentBlock = lastTwoTargetBlocks.get(0);
		return targetBlock.getFace(adjacentBlock);
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
				|| (event.getBlock().getType().equals(Material.COPPER_ORE))
				|| (event.getBlock().getType().equals(Material.DEEPSLATE_IRON_ORE))
				|| (event.getBlock().getType().equals(Material.DEEPSLATE_GOLD_ORE))
				|| (event.getBlock().getType().equals(Material.DEEPSLATE_COAL_ORE))
				|| (event.getBlock().getType().equals(Material.DEEPSLATE_DIAMOND_ORE))
				|| (event.getBlock().getType().equals(Material.DEEPSLATE_EMERALD_ORE))
				|| (event.getBlock().getType().equals(Material.DEEPSLATE_LAPIS_ORE))
				|| (event.getBlock().getType().equals(Material.DEEPSLATE_COPPER_ORE))))
			return;
		event.setCancelled(true);
		Player player = (Player) event.getPlayer();
		Block block = event.getBlock();

		checkNearBlocks(block, event.getPlayer().getInventory().getItemInMainHand(), block, player);

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

	public void squareDetonate(String face, Block block, int number, int count, ItemStack pickaxe, Player player) {
		if (count >= 3) {
			return;

		}

		if (block != null) {
			switch (face) {
			case "West":
				if (block.getType() != Material.STONE && number < 3) {
					squareDetonate("West", block.getRelative(0, 0, 1), number + 1, count, pickaxe, player);
				}
				if (number < 3) {
					squareDetonate("West", block.getRelative(BlockFace.SOUTH), number + 1, count, pickaxe, player);
				}
				if (number >= 3) {
					squareDetonate("West", block.getRelative(0, -1, -2), 1, count + 1, pickaxe, player);
				}

				break;
			case "East":
				if (block.getType() != Material.STONE && number < 3) {
					squareDetonate("East", block.getRelative(0, 0, -1), number + 1, count, pickaxe, player);
				}
				if (number < 3) {
					squareDetonate("East", block.getRelative(BlockFace.NORTH), number + 1, count, pickaxe, player);
				}
				if (number >= 3) {
					squareDetonate("East", block.getRelative(0, -1, 2), 1, count + 1, pickaxe, player);
				}
				break;
			case "South":
				if (block.getType() != Material.STONE && number < 3) {
					squareDetonate("South", block.getRelative(1, 0, 0), number + 1, count, pickaxe, player);
				}
				if (number < 3) {
					squareDetonate("South", block.getRelative(BlockFace.EAST), number + 1, count, pickaxe, player);
				}
				if (number >= 3) {
					squareDetonate("South", block.getRelative(-2, -1, 0), 1, count + 1, pickaxe, player);
				}
				break;
			case "North":
				if (block.getType() != Material.STONE && number < 3) {
					squareDetonate("North", block.getRelative(-1, 0, 0), number + 1, count, pickaxe, player);
				}
				if (number < 3) {
					squareDetonate("North", block.getRelative(BlockFace.WEST), number + 1, count, pickaxe, player);
				}
				if (number >= 3) {
					squareDetonate("North", block.getRelative(2, -1, 0), 1, count + 1, pickaxe, player);
				}
				break;
			case "Up":
				if (block.getType() != Material.STONE && number < 3) {
					squareDetonate("Up", block.getRelative(0, 0, 1), number + 1, count, pickaxe, player);
				}
				if (number < 3) {
					squareDetonate("Up", block.getRelative(BlockFace.SOUTH), number + 1, count, pickaxe, player);
				}
				if (number >= 3) {
					squareDetonate("Up", block.getRelative(-1, 0, -2), 1, count + 1, pickaxe, player);
				}
				break;
			case "Down":
				if (number < 3) {
					squareDetonate("Down", block.getRelative(BlockFace.SOUTH), number++, count, pickaxe, player);
				}
				if (number >= 3) {
					squareDetonate("Down", block.getRelative(-1, 0, -2), 1, count++, pickaxe, player);
				}
				break;

			}
			if (block.getType() != Material.STONE) {
				return;
			}
			nearBlocks.put(block, 1);
			if (pickaxe.getItemMeta().hasEnchant(CustomEnchants.TELEPATHY)) {
				Collection<ItemStack> col = block.getDrops(pickaxe);
				if (!(player.getInventory().firstEmpty() == -1)) {
					player.getInventory().addItem(col.iterator().next());
					block.getWorld().spawnParticle(Particle.BLOCK_DUST, block.getLocation(), count,
							block.getBlockData());
					block.setType(Material.AIR);
				} else {
					block.breakNaturally(pickaxe);
				}
			} else {
				block.breakNaturally(pickaxe);
			}
		}
	}

	@EventHandler
	public void detonate(BlockBreakEvent event) {
// Make Detonate Work With Telepathy
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
					player.getInventory().getItemInMainHand(), player);
			break;
		case "WEST":
			squareDetonate("West", event.getBlock().getRelative(0, 1, -1), 1, 0,
					player.getInventory().getItemInMainHand(), player);
			break;
		case "NORTH":
			squareDetonate("North", event.getBlock().getRelative(1, 1, 0), 1, 0,
					player.getInventory().getItemInMainHand(), player);
			break;
		case "SOUTH":
			squareDetonate("South", event.getBlock().getRelative(-1, 1, 0), 1, 0,
					player.getInventory().getItemInMainHand(), player);
			break;
		case "UP":
		case "DOWN":
			squareDetonate("Up", event.getBlock().getRelative(1, 0, -1), 1, 0,
					player.getInventory().getItemInMainHand(), player);
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
	public void trySilkSpawners(BlockBreakEvent event) {
		if (event.getPlayer().getInventory().getItemInMainHand().getType() != null
				&& event.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null)
			if (!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta()
					.hasEnchant(Enchantment.SILK_TOUCH))) {
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
		if (event.getItem() == null) {
			return;
		}
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
				if (checkSecondWord.equalsIgnoreCase("SPAWN")) {
					cs.setSpawnedType(EntityType.valueOf(type.split("_")[0]));
				} else {
					String newType = type.split("_")[0] + "_" + type.split("_")[1];
					cs.setSpawnedType(EntityType.valueOf(newType));
				}

			}
		}
	}

}