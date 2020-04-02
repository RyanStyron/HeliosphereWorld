package mc.rysty.heliosphereworld.commands;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.BackFileManager;

public class BackCommand implements CommandExecutor, Listener {

    private BackFileManager backFileManager = BackFileManager.getInstance();
    private FileConfiguration backFile = backFileManager.getData();

    public BackCommand(HelioSphereWorld plugin) {
        plugin.getCommand("back").setExecutor(this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("back")) {

        }
        return false;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("hs.back"))
            setLastLocation(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("hs.back"))
            setLastLocation(player);
    }

    private void setLastLocation(Player player) {
        UUID playerId = player.getUniqueId();
        Location location = player.getLocation();
        World world = player.getWorld();
        double locationX = location.getX();
        double locationY = location.getY();
        double locationZ = location.getZ();
        float pitch = location.getPitch();
        float yaw = location.getYaw();
        String lastLocationString = "player." + playerId + ".lastlocation.";
        
        backFile.set(lastLocationString + "world", world.getName());
        backFile.set(lastLocationString + "x", locationX);
        backFile.set(lastLocationString + "y", locationY);
        backFile.set(lastLocationString + "z", locationZ);
        backFile.set(lastLocationString + "pitch", pitch);
        backFile.set(lastLocationString + "yaw", yaw);
        backFileManager.saveData();
    }
}