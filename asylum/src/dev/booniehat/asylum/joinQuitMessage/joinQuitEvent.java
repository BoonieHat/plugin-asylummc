package dev.booniehat.asylum.joinQuitMessage;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.booniehat.asylum.Main;

public class joinQuitEvent implements Listener {
	@SuppressWarnings("unused")
	private Main plugin;
	public joinQuitEvent(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage("");
  }
	@EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage("");
  }
}
