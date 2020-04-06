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

	private int bedPlayers = 0;

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		Player player = event.getPlayer();
		World playerWorld = player.getWorld();

		if (Bukkit.getWorld("ClassicSMP") != null) {
			World classicsmp = Bukkit.getWorld("ClassicSMP");

			if (playerWorld == classicsmp) {
				int playerSize = classicsmp.getPlayers().size();

				bedPlayers++;

				/**
				 * The integer playerSize is multiplied by 2 / 3 here because in Classic SMP,
				 * only two out of every three players are required to sleep in order for it to
				 * become day.
				 */
				if (bedPlayers >= playerSize * 2 / 3) {
					classicsmp.setTime(0);
					bedPlayers = 0;
				}
			}
		}
	}

	// @EventHandler
	// public void onPlayerBedEnter(PlayerBedEnterEvent event) {
	// Player player = event.getPlayer();
	// World playerWorld = player.getWorld();

	// if (Bukkit.getWorld("ClassicSMP") != null) {
	// World classicsmp = Bukkit.getWorld("ClassicSMP");

	// if (playerWorld == classicsmp) {
	// for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
	// World onlinePlayerWorld = onlinePlayer.getWorld();
	// int playerSize = classicsmp.getPlayers().size();

	// if (onlinePlayer.isSleeping())
	// bedPlayers++;

	// if (onlinePlayerWorld == classicsmp) {
	// /**
	// * The integer playerSize is multiplied by 2 / 3 here because in Classic SMP,
	// * only two out of every three players are required to sleep in order for it
	// to
	// * become day.
	// */
	// if (bedPlayers >= playerSize * 2 / 3) {
	// classicsmp.setTime(0);
	// bedPlayers = 0;
	// }
	// }
	// }
	// }
	// }
	// }
}
