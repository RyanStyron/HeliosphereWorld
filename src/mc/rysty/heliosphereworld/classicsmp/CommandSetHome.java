package mc.rysty.heliosphereworld.classicsmp;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.HomesFileManager;
import mc.rysty.heliosphereworld.utils.Utils;

public class CommandSetHome implements CommandExecutor {

    private HomesFileManager homesFileManager = HomesFileManager.getInstance();
    private FileConfiguration homesFile = homesFileManager.getData();

    public CommandSetHome(HelioSphereWorld plugin) {
        plugin.getCommand("sethome").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("sethome")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                UUID playerId = player.getUniqueId();
                World playerWorld = player.getWorld();

                if (playerWorld == Bukkit.getWorld("ClassicSMP")) {
                    if (args.length == 0) {
                        if (homesFile.getString("players." + playerId + ".home") == null) {
                            Location location = player.getLocation();
                            String locationWorldName = location.getWorld().getName();
                            double locationX = location.getX();
                            double locationY = location.getY();
                            double locationZ = location.getZ();

                            homesFile.set("players." + playerId + ".home.world", locationWorldName);
                            homesFile.set("players." + playerId + ".home.x", locationX);
                            homesFile.set("players." + playerId + ".home.y", locationY);
                            homesFile.set("players." + playerId + ".home.z", locationZ);
                            homesFileManager.saveData();

                            Utils.configStringMessage(sender, "SetHomeCommand.home-created-message");
                        } else
                            Utils.configStringMessage(sender, "SetHomeCommand.home-exists-error");
                    } else
                        Utils.configStringMessage(sender, "SetHomeCommand.argument-error");
                } else
                    Utils.configStringMessage(sender, "ClassicSMP.world-error");
            } else
                Utils.consoleErrorMessage(sender);
        }
        return false;
    }
}