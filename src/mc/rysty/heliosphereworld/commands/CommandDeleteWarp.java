package mc.rysty.heliosphereworld.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;
import mc.rysty.heliosphereworld.utils.managers.WarpFileManager;

public class CommandDeleteWarp implements CommandExecutor, TabCompleter {

    private WarpFileManager warpFileManager = HelioSphereWorld.warpFileManager;
    private FileConfiguration warpFile = warpFileManager.getData();

    public CommandDeleteWarp(HelioSphereWorld plugin) {
        plugin.getCommand("deletewarp").setExecutor(this);
        plugin.getCommand("deletewarp").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("deletewarp")) {
            if (sender.hasPermission("hs.deletewarp")) {
                if (args.length == 1) {
                    String warp = args[0].toLowerCase();

                    if (warpFile.getString("warps." + warp) != null) {
                        warpFile.set("warps." + warp, null);
                        warpFileManager.saveData();

                        MessageUtils.configStringMessage(sender, "CommandWarp.warp-deleted-message", "<warp>", warp);
                    } else
                        MessageUtils.configStringMessage(sender, "CommandWarp.warp-null-error");
                } else
                    MessageUtils.argumentError(sender, "/deletewarp <warp>");
            } else
                MessageUtils.noPermissionError(sender);
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