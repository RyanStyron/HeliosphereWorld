package mc.rysty.heliosphereworld.moshpit;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class ListenerMoshpitKitSelect implements Listener {

    private FileConfiguration moshpitFile = HelioSphereWorld.moshpitFileManager.getData();

    public ListenerMoshpitKitSelect(HelioSphereWorld plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (world.equals(Bukkit.getWorld("Moshpit"))) {
            String command = event.getMessage();
            UUID playerId = player.getUniqueId();
            double fileKills = moshpitFile.getDouble("users." + playerId + ".kills");
            double fileDeaths = moshpitFile.getDouble("users." + playerId + ".deaths");
            int kills = (int) fileKills;
            int deaths = (int) fileDeaths;

            if (command.startsWith("/pigman-equip")) {
                if (kills < 50) {
                    MessageUtils.configStringMessage(player, "Moshpit.kit-error", "<kills>", "" + (50 - kills));
                    event.setCancelled(true);
                }
            } else if (command.startsWith("/witch-equip")) {
                if (kills < 120) {
                    MessageUtils.configStringMessage(player, "Moshpit.kit-error", "<kills>", "" + (120 - kills));
                    event.setCancelled(true);
                }
            } else if (command.startsWith("/wither_skeleton-equip")) {
                if (kills < 330) {
                    MessageUtils.configStringMessage(player, "Moshpit.kit-error", "<kills>", "" + (330 - kills));
                    event.setCancelled(true);
                }
            } else if (command.startsWith("/vampire-equip")) {
                if (kills < 700) {
                    MessageUtils.configStringMessage(player, "Moshpit.kit-error", "<kills>", "" + (700 - kills));
                    event.setCancelled(true);
                }
            } else if (command.startsWith("/evoker-equip")) {
                if (kills < 1550) {
                    MessageUtils.configStringMessage(player, "Moshpit.kit-error", "<kills>", "" + (1550 - kills));
                    event.setCancelled(true);
                }
            } else if (command.startsWith("/creeper-equip")) {
                if (deaths < 500) {
                    MessageUtils.configStringMessage(player, "Moshpit.kit-error-deaths", "<deaths>",
                            "" + (500 - deaths));
                    event.setCancelled(true);
                }
            } else if (command.startsWith("/chicken-equip")) {
                if (!player.getScoreboardTags().contains("unlockedChicken")) {
                    MessageUtils.configStringMessage(player, "Moshpit.kit-error-other");
                    event.setCancelled(true);
                }
            } else if (command.startsWith("/pufferfish-equip")) {
                if (kills < 2500) {
                    MessageUtils.configStringMessage(player, "Moshpit.item-error", "<kills>", "" + (2500 - kills));
                    event.setCancelled(true);
                }
            }
        }
    }
}