package edu.ithaca.goosewillis.icook.cookbook;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.util.JsonDeserializer;
import edu.ithaca.goosewillis.icook.util.JsonSerializer;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.RecipeSerializer;

import java.util.Map;

public class CookbookSerializer implements JsonSerializer<CookBook>, JsonDeserializer<CookBook> {

    /**
     * deserializes cookbook
     * @param root
     * @return CookBook
     */
    @Override
    public CookBook deserialize(JsonObject root) {
        CookBook cookBook = new CookBook();
        Map<String, Recipe> recipes = cookBook.getRecipes();
        JsonArray array = root.get("recipes").getAsJsonArray();
        for (JsonElement element : array) {
            JsonObject recipe = element.getAsJsonObject();
            Recipe r = new RecipeSerializer().deserialize(recipe);
            recipes.put(r.getName(), r);
        }
        return cookBook;
    }

    /**
     * serializes cookbook
     * @param toSerialize
     * @return JsonObject
     */
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