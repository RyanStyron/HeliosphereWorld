package mc.rysty.heliosphereworld.moshpit;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class MoshpitCombatLog implements Listener {

	private static HelioSphereWorld plugin = HelioSphereWorld.getInstance();

	public MoshpitCombatLog(HelioSphereWorld plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	private static HashMap<Player, Boolean> playerInCombat = new HashMap<Player, Boolean>();
	private static HashMap<Player, Integer> combatCooldown = new HashMap<Player, Integer>();

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		Location location = entity.getLocation();
		World world = location.getWorld();
		Entity entityDamager = event.getDamager();

		if (entity.getWorld().equals(Bukkit.getWorld("Moshpit")))
			if (location.distanceSquared(world.getSpawnLocation()) > 361)
				if (entity instanceof Player && entityDamager instanceof Player) {
					Player player = (Player) entity;
					Player damager = (Player) entityDamager;

					playerInCombat.put(player, true);
					playerInCombat.put(damager, true);
					combatCooldown.put(player, 20);
					combatCooldown.put(damager, 20);
				}
	}

	/*
	 * In the event that a player is in the Moshpit and is not a combat key, which
	 * would cause the plugin and player's scoreboard to error, they are teleported
	 * to the hub (also tested for in the method
	 * onPlayerCommandPreprocess(PlayerCommandPreprocessEvent)).
	 */
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		if (player.getWorld().equals(Bukkit.getWorld("Moshpit")))
			if (!MoshpitCombatLog.playerInCombat.containsKey(player))
				if (Bukkit.getWorld("Hub") != null)
					player.teleport(Bukkit.getWorld("Hub").getSpawnLocation());
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		playerInCombat.put(event.getPlayer(), false);
	}

	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();

		if (player.getWorld().equals(Bukkit.getWorld("Moshpit")))
			playerInCombat.put(player, false);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		if (player.getWorld().equals(Bukkit.getWorld("Moshpit")))
			if (isInCombat(player)) {
				player.setHealth(0.0);
				playerInCombat.remove(player);
			}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();

		if (player.getWorld().equals(Bukkit.getWorld("Moshpit")))
			if (isInCombat(player))
				playerInCombat.put(player, false);
	}

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		Location location = player.getLocation();
		World world = location.getWorld();
		String message = event.getMessage();

		if (world.equals(Bukkit.getWorld("Moshpit"))) {
			if (!MoshpitCombatLog.playerInCombat.containsKey(player)) {
				if (Bukkit.getWorld("Hub") != null)
					player.teleport(Bukkit.getWorld("Hub").getSpawnLocation());
			} else if (isInCombat(player))
				if (!message.startsWith("/console") && location.distanceSquared(world.getSpawnLocation()) > 361) {
					event.setCancelled(true);
					MessageUtils.configStringMessage(player, "Moshpit.combat-log-error");
				}
		}
	}

	public static boolean isInCombat(Player player) {
		return playerInCombat.get(player);
	}

	/* Returns the amount of seconds left that a player is combat logged. */
	public static int getRemainingCombatTime(Player player) {
		if (isInCombat(player)) {
			int cooldownTime = combatCooldown.get(player);

			Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				@Override
				public void run() {
					if (cooldownTime > 0)
						combatCooldown.put(player, cooldownTime - 1);
				}
			}, 0, 20);
		}

		if (combatCooldown.get(player) == 0)
			playerInCombat.put(player, false);

		return combatCooldown.get(player);
	}
}