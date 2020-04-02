package mc.rysty.heliosphereworld.hub;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class HubPreventModify implements Listener {

	public HubPreventModify(HelioSphereWorld plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		boolean spawnBuildPermission = player.hasPermission("hs.spawnbuild");
		boolean creative = player.getGameMode().equals(GameMode.CREATIVE);

		if (player.getWorld().getName().equalsIgnoreCase("Hub")) {
			if (!creative || !spawnBuildPermission) {
				event.setCancelled(true);
				if (!spawnBuildPermission) {
				} else {
					MessageUtils.configStringMessage(player, "Spawn.no_block_modify");
				}
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		boolean spawnBuildPermission = player.hasPermission("hs.spawnbuild");
		boolean creative = player.getGameMode().equals(GameMode.CREATIVE);

		if (player.getWorld().getName().equalsIgnoreCase("Hub")) {
			if (!creative || !spawnBuildPermission) {
				event.setCancelled(true);
				if (!spawnBuildPermission) {
				} else {
					MessageUtils.configStringMessage(player, "Spawn.no_block_modify");
				}
			}
		}
	}

	@EventHandler
	public void onBarrierBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();

		if (event.getBlock().getType() == Material.BARRIER) {
			if (!player.hasPermission("hs.barrierbreak")) {
				event.setCancelled(true);
				MessageUtils.noPermissionError(player);
			}
		}
	}

	@EventHandler
	public void onItemPickUp(EntityPickupItemEvent event) {
		LivingEntity livingEntity = event.getEntity();
		boolean spawnBuildPermission = livingEntity.hasPermission("hs.spawnbuild");
		boolean creative = ((HumanEntity) livingEntity).getGameMode().equals(GameMode.CREATIVE);

		if (livingEntity.getWorld().getName().equalsIgnoreCase("Hub")) {
			if (!creative || !spawnBuildPermission) {
				event.setCancelled(true);
			}
		}
	}
}
