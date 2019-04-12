package edu.ithaca.goosewillis.icook.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.util.JsonDeserializer;
import edu.ithaca.goosewillis.icook.util.JsonSerializer;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.recipes.ingredients.IngredientSerializer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeSerializer implements JsonSerializer<Recipe>, JsonDeserializer<Recipe> {

    @Override
    public Recipe deserialize(JsonObject root) {
        String name = root.get("name").getAsString();
        String description = root.get("description").getAsString();
        int cookTime = root.get("cookTime").getAsInt();
        JsonArray ingredientsJson = root.get("ingredients").getAsJsonArray();
        JsonArray instructionsJson = root.get("instructions").getAsJsonArray();
        JsonArray tags = root.get("tags").getAsJsonArray();
        List<Ingredient> ingredients = new ArrayList<>();
        List<String> instructions = new ArrayList<>();
        Set<DietType> restrictions = new HashSet<>();
        for (JsonElement ingredient : ingredientsJson) {
            ingredients.add(new IngredientSerializer().deserialize(ingredient.getAsJsonObject()));
        }
        for (JsonElement instruction : instructionsJson) {
            instructions.add(instruction.getAsString());
        }
        for (JsonElement tag : tags){
            if (tag.equals("Vegetarian")) {
                restrictions.add(DietType.Vegetarian);
            } else if (tag.equals("Vegan")) {
                restrictions.add(DietType.Vegan);
            } else if (tag.equals("Gluten Free")) {
                restrictions.add(DietType.GlutenFree);
            } else if (tag.equals("Dairy Free")) {
                restrictions.add(DietType.NonDairy);
            }
        }
        return new Recipe(name, description,restrictions, ingredients, instructions, cookTime);
    }

    @Override
    public JsonObject serialize(Recipe toSerialize) {
        JsonObject root = new JsonObject();
        root.addProperty("name", toSerialize.getName());
        root.addProperty("description", toSerialize.getDescription());
        root.addProperty("cookTime", toSerialize.getCookTime());
        JsonArray instructions = new JsonArray();
        for (String instruction : toSerialize.getInstructions()) {
            instructions.add(instruction);
        }
        root.add("instructions", instructions);
        JsonArray ingredients = new JsonArray();
        for (Ingredient ingredient : toSerialize.getIngredients()) {
            ingredients.add(new IngredientSerializer().serialize(ingredient));
        }
        root.add("ingredients", ingredients);
        JsonArray tags = new JsonArray();
        for (DietType tag : toSerialize.getTags()){
            tags.add(tag.getName());
        }
        root.add("tags", tags);

        return root;
    }

}
