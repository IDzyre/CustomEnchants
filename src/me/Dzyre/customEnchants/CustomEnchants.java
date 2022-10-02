package me.Dzyre.customEnchants;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.enchantments.Enchantment;

public class CustomEnchants {
	public static final Enchantment AUTOSMELT = new EnchantmentWrapper("autosmelt", "AutoSmelt", 1);
	public static final Enchantment DETONATE = new EnchantmentWrapper("detonate", "Detonate", 1);
	public static final Enchantment TELEPATHY = new EnchantmentWrapper("telepathy", "Telepathy", 1);
	public static final Enchantment INSTANTGROW = new EnchantmentWrapper("instantgrow", "InstantGrow", 1);
	public static final Enchantment TREEFELLER = new EnchantmentWrapper("treefeller", "TreeFeller", 1);
	public static final Enchantment SWORDEFFECTS = new EnchantmentWrapper("swordeffects", "SwordEffects", 1);
	public static final Enchantment LIFESTEAL = new EnchantmentWrapper("lifesteal", "LifeSteal", 1);
	public static final Enchantment MINELIGHT = new EnchantmentWrapper("minelight", "MineLight", 1);
	public static final Enchantment VEINMINER = new EnchantmentWrapper("veinminer", "VeinMiner", 1);
	public static final Enchantment EXPBOOST = new EnchantmentWrapper("expboost", "ExpBoost", 1);
	public static final Enchantment SILKSPAWNERS = new EnchantmentWrapper("silkspawners", "SilkSpawners", 1);
	public static final Enchantment HEALTHBOOST = new EnchantmentWrapper("healthboost", "HealthBoost", 1);
	public static final Enchantment ENTITYSHOOTER = new EnchantmentWrapper("entityshooter", "EntityShooter", 1);
	public static final Enchantment BEHEADING = new EnchantmentWrapper("beheading", "Beheading", 1);
	public static final Enchantment MULTISHOTS = new EnchantmentWrapper("multishots", "MultiShots", 1);
	public static final Enchantment ENDERPORT = new EnchantmentWrapper("enderport", "EnderPort", 1);
	public static final Enchantment CULTIVATOR = new EnchantmentWrapper("cultivator", "Cultivator", 1);
	public static final Enchantment BUNNYHOP = new EnchantmentWrapper("bunnyhop", "Bunnyhop", 1);
	public static void register() {
		boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.TELEPATHY);
		boolean registered1 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.INSTANTGROW);
		boolean registered2 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.DETONATE);
		boolean registered3 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.AUTOSMELT);
		boolean registered4 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.TREEFELLER);
		boolean registered5 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.SWORDEFFECTS);
		boolean registered6 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.LIFESTEAL);
		boolean registered7 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.MINELIGHT);
		boolean registered8 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.VEINMINER);
		boolean registered9 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.EXPBOOST);
		boolean registered10 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.SILKSPAWNERS);
		boolean registered11 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.HEALTHBOOST);
		boolean registered12 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.ENTITYSHOOTER);
		boolean registered13 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.BEHEADING);
		boolean registered14 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.MULTISHOTS);
		boolean registered15 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.ENDERPORT);
		boolean registered16 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.CULTIVATOR);
		boolean registered17 = Arrays.stream(Enchantment.values()).collect(Collectors.toList())
				.contains(CustomEnchants.BUNNYHOP);

		if (!registered16)
			registerEnchantment(CULTIVATOR);
		if (!registered17)
			registerEnchantment(BUNNYHOP);
		if (!registered14)
			registerEnchantment(ENDERPORT);
		if (!registered15)
			registerEnchantment(MULTISHOTS);
		if (!registered13)
			registerEnchantment(BEHEADING);
		if (!registered12)
			registerEnchantment(ENTITYSHOOTER);
		if (!registered11)
			registerEnchantment(HEALTHBOOST);
		if (!registered10)
			registerEnchantment(SILKSPAWNERS);
		if (!registered9)
			registerEnchantment(EXPBOOST);
		if (!registered8)
			registerEnchantment(VEINMINER);
		if (!registered7)
			registerEnchantment(MINELIGHT);
		if (!registered6)
			registerEnchantment(LIFESTEAL);
		if (!registered5)
			registerEnchantment(SWORDEFFECTS);
		if (!registered4)
			registerEnchantment(TREEFELLER);
		if (!registered3)
			registerEnchantment(AUTOSMELT);
		if (!registered2)
			registerEnchantment(DETONATE);
		if (!registered1)
			registerEnchantment(INSTANTGROW);
		if (!registered)
			registerEnchantment(TELEPATHY);
	}

	public static void registerEnchantment(Enchantment enchant) {
		boolean registered = true;
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			Enchantment.registerEnchantment(enchant);
		} catch (Exception e) {
			registered = false;
			e.printStackTrace();
		}
		if (registered) {
			//
		}
	}

}
