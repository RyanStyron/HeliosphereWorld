package mc.rysty.heliosphereworld.moshpit;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;

public class ListenerMoshpitSpawn implements Listener {

    public ListenerMoshpitSpawn(HelioSphereWorld plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        Location entityLocation = entity.getLocation();
        World world = entityLocation.getWorld();

        if (world.equals(Bukkit.getWorld("Moshpit")))
            if (entityLocation.distanceSquared(world.getSpawnLocation()) <= 361)
                event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        Location entityLocation = entity.getLocation();
        Location damagerLocation = damager.getLocation();
        World world = entityLocation.getWorld();

        if (world.equals(Bukkit.getWorld("Moshpit")))
            if (entityLocation.distanceSquared(world.getSpawnLocation()) <= 361
                    || damagerLocation.distanceSquared(world.getSpawnLocation()) <= 361)
                event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (world.equals(Bukkit.getWorld("Moshpit")))
            if (player.getGameMode() != GameMode.CREATIVE)
                event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInventoryClickEvent(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            World world = player.getWorld();

            if (world.equals(Bukkit.getWorld("Moshpit")))
                if (player.getGameMode() != GameMode.CREATIVE)
                    event.setCancelled(true);
        }
    }
}