package dev.booniehat.asylum.skyblockgui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import dev.booniehat.asylum.Main;

public class inventoryMenu implements Listener {
	private static Inventory inv;
	
  @SuppressWarnings("unused")
	private Main plugin;
	public inventoryMenu(Main plugin) {
		this.plugin = plugin;
		inv = Bukkit.createInventory(null, 45, "§1§lASYLUM MENU");
		initializeItems();
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

  // You can call this whenever you want to put the items in
  public void initializeItems() {
		int menuSlots = 45;
		int[] doNotFillSlots = {12,14,20,21,22,23,24,38,40,42};
		for (int i = 0; i < menuSlots; i++) {
			for (int element : doNotFillSlots) {
				if (element != i) {
					inv.setItem(i, createGuiGlass());
				}
			}
		}

    inv.setItem(12, createGuiItem(Material.LAVA_BUCKET , "§a§lISLAND PANEL", "§6Manage your island!", "§7(Click Me to Open!)"));
    inv.setItem(14, createGuiItem(Material.EYE_OF_ENDER , "§e§lLOBBY", "§6Go back to the Main Hub!", "§7(Click Me to Teleport!)"));
    inv.setItem(20, createGuiItem(Material.EMERALD, "§a§lSHOP", "§7(Click Me to Open!)", null));
    inv.setItem(21, createGuiItem(Material.EMPTY_MAP  , "§a§lDAILY REWARDS", "§7(Click Me to Open!)", null));
    inv.setItem(22, createGuiItem(Material.TRIPWIRE_HOOK  , "§a§lVOTE", "§6Get amazing rewards by voting!", "§7(Click Me to See Links!)"));
    inv.setItem(23, createGuiItem(Material.BOOK, "§d§lRANKS", "§7(Click Me to Open!)", null));
    inv.setItem(24, createGuiItem(Material.ENDER_CHEST , "§c§lPRIVATE VAULT", "§7(Click Me to Open!)", null));
    inv.setItem(38, createGuiItem(Material.FIREWORK, "§d§lSKILLS", "§7(Click Me to Open!)", null));
    inv.setItem(40, createGuiItem(Material.BARRIER, "§c§lCLOSE MENU", "§7(Click Me to Close!)", null));
    inv.setItem(42, createGuiItem(Material.GOLD_INGOT, "§e§lMONEY", null, null));
  }
  
	//Nice little method to create a gui item with a custom name, and description
	 protected ItemStack createGuiGlass() {
	   final ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
	   final ItemMeta meta = item.getItemMeta();
	   // Set the name of the item
	   meta.setDisplayName("§a");
	   item.setItemMeta(meta);
	   return item;
	 }

  // Nice little method to create a gui item with a custom name, and description
  protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
    final ItemStack item = new ItemStack(material, 1);
    final ItemMeta meta = item.getItemMeta();
    // Set the name of the item
    meta.setDisplayName(name);
    // Set the lore of the item
    meta.setLore(Arrays.asList(lore));
    item.setItemMeta(meta);
    return item;
  }

  // You can open the inventory with this
  public void openInventoryMenu(HumanEntity target) {
  	target.openInventory(inv);
  }
  
  // open menu on right click nether sta
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onRightClick(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Player target = event.getPlayer();
			PlayerInventory inventory = target.getInventory();
	    int slot = inventory.getHeldItemSlot();
	    ItemStack item = target.getInventory().getItem(8);
	    Material itemType = item.getType();
			if (item != null && item.getItemMeta().hasDisplayName()) {
				if (slot == 8 && itemType == Material.NETHER_STAR) {
					event.setCancelled(true);
					target.openInventory(inv);
		    	target.playSound(target.getLocation(), Sound.ORB_PICKUP, 1.0F, 0.5F);
				}
			}
		}
  }
  
  // prevent interacting with items while menu is open
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onInventoryClickMenu(InventoryClickEvent event) {
  	Inventory inventory = event.getInventory(); // The inventory that was clicked in
    if (inventory.getName().equals(inv.getName())) {
    	event.setCancelled(true);
    }
  }
  
  //Prevent user from moving nether star -- fix this its checking the slot and if they have panel so no matter menu you cant use 8th slot
	@EventHandler(priority = EventPriority.HIGHEST)
	public void menuButtonClicks(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		ItemMeta itemMeta = item.getItemMeta();
		String itemName = itemMeta.getDisplayName();
		if (item != null && itemMeta != null) {
			if (itemMeta.hasDisplayName()) {
				if (itemName.equals("§a§lISLAND PANEL")) {
					player.performCommand("island panel");
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 0.5F);
				}
				if (itemName.equals("§e§lLOBBY")) {
					player.performCommand("server lobby");
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 0.5F);
					player.closeInventory();
		      event.setCancelled(true);
				}
				if (itemName.equals("§a§lSHOP")) {
					player.performCommand("shop");
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 0.5F);
				}
				if (itemName.equals("§a§lDAILY REWARDS")) {
					player.performCommand("daily");
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 0.5F);
				}
				if (itemName.equals("§a§lVOTE")) {
					player.performCommand("vote");
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 0.5F);
					player.closeInventory();
		      event.setCancelled(true);
				}
				if (itemName.equals("§d§lRANKS")) {
					player.performCommand("ranks");
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 0.5F);
					player.closeInventory();
		      event.setCancelled(true);
				}
				if (itemName.equals("§c§lPRIVATE VAULT")) {
					player.performCommand("pv 1");
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 0.5F);
				}
				if (itemName.equals("§d§lSKILLS")) {
					player.performCommand("skills menu");
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 0.5F);
				}
				if (itemName.equals("§c§lCLOSE MENU")) {
					player.closeInventory();
		      event.setCancelled(true);
				}
			}
		}
	}
}
