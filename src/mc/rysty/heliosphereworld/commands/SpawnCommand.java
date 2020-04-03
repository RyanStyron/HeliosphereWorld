package mc.rysty.heliosphereworld.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class SpawnCommand implements CommandExecutor {

	public SpawnCommand(HelioSphereWorld plugin) {
		plugin.getCommand("spawn").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spawn")) {
			Player player = (Player) sender;
			World playerWorld = player.getWorld();
			Location worldSpawn = playerWorld.getSpawnLocation();
			String worldName = playerWorld.getName();

			if (player.getLocation().distanceSquared(worldSpawn) > 25) {
				if (Bukkit.getPluginManager().getPlugin("Multiverse-Core").isEnabled())
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"multiverse-core:mvtp " + player.getName() + " " + worldName);
				else
					player.teleport(worldSpawn);

				switch (worldName) {
					case "Hub":
						MessageUtils.configStringMessage(player, "teleported_world_message", "<world>", "Hub");
						break;
					case "MinigamesHub":
						MessageUtils.configStringMessage(player, "teleported_world_message", "<world>", "Minigames");
						break;
					default:
						MessageUtils.configStringMessage(player, "teleported_spawn_message", "<world>", worldName);
						break;
				}
			} else
				Bukkit.dispatchCommand(player, "hub");
		}
		return false;
	}
}
