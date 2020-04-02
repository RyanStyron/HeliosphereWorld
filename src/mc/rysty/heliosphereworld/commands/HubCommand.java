package mc.rysty.heliosphereworld.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;
import net.md_5.bungee.api.ChatColor;

public class HubCommand implements CommandExecutor {

	public HubCommand(HelioSphereWorld plugin) {
		plugin.getCommand("hub").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("hub")) {
			if (Bukkit.getWorld("Hub") != null) {
				Player player = (Player) sender;
				Location hub = Bukkit.getWorld("Hub").getSpawnLocation();

				player.teleport(hub);
				MessageUtils.configStringMessage(player, "teleported_world_message", "<world>", "Hub");
			} else
				MessageUtils.configStringMessage(sender, ChatColor.RED + "This server does not have a registered Hub.");
		}
		return false;
	}
}
