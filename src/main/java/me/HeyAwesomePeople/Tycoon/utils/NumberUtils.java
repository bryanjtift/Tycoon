package me.HeyAwesomePeople.Tycoon.utils;

import java.util.List;

/**
 * @author HeyAwesomePeople
 * @since Friday, November 03 2017
 */
public class NumberUtils {

    public static Integer firstMissingNum(List<Integer> list) {

        for (int i = 0; i < (list.size() - 1); i++) {
            int next = list.get(i + 1);
            int current = list.get(i);
            if ((next - current) > 1) {
                return current + 1;
            }
        }

        return list.size();
    }

    public static Integer firstMissingNum(Integer... list) {

        for (int i = 0; i < (list.length - 1); i++) {
            int next = list[i + 1];
            int current = list[i];
            if ((next - current) > 1) {
                return current + 1;
            }
        }

        return list.length;
    }

}
