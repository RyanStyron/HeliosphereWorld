package mc.rysty.heliosphereworld.moshpit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class ListenerMoshpitSoup implements Listener {

    private HelioSphereWorld plugin = HelioSphereWorld.getInstance();

    public ListenerMoshpitSoup(HelioSphereWorld plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        Location location = player.getLocation();
        World world = location.getWorld();
        PlayerInventory inventory = player.getInventory();

        if (world.equals(Bukkit.getWorld("Moshpit")))
            if (location.distanceSquared(world.getSpawnLocation()) > 361)
                if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
                    ItemStack item = inventory.getItemInMainHand();

                    if (item == null)
                        return;
                    Material material = item.getType();

                    if (material == null)
                        return;

                    if (material.equals(Material.MUSHROOM_STEW)) {
                        int inventorySlot = inventory.getHeldItemSlot();

                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                ItemStack stew = new ItemStack(Material.MUSHROOM_STEW, 1);
                                ItemMeta itemMeta = stew.getItemMeta();

                                itemMeta.setDisplayName(MessageUtils.convertChatColors("&bMushroom Stew"));
                                stew.setItemMeta(itemMeta);
                                /*
                                 * The following line does not use the world variable because the Moshpit is
                                 * already the stored world, so it would always return true.
                                 */
                                if (player.getWorld().equals(Bukkit.getWorld("Moshpit")))
                                    inventory.setItem(inventorySlot, stew);
                            }
                        }, 100);
                    }
                }
    }
}