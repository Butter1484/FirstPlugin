package com.github.butter1484;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandHandler  {
	
	 static FileConfiguration config = FirstPlugin.plugin.getConfig();
	




	public static boolean executeCommands(CommandSender sender, Command cmd, String label, String[] args){
		
		// Give Items
		if (cmd.getName().equalsIgnoreCase("givemeitems") && sender instanceof Player){
			Player player = (Player) sender;
			
			if(player.hasPermission("giveItems.use")){
				player.setItemInHand(new ItemStack(Material.DIAMOND_AXE));
				
			}
			else{
				player.sendMessage(ChatColor.RED + "You do not have permission for that command");
			}
			return true;
		}
		//Random Teleport
		if (cmd.getName().equalsIgnoreCase("teleportme") && sender instanceof Player){
			
			Player player = (Player) sender;
			
			Location originalLocation = player.getLocation();
			Random random = new Random();
			Location teleportLocation = null;

			
			
			int x = random.nextInt(100) + 1;
			int y = 150;
			int z = random.nextInt(100) + 1;
			
		

            boolean isOnLand = false;
            
            while (isOnLand == false) {

                    
					teleportLocation = new Location(player.getWorld(), x, y, z);
                   
                    if (teleportLocation.getBlock().getType() != Material.AIR) {
                            isOnLand = true;
                    } else y--;
           
            }
           
            player.teleport(new Location(player.getWorld(), teleportLocation.getX(), teleportLocation.getY() + 1, teleportLocation.getZ()));
           
            player.sendMessage(ChatColor.GREEN + "You have been teleported " + (int)teleportLocation.distance(originalLocation) + " blocks away!");
			return true;
		}
		//Heal Player
		if (cmd.getName().equalsIgnoreCase("healplayer") && sender instanceof Player){
			Player player = (Player) sender;
			int length = args.length;
			
			if (length == 1){
				
				boolean playerFound = false;
				
				for (Player playerToHeal : Bukkit.getServer().getOnlinePlayers()){
					if(playerToHeal.getName().equalsIgnoreCase(args[0])){
						playerToHeal.setHealth(20.0);
						player.sendMessage(ChatColor.GREEN + "You have healed " + playerToHeal.getName());
						playerToHeal.sendMessage(ChatColor.GREEN + "You have been healed by " + player.getName());
						playerFound = true;
						break;
					}
				}
			if (playerFound == false){
				player.sendMessage(ChatColor.RED + args[0] + " was not found!");
			}
			
			}
			else {
				player.sendMessage(ChatColor.RED + "Incorrect arguments");
			}
			
			return true;
		}
			
		if (cmd.getName().equalsIgnoreCase("totalexp") && sender instanceof Player){


			Player player = (Player) sender;

			int length = args.length;
			//If no arguments, display xp for sender
			if (length == 0){
				player.sendMessage(ChatColor.GOLD + "You have " + player.getTotalExperience() + " EXP");
			}
			if (length == 1){
				boolean playerFound = false;
				for (Player playerToCheck : Bukkit.getServer().getOnlinePlayers()){
					if (playerToCheck.getName().equalsIgnoreCase(args[0])){
						player.sendMessage(ChatColor.GOLD + playerToCheck.getName() + " has " + playerToCheck.getTotalExperience() + " EXP");
						playerFound = true;
						break;
					}
				}
				if (playerFound == false){
					player.sendMessage(ChatColor.RED + args[0] + " not found!");
				}
			}
			else if (args.length > 1) {
				player.sendMessage(ChatColor.RED + "Incorrect arguments");
			}
			
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("zombiekills") && sender instanceof Player){
			
			Player player = (Player) sender;
			
			player.sendMessage(ChatColor.GOLD + "" + config.getInt("zombiekills") + " zombies have been killed");
			return true;
		}


		return false;
		
	
	}



}
