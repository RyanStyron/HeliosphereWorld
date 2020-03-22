package mc.rysty.heliosphereworld.classicsurvival;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class SpawnBedMob implements Listener {

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		Block bed = event.getBed();
		Location location = bed.getLocation();
		String worldName = location.getWorld().getName();

		if (worldName.equalsIgnoreCase("ClassicSurvival")) {
			int lightLevel = bed.getLightLevel();

			if (lightLevel < 8) {
				double bedLocationX = location.getX();
				double bedLocationY = location.getY() + 1;
				double bedLocationZ = location.getZ();

				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawnmob random_night_mob ClassicSurvival,"
						+ bedLocationX + "," + bedLocationY + "," + bedLocationZ);
			}
		}
	}
}
