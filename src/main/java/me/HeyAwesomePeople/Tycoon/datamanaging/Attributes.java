package me.HeyAwesomePeople.Tycoon.datamanaging;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.Document;

import java.util.UUID;

public class Attributes {

    @Getter private final static String FIELD_NAME = "attributes";

    @Getter private final UUID id;
    private final Document document;

    Attributes(UUID id, UserDataManager manager) {
        this.id = id;

        if (manager.getDocument().containsKey(FIELD_NAME)) {
            this.document = (Document) manager.getDocument().get(FIELD_NAME);
        } else {
            this.document = new Document();
            manager.getDocument().put(FIELD_NAME, this.document);
        }
    }

    public boolean hasKey(String key) {
        return document.containsKey(key);
    }

    public void setInt(String key, int value) {
        document.put(key, value);
    }

    public void setLong(String key, long value) { document.put(key, value); }

    public void setString(String key, String value) {
        document.put(key, value);
    }

    public void setDouble(String key, double value) {
        document.put(key, value);
    }

    public int getInt(String key) {
        Object obj = document.get(key);
        if (obj instanceof Integer) {
            return (int) obj;
        }
        return 0;
    }

    public long getLong(String key) {
        Object obj = document.get(key);
        if (obj instanceof Integer) {
            return (long) obj;
        }
        return 0;
    }

    public String getString(String key) {
        Object obj = document.get(key);
        if (obj instanceof String) {
            return (String) obj;
        }
        return "";
    }

    public double getDouble(String key) {
        Object obj = document.get(key);
        if (obj instanceof Double) {
            return (double) obj;
        }
        return 0;
    }

}
