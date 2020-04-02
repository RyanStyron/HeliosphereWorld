package mc.rysty.heliosphereworld.hub.inventory;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
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
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		hubInventorySet(player);
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();

		hubInventorySet(player);
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();

		hubInventorySet(player);
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		boolean hub = player.getLocation().getWorld().getName().equalsIgnoreCase("Hub");
		boolean creative = player.getGameMode().equals(GameMode.CREATIVE);

		if (hub && !creative) {
			event.setCancelled(true);
		}
	}

	private void hubInventorySet(Player player) {
		String worldName = player.getLocation().getWorld().getName();
		ItemStack item = new ItemStack(Material.COMPASS);
		ItemMeta itemData = item.getItemMeta();
		itemData.addEnchant(Enchantment.DAMAGE_ALL, 50, false);
		itemData.setDisplayName("" + ChatColor.AQUA + "Main Menu");
		ArrayList<String> itemLore = new ArrayList<>();
		itemLore.add("");
		itemLore.add(ChatColor.GRAY + " > Click to open the " + ChatColor.DARK_AQUA + "Main Menu");
		itemData.setLore(itemLore);
		item.setItemMeta(itemData);

		if (worldName.equalsIgnoreCase("Hub")) {
			PlayerInventory inv = player.getInventory();

			inv.clear();
			inv.setItem(0, item);
		}
	}
}
