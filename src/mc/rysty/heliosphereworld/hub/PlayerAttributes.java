package mc.rysty.heliosphereworld.hub;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerAttributes implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		p.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0);
	}
}
