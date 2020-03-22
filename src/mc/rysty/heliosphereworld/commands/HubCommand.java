package mc.rysty.heliosphereworld.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class HubCommand implements CommandExecutor {

	HelioSphereWorld plugin = HelioSphereWorld.getInstance();

	public HubCommand(HelioSphereWorld plugin) {
		plugin.getCommand("hub").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("hub")) {
			if (sender.hasPermission("hs.teleport.spawn")) {
				if (Bukkit.getWorld("Hub") != null) {
					Player p = (Player) sender;
					Location hub = Bukkit.getWorld("Hub").getSpawnLocation();
					String worldTpMessage = Utils.chat(plugin.getConfig().getString("teleported_world_message"));

					p.teleport(hub);
					p.sendMessage(worldTpMessage.replaceAll("<world>", "Hub"));
				} else {
					sender.sendMessage(ChatColor.RED + "This server does not have a registered Hub");
				}
			}
		} else {

		}
		return false;
	}

}
