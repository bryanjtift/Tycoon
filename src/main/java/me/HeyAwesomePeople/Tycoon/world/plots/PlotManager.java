package me.HeyAwesomePeople.Tycoon.world.plots;

import com.mongodb.async.client.MongoCollection;
import me.HeyAwesomePeople.Tycoon.Tycoon;
import me.HeyAwesomePeople.Tycoon.mongodb.MongoDBManager;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;
import org.bson.Document;

import java.util.HashMap;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class PlotManager {

    private Tycoon plugin;

    private HashMap<Integer, Plot> plots = new HashMap<>();
    private MongoDBManager manager;

    private Document document;

    public PlotManager(Tycoon plugin) {
        this.plugin = plugin;
        this.manager = plugin.getMongoDBManager();

        Plot p = new Plot(plugin, "world", 2);

        loadDocument();
    }

    private void loadPlots() {
        
    }

    private void loadDocument() {
        MongoCollection<Document> c = manager.getCollection(MongoDBManager.COLL_PLOTDATA);

        c.find().first((document, throwable) -> {
            if (document == null) {
                createNewDocument();
            } else {
                PlotManager.this.document = document;
            }
        });
    }

    private void createNewDocument() {
        document = new Document();
        manager.getCollection(MongoDBManager.COLL_USERDATA).insertOne(this.document,
                (Void result, final Throwable t) -> Debug.debug(DebugType.INFO, "Successfully inserted document for PlotData."));
    }

}
