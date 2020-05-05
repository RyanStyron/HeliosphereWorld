package mc.rysty.heliosphereworld.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;
import mc.rysty.heliosphereworld.utils.WarpFileManager;

public class CommandSetWarp implements CommandExecutor {

    private WarpFileManager warpFileManager = HelioSphereWorld.warpFileManager;
    private FileConfiguration warpFile = warpFileManager.getData();

    public CommandSetWarp(HelioSphereWorld plugin) {
        plugin.getCommand("setwarp").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("setwarp")) {
            if (sender.hasPermission("hs.setwarp")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    String warpName;
                    Location location = player.getLocation();
                    String worldName = location.getWorld().getName();
                    double x = location.getX();
                    double y = location.getY();
                    double z = location.getZ();
                    float pitch = location.getPitch();
                    float yaw = location.getYaw();

                    if (args.length == 1 || args.length == 2) {
                        warpName = args[0].toLowerCase();

                        if (warpFile.getString("warps." + warpName) == null) {
                            String warpFileString = "warps." + warpName;
                            String warpFileLocationString = warpFileString + ".location.";

                            warpFile.set(warpFileLocationString + "world", worldName);
                            warpFile.set(warpFileLocationString + "x", x);
                            warpFile.set(warpFileLocationString + "y", y);
                            warpFile.set(warpFileLocationString + "z", z);
                            warpFile.set(warpFileLocationString + "pitch", pitch);
                            warpFile.set(warpFileLocationString + "yaw", yaw);
                            if (args.length == 2)
                                warpFile.set(warpFileString + ".permission", args[1].toLowerCase());
                            warpFileManager.saveData();

                            MessageUtils.configStringMessage(sender, "CommandWarp.warp-set-message");
                        } else
                            MessageUtils.configStringMessage(sender, "CommandWarp.warp-exists-error");
                    } else
                        MessageUtils.argumentError(sender, "/setwarp <name> [permission]");
                } else
                    MessageUtils.consoleError();
            } else
                MessageUtils.noPermissionError(sender);
        }
        return false;
    }
}
