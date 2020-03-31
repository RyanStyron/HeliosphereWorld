package mc.rysty.heliosphereworld.classicsmp;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.HomesFileManager;
import mc.rysty.heliosphereworld.utils.Utils;

public class CommandDeleteHome implements CommandExecutor {

    private HomesFileManager homesFileManager = HomesFileManager.getInstance();
    private FileConfiguration homesFile = homesFileManager.getData();

    public CommandDeleteHome(HelioSphereWorld plugin) {
        plugin.getCommand("deletehome").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("deletehome")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                UUID playerId = player.getUniqueId();
                World playerWorld = player.getWorld();

                if (playerWorld == Bukkit.getWorld("ClassicSMP")) {
                    if (args.length == 0) {
                        if (homesFile.getString("players." + playerId + ".home") != null) {
                            homesFile.set("players." + playerId + ".home", null);
                            homesFileManager.saveData();

                            Utils.configStringMessage(sender, "DeleteHomeCommand.home-deleted-message");
                        } else
                            Utils.configStringMessage(sender, "DeleteHomeCommand.home-null-error");
                    } else
                        Utils.configStringMessage(sender, "DeleteHomeCommand.argument-error");
                } else
                    Utils.configStringMessage(sender, "ClassicSMP.world-error");
            } else
                Utils.consoleErrorMessage(sender);
        }
        return false;
    }
}