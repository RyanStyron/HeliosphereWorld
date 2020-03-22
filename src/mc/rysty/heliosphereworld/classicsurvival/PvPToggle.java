package mc.rysty.heliosphereworld.classicsurvival;

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
import mc.rysty.heliosphereworld.utils.SettingsManager;
import mc.rysty.heliosphereworld.utils.Utils;

public class PvPToggle implements CommandExecutor, Listener {

	HelioSphereWorld plugin = HelioSphereWorld.getInstance();
	FileConfiguration config = plugin.getConfig();
	SettingsManager settings = SettingsManager.getInstance();
	FileConfiguration data = settings.getData();

	public PvPToggle(HelioSphereWorld plugin) {
		plugin.getCommand("pvptoggle").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("pvptoggle")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (player.hasPermission("hs.pvptoggle")) {
					if (args.length == 0) {
						String worldName = player.getWorld().getName();
						UUID uuid = player.getUniqueId();

						if (worldName.equalsIgnoreCase("ClassicSurvival")) {
							if (data.getString("players." + uuid + ".classicSurvival.pvpDisabled") == "true") {
								data.set("players." + uuid + ".classicSurvival.pvpDisabled", null);
								player.sendMessage(Utils.chat(config.getString("ClassicSurvival.pvp_toggle_enabled")));
							} else if (data.getString("players." + uuid + ".classicSurvival.pvpDisabled") == null) {
								data.set("players." + uuid + ".classicSurvival.pvpDisabled", "true");
								player.sendMessage(Utils.chat(config.getString("ClassicSurvival.pvp_toggle_disabled")));
							}
							settings.saveData();
						} else {
							player.sendMessage(Utils.chat(config.getString("ClassicSurvival.world_error")));
						}
					} else {
						player.sendMessage(Utils.chat(config.getString("too_many_args_error")));
					}
				} else {
					player.sendMessage(Utils.chat(config.getString("no_perm_message")));
				}
			} else {
				sender.sendMessage(Utils.chat(config.getString("console_error_message")));
			}
		}
		return false;
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();

		if (damager instanceof Player) {
			if (entity instanceof Player) {
				Player player = (Player) entity;
				UUID uuid = player.getUniqueId();
				String worldName = player.getWorld().getName();

				if (worldName.equalsIgnoreCase("ClassicSurvival")) {
					if (data.getString("players." + uuid + ".classicSurvival.pvpDisabled") == "true") {
						event.setCancelled(true);
						damager.sendMessage(Utils.chat(config.getString("ClassicSurvival.damager_pvp_error")));
					}
				}
			}
		}
		if (damager instanceof Player) {
			if (entity instanceof Player) {
				Player player = (Player) damager;
				UUID uuid = player.getUniqueId();
				String worldName = player.getWorld().getName();

				if (worldName.equalsIgnoreCase("ClassicSurvival")) {
					if (data.getString("players." + uuid + ".classicSurvival.pvpDisabled") == "true") {
						event.setCancelled(true);
						player.sendMessage(Utils.chat(config.getString("ClassicSurvival.player_pvp_error")));
					}
				}
			}
		}
	}
}
