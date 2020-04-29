package mc.rysty.heliosphereworld.moshpit;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

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

                if (moshpitFile.getString("users." + playerId + ".displayname") != playerDisplayname)
                    moshpitFile.set("users." + playerId + ".displayname", playerDisplayname);
                moshpitFile.set("users." + playerId + ".deaths",
                        moshpitFile.getDouble("users." + playerId + ".deaths") + 1.0);
                moshpitFile.set("users." + playerId + ".kdr", moshpitFile.getDouble("users." + playerId + ".kills")
                        / moshpitFile.getDouble("users." + playerId + ".deaths"));
                moshpitFile.set("users." + killerId + ".kills",
                        moshpitFile.getDouble("users." + killerId + ".kills") + 1.0);
                moshpitFile.set("users." + killerId + ".kdr", moshpitFile.getDouble("users." + killerId + ".kills")
                        / moshpitFile.getDouble("users." + killerId + ".deaths"));
                moshpitFileManager.saveData();
            }
        }
    }
}