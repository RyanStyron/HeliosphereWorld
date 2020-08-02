package mc.rysty.heliosphereworld.moshpit.leaderboard;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class CommandMoshpitLeaderboardDeaths implements CommandExecutor {

    public CommandMoshpitLeaderboardDeaths(HelioSphereWorld plugin) {
        plugin.getCommand("moshpitleaderboarddeaths").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("moshpitleaderboarddeaths")) {
            if (!(sender instanceof Player && !((Entity) sender).getWorld().equals(Bukkit.getWorld("Moshpit")))) {
                MoshpitLeaderboardUtils.getMoshpitLeaderboard("Deaths", sender, MoshpitLeaderboardUtils.getDeathsMap());
            } else
                MessageUtils.configStringMessage(sender, "world_command_error", "<world>",
                        ((Entity) sender).getWorld().getName());
        }
        return false;
    }

}