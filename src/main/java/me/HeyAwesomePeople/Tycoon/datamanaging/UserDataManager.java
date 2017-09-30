package me.HeyAwesomePeople.Tycoon.datamanaging;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import lombok.Getter;
import me.HeyAwesomePeople.Tycoon.mongodb.MongoDBManager;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import org.bson.Document;

import java.util.ArrayList;
import java.util.UUID;

public class UserDataManager {

    private MongoDBManager manager;

    @Getter private final UUID id;
    @Getter private String username;
    @Getter private Document document;

    @Getter private Statistics stats;
    @Getter private Attributes attributes;

    public UserDataManager(final MongoDBManager manager, UUID id, String username, final MongoCollection<Document> collection) {
        this.manager = manager;
        this.id = id;
        this.username = username;

        collection.find(Filters.regex("uuid", id.toString())).into(new ArrayList<>(), (documents, throwable) -> {
            if (documents.size() == 0)
                Debug.debug(DebugType.WARNING, "No collections found for '" + UserDataManager.this.id.toString() + "' (" + UserDataManager.this.username + ").");
        });


        this.stats = new Statistics(this.id, this);
        this.attributes = new Attributes(this.id, this);
    }

    public void setString(String key, String value) {
        document.put(key, value);
    }

    public String getString(String key) {
        Object object = document.get(key);
        if (object instanceof String) {
            return (String) object;
        }
        return "ERROR_NOT_STRING";
    }

    public void setInt(String key, Integer value) {
        document.put(key, value);
    }

    public Integer getInt(String key) {
        Object object = document.get(key);
        if (object instanceof Integer) {
            return (Integer) object;
        }
        return -1;
    }

    private void uploadDocument() {

    }

    private void createNewDocument() {
        document = new Document("uuid", id.toString()).append("name", this.username);
        manager.getCollection(MongoDBManager.COLL_USERDATA).insertOne(this.document,
                (Void result, final Throwable t) -> Debug.debug(DebugType.INFO, "Successfully inserted document for '" + this.id.toString() + "'."));
    }

    public void updateDocument() {
        manager.getCollection(MongoDBManager.COLL_USERDATA).updateOne(Filters.regex("uuid", this.id.toString()), this.document, new SingleResultCallback<UpdateResult>() {
            public void onResult(UpdateResult updateResult, Throwable throwable) {
                if (updateResult == null) {
                    Debug.debug(DebugType.ERROR, "Update Result Null");
                }
                if (id == null) {
                    Debug.debug(DebugType.ERROR, "ID null");
                }
                Debug.debug(DebugType.INFO, "Updated document for '" + id.toString() + "' with " + updateResult.getModifiedCount() + " modifications, " + updateResult.getMatchedCount() + " matches, and acknowledgement was " + updateResult.wasAcknowledged());
            }
        });
    }

    public void reloadDocument(com.mongodb.async.client.MongoCollection<Document> collection) {
        collection.find(Filters.regex("uuid", this.id.toString())).first((document, throwable) -> UserDataManager.this.document = document);
    }

}
