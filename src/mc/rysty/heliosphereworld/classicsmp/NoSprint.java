package mc.rysty.heliosphereworld.classicsmp;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * This class is currently not called in the HelioSphereWorld class because it
 * is no longer needed.
 */
public class NoSprint implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		World world = player.getWorld();
		String worldName = world.getName();

		if (worldName.equalsIgnoreCase("ClassicSurvival")) {
			player.setFoodLevel(6);
			player.setSprinting(false);
		}
	}

	/**
	 * This method of preventing sprinting does not work due to a bug where a player
	 * can force sprint through it.
	 */
	/*
	 * @EventHandler public void onPlayerMove(PlayerMoveEvent event) { Player player
	 * = event.getPlayer(); World world = player.getWorld(); String worldName =
	 * world.getName(); GameMode gamemode = player.getGameMode();
	 * 
	 * if (worldName.equalsIgnoreCase("ClassicSurvival")) { if (gamemode !=
	 * GameMode.CREATIVE) { if (player.isSprinting()) { player.setSprinting(false);
	 * } } } }
	 */
}
