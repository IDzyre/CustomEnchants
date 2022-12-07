package me.Dzyre.customEnchants ;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
public class commands implements CommandExecutor {

public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (label.equals("anvil")) {
        Player player = (Player) sender;
            player.openInventory(inventories.createAnvilInventory());
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
        }if (label.equalsIgnoreCase("enderport")) {	
            if (!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player) sender;
            if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.ENDERPORT)) {
                player.sendMessage(ChatColor.RED + "You already have that enchant!");
                return true;
            }
            ItemStack item = (player.getInventory().getItemInMainHand());
            item.addUnsafeEnchantment(CustomEnchants.ENDERPORT, 1);
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Enderport");
            if (meta.hasLore()) {
                for (String l : meta.getLore())
                    lore.add(l);
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            return true;
        }
        if (label.equalsIgnoreCase("healthboost")) {
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
}