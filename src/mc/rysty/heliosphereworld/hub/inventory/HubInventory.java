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

public class HubInventory implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		hubInventorySet(p);
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();

		hubInventorySet(p);
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();

		hubInventorySet(p);
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		boolean hub = p.getLocation().getWorld().getName().equalsIgnoreCase("Hub");
		boolean creative = p.getGameMode().equals(GameMode.CREATIVE);

		if (hub && !creative) {
			e.setCancelled(true);
		}
	}

	private void hubInventorySet(Player player) {
		String worldName = player.getLocation().getWorld().getName();
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta cData = compass.getItemMeta();
		cData.addEnchant(Enchantment.DAMAGE_ALL, 50, false);
		cData.setDisplayName("" + ChatColor.AQUA + "Main Menu");
		ArrayList<String> cLore = new ArrayList<>();
		cLore.add("");
		cLore.add(ChatColor.GRAY + " > Click to open the " + ChatColor.DARK_AQUA + "Main Menu");
		cData.setLore(cLore);
		compass.setItemMeta(cData);

		if (worldName.equalsIgnoreCase("Hub")) {
			PlayerInventory inv = player.getInventory();

			inv.clear();
			inv.setItem(0, compass);
		}
	}
}
