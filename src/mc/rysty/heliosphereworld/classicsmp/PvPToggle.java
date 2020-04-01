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
import mc.rysty.heliosphereworld.utils.SettingsManager;
import mc.rysty.heliosphereworld.utils.Utils;

public class PvPToggle implements CommandExecutor, Listener {

	private HelioSphereWorld plugin = HelioSphereWorld.getInstance();
	private FileConfiguration config = plugin.getConfig();
	private SettingsManager settings = SettingsManager.getInstance();
	private FileConfiguration data = settings.getData();

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

						if (worldName.equalsIgnoreCase("ClassicSMP")) {
							if (data.getString("players." + uuid + ".classicSMP.pvpDisabled") == "true") {
								data.set("players." + uuid + ".classicSMP.pvpDisabled", null);
								player.sendMessage(Utils.chat(config.getString("ClassicSMP.pvp_toggle_enabled")));
							} else if (data.getString("players." + uuid + ".classicSMP.pvpDisabled") == null) {
								data.set("players." + uuid + ".classicSMP.pvpDisabled", "true");
								player.sendMessage(Utils.chat(config.getString("ClassicSMP.pvp_toggle_disabled")));
							}
							settings.saveData();
						} else {
							player.sendMessage(Utils.chat(config.getString("ClassicSMP.world-error")));
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

		if (entity instanceof Player) {
			Player player = (Player) entity;
			UUID uuid = player.getUniqueId();
			String worldName = player.getWorld().getName();

			if (worldName.equalsIgnoreCase("ClassicSMP")) {
				if (data.getString("players." + uuid + ".classicSMP.pvpDisabled") == "true") {
					event.setCancelled(true);
					damager.sendMessage(Utils.chat(config.getString("ClassicSMP.damager_pvp_error")));
				}
			}
		}
		if (damager instanceof Player) {
			Player player = (Player) damager;
			UUID uuid = player.getUniqueId();
			String worldName = player.getWorld().getName();

			if (worldName.equalsIgnoreCase("ClassicSMP")) {
				if (data.getString("players." + uuid + ".classicSMP.pvpDisabled") == "true") {
					event.setCancelled(true);
					player.sendMessage(Utils.chat(config.getString("ClassicSMP.player_pvp_error")));
				}
			}
		}
	}
}
