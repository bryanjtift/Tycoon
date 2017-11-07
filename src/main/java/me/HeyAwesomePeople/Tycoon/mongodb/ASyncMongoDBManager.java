package me.HeyAwesomePeople.Tycoon.mongodb;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;

public class ASyncMongoDBManager {

    private MongoDatabase database;
    private HashMap<String, MongoCollection> collections = new HashMap<>();

    public ASyncMongoDBManager() {
        Debug.debug(DebugType.INFO, "Starting ASync'd MongoDB client...");
        ASyncMongoStarter starter = new ASyncMongoStarter();
        database = starter.getDatabase(Database.MONGO_DATABASE.getDBName());

        database.listCollectionNames().into(new ArrayList<>(), (strings, throwable) -> {
            for (String coll : strings) {
                collections.put(coll, database.getCollection(coll));
            }

            Debug.debug(DebugType.INFO, "Loaded Collections: " + collections.keySet().toString());
        });
        Debug.debug(DebugType.INFO, "ASync'd MongoDB successfully connected.");
    }

    public MongoCollection<Document> getCollection(String name) {
        MongoCollection<Document> coll = database.getCollection(name);
        collections.put(coll.getNamespace().getCollectionName(), coll);
        return coll;
    }

    public void delCollection(String name, SingleResultCallback<Void> callback) {
        database.getCollection(name).drop(callback);
    }

}
