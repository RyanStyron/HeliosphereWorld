package mc.rysty.heliosphereworld.moshpit.scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;
import mc.rysty.heliosphereworld.utils.MoshpitFileManager;

public class MoshpitScoreboard implements Listener {

    private HelioSphereWorld plugin = HelioSphereWorld.getInstance();
    private MoshpitFileManager moshpitFileManager = HelioSphereWorld.moshpitFileManager;
    private FileConfiguration moshpitFile = moshpitFileManager.getData();
    private PluginManager pluginManager = plugin.getServer().getPluginManager();

    public MoshpitScoreboard(HelioSphereWorld plugin) {
        pluginManager.registerEvents(this, plugin);
    }

    private HashMap<UUID, Scoreboard> scoreboardMap = new HashMap<UUID, Scoreboard>();
    private ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    private String displayNameString;
    private HashMap<UUID, String> lastDisplayNameMap = new HashMap<UUID, String>();
    private String killsString;
    private HashMap<UUID, String> lastKillsMap = new HashMap<UUID, String>();
    private String deathsString;
    private HashMap<UUID, String> lastDeathsMap = new HashMap<UUID, String>();
    private String streakString;
    private HashMap<UUID, String> lastStreakMap = new HashMap<UUID, String>();
    private String highestStreakString;
    private HashMap<UUID, String> lastHighestStreakMap = new HashMap<UUID, String>();
    private String kdrString;
    private HashMap<UUID, String> lastKdrMap = new HashMap<UUID, String>();
    private String combatLogString;
    private HashMap<UUID, String> lastCombatLogMap = new HashMap<UUID, String>();

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        World world = player.getWorld();

        if (world.equals(Bukkit.getWorld("Moshpit"))) {
            lastDisplayNameMap.put(playerId, "");

            updateMoshpitScoreboardVariables(player);
        } else if (!world.equals(Bukkit.getWorld("Skyforge")))
            player.setScoreboard(scoreboardManager.getNewScoreboard());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        updateMoshpitScoreboardVariables(event.getPlayer());
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Location toLocation = event.getTo();
        Location fromLocation = event.getFrom();

        if (toLocation.getWorld() == fromLocation.getWorld())
            updateMoshpitScoreboardVariables(event.getPlayer());
    }

    @SuppressWarnings("deprecation")
    private void updateMoshpitScoreboard(Player player) {
        UUID playerId = player.getUniqueId();

        if (!scoreboardMap.containsKey(playerId)) {
            Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
            scoreboardMap.put(playerId, scoreboard);
        }
        Scoreboard scoreboard = scoreboardMap.get(playerId);
        Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);

        if (objective != null)
            objective.unregister();
        objective = scoreboard.registerNewObjective("Moshpit", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        animatedMoshpitTitle(objective);

        Score fillerLine = objective.getScore(MessageUtils.convertChatColors("&3----------------------"));
        Score displayName = objective.getScore(MessageUtils.convertChatColors("      " + displayNameString));
        Score fillerLine2 = objective.getScore(MessageUtils.convertChatColors("&f&3----------------------"));
        Score kills = objective.getScore(MessageUtils.convertChatColors("&fKills&7:&b " + killsString));
        Score deaths = objective.getScore(MessageUtils.convertChatColors("&fDeaths&7:&b " + deathsString));
        Score streak = objective.getScore(MessageUtils.convertChatColors("&fStreak&7:&b " + streakString));
        Score highestStreak = objective
                .getScore(MessageUtils.convertChatColors("&fHighest Streak&7:&b " + highestStreakString));
        Score kdr = objective.getScore(MessageUtils.convertChatColors("&fK/D Ratio&7:&b " + kdrString));
        Score fillerLine3 = objective.getScore(MessageUtils.convertChatColors("&f&f&3----------------------"));
        Score combatLog = objective.getScore(MessageUtils.convertChatColors("&fCombat Log&7:&b " + combatLogString));
        Score fillerLine4 = objective.getScore(MessageUtils.convertChatColors("&f&f&f&3----------------------"));

        fillerLine.setScore(16);
        displayName.setScore(15);
        fillerLine2.setScore(14);
        kills.setScore(13);
        deaths.setScore(12);
        streak.setScore(11);
        highestStreak.setScore(10);
        kdr.setScore(9);
        fillerLine3.setScore(8);
        combatLog.setScore(7);
        fillerLine4.setScore(6);

        scoreboardMap.put(playerId, scoreboard);
        player.setScoreboard(scoreboard);

        lastDisplayNameMap.put(playerId, displayNameString);
        lastKillsMap.put(playerId, killsString);
        lastDeathsMap.put(playerId, deathsString);
        lastStreakMap.put(playerId, streakString);
        lastHighestStreakMap.put(playerId, highestStreakString);
        lastKdrMap.put(playerId, kdrString);
        lastCombatLogMap.put(playerId, combatLogString);
    }

    private void updateMoshpitScoreboardVariables(Player player) {
        UUID playerId = player.getUniqueId();
        World world = player.getWorld();

        if (world.equals(Bukkit.getWorld("Moshpit"))) {
            displayNameString = player.getDisplayName();
            killsString = "" + (int) moshpitFile.getDouble("users." + playerId + ".kills");
            deathsString = "" + (int) moshpitFile.getDouble("users." + playerId + ".deaths");
            streakString = "" + (int) moshpitFile.getDouble("users." + playerId + ".killstreak");
            highestStreakString = "" + (int) moshpitFile.getDouble("users." + playerId + ".killstreakhighest");
            kdrString = "" + Math.round(moshpitFile.getDouble("users." + playerId + ".kdr") * 10) / 10.0;
            combatLogString = "None";

            /* Update scoreboard. */
            if (moshpitScoreboardValuesChanged(player))
                updateMoshpitScoreboard(player);
        }
    }

    private boolean moshpitScoreboardValuesChanged(Player player) {
        UUID playerId = player.getUniqueId();
        String lastPlayerDisplayName = lastDisplayNameMap.get(playerId);
        String lastPlayerKills = lastKillsMap.get(playerId);
        String lastPlayerDeaths = lastDeathsMap.get(playerId);
        String lastPlayerStreak = lastStreakMap.get(playerId);
        String lastPlayerHighestStreak = lastHighestStreakMap.get(playerId);
        String lastPlayerKdr = lastKdrMap.get(playerId);
        String lastPlayerCombatLog = lastCombatLogMap.get(playerId);

        if (lastPlayerDisplayName == null)
            lastPlayerDisplayName = "";
        if (lastPlayerKills == null)
            lastPlayerKills = "";
        if (lastPlayerDeaths == null)
            lastPlayerDeaths = "";
        if (lastPlayerStreak == null)
            lastPlayerStreak = "";
        if (lastPlayerHighestStreak == null)
            lastPlayerHighestStreak = "";
        if (lastPlayerKdr == null)
            lastPlayerKdr = "";
        if (lastPlayerCombatLog == null)
            lastPlayerCombatLog = "";

        if (!lastPlayerDisplayName.equals(displayNameString) || !lastPlayerKills.equals(killsString)
                || !lastPlayerDeaths.equals(deathsString) || !lastPlayerStreak.equals(streakString)
                || !lastPlayerHighestStreak.equals(highestStreakString) || !lastPlayerKdr.equals(kdrString)
                || !lastPlayerCombatLog.equals(combatLogString))
            return true;
        return false;
    }

    private void animatedMoshpitTitle(Objective objective) {
        List<String> titles = new ArrayList<>();

        titles.add(MessageUtils.convertChatColors("&3&lMOSHPIT"));
        titles.add(MessageUtils.convertChatColors("&b&lMOSHPIT"));
        titles.add(MessageUtils.convertChatColors("&f&lMOSHPIT"));
        titles.add(MessageUtils.convertChatColors("&b&lMOSHPIT"));
        titles.add(MessageUtils.convertChatColors("&3&lMOSHPIT"));

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            int integer = 0;

            @Override
            public void run() {
                if (integer >= titles.size())
                    integer = 0;
                else if (integer < titles.size())
                    objective.setDisplayName(titles.get(integer));
                integer++;
            }
        }, 0, 5);
    }
}