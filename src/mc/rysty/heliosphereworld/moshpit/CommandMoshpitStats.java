package mc.rysty.heliosphereworld.moshpit;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class CommandMoshpitStats implements CommandExecutor {

    private FileConfiguration moshpitFile = HelioSphereWorld.moshpitFileManager.getData();

    public CommandMoshpitStats(HelioSphereWorld plugin) {
        plugin.getCommand("moshpitstats").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("moshpitstats")) {
            if (!(sender instanceof Player && !((Entity) sender).getWorld().equals(Bukkit.getWorld("Moshpit")))) {
                Player target = null;

                if (args.length > 0)
                    target = Bukkit.getPlayer(args[0]);
                else if (sender instanceof Player)
                    target = (Player) sender;

                if (target == null)
                    MessageUtils.validPlayerError(sender);
                else {
                    UUID targetId = target.getUniqueId();

                    if (moshpitFile.getString("users." + targetId + ".deaths") != null
                            && moshpitFile.getString("users." + targetId + ".kills") != null) {
                        double kills = moshpitFile.getDouble("users." + targetId + ".kills");
                        double deaths = moshpitFile.getDouble("users." + targetId + ".deaths");
                        double kdr = moshpitFile.getDouble("users." + targetId + ".kdr");

                        MessageUtils.message(sender,
                                "&b-===-&3 Moshpit Stats:&f " + target.getDisplayName() + " &b-===-");
                        MessageUtils.message(sender, "&bKills:&f " + Math.round(kills * 1) / 1);
                        MessageUtils.message(sender, "&bDeaths:&f " + Math.round(deaths * 1) / 1);
                        MessageUtils.message(sender, "&bK/D ratio:&f " + kdr);
                    }
                }
            } else
                MessageUtils.configStringMessage(sender, "world_command_error", "<world>",
                        ((Entity) sender).getWorld().getName());
        }
        return false;
    }
}