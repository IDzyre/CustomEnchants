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

public class inventories implements Listener {
  public static Inventory anvil = Bukkit.createInventory(null, 9, ChatColor.GOLD + "" + "Combine");

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
          List < String > temp = new ArrayList < String > ();
          meta.setLore(temp);
          event.getCurrentItem().setItemMeta(meta);
        }
      }
    }
  }

  public int random(int upperbound) {
    Random rand = new Random();
    int int_random = rand.nextInt(upperbound);
    return int_random;
  }

  public void enchantingTreeFeller(ItemStack item, Player player) {
    ItemMeta meta = item.getItemMeta();
    List < String > lore = new ArrayList < String > ();
    lore.add(ChatColor.GOLD + "" + "TreeFeller");
    if (meta.hasLore()) {
      for (String l: meta.getLore())
        lore.add(l);
    }
    meta.setLore(lore);
    item.setItemMeta(meta);
    item.addUnsafeEnchantment(CustomEnchants.TREEFELLER, 1);
    player.sendMessage(ChatColor.RED + "" +
      "Your item has been Enchanted with the power to remove whole trees with one swing!");
    return;
  }

  public void enchantingExpBoost(ItemStack item, Player player) {
    ItemMeta meta = item.getItemMeta();
    List < String > lore = new ArrayList < String > ();
    lore.add(ChatColor.GOLD + "" + "ExpBoost");
    if (meta.hasLore()) {
      for (String l: meta.getLore())
        lore.add(l);
    }
    meta.setLore(lore);
    item.setItemMeta(meta);
    item.addUnsafeEnchantment(CustomEnchants.EXPBOOST, 1);
    player.sendMessage(ChatColor.RED + "" +
      "Your item has been Enchanted with the power to INCREASE exp gain!");
    return;
  }

  public void enchantingLifeSteal(ItemStack item, Player player) {
    ItemMeta meta = item.getItemMeta();
    List < String > lore = new ArrayList < String > ();
    lore.add(ChatColor.GOLD + "" + "Lifesteal");
    if (meta.hasLore()) {
      for (String l: meta.getLore())
        lore.add(l);
    }
    meta.setLore(lore);
    item.setItemMeta(meta);
    item.addUnsafeEnchantment(CustomEnchants.LIFESTEAL, 1);
    player.sendMessage(ChatColor.RED + "" +
      "Your item has been Enchanted with the a chance to heal you when you deal damage!");
    return;
  }

  public void enchantingMineLight(ItemStack item, Player player) {
    ItemMeta meta = item.getItemMeta();
    List < String > lore = new ArrayList < String > ();
    lore.add(ChatColor.GOLD + "" + "MineLight");
    if (meta.hasLore()) {
      for (String l: meta.getLore())
        lore.add(l);
    }
    meta.setLore(lore);
    item.setItemMeta(meta);
    item.addUnsafeEnchantment(CustomEnchants.MINELIGHT, 1);
    player.sendMessage(ChatColor.RED + "" +
      "Your item has been Enchanted with Increased vision in caves!");
    return;

  }

  public void enchantingHealthBoost(ItemStack item, Player player) {
    ItemMeta meta = item.getItemMeta();
    List < String > lore = new ArrayList < String > ();
    lore.add(ChatColor.GOLD + "" + "HealthBoost");
    if (meta.hasLore()) {
      for (String l: meta.getLore())
        lore.add(l);
    }
    meta.setLore(lore);
    item.setItemMeta(meta);
    item.addUnsafeEnchantment(CustomEnchants.HEALTHBOOST, 1);
    player.sendMessage(ChatColor.RED + "" +
      "Your item has been Enchanted with increased health!");
    return;
  }

  public void enchantingVeinMiner(ItemStack item, Player player) {
    ItemMeta meta = item.getItemMeta();
    List < String > lore = new ArrayList < String > ();
    lore.add(ChatColor.GOLD + "" + "VeinMiner");
    if (meta.hasLore()) {
      for (String l: meta.getLore())
        lore.add(l);
    }
    meta.setLore(lore);
    item.setItemMeta(meta);
    item.addUnsafeEnchantment(CustomEnchants.VEINMINER, 1);
    player.sendMessage(ChatColor.RED + "" +
      "Your item has been Enchanted with the ability to mine all of the same type of ores around the one you mine!");
    return;
  }

  public void enchantingDetonate(ItemStack item, Player player) {
    ItemMeta meta = item.getItemMeta();
    List < String > lore = new ArrayList < String > ();
    lore.add(ChatColor.GOLD + "" + "Detonate");
    if (meta.hasLore()) {
      for (String l: meta.getLore())
        lore.add(l);
    }
    meta.setLore(lore);
    item.setItemMeta(meta);
    item.addUnsafeEnchantment(CustomEnchants.DETONATE, 1);
    player.sendMessage(ChatColor.RED + "" +
      "Your item has been Enchanted with the ability to mine a 3x3x1 area around the block you break!");
    return;
  }

  public void enchantingTelepathy(ItemStack item, Player player) {
    ItemMeta meta = item.getItemMeta();
    List < String > lore = new ArrayList < String > ();
    lore.add(ChatColor.GOLD + "" + "Telepathy");
    if (meta.hasLore()) {
      for (String l: meta.getLore())
        lore.add(l);
    }
    meta.setLore(lore);
    item.setItemMeta(meta);
    item.addUnsafeEnchantment(CustomEnchants.TELEPATHY, 1);
    player.sendMessage(ChatColor.RED + "" +
      "Your item has been enchanted with Telepathy, all items you mine will go directly into your inventory!");
    return;
  }

  public void enchantingAutosmelt(ItemStack item, Player player) {
    ItemMeta meta = item.getItemMeta();
    List < String > lore = new ArrayList < String > ();
    lore.add(ChatColor.GOLD + "" + "AutoSmelt");
    if (meta.hasLore()) {
      for (String l: meta.getLore())
        lore.add(l);
    }
    meta.setLore(lore);
    item.setItemMeta(meta);
    item.addUnsafeEnchantment(CustomEnchants.AUTOSMELT, 1);
    player.sendMessage(ChatColor.RED + "" +
      "Your item has been enchanted with the power to instantly smelt ores you mine!");
    return;
  }
  @EventHandler
  public void enchanting(EnchantItemEvent event) {
    if (event.getExpLevelCost() < 30) {
      return;
    }
    ItemStack item = event.getItem();
    Player player = event.getEnchanter();
    if (event.getItem().getType().name().toString().toLowerCase().contains("_axe")) {
      switch (random(4)) {
      case 1:
        enchantingTreeFeller(item, player);
        return;
      default:

      }
    } else if (item.getType().name().toLowerCase().contains("sword")) {
      switch (random(7)) {
      case 1:
        enchantingExpBoost(item, player);
        return;
      case 2:
        enchantingLifeSteal(item, player);
        return;

      default:
      }
    } else if (item.getType().name().toLowerCase().contains("helmet")) {
      switch (random(6)) {
      case 1:

        return;
      case 2:
        enchantingHealthBoost(item, player);
        return;
      case 3:
        enchantingMineLight(item, player);
        return;
      default:

      }
    } else if ((item.getType().name().toLowerCase().contains("boots")) || (item.getType().name().toLowerCase().contains("leggings")) || (item.getType().name().toLowerCase().contains("chestplate"))) {
      switch (random(5)) {
      case 1:

        enchantingHealthBoost(item, player);
        return;
      default:
      }
    } else if (item.getType().name().toLowerCase().contains("pickaxe")) {
      switch (random(15)) {
      case 1:
        enchantingVeinMiner(item, player);
        return;
      case 2:
        enchantingDetonate(item, player);
        return;
      case 3:
        enchantingExpBoost(item, player);
        return;
      case 4:
        enchantingAutosmelt(item, player);
        return;
      case 5:
        enchantingTelepathy(item, player);
        return;

      default:

      }
    }

    player.sendMessage(ChatColor.RED + "Sorry, No custom enchants were attached to your weapon.");
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

        if (event.getInventory().equals(anvil) && event.getCurrentItem().equals(event.getInventory().getItem(6)) &&
          player.getLevel() > 30) {
          player.setLevel(player.getLevel() - 15);
          player.sendMessage("Successfully Combined");
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 10, 10);
          event.getInventory().setItem(2, null);
          event.getInventory().setItem(4, null);
        }

        if (event.getCurrentItem() != null &&
          event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
          event.setCancelled(true);
          return;
        }
        if (event.getCurrentItem() != null &&
          event.getCurrentItem().getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
          event.setCancelled(true);
          return;
        }
        if (event.getCurrentItem() == event.getInventory().getItem(2) || event.getCurrentItem() == event.getInventory().getItem(4)) {
          event.getInventory().setItem(6, null);
        }
      }
    }
  }

  @EventHandler
  public void anvilItemClick(InventoryClickEvent event) {
    if ((event.getInventory().equals(anvil))) {
      if (event.getCurrentItem() == null) {
        return;
      }
      if (!(event.getCurrentItem().getType().equals(Material.GREEN_STAINED_GLASS_PANE) ||
          event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE))) {
        if (event.getSlot() != 6)
          event.getInventory().setItem(6, null);
      }
      if (!event.getCurrentItem().getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
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

    List < String > newLore = new ArrayList < String > ();
    ItemMeta meta = item.getItemMeta();
    List < String > lore = new ArrayList < String > ();
    if (item1.getItemMeta().hasLore()) {
      lore = item1.getItemMeta().getLore();
    }
    if (item2.getItemMeta().hasLore()) {
      List < String > Lore1 = item2.getItemMeta().getLore();
      for (String Lore: Lore1) {
        if (!(lore.contains(Lore))) {
          lore.add(Lore);
        }
      }
    }

    for (String Lore: lore) {
      if (Lore.contains("Tree")) {
        newLore.add(ChatColor.GOLD + "" + "TreeFeller");

      }
      if (Lore.contains("Telepathy")) {
        newLore.add(ChatColor.GOLD + "" + "Telepathy");

      }
      if (Lore.contains("Detonate")) {

        newLore.add(ChatColor.GOLD + "" + "Detonate");

      }
      if (Lore.contains("AutoSmelt")) {
        newLore.add(ChatColor.GOLD + "" + "AutoSmelt");

      }
      if (Lore.contains("Exp")) {
        newLore.add(ChatColor.GOLD + "" + "ExpBoost");

      }
      if (Lore.contains("Life")) {
        newLore.add(ChatColor.GOLD + "" + "LifeSteal");

      }
      if (Lore.contains("Health")) {
        newLore.add(ChatColor.GOLD + "" + "HealthBoost");

      }
      if (Lore.contains("MineLight")) {
        newLore.add(ChatColor.GOLD + "" + "MineLight");

      }
      if (Lore.contains("Vein")) {
        newLore.add(ChatColor.GOLD + "" + "Vein Miner");

      }
      if (Lore.contains("Beheading")) {
        newLore.add(ChatColor.GOLD + "" + "Beheading");

      }
    }

    meta.setLore(newLore);
    item.setItemMeta(meta);
    return item;

  }
}

/*else if ((event.getItem().getType() == Material.BOW)) {
	ItemMeta meta = item.getItemMeta();
	List<String> lore = new ArrayList<String>();
	Player player = event.getEnchanter();
	switch (random(7)) {
	case 1:

		lore.add(ChatColor.GOLD + ""  + "EnderPort");
		if (meta.hasLore()) {
			for (String l : meta.getLore())
				lore.add(l);
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(CustomEnchants.ENDERPORT, 1);
		player.sendMessage(ChatColor.RED + "" 
				+ "Your item has been Enchanted with the power to teleport with a bow");
		return;
	case 2:
		lore.add(ChatColor.GOLD + ""  + "Multishots");
		if (meta.hasLore()) {
			for (String l : meta.getLore())
				lore.add(l);
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(CustomEnchants.MULTISHOTS, 1);
		player.sendMessage(ChatColor.RED + "" 
				+ "Your item has been Enchanted with the power to shoot three arrows at once");
		return;
	default:
		player.sendMessage(ChatColor.RED + "Sorry, No custom enchants were attached to your weapon.");
		player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 10, 10);

		return;
	}
}
*/