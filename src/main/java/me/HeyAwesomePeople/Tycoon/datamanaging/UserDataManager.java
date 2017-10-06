package me.HeyAwesomePeople.Tycoon.datamanaging;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import lombok.Setter;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.mongodb.MongoDBManager;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import org.bson.Document;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class UserDataManager {

    private Tycoon plugin;
    private MongoDBManager manager;

    @Setter @Getter private boolean isReady = false;

    @Getter private final UUID id;
    @Getter private String username;
    @Getter private Document document;

    @Getter private Statistics stats;
    @Getter private Attributes attributes;

    public UserDataManager(final Tycoon plugin, UUID id, String username, final MongoCollection<Document> collection) {
        this.plugin = plugin;
        this.manager = plugin.getMongoDBManager();
        this.id = id;
        this.username = username;

        Loader loader = new Loader(collection);
        loader.runSync(plugin, (aBoolean, throwable) -> {
            setReady(true);
        });

        while (true) {
            if (isReady) {
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
        manager.getCollection(MongoDBManager.COLL_USERDATA).insertOne(this.document,
                (Void result, final Throwable t) -> Debug.debug(DebugType.INFO, "Successfully inserted document for '" + this.id.toString() + "'."));
    }

    public void updateDocument() {
        manager.getCollection(MongoDBManager.COLL_USERDATA).replaceOne(Filters.regex("uuid", this.id.toString()), this.document, (updateResult, throwable) -> {
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
