package mc.rysty.heliosphereworld.classicsmp;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;

public class MultiplayerSleep implements Listener {

	public MultiplayerSleep(HelioSphereWorld plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		Player player = event.getPlayer();
		World playerWorld = player.getWorld();
		int totalPlayers = 0;
		int spleepingPlayers = 0;

		if (playerWorld == Bukkit.getWorld("ClassicSMP")) {
			for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				if (onlinePlayer.getWorld() == playerWorld) {
					totalPlayers++;

					if (onlinePlayer.isSleeping())
						spleepingPlayers++;
				}
			}
			if (spleepingPlayers >= totalPlayers * 2 / 3)
				playerWorld.setTime(23920);
		}
		totalPlayers = 0;
		spleepingPlayers = 0;
	}
}
