package mc.rysty.heliosphereworld.hardcoresmp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;

public class ListenerHardcoreSmpSleep implements Listener {

    public ListenerHardcoreSmpSleep(HelioSphereWorld plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private int playerCount = 0;
    private int sleepingPlayerCount = 0;

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        World world = location.getWorld();

        if (world == Bukkit.getWorld("Hardcore_SMP")) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.getWorld() == Bukkit.getWorld("Hardcore_SMP")) {
                    playerCount++;

                    if (onlinePlayer.isSleeping())
                        sleepingPlayerCount++;
                }
            }
            long time = world.getTime();

            if (time > 12550 && time < 23460) {
                if (location.getBlock().getLightLevel() < 8)
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn -t random_night_mob 1 Hardcore_SMP"
                            + "," + location.getX() + "," + location.getY() + 1 + "," + location.getZ());

                if (sleepingPlayerCount >= 2 / 3 * playerCount)
                    world.setTime(23000);
            }
        }
        playerCount = 0;
        sleepingPlayerCount = 0;
    }
}