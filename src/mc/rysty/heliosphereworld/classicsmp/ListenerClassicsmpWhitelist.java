package mc.rysty.heliosphereworld.classicsmp;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class ListenerClassicsmpWhitelist implements Listener {

    private FileConfiguration classicsmpFile = HelioSphereWorld.classicsmpFileManager.getData();

    public ListenerClassicsmpWhitelist(HelioSphereWorld plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        World world = player.getWorld();

        if (world.equals(Bukkit.getWorld("Tutorial"))) {
            if (!classicsmpFile.getStringList("whitelist").contains(playerName)) {
                player.teleport(Bukkit.getWorld("Hub").getSpawnLocation());
                MessageUtils.configStringMessage(player, "ClassicSMP.whitelist-error");
            }
        }
    }
}