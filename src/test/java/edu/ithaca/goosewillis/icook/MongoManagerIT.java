package edu.ithaca.goosewillis.icook;


import static org.junit.jupiter.api.Assertions.*;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import edu.ithaca.goosewillis.icook.util.MongoManager;


public class MongoManagerIT {


    static ObjectId id;


    @Test
    void MongoManagerTest() {
        try {
            MongoManager mongo = new MongoManager();
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    void chooseCollectionTest() throws Exception{
            MongoManager mongo = new MongoManager();

            // this will throw an exception and fail the test if the collection does not exist
            mongo.chooseCollection("cookbook");

            // this tests that an exception is thrown when a collection with that name doesn't exist
            assertThrows(Exception.class, () -> mongo.chooseCollection("fail"));
    }


    @Test
    void findAllTest() throws Exception {
        MongoManager mongo = new MongoManager();

        // this will throw an exception and fail the test if the collection does not exist
        mongo.chooseCollection("cookbook");

        boolean isMongoCursor = mongo.findAll() instanceof MongoCursor;

        assertTrue(isMongoCursor);
    }


    @Test
    void insertTest() throws Exception {
        MongoManager mongo = new MongoManager();

        // this will throw an exception and fail the test if the collection does not exist
        mongo.chooseCollection("cookbook");

        Document document = new Document();
        document.put("my", "123");
        document.put("test", "456");
        mongo.insert(document);

        this.id = (ObjectId)document.get("_id");
    }


    @Test
    void removeTest() throws Exception {
        MongoManager mongo = new MongoManager();

        // this will throw an exception and fail the test if the collection does not exist
        mongo.chooseCollection("cookbook");

        System.out.println(id);
        mongo.remove(id);
    }


}
