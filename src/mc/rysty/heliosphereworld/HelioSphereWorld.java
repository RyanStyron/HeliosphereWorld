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

	private static HelioSphereWorld plugin;

	public static HelioSphereWorld getInstance() {
		return plugin;
	}

	private PluginManager pluginManager = Bukkit.getPluginManager();
	private SettingsManager settings = SettingsManager.getInstance();
	private HomesFileManager homesFileManager = HomesFileManager.getInstance();

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
		pluginManager.registerEvents(new PlayerJoin(), this);
		pluginManager.registerEvents(new PlayerAttributes(), this);

		// ClassicSMP-related commands and events.
		new CommandHome(this);
		new CommandSetHome(this);
		new CommandDeleteHome(this);
		new PvPToggle(this);
		pluginManager.registerEvents(new PvPToggle(this), this);
		pluginManager.registerEvents(new MultiplayerSleep(), this);
		pluginManager.registerEvents(new SpawnBedMob(), this);
		pluginManager.registerEvents(new WorldVersionCheck(), this);

		// Hub-related events.
		pluginManager.registerEvents(new HubInventory(), this);
		pluginManager.registerEvents(new HubNoBuild(), this);
		pluginManager.registerEvents(new HubNoDamage(), this);
		pluginManager.registerEvents(new HubInventoryMove(), this);

		System.out.println("HS-World enabled");
	}

	public void onDisable() {
		System.out.println("HS-World disabled");
	}

}
