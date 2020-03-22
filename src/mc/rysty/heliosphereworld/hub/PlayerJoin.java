package mc.rysty.heliosphereworld.hub;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import mc.rysty.heliosphereworld.utils.SettingsManager;

public class PlayerJoin implements Listener {

	private SettingsManager settings = SettingsManager.getInstance();
	private FileConfiguration data = settings.getData();

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if (Bukkit.getWorld("Hub") != null) {
			World hubWorld = Bukkit.getWorld("Hub");
			Location hub = hubWorld.getSpawnLocation();

			player.teleport(hub);
		}
		UUID pId = player.getUniqueId();
		String playerName = player.getName();

		if (data.getString("players." + pId) != null) {
			data.set("players." + pId + ".username", playerName);
			settings.saveData();
		}
	}
}
