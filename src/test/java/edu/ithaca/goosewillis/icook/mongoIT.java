package edu.ithaca.goosewillis.icook;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;

public class mongoIT {

    @Test
    void mongoConnectionTest() {
        String user = "goosewillis"; // the user name
        String database = "icook"; // the name of the database in which the user is defined
        char[] password = { 'p', 'a', 's', 's', 'w', 'o', 'r', 'd' };; // the password as a character array

        MongoCredential credential = MongoCredential.createCredential(user, database, password);

        MongoClientOptions options = MongoClientOptions.builder().sslEnabled(true).build();

        MongoClient mongoClient = new MongoClient(new ServerAddress("74.67.243.167", 101),
                Arrays.asList(credential),
                options);

        // todo: confirm connection was successful!
    }

}
