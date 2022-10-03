package me.Dzyre.customEnchants;

public class other implements Listener{


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


}