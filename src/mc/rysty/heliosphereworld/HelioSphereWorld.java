package mc.rysty.heliosphereworld;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import mc.rysty.heliosphereworld.classicsmp.CommandDeleteHome;
import mc.rysty.heliosphereworld.classicsmp.CommandHome;
import mc.rysty.heliosphereworld.classicsmp.CommandSetHome;
import mc.rysty.heliosphereworld.classicsmp.MultiplayerSleep;
import mc.rysty.heliosphereworld.classicsmp.PvPToggle;
import mc.rysty.heliosphereworld.classicsmp.SpawnBedMob;
import mc.rysty.heliosphereworld.classicsmp.WorldVersionCheck;
import mc.rysty.heliosphereworld.commands.HubCommand;
import mc.rysty.heliosphereworld.commands.SpawnCommand;
import mc.rysty.heliosphereworld.hub.HubNoBuild;
import mc.rysty.heliosphereworld.hub.HubNoDamage;
import mc.rysty.heliosphereworld.hub.PlayerAttributes;
import mc.rysty.heliosphereworld.hub.PlayerJoin;
import mc.rysty.heliosphereworld.hub.inventory.HubInventory;
import mc.rysty.heliosphereworld.hub.inventory.HubInventoryMove;
import mc.rysty.heliosphereworld.utils.HomesFileManager;
import mc.rysty.heliosphereworld.utils.SettingsManager;

public class HelioSphereWorld extends JavaPlugin {

	public static HelioSphereWorld plugin;

	public static HelioSphereWorld getInstance() {
		return plugin;
	}

	PluginManager pm = Bukkit.getPluginManager();
	SettingsManager settings = SettingsManager.getInstance();
	HomesFileManager homesFileManager = HomesFileManager.getInstance();

	public void onEnable() {
		// Plugin setup.
		plugin = this;
		saveDefaultConfig();
		settings.setup(this);
		homesFileManager.setup(this);

		// General commands.
		new SpawnCommand(this);
		new HubCommand(this);

		// General events.
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerAttributes(), this);

		// ClassicSMP-related commands and events.
		new CommandHome(this);
		new CommandSetHome(this);
		new CommandDeleteHome(this);
		new PvPToggle(this);
		pm.registerEvents(new PvPToggle(this), this);
		pm.registerEvents(new MultiplayerSleep(), this);
		pm.registerEvents(new SpawnBedMob(), this);
		pm.registerEvents(new WorldVersionCheck(), this);

		// Hub-related events.
		pm.registerEvents(new HubInventory(), this);
		pm.registerEvents(new HubNoBuild(), this);
		pm.registerEvents(new HubNoDamage(), this);
		pm.registerEvents(new HubInventoryMove(), this);
		// pm.registerEvents(new PlayerDeathRespawn(), this);

		System.out.println("HS-World enabled");
	}

	public void onDisable() {
		System.out.println("HS-World disabled");
	}

}
