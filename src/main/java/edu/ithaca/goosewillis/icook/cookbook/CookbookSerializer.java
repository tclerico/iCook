package edu.ithaca.goosewillis.icook.cookbook;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.recipes.ingredients.IngredientSerializer;
import edu.ithaca.goosewillis.icook.util.JsonDeserializer;
import edu.ithaca.goosewillis.icook.util.JsonSerializer;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.RecipeSerializer;

import java.util.Map;

public class CookbookSerializer implements JsonSerializer<CookBook>, JsonDeserializer<CookBook> {

    @Override
    public CookBook deserialize(JsonObject root) {
        CookBook cookBook = new CookBook();
        Map<String, Recipe> recipes = cookBook.getRecipes();
        Map<String, Ingredient> ingredients = cookBook.getIngredients();
        JsonArray jsonRecipes = root.get("recipes").getAsJsonArray();
        JsonArray jsonIngredients = root.get("ingredients").getAsJsonArray();
        for (JsonElement element : jsonRecipes) {
            JsonObject recipe = element.getAsJsonObject();
            Recipe r = new RecipeSerializer().deserialize(recipe);
            recipes.put(r.getName(), r);
        }
        for (JsonElement element : jsonIngredients) {
            JsonObject ingredient = element.getAsJsonObject();
            Ingredient i = new IngredientSerializer().deserialize(ingredient);
            ingredients.put(i.getName(), i);
        }
        return cookBook;
    }

    @Override
    public JsonObject serialize(CookBook toSerialize) {
        JsonObject root = new JsonObject();
        JsonArray recipes = new JsonArray();
        JsonArray ingredients = new JsonArray();
        for (Recipe recipe : toSerialize.getRecipes().values()) {
            recipes.add(new RecipeSerializer().serialize(recipe));
        }
        for (Ingredient ingredient : toSerialize.getIngredients().values()) {
            ingredients.add(new IngredientSerializer().serialize(ingredient));
        }
        root.add("recipes", recipes);
        root.add("ingredients", ingredients);
        return root;
    }
}
