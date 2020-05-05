package mc.rysty.heliosphereworld.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;
import mc.rysty.heliosphereworld.utils.WarpFileManager;

public class CommandWarp implements CommandExecutor, TabCompleter {

    private WarpFileManager warpFileManager = HelioSphereWorld.warpFileManager;
    private FileConfiguration warpFile = warpFileManager.getData();

    public CommandWarp(HelioSphereWorld plugin) {
        plugin.getCommand("warp").setExecutor(this);
        plugin.getCommand("warp").setTabCompleter(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("warp")) {
            if (sender instanceof Player) {
                if (args.length == 1) {
                    Player player = (Player) sender;
                    String warp = args[0].toLowerCase();

                    if (warpFile.getString("warps." + warp) != null) {
                        if (warpFile.getString("warps." + warp + ".permission") != null)
                            if (!sender.hasPermission(warpFile.getString("warps." + warp + ".permission"))) {
                                MessageUtils.configStringMessage(sender, "CommandWarp.permission-error");
                                return false;
                            }
                        World world = Bukkit.getWorld(warpFile.getString("warps." + warp + ".location.world"));
                        double x = warpFile.getDouble("warps." + warp + ".location.x");
                        double y = warpFile.getDouble("warps." + warp + ".location.y");
                        double z = warpFile.getDouble("warps." + warp + ".location.z");
                        float pitch = (float) warpFile.getDouble("warps." + warp + ".location.pitch");
                        float yaw = (float) warpFile.getDouble("warps." + warp + ".location.yaw");
                        Location warpLocation = new Location(world, x, y, z);

                        warpLocation.setPitch(pitch);
                        warpLocation.setYaw(yaw);

                        player.teleport(warpLocation);
                        MessageUtils.configStringMessage(sender, "CommandWarp.warp-message", "<warp>", warp);
                    } else
                        MessageUtils.configStringMessage(sender, "CommandWarp.warp-null-error");
                } else
                    MessageUtils.argumentError(sender, "/warp <warp>");
            } else {
                MessageUtils.consoleError();
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> warps = new ArrayList<>();

            if (warpFile.getConfigurationSection("warps") != null) {
                for (String key : warpFile.getConfigurationSection("warps").getKeys(false)) {
                    warps.add(key);
                    if (warpFile.getString("warps." + key + ".permission") != null)
                        if (!sender.hasPermission(warpFile.getString("warps." + key + ".permission")))
                            warps.remove(key);
                }
                return warps;
            }
        }
        return null;
    }
}