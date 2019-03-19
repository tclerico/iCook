package edu.ithaca.icook;

import com.google.gson.JsonObject;
import edu.ithaca.icook.util.JsonDeserializer;
import edu.ithaca.icook.util.JsonSerializer;

public class Recipe implements JsonDeserializer<Recipe>, JsonSerializer<Recipe> {
    
    @Override
    public Recipe deserialized(JsonObject root) {
        return null;
    }

    @Override
    public JsonObject serialize(Recipe toSerialize) {
        return null;
    }
}
