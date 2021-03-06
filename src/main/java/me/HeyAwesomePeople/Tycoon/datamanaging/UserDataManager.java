package me.HeyAwesomePeople.Tycoon.datamanaging;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.mongodb.ASyncMongoDBManager;
import me.HeyAwesomePeople.Tycoon.mongodb.Collection;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import org.bson.Document;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.Vector;

public class UserDataManager {

    private Tycoon plugin;
    private ASyncMongoDBManager manager;

    private Vector<Boolean> isReady = new Vector<>();

    @Getter private final UUID id;
    @Getter private String username;
    @Getter private Document document;

    @Getter private Statistics stats;
    @Getter private Attributes attributes;

    public UserDataManager(final Tycoon plugin, UUID id, String username, final MongoCollection<Document> collection) {
        this.plugin = plugin;
        this.manager = plugin.getASyncMongoDBManager();
        this.id = id;
        this.username = username;
        isReady.add(false);

        Loader loader = new Loader(collection);
        loader.runSync(plugin, (aBoolean, throwable) -> {
            isReady.set(0, true);
        });

        while (true) {
            if (isReady.get(0)) {
                break;
            }
        }

        this.stats = new Statistics(this.id, this);
        this.attributes = new Attributes(this.id, this);
    }


    public class Loader extends BukkitRunnable {

        private MongoCollection<Document> collection;
        private SingleResultCallback<Boolean> callback;

        Loader(MongoCollection<Document> collection) {
            this.collection = collection;
        }

        void runSync(Tycoon plugin, SingleResultCallback<Boolean> yeah) {
            this.runTask(plugin);
            callback = yeah;
        }

        @Override
        public void run() {
            collection.find(Filters.regex("uuid", id.toString())).first((document, throwable) -> {
                if (document == null) {
                    Debug.debug(DebugType.WARNING, "No documents found for '" + UserDataManager.this.id.toString() + "' (" + UserDataManager.this.username + ").");
                    createNewDocument();
                } else {
                    UserDataManager.this.document = document;
                }
                callback.onResult(true, new Throwable());
            });
        }
    }

    private void createNewDocument() {
        document = new Document("uuid", id.toString()).append("name", this.username);
        manager.getCollection(Collection.USERDATA.getCollName()).insertOne(this.document,
                (Void result, final Throwable t) -> Debug.debug(DebugType.INFO, "Successfully inserted document for '" + this.id.toString() + "'."));
    }

    public void updateDocument() {
        manager.getCollection(Collection.USERDATA.getCollName()).replaceOne(Filters.regex("uuid", this.id.toString()), this.document, (updateResult, throwable) -> {
            if (updateResult == null) {
                Debug.debug(DebugType.ERROR, "Update Result Null");
                return;
            }
            Debug.debug(DebugType.INFO, "Updated document for '" + id.toString() + "' with " + updateResult.getModifiedCount() + " modifications, " + updateResult.getMatchedCount() + " matches, and acknowledgement was " + updateResult.wasAcknowledged());
        });
    }

    public void reloadDocument(com.mongodb.async.client.MongoCollection<Document> collection) {
        collection.find(Filters.regex("uuid", this.id.toString())).first((document, throwable) -> UserDataManager.this.document = document);
    }

}
