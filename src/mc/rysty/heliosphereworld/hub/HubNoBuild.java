package mc.rysty.heliosphereworld.hub;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.Utils;

public class HubNoBuild implements Listener {

	HelioSphereWorld plugin = HelioSphereWorld.getInstance();
	FileConfiguration config = plugin.getConfig();
	String noPermMessage = Utils.chat(config.getString("no_perm_message"));
	String noBlockModifyMessage = Utils.chat(config.getString("Spawn.no_block_modify"));

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		boolean spawnBuild = p.hasPermission("hs.spawnbuild");
		boolean creative = p.getGameMode().equals(GameMode.CREATIVE); 

		if (p.getWorld().getName().equalsIgnoreCase("Hub")) {
			if (!creative || !spawnBuild) {
				e.setCancelled(true);
				if (!spawnBuild) {
					
				} else {
					p.sendMessage(noBlockModifyMessage);
				}
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		boolean spawnBuild = p.hasPermission("hs.spawnbuild");
		boolean creative = p.getGameMode().equals(GameMode.CREATIVE); 

		if (p.getWorld().getName().equalsIgnoreCase("Hub")) {
			if (!creative || !spawnBuild) {
				e.setCancelled(true);
				if (!spawnBuild) {
					
				} else {
					p.sendMessage(noBlockModifyMessage);
				}
			}
		}
	}

	@EventHandler
	public void onBarrierBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();

		if (p.getWorld().getName().equalsIgnoreCase("Hub")) {
			if (e.getBlock().getType() == Material.BARRIER) {
				if (!p.hasPermission("hs.barrierbreak")) {
					e.setCancelled(true);
					p.sendMessage(noPermMessage);
				}
			}
		}
	}

	@EventHandler
	public void onPickUp(EntityPickupItemEvent e) {
		LivingEntity p = e.getEntity();
		boolean spawnBuild = p.hasPermission("hs.spawnbuild");
		boolean creative = ((HumanEntity) p).getGameMode().equals(GameMode.CREATIVE);		

		if (p.getWorld().getName().equalsIgnoreCase("Hub")) {
			if (!creative || !spawnBuild) {
				e.setCancelled(true);
			}
		}
	}
}
