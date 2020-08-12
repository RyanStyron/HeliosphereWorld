package mc.rysty.heliosphereworld;

import org.bukkit.plugin.java.JavaPlugin;

import mc.rysty.heliosphereworld.commands.BackCommand;
import mc.rysty.heliosphereworld.commands.CommandDeleteWarp;
import mc.rysty.heliosphereworld.commands.CommandSetWarp;
import mc.rysty.heliosphereworld.commands.CommandWarp;
import mc.rysty.heliosphereworld.hub.HubCommand;
import mc.rysty.heliosphereworld.commands.SpawnCommand;
import mc.rysty.heliosphereworld.hub.HubInventory;
import mc.rysty.heliosphereworld.hub.HubPreventModify;
import mc.rysty.heliosphereworld.moshpit.CommandAutoEquip;
import mc.rysty.heliosphereworld.moshpit.CommandMoshpitSoup;
import mc.rysty.heliosphereworld.moshpit.CommandMoshpitStats;
import mc.rysty.heliosphereworld.moshpit.ListenerMoshpitKitSelect;
import mc.rysty.heliosphereworld.moshpit.ListenerMoshpitSoup;
import mc.rysty.heliosphereworld.moshpit.ListenerMoshpitSpawn;
import mc.rysty.heliosphereworld.moshpit.ListenerMoshpitStats;
import mc.rysty.heliosphereworld.moshpit.MoshpitCombatLog;
import mc.rysty.heliosphereworld.moshpit.MoshpitScoreboard;
import mc.rysty.heliosphereworld.moshpit.leaderboard.CommandMoshpitLeaderboardDeaths;
import mc.rysty.heliosphereworld.moshpit.leaderboard.CommandMoshpitLeaderboardKdr;
import mc.rysty.heliosphereworld.moshpit.leaderboard.CommandMoshpitLeaderboardKills;
import mc.rysty.heliosphereworld.moshpit.leaderboard.CommandMoshpitLeaderboardStreak;
import mc.rysty.heliosphereworld.utils.managers.BackFileManager;
import mc.rysty.heliosphereworld.utils.managers.MoshpitFileManager;
import mc.rysty.heliosphereworld.utils.managers.WarpFileManager;

public class HelioSphereWorld extends JavaPlugin {

	private static HelioSphereWorld plugin;

	public static HelioSphereWorld getInstance() {
		return plugin;
	}

	public static BackFileManager backFileManager = BackFileManager.getInstance();
	public static MoshpitFileManager moshpitFileManager = MoshpitFileManager.getInstance();
	public static WarpFileManager warpFileManager = WarpFileManager.getInstance();

	public void onEnable() {
		/* Plugin setup. */
		plugin = this;
		saveDefaultConfig();
		backFileManager.setup(this);
		moshpitFileManager.setup(this);
		warpFileManager.setup(this);

		/* General. */
		new BackCommand(this);
		new SpawnCommand(this);
		new CommandWarp(this);
		new CommandSetWarp(this);
		new CommandDeleteWarp(this);

		/* Moshpit-related. */
		new CommandAutoEquip(this);
		new CommandMoshpitSoup(this);
		new ListenerMoshpitSoup(this);
		new ListenerMoshpitSpawn(this);
		new ListenerMoshpitStats(this);
		new CommandMoshpitStats(this);
		new ListenerMoshpitKitSelect(this);
		new CommandMoshpitLeaderboardKdr(this);
		new CommandMoshpitLeaderboardKills(this);
		new CommandMoshpitLeaderboardDeaths(this);
		new CommandMoshpitLeaderboardStreak(this);
		new MoshpitCombatLog(this);
		new MoshpitScoreboard(this);

		/* Hub-related. */
		new HubCommand(this);
		new HubInventory(this);
		new HubPreventModify(this);

		System.out.println("HS-World enabled");
	}

	public void onDisable() {
		System.out.println("HS-World disabled");
	}
}
