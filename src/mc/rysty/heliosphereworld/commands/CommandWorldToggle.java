package mc.rysty.heliosphereworld.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class CommandWorldToggle implements CommandExecutor, TabCompleter, Listener {

    private HelioSphereWorld plugin = HelioSphereWorld.getInstance();

    public CommandWorldToggle(HelioSphereWorld plugin) {
        plugin.getCommand("worldtoggle").setExecutor(this);
        plugin.getCommand("worldtoggle").setTabCompleter(this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private HashMap<World, Boolean> joinableWorldMap = new HashMap<World, Boolean>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("worldtoggle")) {
            if (sender.hasPermission("hs.worldtoggle")) {
                World world = null;

                if (args.length == 1)
                    world = Bukkit.getWorld(args[0]);
                else
                    MessageUtils.argumentError(sender, "/worldtoggle <world>");

                if (world != null) {
                    if (joinableWorldMap.get(world) == null)
                        joinableWorldMap.put(world, false);
                    else if (joinableWorldMap.get(world))
                        joinableWorldMap.put(world, false);
                    else if (!joinableWorldMap.get(world))
                        joinableWorldMap.put(world, true);

                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (onlinePlayer.getWorld().equals(world) && !joinableWorldMap.get(world))
                            onlinePlayer.teleport(Bukkit.getWorld("Hub").getSpawnLocation());
                        MessageUtils
                                .configStringMessage(onlinePlayer,
                                        joinableWorldMap.get(world) ? "WorldToggleCommand.world-maintenance-disabled"
                                                : "WorldToggleCommand.world-maintenance-enabled",
                                        "<world>", world.getName());
                        onlinePlayer.playSound(onlinePlayer.getLocation(), "block.note_block.harp", 1, 1);
                    }
                } else
                    MessageUtils.configStringMessage(sender, "WorldToggleCommand.world-error");
            } else
                MessageUtils.noPermissionError(sender);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("hs.worldtoggle")) {
            if (args.length == 1) {
                List<String> worldNames = new ArrayList<>();

                for (World world : Bukkit.getWorlds())
                    worldNames.add(world.getName());
                return worldNames;
            }
        }
        return null;
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (joinableWorldMap.containsKey(world))
            if (!joinableWorldMap.get(world))
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    /*
                     * This task is delayed because the player is temporarily an operator as they
                     * are teleported, so them having the permission would always return true.
                     */
                    @Override
                    public void run() {
                        if (!player.hasPermission("hs.worldtoggle")) {
                            player.teleport(Bukkit.getWorld("Hub").getSpawnLocation());
                            MessageUtils.configStringMessage(player, "WorldToggleCommand.world-maintenance-enabled");
                        }
                    }
                }, 20);
    }
}