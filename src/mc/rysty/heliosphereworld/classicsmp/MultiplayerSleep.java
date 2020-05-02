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
		int sleeping = 0;

		if (playerWorld.equals(Bukkit.getWorld("Tutorial"))) {
			for (Player online : playerWorld.getPlayers()) {
				if (online.isSleeping())
					sleeping++;
				else
					sleeping--;
			}

			if (sleeping > 0)
				playerWorld.setTime(23900);
		}
	}
}
