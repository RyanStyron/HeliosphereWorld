package mc.rysty.heliosphereworld.classicsmp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;

public class SpawnBedMob implements Listener {

	public SpawnBedMob(HelioSphereWorld plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		Block bed = event.getBed();
		Location location = bed.getLocation();
		String worldName = location.getWorld().getName();

		if (worldName.equalsIgnoreCase("Tutorial")) {
			int lightLevel = bed.getLightLevel();

			if (lightLevel < 8) {
				double bedLocationX = location.getX();
				double bedLocationY = location.getY() + 1;
				double bedLocationZ = location.getZ();

				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn -t random_night_mob 1 " + worldName + ","
						+ bedLocationX + "," + bedLocationY + "," + bedLocationZ);
			}
		}
	}
}
