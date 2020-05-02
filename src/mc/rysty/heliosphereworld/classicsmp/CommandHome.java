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
import mc.rysty.heliosphereworld.utils.ClassicSMPFileManager;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class CommandHome implements CommandExecutor {

    private ClassicSMPFileManager classicsmpFileManager = ClassicSMPFileManager.getInstance();
    private FileConfiguration classicsmpFile = classicsmpFileManager.getData();

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

                if (playerWorld == Bukkit.getWorld("Tutorial")) {
                    if (args.length == 0) {
                        if (classicsmpFile.getString("players." + playerId + ".home") != null) {
                            String homeWorld = classicsmpFile.getString("players." + playerId + ".home.world");
                            double homeX = classicsmpFile.getDouble("players." + playerId + ".home.x");
                            double homeY = classicsmpFile.getDouble("players." + playerId + ".home.y");
                            double homeZ = classicsmpFile.getDouble("players." + playerId + ".home.z");
                            float homeYaw = (float) classicsmpFile.getDouble("players." + playerId + ".home.yaw");
                            float homePitch = (float) classicsmpFile.getDouble("players." + playerId + ".home.pitch");
                            Location homeLocation = new Location(Bukkit.getWorld(homeWorld), homeX, homeY, homeZ);
                            homeLocation.setYaw(homeYaw);
                            homeLocation.setPitch(homePitch);

                            MessageUtils.configStringMessage(sender, "HomeCommand.home-teleport-message");
                            player.teleport(homeLocation);
                        } else
                            MessageUtils.configStringMessage(sender, "HomeCommand.home-null-error");
                    } else
                        MessageUtils.configStringMessage(sender, "HomeCommand.argument-error");
                } else
                    MessageUtils.configStringMessage(sender, "ClassicSMP.world-error");
            } else
                MessageUtils.consoleError();
        }
        return false;
    }
}