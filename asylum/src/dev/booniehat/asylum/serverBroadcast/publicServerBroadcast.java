package dev.booniehat.asylum.serverBroadcast;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import dev.booniehat.asylum.Main;

public class publicServerBroadcast {
	public String bold = ChatColor.BOLD.toString();
	public String red = ChatColor.RED.toString();
	public String gray = ChatColor.GRAY.toString();
	public String green = ChatColor.GREEN.toString();
	public String yellow = ChatColor.YELLOW.toString();
	public String darkRed = ChatColor.DARK_RED.toString();
	public String asylumText = yellow+bold+"Asylum : ";
	
	public int messageInterval = 10000;
	
	String[] messageArray = {"Hello 1", "Hello 2"};
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	
	
	public publicServerBroadcast(Main plugin) {
		this.plugin = plugin;
	}
	
	private String getRandomElement(Set<String> set) {
		int index = new Random().nextInt(set.size());
		Iterator<String> iter = set.iterator();
		for (int i = 0; i < index; i++) {
			iter.next();
		}
		return iter.next();
	}
	
	private void startBroadcastTimer(BukkitScheduler scheduler) {
		@SuppressWarnings("unused")
		int scheduleId = scheduler.scheduleSyncDelayedTask(instance, () -> {
			if (getConfig().getBoolean("broadcast-enable")) {
				String title = getConfig().getString("broadcast-title");
				Set<String> broadcastsList = getConfig().getConfigurationSection("broadcasts").getKeys(false);
				String broadcastId = getRandomElement(broadcastsList);
				ConfigurationSection broadcast = getConfig().getConfigurationSection("broadcasts." + broadcastId);
				for (String message : broadcast.getStringList("messages")) {
					getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', title)+ChatColor.translateAlternateColorCodes('&', message));
				}
				if (broadcast.getString("sound") != null) {
					for (Player p : getServer().getOnlinePlayers()) {
						try {
							p.playSound(p.getLocation(), Sound.valueOf(broadcast.getString("sound")), 5, 5);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			startBroadcastTimer(scheduler);
		}, getConfig().getLong("broadcast-interval"));
	}
}
