package me.HeyAwesomePeople.Tycoon.datamanaging;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.Document;

import java.util.UUID;

public class UserDataManager {

    @Getter private UUID id;
    @Getter private String username;
    @Getter private Document document;

    @Getter private Statistics stats;
    @Getter private Attributes attributes;

    public UserDataManager(UUID id, String username, MongoCollection collection) {
        this.id = id;
        this.username = username;


        this.stats = new Statistics(this.id, this);
        this.attributes = new Attributes(this.id, this);
    }

    private void createNewDocument() {
        document = new Document("uuid", id.toString()).append("name", this.username);
    }

    public void uploadDocument() {

    }

    public void reloadDocument() {

    }

}
