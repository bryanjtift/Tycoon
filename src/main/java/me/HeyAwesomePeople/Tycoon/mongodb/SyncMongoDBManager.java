package me.HeyAwesomePeople.Tycoon.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import org.bson.Document;

import java.util.HashMap;

public class SyncMongoDBManager {

    private MongoDatabase database;
    private HashMap<String, MongoCollection> collections = new HashMap<>();

    public SyncMongoDBManager() {
        Debug.debug(DebugType.INFO, "Starting Sync'd MongoDB client...");
        SyncMongoStarter starter = new SyncMongoStarter();
        database = starter.getDatabase(Database.MONGO_DATABASE.getDBName());

        for (String coll : database.listCollectionNames()) {
            collections.put(coll, database.getCollection(coll));
        }
        Debug.debug(DebugType.INFO, "Loaded Collections: " + collections.keySet().toString());
        Debug.debug(DebugType.INFO, "Sync'd MongoDB successfully connected.");
    }

    public MongoCollection<Document> getCollection(String name) {
        MongoCollection<Document> coll = database.getCollection(name);
        collections.put(coll.getNamespace().getCollectionName(), coll);
        return coll;
    }

    public void delCollection(String name) {
        database.getCollection(name).drop();
    }

}
