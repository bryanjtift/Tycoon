package me.HeyAwesomePeople.Tycoon.mongodb;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;

public class MongoDBManager {

    public static String MONGO_DATABASE = "tycoon";
    public static String COLL_USERDATA = "userdata";
    public static String COLL_TYCOONDATA = "tycoondata";
    public static String COLL_PLOTDATA = "plotdata";

    private MongoDatabase database;
    private HashMap<String, MongoCollection> collections = new HashMap<>();

    public MongoDBManager() {
        Debug.debug(DebugType.INFO, "Starting MongoDB client...");
        ASyncMongoStarter starter = new ASyncMongoStarter();
        database = starter.getDatabase(MONGO_DATABASE);

        database.listCollectionNames().into(new ArrayList<>(), (strings, throwable) -> {
            for (String coll : strings) {
                collections.put(coll, database.getCollection(coll));
            }

            Debug.debug(DebugType.INFO, "Loaded Collections: " + collections.keySet().toString());
        });
        Debug.debug(DebugType.INFO, "MongoDB successfully connected.");
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
