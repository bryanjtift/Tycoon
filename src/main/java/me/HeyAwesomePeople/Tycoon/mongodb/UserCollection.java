package me.HeyAwesomePeople.Tycoon.mongodb;

import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;

import java.util.HashMap;
import java.util.UUID;

public class UserCollection {

    private MongoDatabase mongoDatabase;

    private HashMap<UUID, MongoCollection> collections = new HashMap<UUID, MongoCollection>();

    public UserCollection(MongoDatabase mongoDB) {
        this.mongoDatabase = mongoDB;
    }

    public boolean addUser(UUID id) {
        MongoCollection collection = mongoDatabase.getCollection(id.toString());
        return true;
    }

}
