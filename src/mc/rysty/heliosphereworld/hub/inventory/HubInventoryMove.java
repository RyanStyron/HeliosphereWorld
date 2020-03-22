package mc.rysty.heliosphereworld.hub.inventory;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class HubInventoryMove implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		HumanEntity p = e.getWhoClicked();

		if (p.getWorld().getName().equalsIgnoreCase("Hub")) {
			if (p.getGameMode() != GameMode.CREATIVE) {
				if (e.getCurrentItem() == null) {
					return;
				}
				ItemStack item = e.getCurrentItem();

				if (item.getType() == null) {
					return;
				}
				Material itemType = item.getType();

				if (itemType == Material.COMPASS) {
					e.setCancelled(true);
				}
				if (itemType == Material.ARROW) {
					e.setCancelled(true);
				}
			}
		}
	}
}
