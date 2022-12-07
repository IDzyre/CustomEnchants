package me.Dzyre.customEnchants;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class SetDrops implements Listener {
	
	@EventHandler
	public void setMooshroomDrop(EntityDeathEvent event) {
		int number = 2;
		if(event.getEntity().getType().equals(EntityType.MUSHROOM_COW)) {
			if(event.getEntity().getKiller() instanceof Player) {
				Player player = (Player)event.getEntity().getKiller();
				if(player.getInventory().getItemInMainHand().hasItemMeta()) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_MOBS)) {
						Random rand = new Random();
						switch(rand.nextInt(5)) {
						case 0:
							break;
						case 1:
						case 2:
						case 3:
							number = number * player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_MOBS);
							break;
						default:
							break;
						}
					}
				}
			}
			event.getEntity().getLocation().getWorld().dropItemNaturally(event.getEntity().getLocation(),new ItemStack(Material.IRON_NUGGET, number));
		}
	}
}
