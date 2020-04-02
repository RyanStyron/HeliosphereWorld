package mc.rysty.heliosphereworld.classicsmp;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.ClassicSMPFileManager;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class PvPToggle implements CommandExecutor, Listener {

	private ClassicSMPFileManager classicsmpFileManager = ClassicSMPFileManager.getInstance();
	private FileConfiguration classicsmpFile = classicsmpFileManager.getData();

	public PvPToggle(HelioSphereWorld plugin) {
		plugin.getCommand("pvptoggle").setExecutor(this);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("pvptoggle")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args.length == 0) {
					String worldName = player.getWorld().getName();
					UUID uuid = player.getUniqueId();

					if (worldName.equalsIgnoreCase("ClassicSMP")) {
						if (classicsmpFile.getString("players." + uuid + ".pvpDisabled") == "true") {
							classicsmpFile.set("players." + uuid + ".pvpDisabled", null);
							MessageUtils.configStringMessage(player, "ClassicSMP.pvp_toggle_enabled");
						} else if (classicsmpFile.getString("players." + uuid + ".pvpDisabled") == null) {
							classicsmpFile.set("players." + uuid + ".pvpDisabled", "true");
							MessageUtils.configStringMessage(player, "ClassicSMP.pvp_toggle_disabled");
						}
						classicsmpFileManager.saveData();
					} else
						MessageUtils.configStringMessage(player, "ClassicSMP.world-error");
				} else
					MessageUtils.configStringMessage(player, "PvPToggleCommand.argument-error");
			} else
				MessageUtils.consoleError();
		}
		return false;
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();

		if (entity instanceof Player && damager instanceof Player) {
			Player playerEntity = (Player) entity;
			Player playerDamager = (Player) damager;
			UUID playerId = playerEntity.getUniqueId();
			UUID damagerId = playerDamager.getUniqueId();
			String worldName = entity.getWorld().getName();

			if (worldName.equalsIgnoreCase("ClassicSMP")) {
				if (classicsmpFile.getString("players." + playerId + ".pvpDisabled") == "true") {
					event.setCancelled(true);
					MessageUtils.configStringMessage(playerDamager, "ClassicSMP.damager_pvp_error");
				}
				if (classicsmpFile.getString("players." + damagerId + ".pvpDisabled") == "true") {
					event.setCancelled(true);
					MessageUtils.configStringMessage(playerDamager, "ClassicSMP.player_pvp_error");
				}
			}
		}
	}
}
