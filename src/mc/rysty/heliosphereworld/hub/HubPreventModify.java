package mc.rysty.heliosphereworld.hub;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;

public class HubPreventModify implements Listener {

	public HubPreventModify(HelioSphereWorld plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();

		if (entity.getWorld().equals(Bukkit.getWorld("Hub")))
			event.setCancelled(true);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();

		if (!canHubBuild(player))
			event.setCancelled(true);

		if (event.getBlock().getType() == Material.BARRIER)
			if (!player.hasPermission("hs.barrierbreak"))
				event.setCancelled(true);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (!canHubBuild(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onEntityPickupItem(EntityPickupItemEvent event) {
		LivingEntity entity = event.getEntity();

		if (entity instanceof Player)
			if (!canHubBuild((Player) entity))
				event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (!canHubBuild(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (!canHubBuild(event.getPlayer()))
			if (event.getRightClicked() instanceof ItemFrame)
				event.setCancelled(true);
	}

	private boolean canHubBuild(Player player) {
		GameMode gamemode = player.getGameMode();
		boolean canSpawnBuild = player.hasPermission("hs.spawnbuild");

		if (player.getWorld().equals(Bukkit.getWorld("Hub")))
			if (gamemode != GameMode.CREATIVE || !canSpawnBuild)
				return false;
		return true;
	}
}
