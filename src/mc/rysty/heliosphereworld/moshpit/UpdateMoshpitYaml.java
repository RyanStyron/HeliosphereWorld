package mc.rysty.heliosphereworld.moshpit;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MoshpitFileManager;

public class UpdateMoshpitYaml implements Listener {

    private MoshpitFileManager moshpitFileManager = HelioSphereWorld.moshpitFileManager;
    private FileConfiguration moshpitFile = moshpitFileManager.getData();

    public UpdateMoshpitYaml(HelioSphereWorld plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (player.getWorld().equals(Bukkit.getWorld("Moshpit"))) {
            if (moshpitFile.getConfigurationSection("users." + playerId + ".deaths") == null)
                moshpitFile.set("users." + playerId + ".deaths", 0);
            if (moshpitFile.getConfigurationSection("users." + playerId + ".kills") == null)
                moshpitFile.set("users." + playerId + ".kills", 0);
            moshpitFileManager.saveData();
        }
    }
}