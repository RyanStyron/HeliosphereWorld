package mc.rysty.heliosphereworld.hub;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class HubCommand implements CommandExecutor {

	public HubCommand(HelioSphereWorld plugin) {
		plugin.getCommand("hub").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("hub")) {
			if (Bukkit.getWorld("Hub") != null) {
				Player player = (Player) sender;
				Location hubSpawnLocation = Bukkit.getWorld("Hub").getSpawnLocation();

				player.teleport(hubSpawnLocation);
				MessageUtils.configStringMessage(player, "teleported_world_message", "<world>", "Hub");
			} else
				MessageUtils.message(sender, "&c&l(!)&f This server does not have a registered Hub.");
		}
		return false;
	}
}
