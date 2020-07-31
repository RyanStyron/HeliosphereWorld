package mc.rysty.heliosphereworld.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.command.CommandSender;

public class MoshpitLeaderboardUtils {

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
}