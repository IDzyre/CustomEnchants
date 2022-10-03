package me.Dzyre.customEnchants;

public class weapons implements Listener{


    
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



}