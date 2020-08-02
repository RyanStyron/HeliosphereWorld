package mc.rysty.heliosphereworld.moshpit.leaderboard;

import java.util.HashMap;

public class MoshpitLeaderboardPositions {

    public static HashMap<String, Integer> killsPosition = new HashMap<String, Integer>();
    public static HashMap<String, Integer> deathsPosition = new HashMap<String, Integer>();
    public static HashMap<String, Integer> streakPosition = new HashMap<String, Integer>();
    public static HashMap<String, Integer> highestStreakPosition = new HashMap<String, Integer>();
    public static HashMap<String, Integer> kdrPosition = new HashMap<String, Integer>();

    public static int getKillsPosition(String displayName) {
        return killsPosition.get(displayName);
    }

    public static int getDeathsPosition(String displayName) {
        return deathsPosition.get(displayName);
    }

    public static int getStreakPosition(String displayName) {
        return streakPosition.get(displayName);
    }

    public static int getHighestStreakPosition(String displayName) {
        return highestStreakPosition.get(displayName);
    }

    public static int getKdrPosition(String displayName) {
        return kdrPosition.get(displayName);
    }
}