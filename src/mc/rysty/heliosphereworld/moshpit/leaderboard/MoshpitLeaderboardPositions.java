package mc.rysty.heliosphereworld.moshpit.leaderboard;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.configuration.file.FileConfiguration;

import mc.rysty.heliosphereworld.HelioSphereWorld;

public class MoshpitLeaderboardPositions {

    private static FileConfiguration moshpitFile = HelioSphereWorld.moshpitFileManager.getData();

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

    public static void calculateLeaderboardPositions() {
        int usersConfigurationSize = moshpitFile.getConfigurationSection("users").getKeys(false).size();
        int storedUserCount = 0;

        for (int i = 0; i < usersConfigurationSize; i++)
            storedUserCount++;
        HashMap<String, Double> killsMap = MoshpitLeaderboardUtils.getKillsMap();
        ValueComparator killsMapValueComparator = new ValueComparator(killsMap);
        TreeMap<String, Double> sortedKillsMap = new TreeMap<String, Double>(killsMapValueComparator);
        HashMap<String, Double> deathsMap = MoshpitLeaderboardUtils.getDeathsMap();
        ValueComparator deathsMapValueComparator = new ValueComparator(deathsMap);
        TreeMap<String, Double> sortedDeathsMap = new TreeMap<String, Double>(deathsMapValueComparator);
        HashMap<String, Double> currentStreakMap = MoshpitLeaderboardUtils.getCurrentStreakMap();
        ValueComparator currentStreakMapValueComparator = new ValueComparator(currentStreakMap);
        TreeMap<String, Double> sortedCurrentStreakMap = new TreeMap<String, Double>(currentStreakMapValueComparator);
        HashMap<String, Double> highestStreakMap = MoshpitLeaderboardUtils.getHighestStreakMap();
        ValueComparator highestStreakMapValueComparator = new ValueComparator(highestStreakMap);
        TreeMap<String, Double> sortedHighestStreakMap = new TreeMap<String, Double>(highestStreakMapValueComparator);
        HashMap<String, Double> kdrMap = MoshpitLeaderboardUtils.getKdrMap();
        ValueComparator kdrMapValueComparator = new ValueComparator(kdrMap);
        TreeMap<String, Double> sortedKdrMap = new TreeMap<String, Double>(kdrMapValueComparator);

        sortedKillsMap.putAll(killsMap);
        sortedDeathsMap.putAll(deathsMap);
        sortedCurrentStreakMap.putAll(currentStreakMap);
        sortedHighestStreakMap.putAll(highestStreakMap);
        sortedKdrMap.putAll(kdrMap);
        for (int i = 1; i < storedUserCount; i++) {
            Entry<String, Double> killsEntry = sortedKillsMap.pollFirstEntry();
            String killsKey = killsEntry.getKey();
            Entry<String, Double> deathsEntry = sortedDeathsMap.pollFirstEntry();
            String deathsKey = deathsEntry.getKey();
            Entry<String, Double> currentStreakEntry = sortedCurrentStreakMap.pollFirstEntry();
            String currentStreakKey = currentStreakEntry.getKey();
            Entry<String, Double> highestStreakEntry = sortedHighestStreakMap.pollFirstEntry();
            String highestStreakKey = highestStreakEntry.getKey();
            Entry<String, Double> kdrEntry = sortedKdrMap.pollFirstEntry();
            String kdrKey = kdrEntry.getKey();

            MoshpitLeaderboardPositions.killsPosition.put(killsKey, i);
            MoshpitLeaderboardPositions.deathsPosition.put(deathsKey, i);
            MoshpitLeaderboardPositions.streakPosition.put(currentStreakKey, i);
            MoshpitLeaderboardPositions.highestStreakPosition.put(highestStreakKey, i);
            MoshpitLeaderboardPositions.kdrPosition.put(kdrKey, i);
        }
    }

    public static class ValueComparator implements Comparator<String> {
        private Map<String, Double> base;

        public ValueComparator(HashMap<String, Double> map) {
            this.base = map;
        }

        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b))
                return -1;
            else
                return 1;
        }
    }
}