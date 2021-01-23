package mc.rysty.heliosphereworld.moshpit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.moshpit.leaderboard.MoshpitLeaderboardPositions;
import mc.rysty.heliosphereworld.utils.MessageUtils;
import mc.rysty.heliosphereworld.utils.managers.MoshpitFileManager;

public class MoshpitScoreboard {

    private static HelioSphereWorld plugin = HelioSphereWorld.getInstance();
    private static MoshpitFileManager moshpitFileManager = HelioSphereWorld.moshpitFileManager;
    private static FileConfiguration moshpitFile = moshpitFileManager.getData();

    private static HashMap<UUID, Scoreboard> scoreboardMap = new HashMap<UUID, Scoreboard>();
    private static ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    private static String killsString;
    private static HashMap<UUID, String> lastKillsMap = new HashMap<UUID, String>();
    private static String deathsString;
    private static HashMap<UUID, String> lastDeathsMap = new HashMap<UUID, String>();
    private static String streakString;
    private static HashMap<UUID, String> lastStreakMap = new HashMap<UUID, String>();
    private static String highestStreakString;
    private static HashMap<UUID, String> lastHighestStreakMap = new HashMap<UUID, String>();
    private static String kdrString;
    private static HashMap<UUID, String> lastKdrMap = new HashMap<UUID, String>();
    private static String combatLogString;
    private static HashMap<UUID, String> lastCombatLogMap = new HashMap<UUID, String>();

    public static void enableMoshpitScheduler() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getWorld() == Bukkit.getWorld("Moshpit")) {
                        if (!MoshpitCombatLog.playerInCombat.containsKey(player))
                            MoshpitCombatLog.playerInCombat.put(player, false);

                        MoshpitLeaderboardPositions.calculateLeaderboardPositions();
                        updateMoshpitScoreboardVariables(player);
                    } else {
                        UUID playerId = player.getUniqueId();

                        lastKillsMap.put(playerId, null);
                        lastDeathsMap.put(playerId, null);
                        lastStreakMap.put(playerId, null);
                        lastHighestStreakMap.put(playerId, null);
                        lastKdrMap.put(playerId, null);
                        lastCombatLogMap.put(playerId, null);
                    }
                }
            }
        }, 0, 20);
    }

    private static void updateMoshpitScoreboardVariables(Player player) {
        UUID playerId = player.getUniqueId();
        String playerDisplayName = player.getDisplayName();
        World world = player.getWorld();
        int killsPosition = MoshpitLeaderboardPositions.getKillsPosition(playerDisplayName);
        int deathsPosition = MoshpitLeaderboardPositions.getDeathsPosition(playerDisplayName);
        int highestStreakPosition = MoshpitLeaderboardPositions.getHighestStreakPosition(playerDisplayName);
        int kdrPosition = MoshpitLeaderboardPositions.getKdrPosition(playerDisplayName);

        if (world.equals(Bukkit.getWorld("Moshpit"))) {
            killsString = "" + (int) moshpitFile.getDouble("users." + playerId + ".kills") + " (#" + killsPosition
                    + ")";
            deathsString = "" + (int) moshpitFile.getDouble("users." + playerId + ".deaths") + " (#" + deathsPosition
                    + ")";
            streakString = "" + (int) moshpitFile.getDouble("users." + playerId + ".killstreak");
            highestStreakString = "" + (int) moshpitFile.getDouble("users." + playerId + ".killstreakhighest") + " (#"
                    + highestStreakPosition + ")";
            kdrString = "" + Math.round(moshpitFile.getDouble("users." + playerId + ".kdr") * 10) / 10.0 + " (#"
                    + kdrPosition + ")";
            combatLogString = (MoshpitCombatLog.isInCombat(player)
                    ? "In Combat! (" + MoshpitCombatLog.getRemainingCombatTime(player) + ")"
                    : "Not In Combat!");

            if (moshpitScoreboardValuesChanged(player))
                updateMoshpitScoreboard(player);
        }
    }

    @SuppressWarnings("deprecation")
    private static void updateMoshpitScoreboard(Player player) {
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
        Score kills = objective.getScore(MessageUtils.convertChatColors("&fKills&7:&b " + killsString));
        Score deaths = objective.getScore(MessageUtils.convertChatColors("&fDeaths&7:&b " + deathsString));
        Score streak = objective.getScore(MessageUtils.convertChatColors("&fCurrent Streak&7:&b " + streakString));
        Score highestStreak = objective
                .getScore(MessageUtils.convertChatColors("&fHighest Streak&7:&b " + highestStreakString));
        Score kdr = objective.getScore(MessageUtils.convertChatColors("&fK/D Ratio&7:&b " + kdrString));
        Score fillerLine2 = objective.getScore(MessageUtils.convertChatColors("&f&f&3----------------------"));
        Score combatLog = objective.getScore(MessageUtils.convertChatColors("&fCombat Log&7:&b " + combatLogString));
        Score fillerLine3 = objective.getScore(MessageUtils.convertChatColors("&f&f&f&3----------------------"));

        fillerLine.setScore(8);
        kills.setScore(7);
        deaths.setScore(6);
        streak.setScore(5);
        highestStreak.setScore(4);
        kdr.setScore(3);
        fillerLine2.setScore(2);
        combatLog.setScore(1);
        fillerLine3.setScore(0);

        scoreboardMap.put(playerId, scoreboard);
        player.setScoreboard(scoreboard);

        lastKillsMap.put(playerId, killsString);
        lastDeathsMap.put(playerId, deathsString);
        lastStreakMap.put(playerId, streakString);
        lastHighestStreakMap.put(playerId, highestStreakString);
        lastKdrMap.put(playerId, kdrString);
        lastCombatLogMap.put(playerId, combatLogString);
    }

    private static boolean moshpitScoreboardValuesChanged(Player player) {
        UUID playerId = player.getUniqueId();
        String lastPlayerKills = lastKillsMap.get(playerId);
        String lastPlayerDeaths = lastDeathsMap.get(playerId);
        String lastPlayerStreak = lastStreakMap.get(playerId);
        String lastPlayerHighestStreak = lastHighestStreakMap.get(playerId);
        String lastPlayerKdr = lastKdrMap.get(playerId);
        String lastPlayerCombatLog = lastCombatLogMap.get(playerId);

        if (lastPlayerKills == null) {
            lastKillsMap.put(playerId, killsString);
            lastPlayerKills = lastKillsMap.get(playerId) + "x";
        }
        if (lastPlayerDeaths == null) {
            lastDeathsMap.put(playerId, deathsString);
            lastPlayerDeaths = lastDeathsMap.get(playerId);
        }
        if (lastPlayerStreak == null) {
            lastStreakMap.put(playerId, streakString);
            lastPlayerStreak = lastStreakMap.get(playerId);
        }
        if (lastPlayerHighestStreak == null) {
            lastHighestStreakMap.put(playerId, highestStreakString);
            lastPlayerHighestStreak = lastHighestStreakMap.get(playerId);
        }
        if (lastPlayerKdr == null) {
            lastKdrMap.put(playerId, kdrString);
            lastPlayerKdr = lastKdrMap.get(playerId);
        }
        if (lastPlayerCombatLog == null) {
            lastCombatLogMap.put(playerId, combatLogString);
            lastPlayerCombatLog = lastCombatLogMap.get(playerId);
        }

        if (!lastPlayerKills.equals(killsString) || !lastPlayerDeaths.equals(deathsString)
                || !lastPlayerStreak.equals(streakString) || !lastPlayerHighestStreak.equals(highestStreakString)
                || !lastPlayerKdr.equals(kdrString) || !lastPlayerCombatLog.equals(combatLogString))
            return true;
        return false;
    }

    private static void animatedMoshpitTitle(Objective objective) {
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