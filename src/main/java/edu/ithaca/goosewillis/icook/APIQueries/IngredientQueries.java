package edu.ithaca.goosewillis.icook.APIQueries;

import java.io.*;
import java.net.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import com.google.gson.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import edu.ithaca.goosewillis.icook.cookbook.CookBook;
import edu.ithaca.goosewillis.icook.cookbook.CookbookSerializer;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.util.FileUtil;
import org.apache.http.client.HttpRequestRetryHandler;


public class IngredientQueries {

    public static JsonObject getJsonFromUrl(String s) throws IOException {
        HttpURLConnection urlConnection = null;
        String json = "";
        try {
            URL url = new URL(s);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader reader = new InputStreamReader(url.openStream());
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(reader).getAsJsonObject();
            return root;
        } catch (IOException e) {
            throw new IOException("There was an error reading from the url. Check your parameters, and that the endpoint is online.");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }


    public static List<String> parseInstructions(String instructions){
        String [] s = instructions.split(". ");
        List<String> clean = new ArrayList<>();
        for (int i=0; i<s.length; i++){
            clean.add(s[i]);
        }
        return clean;
    }

    public static List<String> readInIngredients(){
        List<String> ingredientList = new ArrayList<>();

        //Load in a list of a bunch of ingredients
        try {
            //can use this one to front load ingredients
            String str = "https://www.themealdb.com/api/json/v1/1/list.php?i=list";
            JsonObject test = getJsonFromUrl(str);
            //System.out.println(test);
            //System.out.println(test.get("meals"));
            JsonArray ingredients = test.getAsJsonArray("meals");
            for(JsonElement element : ingredients){
                JsonObject ing = element.getAsJsonObject();
                JsonElement name = ing.get("strIngredient");
                ingredientList.add(name.getAsString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingredientList;
    }

    public static List<String> loadRecipeNames(String key, String host, List<String> ingredientList){
        List<String> recipeNames = new ArrayList<>();
        // Get a list of recipes for each ingredient and name to list
        for(String name : ingredientList){
            //System.out.println(name);
            try{
                HttpResponse<String> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/site/search?query="+name)
                        .header("X-RapidAPI-Host", host)
                        .header("X-RapidAPI-Key", key)
                        .asString();

                Gson g = new Gson();
                if(response.getStatus() == 200){
                    JsonObject r = g.fromJson(response.getBody(), JsonObject.class);
                    JsonArray recipes = r.getAsJsonArray("Recipes");
                    for(JsonElement element : recipes){
                        JsonObject rec = element.getAsJsonObject();
                        recipeNames.add(rec.get("name").getAsString());
                        //System.out.println("Adding: "+rec.get("name").getAsString());
                    }
                }
                //break;
            }catch (Exception e){
                e.printStackTrace();
            }
            //break;
        }
        return recipeNames;
    }

    public static List<Integer> loadRecipeIDs(String key, String host, List<String> recipeNames){
        // get recipe ID's to be used later
        List<Integer> recipeID = new ArrayList<>();
        for (String recipeName : recipeNames){
            try{
                System.out.println(recipeName);
                String clean = recipeName.replace(' ', '+');
                HttpResponse<String> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/search?number=1&offset=0&instructionsRequired=true&query="+clean)
                        .header("X-RapidAPI-Host", host)
                        .header("X-RapidAPI-Key", key)
                        .asString();
                if(response.getStatus() == 200){
                    Gson g = new Gson();
                    JsonObject r = g.fromJson(response.getBody(), JsonObject.class);
                    JsonArray result = r.getAsJsonArray("results");
                    for (JsonElement element : result){
                        JsonObject recipe = element.getAsJsonObject();
                        recipeID.add(recipe.get("id").getAsInt());
                    }
                }
                //break;
            }catch (Exception e){
                e.printStackTrace();
            }
            //break;
        }
        return recipeID;
    }

    public static void constructRecipe(CookBook cb, String name, Integer cooktime, List<String> ingredients, List<String> instructions, Set<DietType> tags){
        try{
            //TODO SEARCH TO SEE IF INGREDIENT ALREADY EXISTS IN DB OR WHATEVER IS STORING IT -> create if doesnt exist -> get if does

            List<Ingredient> ingToAdd = new ArrayList<>();
            for(String ing: ingredients){
                Ingredient toAdd = new Ingredient(ing, 1,1);
                ingToAdd.add(toAdd);
            }

            Recipe nRecipe = new Recipe(name, " ", tags, ingToAdd, instructions, cooktime);
            //System.out.println(nRecipe.getInstructions().size());
            cb.addRecipe(name, nRecipe);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void createRecipes(String key, String host, List<Integer> recipeID){
        List<Recipe> recipes = new ArrayList<>();
        try {
            CookBook cookBook = new CookBook();
            for (Integer id : recipeID) {
                System.out.println(id);
                if(id != null){
                    String sid = id.toString();
                    HttpResponse<String> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + sid + "/information")
                            .header("X-RapidAPI-Host", host)
                            .header("X-RapidAPI-Key", key)
                            .asString();
                    Gson g = new Gson();
                    try{
                        if (response.getStatus()==200) {
                            JsonObject recipe = g.fromJson(response.getBody(), JsonObject.class);
                            System.out.println(recipe.toString());
                            //Recipe info
                            JsonArray analIns = recipe.get("analyzedInstructions").getAsJsonArray().get(0).getAsJsonObject().get("steps").getAsJsonArray();
                            String name = recipe.get("title").getAsString();
                            Integer cooktime = recipe.get("readyInMinutes").getAsInt();
                            List<String> instructs = new ArrayList<>();
                            for (JsonElement element : analIns) {
                                JsonObject obj = element.getAsJsonObject();
                                String inst = obj.get("step").getAsString();
                                instructs.add(inst);
                            }

                            //Use these for the tags
                            Set<DietType> tags = new HashSet<>();
                            if (recipe.get("vegetarian").getAsBoolean()){
                                tags.add(DietType.Vegetarian);
                            }
                            if (recipe.get("vegan").getAsBoolean()){
                                tags.add(DietType.Vegan);
                            }
                            if (recipe.get("glutenFree").getAsBoolean()){
                                tags.add(DietType.GlutenFree);
                            }
                            if (recipe.get("dairyFree").getAsBoolean()){
                                tags.add(DietType.NonDairy);
                            }
                            System.out.println(tags);

                            JsonArray ingredients = recipe.getAsJsonArray("extendedIngredients");
                            List<String> recipeIngredients = new ArrayList<>();
                            for (JsonElement element : ingredients) {
                                JsonObject ing = element.getAsJsonObject();
                                String ingName = ing.get("name").getAsString();
                                recipeIngredients.add(ingName);
                            }
                            constructRecipe(cookBook, name, cooktime, recipeIngredients, instructs, tags);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            cookBook.saveToFile();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        JsonObject api = FileUtil.readFromJson("credentials.json");
        JsonObject spoon = api.get("spoonacular").getAsJsonObject();
        String key = spoon.get("key").getAsString();
        String host = spoon.get("host").getAsString();

        List<String> ingredientList = readInIngredients();
        System.out.println(ingredientList);
        List<String> recipeNames = loadRecipeNames(key, host, ingredientList);
        System.out.println(recipeNames);
        List<Integer> recipeID = loadRecipeIDs(key,host,recipeNames);
        System.out.println(recipeID);
        createRecipes(key,host,recipeID);

    }



}
