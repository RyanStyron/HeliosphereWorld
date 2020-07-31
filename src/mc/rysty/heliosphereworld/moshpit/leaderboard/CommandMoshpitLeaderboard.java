package mc.rysty.heliosphereworld.moshpit.leaderboard;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;
import mc.rysty.heliosphereworld.utils.MoshpitLeaderboardUtils;

public class CommandMoshpitLeaderboard implements CommandExecutor {

	private FileConfiguration moshpitFile = HelioSphereWorld.moshpitFileManager.getData();

	public CommandMoshpitLeaderboard(HelioSphereWorld plugin) {
		plugin.getCommand("moshpitleaderboard").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("moshpitleaderboard")) {
			if (!(sender instanceof Player && !((Entity) sender).getWorld().equals(Bukkit.getWorld("Moshpit")))) {
				HashMap<String, Double> userMap = new HashMap<>();

				for (String storedUsers : moshpitFile.getConfigurationSection("users").getKeys(false)) {
					String storedDisplayname = moshpitFile.getString("users." + storedUsers + ".displayname");
					double storedScores = moshpitFile.getDouble("users." + storedUsers + ".kdr");

					userMap.put(storedDisplayname, storedScores);
				}
				MessageUtils.message(sender, "&b-===-&3 Moshpit KDR Leaderboard &b-===-");
				MoshpitLeaderboardUtils.getMoshpitLeaderboard(sender, userMap);
			} else
				MessageUtils.configStringMessage(sender, "world_command_error", "<world>",
						((Entity) sender).getWorld().getName());
		}
		return false;
	}
}