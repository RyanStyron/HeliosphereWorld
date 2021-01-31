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

    private static HashMap<Player, Scoreboard> scoreboardMap = new HashMap<Player, Scoreboard>();
    private static ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    private static String killsString;
    private static HashMap<Player, String> lastKillsMap = new HashMap<Player, String>();
    private static String deathsString;
    private static HashMap<Player, String> lastDeathsMap = new HashMap<Player, String>();
    private static String streakString;
    private static HashMap<Player, String> lastStreakMap = new HashMap<Player, String>();
    private static String highestStreakString;
    private static HashMap<Player, String> lastHighestStreakMap = new HashMap<Player, String>();
    private static String kdrString;
    private static HashMap<Player, String> lastKdrMap = new HashMap<Player, String>();
    private static String combatLogString;
    private static HashMap<Player, String> lastCombatLogMap = new HashMap<Player, String>();
    private static HashMap<Player, Boolean> clearStoredValuesMap = new HashMap<Player, Boolean>();

    public static void enableMoshpitScheduler() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (clearStoredValuesMap.get(player) == null)
                        clearStoredValuesMap.put(player, true);
                    clearStoredValues(player);

                    if (player.getWorld() == Bukkit.getWorld("Moshpit")) {
                        if (!MoshpitCombatLog.playerInCombat.containsKey(player))
                            MoshpitCombatLog.playerInCombat.put(player, false);
                        clearStoredValuesMap.put(player, false);

                        MoshpitLeaderboardPositions.calculateLeaderboardPositions();
                        updateMoshpitScoreboardVariables(player);
                    } else
                        clearStoredValuesMap.put(player, true);
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
        if (!scoreboardMap.containsKey(player)) {
            Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
            scoreboardMap.put(player, scoreboard);
        }
        Scoreboard scoreboard = scoreboardMap.get(player);
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

        scoreboardMap.put(player, scoreboard);
        player.setScoreboard(scoreboard);

        lastKillsMap.put(player, killsString);
        lastDeathsMap.put(player, deathsString);
        lastStreakMap.put(player, streakString);
        lastHighestStreakMap.put(player, highestStreakString);
        lastKdrMap.put(player, kdrString);
        lastCombatLogMap.put(player, combatLogString);
    }

    private static boolean moshpitScoreboardValuesChanged(Player player) {
        String lastPlayerKills = lastKillsMap.get(player);
        String lastPlayerDeaths = lastDeathsMap.get(player);
        String lastPlayerStreak = lastStreakMap.get(player);
        String lastPlayerHighestStreak = lastHighestStreakMap.get(player);
        String lastPlayerKdr = lastKdrMap.get(player);
        String lastPlayerCombatLog = lastCombatLogMap.get(player);

        if (lastPlayerKills == null) {
            lastKillsMap.put(player, killsString);
            lastPlayerKills = lastKillsMap.get(player) + "x";
        }
        if (lastPlayerDeaths == null) {
            lastDeathsMap.put(player, deathsString);
            lastPlayerDeaths = lastDeathsMap.get(player);
        }
        if (lastPlayerStreak == null) {
            lastStreakMap.put(player, streakString);
            lastPlayerStreak = lastStreakMap.get(player);
        }
        if (lastPlayerHighestStreak == null) {
            lastHighestStreakMap.put(player, highestStreakString);
            lastPlayerHighestStreak = lastHighestStreakMap.get(player);
        }
        if (lastPlayerKdr == null) {
            lastKdrMap.put(player, kdrString);
            lastPlayerKdr = lastKdrMap.get(player);
        }
        if (lastPlayerCombatLog == null) {
            lastCombatLogMap.put(player, combatLogString);
            lastPlayerCombatLog = lastCombatLogMap.get(player);
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

    private static void clearStoredValues(Player player) {
        if (clearStoredValuesMap.get(player)) {
            if (lastKillsMap.get(player) != null) {
                lastKillsMap.put(player, null);
                lastDeathsMap.put(player, null);
                lastStreakMap.put(player, null);
                lastHighestStreakMap.put(player, null);
                lastKdrMap.put(player, null);
                lastCombatLogMap.put(player, null);
            }
        }
    }
}