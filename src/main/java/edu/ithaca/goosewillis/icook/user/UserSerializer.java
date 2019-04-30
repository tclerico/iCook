package edu.ithaca.goosewillis.icook.user;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.fridge.Fridge;
import edu.ithaca.goosewillis.icook.fridge.FridgeSerializer;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.RecipeSerializer;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.recipes.ingredients.IngredientSerializer;
import edu.ithaca.goosewillis.icook.util.JsonDeserializer;
import edu.ithaca.goosewillis.icook.util.JsonSerializer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserSerializer implements JsonSerializer<User>, JsonDeserializer<User> {

    public User deserialize(JsonObject root){
        String username = root.get("username").getAsString();
        String password = root.get("password").getAsString();
        JsonObject fr = root.get("fridge").getAsJsonObject();
        Fridge fridge = new FridgeSerializer().deserialize(fr);
        ArrayList<Ingredient> dislike = new ArrayList<>();
        ArrayList<Recipe> fav = new ArrayList<>();
        ArrayList<DietType> restr = new ArrayList<>();
        ArrayList<Recipe> hist = new ArrayList<>();
        JsonArray disliked = root.get("dislikedIngredients").getAsJsonArray();
        JsonArray favorites = root.get("favoriteRecipes").getAsJsonArray();
        JsonArray restrictions = root.get("restrictions").getAsJsonArray();
        JsonArray history = root.get("history").getAsJsonArray();
        for (JsonElement e : disliked) {
            JsonObject object = e.getAsJsonObject();
            dislike.add(new IngredientSerializer().deserialize(object));
        }
        for (JsonElement e : favorites) {
            JsonObject object = e.getAsJsonObject();
            fav.add(new RecipeSerializer().deserialize(object));
        }
        for (JsonElement e : restrictions) {
            restr.add(DietType.valueOf(e.getAsString()));
        }
        for (JsonElement e : history) {
            JsonObject object = e.getAsJsonObject();
            hist.add(new RecipeSerializer().deserialize(object));
        }

        return new User(username, password, fridge, dislike, fav, restr, hist);
    }


    /*
    private String username;
    private String password;
    private Fridge fridge;
    private ArrayList<Ingredient> dislikedIngredients;
    private ArrayList<Recipe> favoriteRecipes;
    private ArrayList<DietType> restrictions;
    private ArrayList<Recipe> history;
     */

    public JsonObject serialize(User toSerialize){
        JsonObject root = new JsonObject();
        //Add username and pass
        root.addProperty("username", toSerialize.getUsername());
        root.addProperty("password", toSerialize.getPassword());
        //Add Fridge
        root.add("fridge", new FridgeSerializer().serialize(toSerialize.getFridge()));

        JsonArray dislikedArray = new JsonArray();
        for(Ingredient ingredient : toSerialize.getDislikedIngredients()){
            dislikedArray.add(new IngredientSerializer().serialize(ingredient));
        }
        root.add("dislikedIngredients", dislikedArray);

        JsonArray favoritesArray = new JsonArray();
        for(Recipe recipe : toSerialize.getFavoriteRecipes()){
            favoritesArray.add(new RecipeSerializer().serialize(recipe));
        }
        root.add("favoriteRecipes", favoritesArray);

        JsonArray restrictionsArray = new JsonArray();
        for(DietType dietType : toSerialize.getRestrictions()){
            restrictionsArray.add(dietType.getName());
        }
        root.add("restrictions", restrictionsArray);

        JsonArray historyArray = new JsonArray();
        for (Recipe recipe : toSerialize.getHistory()) {
            historyArray.add(new RecipeSerializer().serialize(recipe));
        }
        root.add("history", historyArray);

        return root;
    }
}
