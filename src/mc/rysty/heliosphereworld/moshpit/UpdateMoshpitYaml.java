package mc.rysty.heliosphereworld.moshpit;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MoshpitFileManager;

public class UpdateMoshpitYaml implements Listener {

    private MoshpitFileManager moshpitFileManager = HelioSphereWorld.moshpitFileManager;
    private FileConfiguration moshpitFile = moshpitFileManager.getData();

    public UpdateMoshpitYaml(HelioSphereWorld plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (!player.hasPlayedBefore()) {
            moshpitFile.set("users." + playerId + ".deaths", 0);
            moshpitFile.set("users." + playerId + ".kills", 0);
            moshpitFile.set("users." + playerId + ".kdr", 0);
            moshpitFileManager.saveData();
        }
    }
}