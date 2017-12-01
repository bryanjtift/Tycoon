package me.HeyAwesomePeople.Tycoon.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListUtils {

    public static List<String> toStringList(List<UUID> uuids) {
        List<String> strings = new ArrayList<>();
        for (UUID id : uuids) {
            strings.add(id.toString());
        }
        return strings;
    }

    public static List<UUID> toUUIDList(List<String> strings) {
        List<UUID> uuids = new ArrayList<>();
        for (String s : strings) {
            uuids.add(UUID.fromString(s));
        }
        return uuids;
    }

}
