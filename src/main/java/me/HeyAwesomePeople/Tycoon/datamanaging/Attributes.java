package me.HeyAwesomePeople.Tycoon.datamanaging;

import com.mongodb.async.SingleResultCallback;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor public class Attributes {

    @Getter private final static String FIELD_NAME = "attributes";

    @Getter private final UUID id;
    @Getter private final UserDataManager dataManager;

    public void setInt(String key, int value) {
    }

    public void setString(String key, String value) {
    }

    public void setDouble(String key, double value) {
    }

    public int getInt(String key) {
        return 0;
    }

    public String getString(String key) {
        return null;
    }

    public double getDouble(String key) {
        return 0.0;
    }

}