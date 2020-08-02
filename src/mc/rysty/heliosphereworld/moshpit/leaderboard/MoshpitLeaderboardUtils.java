package mc.rysty.heliosphereworld.moshpit.leaderboard;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;
import mc.rysty.heliosphereworld.utils.MoshpitFileManager;

public class MoshpitLeaderboardUtils {

    private static MoshpitFileManager moshpitFileManager = HelioSphereWorld.moshpitFileManager;
    private static FileConfiguration moshpitFile = moshpitFileManager.getData();

    public static void getMoshpitLeaderboard(String leaderboard, CommandSender sender,
            HashMap<String, Double> userMap) {
        ValueComparator valueComparator = new ValueComparator(userMap);
        TreeMap<String, Double> sortedUserMap = new TreeMap<String, Double>(valueComparator);

        sortedUserMap.putAll(userMap);
        MessageUtils.message(sender, "&b-===-&3 Moshpit " + leaderboard + " Leaderboard &b-===-");
        for (int i = 1; i < 6; i++) {
            Entry<String, Double> entry = sortedUserMap.pollFirstEntry();
            String key = entry.getKey();
            double value = entry.getValue();

            if (leaderboard.equals("KDR"))
                MessageUtils.message(sender, "&b" + i + ". &e" + key + " &b--&3 " + Math.round(value * 10) / 10.0);
            else
                MessageUtils.message(sender, "&b" + i + ". &e" + key + " &b--&3 " + (int) value);
        }
    }

    public static void calculateLeaderboardPositions() {
        int usersConfigurationSize = moshpitFile.getConfigurationSection("users").getKeys(false).size();
        int storedUserCount = 0;

        for (int i = 0; i < usersConfigurationSize; i++)
            storedUserCount++;
        HashMap<String, Double> killsMap = getKillsMap();
        ValueComparator killsMapValueComparator = new ValueComparator(killsMap);
        TreeMap<String, Double> sortedKillsMap = new TreeMap<String, Double>(killsMapValueComparator);
        HashMap<String, Double> deathsMap = getDeathsMap();
        ValueComparator deathsMapValueComparator = new ValueComparator(deathsMap);
        TreeMap<String, Double> sortedDeathsMap = new TreeMap<String, Double>(deathsMapValueComparator);
        HashMap<String, Double> currentStreakMap = getCurrentStreakMap();
        ValueComparator currentStreakMapValueComparator = new ValueComparator(currentStreakMap);
        TreeMap<String, Double> sortedCurrentStreakMap = new TreeMap<String, Double>(currentStreakMapValueComparator);
        HashMap<String, Double> highestStreakMap = getHighestStreakMap();
        ValueComparator highestStreakMapValueComparator = new ValueComparator(highestStreakMap);
        TreeMap<String, Double> sortedHighestStreakMap = new TreeMap<String, Double>(highestStreakMapValueComparator);
        HashMap<String, Double> kdrMap = getKdrMap();
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

    private static class ValueComparator implements Comparator<String> {
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

    public static HashMap<String, Double> getKillsMap() {
        HashMap<String, Double> userMap = new HashMap<>();

        for (String storedUsers : moshpitFile.getConfigurationSection("users").getKeys(false)) {
            String storedDisplayname = moshpitFile.getString("users." + storedUsers + ".displayname");
            double storedScores = moshpitFile.getDouble("users." + storedUsers + ".kills");

            userMap.put(storedDisplayname, storedScores);
        }
        return userMap;
    }

    public static HashMap<String, Double> getDeathsMap() {
        HashMap<String, Double> userMap = new HashMap<>();

        for (String storedUsers : moshpitFile.getConfigurationSection("users").getKeys(false)) {
            String storedDisplayname = moshpitFile.getString("users." + storedUsers + ".displayname");
            double storedScores = moshpitFile.getDouble("users." + storedUsers + ".deaths");

            userMap.put(storedDisplayname, storedScores);
        }
        return userMap;
    }

    public static HashMap<String, Double> getCurrentStreakMap() {
        HashMap<String, Double> userMap = new HashMap<>();

        for (String storedUsers : moshpitFile.getConfigurationSection("users").getKeys(false)) {
            String storedDisplayname = moshpitFile.getString("users." + storedUsers + ".displayname");
            double storedScores = moshpitFile.getDouble("users." + storedUsers + ".killstreak");

            userMap.put(storedDisplayname, storedScores);
        }
        return userMap;
    }

    public static HashMap<String, Double> getHighestStreakMap() {
        HashMap<String, Double> userMap = new HashMap<>();

        for (String storedUsers : moshpitFile.getConfigurationSection("users").getKeys(false)) {
            String storedDisplayname = moshpitFile.getString("users." + storedUsers + ".displayname");
            double storedScores = moshpitFile.getDouble("users." + storedUsers + ".killstreakhighest");

            userMap.put(storedDisplayname, storedScores);
        }
        return userMap;
    }

    public static HashMap<String, Double> getKdrMap() {
        HashMap<String, Double> userMap = new HashMap<>();

        for (String storedUsers : moshpitFile.getConfigurationSection("users").getKeys(false)) {
            String storedDisplayname = moshpitFile.getString("users." + storedUsers + ".displayname");
            double storedScores = moshpitFile.getDouble("users." + storedUsers + ".kdr");

            userMap.put(storedDisplayname, storedScores);
        }
        return userMap;
    }
}