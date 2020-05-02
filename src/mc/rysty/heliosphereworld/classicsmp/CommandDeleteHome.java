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
import mc.rysty.heliosphereworld.utils.ClassicSMPFileManager;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class CommandDeleteHome implements CommandExecutor {

    private ClassicSMPFileManager classicsmpFileManager = ClassicSMPFileManager.getInstance();
    private FileConfiguration classicsmpFile = classicsmpFileManager.getData();

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

                if (playerWorld == Bukkit.getWorld("Tutorial")) {
                    if (args.length == 0) {
                        if (classicsmpFile.getString("players." + playerId + ".home") != null) {
                            classicsmpFile.set("players." + playerId + ".home", null);
                            classicsmpFileManager.saveData();

                            MessageUtils.configStringMessage(sender, "DeleteHomeCommand.home-deleted-message");
                        } else
                            MessageUtils.configStringMessage(sender, "DeleteHomeCommand.home-null-error");
                    } else
                        MessageUtils.configStringMessage(sender, "DeleteHomeCommand.argument-error");
                } else
                    MessageUtils.configStringMessage(sender, "ClassicSMP.world-error");
            } else
                MessageUtils.consoleError();
        }
        return false;
    }
}