package mc.rysty.heliosphereworld.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.Utils;

public class SpawnCommand implements CommandExecutor {

	HelioSphereWorld plugin = HelioSphereWorld.getInstance();
	FileConfiguration config = plugin.getConfig();

	public SpawnCommand(HelioSphereWorld plugin) {
		plugin.getCommand("spawn").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spawn")) {
			if (sender.hasPermission("hs.teleport.spawn")) {
				Player p = (Player) sender;
				World pWorld = p.getWorld();
				Location spawn = pWorld.getSpawnLocation();
				String worldName = pWorld.getName();

				p.teleport(spawn);
				if (!worldName.equalsIgnoreCase("Hub") && !worldName.equalsIgnoreCase("MinigamesHub")) {
					p.sendMessage(Utils.chat(config.getString("teleported_spawn_message").replaceAll("<world>", worldName)));
				} else if (worldName.equalsIgnoreCase("Hub")) {
					p.sendMessage(Utils.chat(config.getString("teleported_world_message").replaceAll("<world>", "Hub")));
				} else if (worldName.equalsIgnoreCase("MinigamesHub")) {
					p.sendMessage(Utils.chat(config.getString("teleported_world_message").replaceAll("<world>", "Minigames")));
				}
			} else {
				sender.sendMessage(Utils.chat(config.getString("no_perm_message")));
			}
		}
		return false;
	}

}
