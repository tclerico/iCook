package edu.ithaca.icook.cookbook;

import com.google.gson.JsonObject;
import edu.ithaca.icook.util.JsonDeserializer;
import edu.ithaca.icook.util.JsonSerializer;

public class CookBook implements JsonDeserializer<CookBook>, JsonSerializer<CookBook> {


    public CookBook() {

    }


    public CookBook(JsonObject root) {

    }

    public void saveToFile() {

    }


    @Override
    public CookBook deserialized(JsonObject root) {
        return null;
    }

    @Override
    public JsonObject serialize(CookBook toSerialize) {
        return null;
    }
}
