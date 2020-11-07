package dev.booniehat.asylum.commands;

import dev.booniehat.asylum.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class versionCommand implements CommandExecutor{
		
	@SuppressWarnings("unused")
	private Main plugin;
	
	public versionCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("boonieversion").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("This command must be ran by a player.");
			return true;
		}
		
		Player target = (Player) sender;
		
		if (target.hasPermission("boonie.command.version") ) {
			target.sendMessage("Version 0.1.2");
			return true;
		} else {
			target.sendMessage("You do not have permission to execute this command!");
		}
		
		return false;
	}
}