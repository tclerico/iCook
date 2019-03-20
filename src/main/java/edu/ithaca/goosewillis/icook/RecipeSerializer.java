package edu.ithaca.goosewillis.icook;

import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.util.JsonDeserializer;
import edu.ithaca.goosewillis.icook.util.JsonSerializer;

public class RecipeSerializer implements JsonSerializer<Recipe>, JsonDeserializer<Recipe> {

    @Override
    public Recipe deserialize(JsonObject root) {
        
        return null;
    }

    @Override
    public JsonObject serialize(Recipe toSerialize) {
        return null;
    }

}
