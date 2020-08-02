package mc.rysty.heliosphereworld.moshpit.leaderboard;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class CommandMoshpitLeaderboardStreak implements CommandExecutor {

    public CommandMoshpitLeaderboardStreak(HelioSphereWorld plugin) {
        plugin.getCommand("moshpitleaderboardstreak").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("moshpitleaderboardstreak")) {
            if (!(sender instanceof Player && !((Entity) sender).getWorld().equals(Bukkit.getWorld("Moshpit")))) {
                MoshpitLeaderboardUtils.getMoshpitLeaderboard("Streak", sender, MoshpitLeaderboardUtils.getHighestStreakMap());
            } else
                MessageUtils.configStringMessage(sender, "world_command_error", "<world>",
                        ((Entity) sender).getWorld().getName());
        }
        return false;
    }
}