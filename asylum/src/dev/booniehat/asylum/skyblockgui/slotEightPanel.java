package dev.booniehat.asylum.skyblockgui;

import dev.booniehat.asylum.Main;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class slotEightPanel implements Listener {
	public String bold = ChatColor.BOLD.toString();
	public String red = ChatColor.RED.toString();
	public String gray = ChatColor.GRAY.toString();
	public String green = ChatColor.GREEN.toString();
	public String yellow = ChatColor.YELLOW.toString();
	public String darkRed = ChatColor.DARK_RED.toString();
	public String asylumText = red+bold+"Asylum : ";
	
	public ItemStack panelItem = new ItemStack(Material.NETHER_STAR, 1);
	public ItemMeta panelItemMeta = panelItem.getItemMeta();
	public String panelItemName = yellow+bold+"ASYLUM MENU";
	public String panelLore = gray+"(Right Click to Open!)";
	
	public static int panelItemSlot = 8;
	
	@SuppressWarnings("unused")
	private Main plugin;
	public slotEightPanel(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	// Give Panel to user on Server Join
	@EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerJoin(PlayerJoinEvent event) {
		Player target = event.getPlayer();
		
    ArrayList<String> loreArray = new ArrayList<String>();
    panelItemMeta.setDisplayName(panelItemName);
    loreArray.add(panelLore);
    panelItemMeta.setLore(loreArray);
    panelItem.setItemMeta(panelItemMeta);
    
		target.getInventory().setItem(panelItemSlot, panelItem);
  }
	// Give Panel to user on Respawn
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player target = event.getPlayer();
		ArrayList<String> loreArray = new ArrayList<String>();
   	panelItemMeta.setDisplayName(panelItemName);
   	loreArray.add(panelLore);
   	panelItemMeta.setLore(loreArray);
   	panelItem.setItemMeta(panelItemMeta);
		target.getInventory().setItem(panelItemSlot, panelItem);
	}
	// Remove Panel from inventory on Death
	// Prevent the panel from dropping so users can't pick it up.
	@EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerDeath(PlayerDeathEvent event) {
		// CLearing unwanted items
		Iterator<ItemStack> iterator = event.getDrops().iterator();
		while(iterator.hasNext()) {
			ItemStack stack = iterator.next();
			if (stack.getItemMeta().hasDisplayName()) {
				switch(stack.getItemMeta().getDisplayName()) {
					case "§e§lASYLUM MENU":
						iterator.remove();
						break;
					default:
						break;
				}
			}
		}
  }
	// Prevent user from moving nether star -- fix this its checking the slot and if they have panel so no matter menu you cant use 8th slot
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMove(InventoryClickEvent event) {
		if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
			if (event.getCurrentItem().getItemMeta().hasDisplayName()) {
				if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§e§lASYLUM MENU")) {
					event.setCancelled(true);
				}
			}
		}
	}
	//Prevent user from dropping nether star
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player target = event.getPlayer();
		int handSlot = target.getInventory().getHeldItemSlot();

		if (event.getItemDrop().getItemStack().getType() == Material.NETHER_STAR && handSlot == panelItemSlot) {
			event.setCancelled(true);
			target.sendMessage(asylumText + gray + "You can't drop your panel!");
		}
	}
}