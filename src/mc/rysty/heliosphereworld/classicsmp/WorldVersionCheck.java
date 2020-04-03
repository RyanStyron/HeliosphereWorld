package mc.rysty.heliosphereworld.classicsmp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;
import mc.rysty.heliosphereworld.utils.VersionUtils;

public class WorldVersionCheck implements Listener {

    public WorldVersionCheck(HelioSphereWorld plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld() == Bukkit.getWorld("ClassicSMP")) {
            String serverVersion = VersionUtils.getServerVersion();
            String playerVersion = VersionUtils.getPlayerVersion(player);

            if (!playerVersion.equals(serverVersion)) {
                if (Bukkit.getWorld("Hub") != null)
                    player.teleport(Bukkit.getWorld("Hub").getSpawnLocation());
                MessageUtils.configStringMessage(player, "ClassicSMP.version-error");
            }
        }
    }
}