package edu.ithaca.goosewillis.icook.user;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.fridge.Fridge;
import edu.ithaca.goosewillis.icook.fridge.FridgeSerializer;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
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
        return new User(username, password, fridge, dislike, fav, restr);
    }


    /*
    private String username;
    private String password;
    private Fridge fridge;
    private ArrayList<Ingredient> dislikedIngredients;
    private ArrayList<Recipe> favoriteRecipes;
    private ArrayList<DietType> restrictions;
     */

    public JsonObject serialize(User toSerialize){
        JsonObject root = new JsonObject();
        //Add username and pass
        root.addProperty("username", toSerialize.getUsername());
        root.addProperty("password", toSerialize.getPassword());
        //Add Fridge
        root.add("fridge", new FridgeSerializer().serialize(toSerialize.getFridge()));
        return root;
    }



}
