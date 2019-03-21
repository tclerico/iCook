package edu.ithaca.goosewillis.icook.recipes.ingredients;

import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.util.JsonDeserializer;
import edu.ithaca.goosewillis.icook.util.JsonSerializer;

public class IngredientSerializer implements JsonSerializer<Ingredient>, JsonDeserializer<Ingredient> {

    @Override
    public Ingredient deserialize(JsonObject root) {
        String name = root.get("name").getAsString();
        int count = root.get("count").getAsInt();
        DietType dietType = DietType.valueOf(root.get("dietType").getAsString());
        return new Ingredient(name, count, dietType);
    }

    @Override
    public JsonObject serialize(Ingredient toSerialize) {
        JsonObject root = new JsonObject();
        root.addProperty("name", toSerialize.getName());
        root.addProperty("count", toSerialize.getCount());
        root.addProperty("dietType", toSerialize.getDietType().getName());
        return root;
    }

}
