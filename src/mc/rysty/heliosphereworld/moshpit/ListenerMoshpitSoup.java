package mc.rysty.heliosphereworld.moshpit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MathUtils;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class ListenerMoshpitSoup implements Listener {

    private HelioSphereWorld plugin = HelioSphereWorld.getInstance();

    public ListenerMoshpitSoup(HelioSphereWorld plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private HashMap<Player, List<Integer>> playerInventorySlotsMap = new HashMap<Player, List<Integer>>();

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        Location location = player.getLocation();
        World world = location.getWorld();
        PlayerInventory inventory = player.getInventory();

        if (world.equals(Bukkit.getWorld("Moshpit"))) {
            if (location.distanceSquared(world.getSpawnLocation()) > 361) {
                if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
                    ItemStack item = inventory.getItemInMainHand();

                    if (item == null)
                        return;
                    Material material = item.getType();

                    if (material == null)
                        return;

                    if (material.equals(Material.MUSHROOM_STEW)) {
                        double playerMaxHealth = player.getMaxHealth();
                        double playerHealth = player.getHealth();
                        int inventorySlot = inventory.getHeldItemSlot();
                        ItemStack bowl = new ItemStack(Material.BOWL, 1);

                        if (playerHealth + 6.0 <= playerMaxHealth)
                            player.setHealth(playerHealth + 6.0);
                        else
                            player.setHealth(playerHealth + (playerMaxHealth - playerHealth));
                        inventory.setItem(inventorySlot, bowl);

                        if (playerInventorySlotsMap.get(player) == null) {
                            List<Integer> inventorySlots = new ArrayList<Integer>();

                            playerInventorySlotsMap.put(player, inventorySlots);
                        }
                        playerInventorySlotsMap.get(player).add(inventorySlot);

                        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                            if (onlinePlayer.getWorld().equals(Bukkit.getWorld("Moshpit")))
                                onlinePlayer.playSound(location, Sound.ENTITY_GENERIC_EAT, 1.0F, 1.0F);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                /*
                                 * The following line does not use the world or location variable because the
                                 * Moshpit is already the stored world as is their location, so it would always
                                 * return true.
                                 */
                                if (player.getWorld().equals(Bukkit.getWorld("Moshpit"))) {
                                    if (playerInventorySlotsMap.get(player).contains(inventorySlot)) {
                                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                                        inventory.setItem(inventorySlot, getStewItem());
                                        playerInventorySlotsMap.get(player).remove(MathUtils
                                                .findIndex(playerInventorySlotsMap.get(player), inventorySlot));
                                    }
                                } else
                                    playerInventorySlotsMap.remove(player);
                            }
                        }, 600);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getKiller();
        List<Integer> inventorySlots = playerInventorySlotsMap.get(player);

        if (player.getWorld().equals(Bukkit.getWorld("Moshpit"))) {
            if (inventorySlots != null && inventorySlots.size() > 0) {
                int inventorySlot = inventorySlots.get(0);

                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                player.getInventory().setItem(inventorySlot, getStewItem());
                playerInventorySlotsMap.get(player)
                        .remove(MathUtils.findIndex(playerInventorySlotsMap.get(player), inventorySlot));
            }
        }
    }

    private ItemStack getStewItem() {
        ItemStack stew = new ItemStack(Material.MUSHROOM_STEW, 1);
        ItemMeta itemMeta = stew.getItemMeta();

        itemMeta.setDisplayName(MessageUtils.convertChatColors("&bMushroom Stew"));
        stew.setItemMeta(itemMeta);

        return stew;
    }
}