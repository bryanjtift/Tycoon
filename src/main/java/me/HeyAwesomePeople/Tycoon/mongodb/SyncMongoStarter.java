package me.HeyAwesomePeople.Tycoon.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.HeyAwesomePeople.Tycoon.mongodb.listeners.CommandListener;

import java.util.ArrayList;
import java.util.List;

public class SyncMongoStarter {

    @Getter private MongoClient syncMongoClient;

    SyncMongoStarter() {
        MongoClientOptions options = MongoClientOptions.builder()
                .addCommandListener(new CommandListener("SYNC"))
                .build();
        ServerAddress address = new ServerAddress();
        syncMongoClient = new MongoClient(address, options);
    }

    public SyncMongoStarter(String username, String password, String address, int port) {
        MongoCredential credential = MongoCredential.createCredential(username, Database.MONGO_DATABASE.getDBName(), password.toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);
        MongoClientOptions options = MongoClientOptions.builder()
                .addCommandListener(new CommandListener("SYNC"))
                .build();
        syncMongoClient = new MongoClient(new ServerAddress(), credentials, options);
    }

    MongoDatabase getDatabase(String database) {
        return syncMongoClient.getDatabase(database);
    }

}
