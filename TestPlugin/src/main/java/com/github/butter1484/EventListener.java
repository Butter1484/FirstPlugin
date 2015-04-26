package com.github.butter1484;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

	public class EventListener implements Listener {
		
		FirstPlugin config;
	
	public EventListener(FirstPlugin plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	config = plugin;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
	
	Player player = e.getPlayer();
	
	if (e.getBlock().getType() == Material.BEDROCK){
		
	
	if(!player.hasPermission("place.bedrock")){
			e.setCancelled(true);
			player.sendMessage(ChatColor.RED + "You do not have permission to place " + e.getBlock().getType().toString());
			}
		}
	if (e.getBlock().getType() == Material.GLOWSTONE){
		
		Location location = e .getBlock().getLocation();
		
		if (new Location(location.getWorld(), location.getX(), location.getY() - 1, location.getZ()).getBlock().getType() == Material.GRASS || player.hasPermission("place.glowstone")){
		
			
			
		}
		else{
			e.setCancelled(true);
			player.sendMessage(ChatColor.RED + "You can only place that on grass");
		}
		
	}
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();
		e.setJoinMessage(ChatColor.AQUA + "Welcome, " + player.getName() + " to the server!");
		player.sendMessage(ChatColor.YELLOW + "This is the development server of Butter1484");
	if (player.hasPlayedBefore() == false){
		player.sendMessage(ChatColor.GREEN + "Hey, you're new. Have some food");
		player.getInventory().addItem(new ItemStack(Material.APPLE, 1));
		ItemStack selector = new ItemStack(Material.WOOD_SWORD);
		ItemMeta selectorMeta = selector.getItemMeta();
		selectorMeta.setDisplayName("GameMode Selector");
		selector.setItemMeta(selectorMeta);
		if (player.hasPermission("have.selector")){
		e.getPlayer().getInventory().addItem(selector);
		}
	}
	
	}
	@EventHandler
	public void killZombie(EntityDeathEvent e){
		Entity deadEntity = e.getEntity();
		Entity killer = e.getEntity().getKiller();
		if (killer instanceof Player && deadEntity instanceof Zombie){
			
			Player player = (Player) killer;
			int killcount = config.getConfig().getInt("zombiekills");
			config.getConfig().set("zombiekills", killcount + 1);
			Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + " has just killed a zombie! Total kills is now " + config.getConfig().getInt("zombiekills") );
			
		}
	}
	

	
	private void openGUI(Player player){
		//create new inventory
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Choose Gamemode");
		//create item stacks and set meta
		ItemStack survival = new ItemStack (Material.DIAMOND_SWORD);
		ItemMeta survivalMeta = survival.getItemMeta();
		ItemStack creative = new ItemStack (Material.BEDROCK);
		ItemMeta creativeMeta = creative.getItemMeta();
		survivalMeta.setDisplayName("Survival Mode");
		survival.setItemMeta(survivalMeta);
		creativeMeta.setDisplayName("Creative Mode");
		creative.setItemMeta(creativeMeta);
		//place items in inv
		inv.setItem(3, survival);
		inv.setItem(5, creative);
		//Open inv
		player.openInventory(inv);
		
		
	}
	
	@EventHandler
	public void onInventroyClick(InventoryClickEvent e){
		if (!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Choose Gamemode")){
			return;
		}
		Player player = (Player) e.getWhoClicked();
		e.setCancelled(true);
		
		if (e.getCurrentItem()==null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()){
			player.closeInventory();
			return;
		}
		switch(e.getCurrentItem().getType()){
		case DIAMOND_SWORD:
			player.setGameMode(GameMode.SURVIVAL);
			player.closeInventory();
			player.sendMessage(ChatColor.GOLD + "You have been set to " + ChatColor.GREEN + "Survival " + ChatColor.GOLD + "Mode");
			break;
		case BEDROCK:
			player.setGameMode(GameMode.CREATIVE);
			player.closeInventory();
			player.sendMessage(ChatColor.GOLD + "You have been set to " + ChatColor.GREEN + "Creative " + ChatColor.GOLD + "Mode");
			break;
		default:
			player.closeInventory();
			break;
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		Action a = e.getAction();
		ItemStack is = e.getItem();
		if(a == Action.PHYSICAL || is == null || is.getType() == Material.AIR){
			return;
		}
		if(is.getType() == Material.WOOD_SWORD){
			openGUI(e.getPlayer());
		}
	}
}
