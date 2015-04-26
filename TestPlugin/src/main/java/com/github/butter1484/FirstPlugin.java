package com.github.butter1484;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FirstPlugin extends JavaPlugin{
	public static FirstPlugin plugin;
	//These permissions are not registered in plugin.yml
	public Permission giveItems = new Permission("giveItems.use");
	public Permission placeBedrock = new Permission("place.bedrock");
	public Permission placeGlowstone = new Permission("place.glowstone");
	public Permission haveSelector = new Permission("have.selector");

	@Override
	public void  onEnable() {
		plugin = this;
		new EventListener(this);
		
		PluginManager pm = getServer().getPluginManager();
		pm.addPermission(giveItems);
		pm.addPermission(placeBedrock);
		pm.addPermission(placeGlowstone);
		pm.addPermission(haveSelector);
		this.getConfig().addDefault("zombiekills", 0);
		this.getConfig().options().copyDefaults(true);
		saveConfig();
		
	}
	
	@Override
	public void  onDisable() {
		saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CommandHandler.executeCommands(sender, cmd, label, args);
		return false;
		
		
}
}

