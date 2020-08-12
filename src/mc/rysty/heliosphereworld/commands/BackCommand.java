package mc.rysty.heliosphereworld.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.managers.BackFileManager;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class BackCommand implements CommandExecutor, Listener {

    private BackFileManager backFileManager = BackFileManager.getInstance();
    private FileConfiguration backFile = backFileManager.getData();

    public BackCommand(HelioSphereWorld plugin) {
        plugin.getCommand("back").setExecutor(this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("back")) {
            if (sender instanceof Player) {
                if (args.length == 0) {
                    Player player = (Player) sender;
                    UUID playerId = player.getUniqueId();

                    if (player.hasPermission("hs.back") || player.getWorld().equals(Bukkit.getWorld("ClassicSMP"))) {
                        if (backFile.getString("players." + playerId + ".lastlocation") != null) {
                            String lastLocationString = "players." + playerId + ".lastlocation.";
                            World locationWorld = Bukkit.getWorld(backFile.getString(lastLocationString + "world"));
                            double locationX = backFile.getDouble(lastLocationString + "x");
                            double locationY = backFile.getDouble(lastLocationString + "y");
                            double locationZ = backFile.getDouble(lastLocationString + "z");
                            float locationPitch = (float) backFile.getDouble(lastLocationString + "pitch");
                            float locationYaw = (float) backFile.getDouble(lastLocationString + "yaw");
                            Location lastLocation = new Location(locationWorld, locationX, locationY, locationZ);

                            lastLocation.setPitch(locationPitch);
                            lastLocation.setYaw(locationYaw);
                            player.teleport(lastLocation);
                            MessageUtils.configStringMessage(sender, "BackCommand.back-message");
                        } else
                            MessageUtils.configStringMessage(sender, "BackCommand.back-error");
                    } else if (!player.hasPermission("hs.back"))
                        MessageUtils.noPermissionError(sender);
                } else
                    MessageUtils.configStringMessage(sender, "BackCommand.argument-error");
            } else
                MessageUtils.consoleError();
        }
        return false;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Location fromLocation = event.getFrom();
        Location toLocation = event.getTo();

        if (!(fromLocation.getWorld() == toLocation.getWorld() && fromLocation.distanceSquared(toLocation) <= 4))
            setLastLocation(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        setLastLocation(player);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

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
        String lastLocationString = "players." + playerId + ".lastlocation.";

        backFile.set(lastLocationString + "world", world.getName());
        backFile.set(lastLocationString + "x", locationX);
        backFile.set(lastLocationString + "y", locationY);
        backFile.set(lastLocationString + "z", locationZ);
        backFile.set(lastLocationString + "pitch", pitch);
        backFile.set(lastLocationString + "yaw", yaw);
        backFileManager.saveData();
    }
}