package mc.rysty.heliosphereworld.moshpit.leaderboard;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;
import mc.rysty.heliosphereworld.utils.managers.MoshpitFileManager;

public class MoshpitLeaderboardUtils {

    private static MoshpitFileManager moshpitFileManager = HelioSphereWorld.moshpitFileManager;
    private static FileConfiguration moshpitFile = moshpitFileManager.getData();

    public static void getMoshpitLeaderboard(String leaderboard, CommandSender sender,
            HashMap<String, Double> userMap) {
        MoshpitLeaderboardPositions.ValueComparator valueComparator = new MoshpitLeaderboardPositions.ValueComparator(
                userMap);
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