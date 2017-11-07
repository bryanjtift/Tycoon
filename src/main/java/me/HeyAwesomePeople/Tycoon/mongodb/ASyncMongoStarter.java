package me.HeyAwesomePeople.Tycoon.mongodb;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.connection.ServerSettings;
import lombok.Getter;
import me.HeyAwesomePeople.Tycoon.mongodb.listeners.CommandListener;
import me.HeyAwesomePeople.Tycoon.mongodb.listeners.ServerConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ASyncMongoStarter {

    @Getter private MongoClient asyncMongoClient;

    ASyncMongoStarter() {
        ClusterSettings clusterSettings = ClusterSettings.builder()
                .hosts(Collections.singletonList(new ServerAddress("localhost")))
                .build();
        ServerSettings serverSettings = ServerSettings.builder().addServerMonitorListener(new ServerConnection()).build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .clusterSettings(clusterSettings)
                .addCommandListener(new CommandListener("ASYNC"))
                .serverSettings(serverSettings)
                .build();
        asyncMongoClient = MongoClients.create(settings);
    }

    public ASyncMongoStarter(String username, String password, String address, int port) {
        MongoCredential credential = MongoCredential.createCredential(username, Database.MONGO_DATABASE.getDBName(), password.toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);
        ClusterSettings clusterSettings = ClusterSettings.builder()
                .hosts(Collections.singletonList(new ServerAddress(address, port)))
                .build();
        ServerSettings serverSettings = ServerSettings.builder().addServerMonitorListener(new ServerConnection()).build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .clusterSettings(clusterSettings)
                .addCommandListener(new CommandListener("ASYNC"))
                .serverSettings(serverSettings)
                .credentialList(credentials)
                .build();
        asyncMongoClient = MongoClients.create(settings);
    }

    MongoDatabase getDatabase(String database) {
        return asyncMongoClient.getDatabase(database);
    }

}
