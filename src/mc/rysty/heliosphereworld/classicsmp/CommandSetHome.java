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

public class CommandSetHome implements CommandExecutor {

    private ClassicSMPFileManager classicsmpFileManager = ClassicSMPFileManager.getInstance();
    private FileConfiguration classicsmpFile = classicsmpFileManager.getData();

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
                        if (classicsmpFile.getString("players." + playerId + ".home") == null) {
                            Location location = player.getLocation();
                            String locationWorldName = location.getWorld().getName();
                            double locationX = location.getX();
                            double locationY = location.getY();
                            double locationZ = location.getZ();
                            float locationYaw = location.getYaw();
                            float locationPitch = location.getPitch();

                            classicsmpFile.set("players." + playerId + ".home.world", locationWorldName);
                            classicsmpFile.set("players." + playerId + ".home.x", locationX);
                            classicsmpFile.set("players." + playerId + ".home.y", locationY);
                            classicsmpFile.set("players." + playerId + ".home.z", locationZ);
                            classicsmpFile.set("players." + playerId + ".home.yaw", locationYaw);
                            classicsmpFile.set("players." + playerId + ".home.pitch", locationPitch);
                            classicsmpFileManager.saveData();

                            MessageUtils.configStringMessage(sender, "SetHomeCommand.home-created-message");
                        } else
                            MessageUtils.configStringMessage(sender, "SetHomeCommand.home-exists-error");
                    } else
                        MessageUtils.configStringMessage(sender, "SetHomeCommand.argument-error");
                } else
                    MessageUtils.configStringMessage(sender, "ClassicSMP.world-error");
            } else
                MessageUtils.consoleError();
        }
        return false;
    }
}