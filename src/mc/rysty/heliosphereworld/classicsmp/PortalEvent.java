package  mc.rysty.heliosphereworld.classicsmp;

import org.bukkit.event.Listener;

import mc.rysty.heliosphereworld.HelioSphereWorld;

public class PortalEvent implements Listener {
    
    public PortalEvent(HelioSphereWorld plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    // @EventHandler
    // public void onPortalEnter(PlayerEnterPortalEvent event) {

    // }
}