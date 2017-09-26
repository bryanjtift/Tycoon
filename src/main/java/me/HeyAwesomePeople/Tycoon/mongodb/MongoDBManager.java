package me.HeyAwesomePeople.Tycoon.mongodb;

import com.mongodb.async.client.MongoDatabase;

import java.util.HashMap;

public class MongoDBManager {

    private HashMap<String, MongoDatabase> databases = new HashMap<String, MongoDatabase>();

    public MongoDBManager() {
    }

    public void addDatabase(MongoDatabase database) {
        databases.put(database.getName(), database);
    }

    public MongoDatabase getDatabase(String databaseName) {
        return databases.get(databaseName);
    }

}
