package mc.rysty.heliosphereworld.classicsmp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import mc.rysty.heliosphereworld.utils.Utils;
import mc.rysty.heliosphereworld.utils.VersionUtils;

public class WorldVersionCheck implements Listener {

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld() == Bukkit.getWorld("ClassicSMP")) {
            if (VersionUtils.getPlayerVersion(player) != VersionUtils.getLatest()) {
                if (Bukkit.getWorld("Hub") != null)
                    player.teleport(Bukkit.getWorld("Hub").getSpawnLocation());
                Utils.configStringMessage(player, "ClassicSMP.version-error");
            }
        }
    }
}