package mc.rysty.heliosphereworld.hub;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;

public class HubPreventDamage implements Listener {

	public HubPreventDamage(HelioSphereWorld plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();

		if (entity.getLocation().getWorld().getName().equalsIgnoreCase("Hub")) {
			if (entity instanceof Player)
				event.setCancelled(true);
		}
	}

	@EventHandler
	public void onFallDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();

		if (entity.getLocation().getWorld().getName().equalsIgnoreCase("Hub")) {
			if (entity instanceof Player)
				event.setCancelled(true);
		}
	}
}
