package me.Dzyre.customEnchants;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener, CommandExecutor {
	

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new pickaxes(), this);
		this.getServer().getPluginManager().registerEvents(new weapons(), this);
		this.getServer().getPluginManager().registerEvents(new other(), this);
		this.getServer().getPluginManager().registerEvents(new inventories(), this);
		inventories inv = new inventories();
		CustomEnchants.register();
		inv.createAnvilInventory();
		this.getCommand("enchants").setExecutor(new commands());
		this.getCommand("telepathy").setExecutor(new commands());
		this.getCommand("autosmelt").setExecutor(new commands());
		this.getCommand("detonate").setExecutor(new commands());
		this.getCommand("minelight").setExecutor(new commands());
		this.getCommand("veinmine").setExecutor(new commands());
		this.getCommand("treefeller").setExecutor(new commands());
		//this.getCommand("enderport").setExecutor(new commands());
		this.getCommand("lifesteal").setExecutor(new commands());
		this.getCommand("instantgrow").setExecutor(new commands());
		this.getCommand("cultivate").setExecutor(new commands());
	//	this.getCommand("multishot").setExecutor(new commands());
		this.getCommand("expboost").setExecutor(new commands());
		this.getCommand("beheading").setExecutor(new commands());
		this.getCommand("healthboost").setExecutor(new commands());
		this.getCommand("bunnyhop").setExecutor(new commands());
		this.getCommand("entityshooter").setExecutor(new commands());
		this.getCommand("anvil").setExecutor(new commands());
	}

	@Override
	public void onDisable() {

	}


}
