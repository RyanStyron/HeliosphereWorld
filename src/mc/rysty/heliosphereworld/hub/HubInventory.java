package mc.rysty.heliosphereworld.hub;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
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
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if (Bukkit.getWorld("Hub") != null) {
			World hub = Bukkit.getWorld("Hub");
			Location hubSpawnLocation = hub.getSpawnLocation();

			player.teleport(hubSpawnLocation);
		}
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
