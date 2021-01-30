package mc.rysty.heliosphereworld.utils;

import java.util.List;

public class MathUtils {

    public static int getLargestInt(int... numbers) {
        Integer largest = null;

        for (int number : numbers)
            if (largest == null || number > largest)
                largest = number;

        return largest;
    }

    public static int findIndex(List<Integer> list, int element) {
        if (list != null) {
            int index = 0;

            while (index < list.size())
                if (list.get(index) == element)
                    return index;
                else
                    index = index + 1;
        }
        return -1;
    }
}