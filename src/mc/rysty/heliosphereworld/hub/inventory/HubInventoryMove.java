package mc.rysty.heliosphereworld.hub.inventory;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import mc.rysty.heliosphereworld.HelioSphereWorld;

public class HubInventoryMove implements Listener {

	public HubInventoryMove(HelioSphereWorld plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		HumanEntity player = event.getWhoClicked();

		if (player.getWorld().getName().equalsIgnoreCase("Hub")) {
			if (player.getGameMode() != GameMode.CREATIVE) {
				if (event.getCurrentItem() == null)
					return;
				ItemStack item = event.getCurrentItem();

				if (item.getType() == null)
					return;
				Material itemType = item.getType();

				if (itemType == Material.COMPASS)
					event.setCancelled(true);
				if (itemType == Material.ARROW)
					event.setCancelled(true);
			}
		}
	}
}
