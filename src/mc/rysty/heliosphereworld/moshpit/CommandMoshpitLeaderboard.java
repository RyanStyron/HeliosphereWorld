package mc.rysty.heliosphereworld.moshpit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class CommandMoshpitLeaderboard implements CommandExecutor {

    private FileConfiguration moshpitFile = HelioSphereWorld.moshpitFileManager.getData();

    public CommandMoshpitLeaderboard(HelioSphereWorld plugin) {
        plugin.getCommand("moshpitleadboard").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("moshpitleaderboard")) {
            if (command.getName().equalsIgnoreCase("moshpitstats")) {
                if (!(sender instanceof Player && !((Entity) sender).getWorld().equals(Bukkit.getWorld("Moshpit")))) {
                    Map<String, Double> user = new HashMap<>();

                    for (String storedUsers : moshpitFile.getConfigurationSection("users").getKeys(false)) {
                        String storedDisplayname = moshpitFile.getString("users." + storedUsers + ".displayname");
                        double storedScores = moshpitFile.getDouble("users." + storedUsers + ".kdr");

                        user.put(storedDisplayname, storedScores);
                    }
                    Set<Entry<String, Double>> set = user.entrySet();
                    List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(set);

                    Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
                        public int compare(Map.Entry<String, Double> entryOne, Map.Entry<String, Double> entryTwo) {
                            return (entryOne.getValue()).compareTo(entryTwo.getValue());
                        }
                    });

                    MessageUtils.message(sender, "&b-===-&3 Moshpit Leaderboard &b-===-");
                    for (Map.Entry<String, Double> entry : user.entrySet()) {
                        MessageUtils.message(sender, "&3" + entry.getKey() + "&b -- &3" + entry.getValue() + " KDR");
                    }
                } else
                    MessageUtils.configStringMessage(sender, "world_command_error", "<world>",
                            ((Entity) sender).getWorld().getName());
            }
        }
        return false;
    }
}