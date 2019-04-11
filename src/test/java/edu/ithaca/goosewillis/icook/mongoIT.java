package edu.ithaca.goosewillis.icook;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.util.FileUtil;
import java.util.Arrays;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;

public class mongoIT {

    @Test
    void mongoConnectionTest() {
        try {
            JsonObject credentials = FileUtil.readFromJson("../credentials.json");

            String    user        = credentials.get("user").getAsString();       // the user name
            String    password    = credentials.get("password").getAsString();   // the password as a character array
            String    host        = credentials.get("host").getAsString();       // the host to connect to
            String    port        = credentials.get("port").getAsString();       // the port to connect on
            String    database    = credentials.get("database").getAsString();   // the name of the database in which the user is defined

            String uri = "mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + database;

            MongoClientURI mongoURI = new MongoClientURI(uri);

            MongoClient mongoClient = new MongoClient(mongoURI);

            // todo: confirm connection was successful!
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
