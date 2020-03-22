package mc.rysty.heliosphereworld.hub;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class HubNoDamage implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		Entity e = event.getEntity();

		if (e.getLocation().getWorld().getName().equalsIgnoreCase("Hub")) {
			if (e instanceof Player) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onFallDamage(EntityDamageEvent e) {
		Entity p = e.getEntity();
		DamageCause cause = e.getCause();

		if (p.getLocation().getWorld().getName().equalsIgnoreCase("Hub")) {
			if (e.getEntity() instanceof Player && (cause == DamageCause.FALL || cause == DamageCause.FIRE))
				e.setCancelled(true);
		}
	}
}
