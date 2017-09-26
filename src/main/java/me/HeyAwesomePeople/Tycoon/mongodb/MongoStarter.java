package me.HeyAwesomePeople.Tycoon.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import lombok.Getter;

public class MongoStarter {

    @Getter private MongoClient mongoClient;

    public MongoStarter() {
        mongoClient = MongoClients.create();
    }

    public MongoStarter(String username, String password, String address, String port) {
        mongoClient = MongoClients.create(new ConnectionString("mongodb://"
        + username + ":" + password + "@" + address + ":" + port));
    }

    public MongoDatabase getDatabase(String database) {
        return mongoClient.getDatabase(database);
    }

}
