package mc.rysty.heliosphereworld.hub;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;

public class PlayerJoin implements Listener {

	public PlayerJoin(HelioSphereWorld plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if (Bukkit.getWorld("Hub") != null) {
			World hubWorld = Bukkit.getWorld("Hub");
			Location hub = hubWorld.getSpawnLocation();

			player.teleport(hub);
		}
	}
}
