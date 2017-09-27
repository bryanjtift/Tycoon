package me.HeyAwesomePeople.Tycoon.mongodb;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UserCollection {

    private MongoDatabase mongoDatabase;

    private HashMap<UUID, MongoCollection> collections = new HashMap<UUID, MongoCollection>();

    public UserCollection(MongoDatabase mongoDB) {
        this.mongoDatabase = mongoDB;
    }

    public MongoCollection getUserCollection(UUID id) {
        return collections.get(id);
    }

    public void addUser(UUID id) {
        MongoCollection collection = mongoDatabase.getCollection(id.toString());
        mongoDatabase.listCollectionNames().into(new ArrayList<String>(), new SingleResultCallback<ArrayList<String>>() {
            public void onResult(ArrayList<String> strings, Throwable throwable) {
                strings.contains();
            }
        });
    }

}
