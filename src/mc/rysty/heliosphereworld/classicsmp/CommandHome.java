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

public class CommandHome implements CommandExecutor {

    private HomesFileManager homesFileManager = HomesFileManager.getInstance();
    private FileConfiguration homesFile = homesFileManager.getData();

    public CommandHome(HelioSphereWorld plugin) {
        plugin.getCommand("home").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("home")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                UUID playerId = player.getUniqueId();
                World playerWorld = player.getWorld();

                if (playerWorld == Bukkit.getWorld("ClassicSMP")) {
                    if (args.length == 0) {
                        if (homesFile.getString("players." + playerId + ".home") != null) {
                            String homeWorld = homesFile.getString("players." + playerId + ".home.world");
                            double homeX = homesFile.getDouble("players." + playerId + ".home.x");
                            double homeY = homesFile.getDouble("players." + playerId + ".home.y");
                            double homeZ = homesFile.getDouble("players." + playerId + ".home.z");
                            float homeYaw = (float) homesFile.getDouble("players." + playerId + ".home.yaw");
                            float homePitch = (float) homesFile.getDouble("players." + playerId + ".home.pitch");
                            Location homeLocation = new Location(Bukkit.getWorld(homeWorld), homeX, homeY, homeZ);
                            homeLocation.setYaw(homeYaw);
                            homeLocation.setPitch(homePitch);

                            Utils.configStringMessage(sender, "HomeCommand.home-teleport-message");
                            player.teleport(homeLocation);
                        } else
                            Utils.configStringMessage(sender, "HomeCommand.home-null-error");
                    } else
                        Utils.configStringMessage(sender, "HomeCommand.argument-error");
                } else
                    Utils.configStringMessage(sender, "ClassicSMP.world-error");
            } else
                Utils.consoleErrorMessage(sender);
        }
        return false;
    }
}