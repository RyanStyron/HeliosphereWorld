package mc.rysty.heliosphereworld.hub;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;

public class HubPreventDamage implements Listener {

	public HubPreventDamage(HelioSphereWorld plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();

		if (entity.getWorld().equals(Bukkit.getWorld("Hub")))
			event.setCancelled(true);
	}
}
