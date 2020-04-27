package mc.rysty.heliosphereworld;

import org.bukkit.plugin.java.JavaPlugin;

import mc.rysty.heliosphereworld.classicsmp.CommandDeleteHome;
import mc.rysty.heliosphereworld.classicsmp.CommandHome;
import mc.rysty.heliosphereworld.classicsmp.CommandSetHome;
import mc.rysty.heliosphereworld.classicsmp.MultiplayerSleep;
import mc.rysty.heliosphereworld.classicsmp.PvPToggle;
import mc.rysty.heliosphereworld.classicsmp.SpawnBedMob;
import mc.rysty.heliosphereworld.classicsmp.WorldVersionCheck;
import mc.rysty.heliosphereworld.commands.BackCommand;
import mc.rysty.heliosphereworld.commands.HubCommand;
import mc.rysty.heliosphereworld.commands.SpawnCommand;
import mc.rysty.heliosphereworld.hub.HubPreventDamage;
import mc.rysty.heliosphereworld.hub.HubPreventModify;
import mc.rysty.heliosphereworld.hub.PlayerJoin;
import mc.rysty.heliosphereworld.hub.inventory.HubInventory;
import mc.rysty.heliosphereworld.hub.inventory.HubInventoryMove;
import mc.rysty.heliosphereworld.moshpit.CommandAutoEquip;
import mc.rysty.heliosphereworld.moshpit.CommandMoshpitStats;
import mc.rysty.heliosphereworld.moshpit.ListenerMoshpitSpawn;
import mc.rysty.heliosphereworld.moshpit.ListenerMoshpitStats;
import mc.rysty.heliosphereworld.utils.BackFileManager;
import mc.rysty.heliosphereworld.utils.ClassicSMPFileManager;
import mc.rysty.heliosphereworld.utils.MoshpitFileManager;

public class HelioSphereWorld extends JavaPlugin {

	private static HelioSphereWorld plugin;

	public static HelioSphereWorld getInstance() {
		return plugin;
	}

	public static ClassicSMPFileManager classicsmpFileManager = ClassicSMPFileManager.getInstance();
	public static BackFileManager backFileManager = BackFileManager.getInstance();
	public static MoshpitFileManager moshpitFileManager = MoshpitFileManager.getInstance();

	public void onEnable() {
		// Plugin setup.
		plugin = this;
		saveDefaultConfig();
		classicsmpFileManager.setup(this);
		backFileManager.setup(this);
		moshpitFileManager.setup(this);

		// General.
		new BackCommand(this);
		new SpawnCommand(this);
		new HubCommand(this);
		new PlayerJoin(this);

		// ClassicSMP-related.
		new CommandHome(this);
		new CommandSetHome(this);
		new CommandDeleteHome(this);
		new PvPToggle(this);
		new MultiplayerSleep(this);
		new SpawnBedMob(this);
		new WorldVersionCheck(this);

		// Moshpit-related.
		new CommandAutoEquip(this);
		new ListenerMoshpitSpawn(this);
		new ListenerMoshpitStats(this);
		new CommandMoshpitStats(this);

		// Hub-related.
		new HubInventory(this);
		new HubPreventModify(this);
		new HubPreventDamage(this);
		new HubInventoryMove(this);

		System.out.println("HS-World enabled");
	}

	public void onDisable() {
		System.out.println("HS-World disabled");
	}
}
