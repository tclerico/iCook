package edu.ithaca.goosewillis.icook.util;


import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.Arrays;


public class MongoManager {


    private MongoClient client = null;
    private MongoDatabase database = null;
    private MongoCollection collection = null;


    public MongoManager () throws Exception {
        connect();
    }


    private void connect() throws Exception{
        JsonObject credentials = FileUtil.readFromJson("credentials.json").getAsJsonObject("mongo");

        String user = credentials.get("user").getAsString(); // the user name
        String database = credentials.get("database").getAsString(); // the name of the database in which the user is defined
        char[] password = credentials.get("password").getAsString().toCharArray(); // the password as a character array
        String host = credentials.get("host").getAsString(); // the host to connect to
        int port = credentials.get("port").getAsInt(); // the port to connect on

        MongoCredential credential = MongoCredential.createCredential(user, database, password);

        this.client = new MongoClient(new ServerAddress(host, port),
                Arrays.asList(credential));

        // store database connection
        this.database = client.getDatabase("icook");

        // get connectionStatus to confirm success, will throw error if no connection made
        this.database.runCommand(new Document("connectionStatus", 1));
    }


    // update this to be no connection to close exception
    private void close() throws Exception {
        if (client != null) {

            client.close();

            client = null;
            database = null;
        }
        else {
            throw new Exception("No connection...");
        }
    }


    public void chooseCollection(String name) throws Exception {
        for (String collectionName : database.listCollectionNames()) {
            if (collectionName.equals(name)) {
                this.collection = database.getCollection(name);
                return;
            }
        }
        throw new Exception("No collection with that name...");
    }


    public MongoCursor findAll() {
        return collection.find().iterator();
    }


    public void insert(Document item) {
        collection.insertOne(item);
    }


    public void remove(ObjectId id) {
        collection.deleteOne(new Document("_id", id));
    }

}
