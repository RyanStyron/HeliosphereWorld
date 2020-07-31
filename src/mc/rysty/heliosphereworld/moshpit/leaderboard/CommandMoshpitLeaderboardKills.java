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

public class CommandMoshpitLeaderboardKills implements CommandExecutor {

    private FileConfiguration moshpitFile = HelioSphereWorld.moshpitFileManager.getData();

    public CommandMoshpitLeaderboardKills(HelioSphereWorld plugin) {
        plugin.getCommand("moshpitleaderboardkills").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("moshpitleaderboardkills")) {
            if (!(sender instanceof Player && !((Entity) sender).getWorld().equals(Bukkit.getWorld("Moshpit")))) {
                HashMap<String, Double> userMap = new HashMap<>();

                for (String storedUsers : moshpitFile.getConfigurationSection("users").getKeys(false)) {
                    String storedDisplayname = moshpitFile.getString("users." + storedUsers + ".displayname");
                    double storedScores = moshpitFile.getDouble("users." + storedUsers + ".kills");

                    userMap.put(storedDisplayname, storedScores);
                }
                MoshpitLeaderboardUtils.getMoshpitLeaderboard("Kills", sender, userMap);
            } else
                MessageUtils.configStringMessage(sender, "world_command_error", "<world>",
                        ((Entity) sender).getWorld().getName());
        }
        return false;
    }
}