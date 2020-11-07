package dev.booniehat.asylum;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import dev.booniehat.asylum.commands.versionCommand;
import dev.booniehat.asylum.skyblockgui.inventoryMenu;
import dev.booniehat.asylum.skyblockgui.slotEightPanel;
import dev.booniehat.asylum.joinQuitMessage.joinQuitEvent;
//import dev.booniehat.asylum.serverBroadcast.publicServerBroadcast;

public class Main extends JavaPlugin {
	public static Main instance;
	public static Main get() { return instance; }
	
	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig(); // save default config to server
		startBroadcastTimer(getServer().getScheduler());
		
		new versionCommand(this);
		//new publicServerBroadcast(this);
		new slotEightPanel(this);
		new inventoryMenu(this);
		new joinQuitEvent(this);
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
				Set<String> broadcastsList = getConfig().getConfigurationSection("broadcasts").getKeys(false);
				String broadcastId = getRandomElement(broadcastsList);
				ConfigurationSection broadcast = getConfig().getConfigurationSection("broadcasts." + broadcastId);
				for (String message : broadcast.getStringList("messages")) {
					getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
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
