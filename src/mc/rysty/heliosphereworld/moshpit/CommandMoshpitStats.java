package mc.rysty.heliosphereworld.moshpit;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class CommandMoshpitStats implements CommandExecutor {

    private FileConfiguration moshpitFile = HelioSphereWorld.moshpitFileManager.getData();

    public CommandMoshpitStats(HelioSphereWorld plugin) {
        plugin.getCommand("moshpitstats").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("moshpitstats")) {
            Player target = null;

            if (args.length > 0)
                target = Bukkit.getPlayer(args[0]);
            else if (sender instanceof Player)
                target = (Player) sender;

            if (target == null)
                MessageUtils.validPlayerError(sender);
            else {
                UUID targetId = target.getUniqueId();
                double kills = moshpitFile.getDouble("users." + targetId + ".kills");
                double deaths = moshpitFile.getDouble("users." + targetId + ".deaths");
                double kdr = moshpitFile.getDouble("users." + targetId + ".kdr");

                MessageUtils.message(sender, "&b-===-&3 Moshpit Stats:&f " + target.getDisplayName() + " &b-===-");
                MessageUtils.message(sender, "&bKills:&f " + kills);
                MessageUtils.message(sender, "&bDeaths:&f " + deaths);
                MessageUtils.message(sender, "&bK/D ratio:&f " + kdr);
            }
        }
        return false;
    }
}