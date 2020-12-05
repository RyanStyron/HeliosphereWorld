package mc.rysty.heliosphereworld.hub;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import mc.rysty.heliosphereworld.HelioSphereWorld;

public class HubInventory implements Listener {

	public HubInventory(HelioSphereWorld plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (player.getWorld().equals(Bukkit.getWorld("Hub"))) {
			ItemStack item = event.getItem();

			if (item == null)
				return;
			ItemMeta itemMeta = item.getItemMeta();

			if (itemMeta == null)
				return;
			String itemName = itemMeta.getDisplayName();

			if (itemMeta.getDisplayName() == null)
				return;

			if (itemName.equalsIgnoreCase(ChatColor.AQUA + "Main Menu")) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						"mycmd-runas player " + player.getName() + " /mainmenu");
				player.getInventory().setHeldItemSlot(1);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		player.setHealth(player.getMaxHealth());

		if (Bukkit.getWorld("Hub") != null)
			player.teleport(Bukkit.getWorld("Hub").getSpawnLocation());
		setHubInventory(player);
	}

	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		setHubInventory(event.getPlayer());
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		setHubInventory(event.getPlayer());
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		HumanEntity player = event.getWhoClicked();

		if (player.getWorld().equals(Bukkit.getWorld("Hub")))
			if (player.getGameMode() != GameMode.CREATIVE)
				event.setCancelled(true);
	}

	private void setHubInventory(Player player) {
		ItemStack item = new ItemStack(Material.COMPASS);
		ItemMeta itemData = item.getItemMeta();
		ArrayList<String> itemLore = new ArrayList<>();

		itemData.addEnchant(Enchantment.DAMAGE_ALL, 50, false);
		itemData.setDisplayName("" + ChatColor.AQUA + "Main Menu");
		itemLore.add("");
		itemLore.add(ChatColor.GRAY + " > Click to open the " + ChatColor.DARK_AQUA + "Main Menu");
		itemData.setLore(itemLore);
		item.setItemMeta(itemData);

		if (player.getWorld().equals(Bukkit.getWorld("Hub"))) {
			PlayerInventory inventory = player.getInventory();

			inventory.clear();
			inventory.setItem(0, item);
		}
	}
}
