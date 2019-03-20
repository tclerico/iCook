package edu.ithaca.goosewillis.icook.cookbook;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.Recipe;
import edu.ithaca.goosewillis.icook.RecipeSerializer;
import edu.ithaca.goosewillis.icook.util.JsonDeserializer;
import edu.ithaca.goosewillis.icook.util.JsonSerializer;

import java.util.Map;

public class CookbookSerializer implements JsonSerializer<CookBook>, JsonDeserializer<CookBook> {

    @Override
    public CookBook deserialize(JsonObject root) {
        CookBook cookBook = new CookBook();
        Map<String, Recipe> recipes = cookBook.getRecipes();
        JsonArray array = root.get("recipes").getAsJsonArray();
        for (JsonElement element : array) {
            JsonObject recipe = element.getAsJsonObject();
            String id = recipe.get("id").getAsString();
            Recipe r = new RecipeSerializer().deserialized(recipe.get("recipe").getAsJsonObject());
            recipes.put(id, r);
        }
        return cookBook;
    }

    @Override
    public JsonObject serialize(CookBook toSerialize) {
        JsonObject root = new JsonObject();
        JsonArray array = new JsonArray();
        for (Recipe recipe : toSerialize.getRecipes().values()) {
            array.add(new RecipeSerializer().serialize(recipe));
        }
        root.add("recipes", array);
        return root;
    }
}
