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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ASyncMongoStarter {

    @Getter private MongoClient asyncMongoClient;

    public ASyncMongoStarter() {
        ClusterSettings clusterSettings = ClusterSettings.builder()
                .hosts(Collections.singletonList(new ServerAddress("localhost")))
                .build();
        ServerSettings serverSettings = ServerSettings.builder().addServerMonitorListener(new ServerConnection()).build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .clusterSettings(clusterSettings)
                .addCommandListener(new CommandListener())
                .serverSettings(serverSettings)
                .build();
        asyncMongoClient = MongoClients.create(settings);
    }

    public ASyncMongoStarter(String username, String password, String address, int port) {
        MongoCredential credential = MongoCredential.createCredential(username, MongoDBManager.MONGO_DATABASE, password.toCharArray());
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(credential);
        ClusterSettings clusterSettings = ClusterSettings.builder()
                .hosts(Collections.singletonList(new ServerAddress(address, port)))
                .build();
        ServerSettings serverSettings = ServerSettings.builder().addServerMonitorListener(new ServerConnection()).build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .clusterSettings(clusterSettings)
                .addCommandListener(new CommandListener())
                .serverSettings(serverSettings)
                .credentialList(credentials)
                .build();
        asyncMongoClient = MongoClients.create(settings);
    }

    public MongoDatabase getDatabase(String database) {
        return asyncMongoClient.getDatabase(database);
    }

}
