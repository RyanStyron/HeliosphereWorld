package mc.rysty.heliosphereworld.moshpit;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MoshpitFileManager;

public class ListenerMoshpitStats implements Listener {

    private MoshpitFileManager moshpitFileManager = HelioSphereWorld.moshpitFileManager;
    private FileConfiguration moshpitFile = moshpitFileManager.getData();

    public ListenerMoshpitStats(HelioSphereWorld plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (player.getWorld().equals(Bukkit.getWorld("Moshpit"))) {
            Player killer = event.getEntity().getKiller();
            GameMode creative = GameMode.CREATIVE;

            if (player.getGameMode() != creative || killer.getGameMode() != creative) {
                UUID playerId = player.getUniqueId();
                UUID killerId = killer.getUniqueId();
                String playerDisplayname = player.getDisplayName();
                double playerKills = moshpitFile.getDouble("users." + playerId + ".kills");
                double playerDeaths = moshpitFile.getDouble("users." + playerId + ".deaths");
                double killerKills = moshpitFile.getDouble("users." + killerId + ".kills");
                double killerDeaths = moshpitFile.getDouble("users." + killerId + ".deaths");

                if (moshpitFile.getString("users." + playerId + ".displayname") != playerDisplayname)
                    moshpitFile.set("users." + playerId + ".displayname", playerDisplayname);
                moshpitFile.set("users." + playerId + ".deaths", playerDeaths + 1.0);
                moshpitFile.set("users." + playerId + ".kdr",
                        playerDeaths != 0.0 ? playerKills / playerDeaths : playerKills);
                moshpitFile.set("users." + killerId + ".kills", killerKills + 1.0);
                moshpitFile.set("users." + killerId + ".kdr",
                        killerDeaths != 0.0 ? killerKills / killerDeaths : killerKills);
                moshpitFileManager.saveData();
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (!player.hasPlayedBefore() && moshpitFile.getString("users." + playerId + ".displayname") == null) {
            moshpitFile.set("users." + playerId + ".displayname", player.getDisplayName());
            moshpitFile.set("users." + playerId + ".deaths", 0);
            moshpitFile.set("users." + playerId + ".kills", 0);
            moshpitFile.set("users." + playerId + ".kdr", 0);
            moshpitFileManager.saveData();
        }
    }
}