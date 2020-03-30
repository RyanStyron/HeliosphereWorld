package mc.rysty.heliosphereworld.classicsmp;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class MultiplayerSleep implements Listener {

	private int bedPlayers = 0;

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		Player player = event.getPlayer();
		World playerWorld = player.getWorld();

		if (Bukkit.getWorld("ClassicSMP") != null) {
			World classicSurvival = Bukkit.getWorld("ClassicSMP");

			if (playerWorld == classicSurvival) {
				for (Player classicSurvivalPlayers : Bukkit.getOnlinePlayers()) {
					World classicSurvivalPlayersWorld = classicSurvivalPlayers.getWorld();
					int playerSize = classicSurvival.getPlayers().size();

					if (classicSurvivalPlayers.isSleeping())
						bedPlayers++;

					if (classicSurvivalPlayersWorld == classicSurvival) {
						/**
						 * The integer playerSize is multiplied by 2 / 3 here because in Classic
						 * Survival, only two out of every three players are required to sleep in order
						 * for it to become day.
						 */
						if (bedPlayers >= playerSize * 2 / 3) {
							classicSurvival.setTime(600);
							bedPlayers = 0;
						}
					}
				}
			}
		}
	}
}
