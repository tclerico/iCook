package edu.ithaca.goosewillis.icook;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.ithaca.goosewillis.icook.APIQueries.IngredientQueries;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.util.FileUtil;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static edu.ithaca.goosewillis.icook.APIQueries.IngredientQueries.readInIngredients;

public class IngredientQueriesIT {

    @Test
    public static void fullAPITest() throws UnirestException {
        String key = "";
        String host = "";
        try {
            JsonObject api = FileUtil.readFromJson("credentials.json");
            JsonObject spoon = api.get("spoonacular").getAsJsonObject();
            key = spoon.get("key").getAsString();
            host = spoon.get("host").getAsString();
        }catch (Exception e){
            e.printStackTrace();
        }

        String name = "apple";

        HttpResponse<String> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/site/search?query=" + name)
                .header("X-RapidAPI-Host", host)
                .header("X-RapidAPI-Key", key)
                .asString();

        assertEquals(200, response.getStatus());
        Gson g = new Gson();
        JsonObject rec = g.fromJson(response.getBody(), JsonObject.class);
        JsonArray query = rec.getAsJsonArray("Recipes");
        JsonObject first = query.get(0).getAsJsonObject();
        String rName = first.get("name").getAsString();

        String clean = rName.trim().replace(' ', '+');
        HttpResponse<String> idResponse = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/search?number=1&offset=0&instructionsRequired=true&query="+clean)
                .header("X-RapidAPI-Host", host)
                .header("X-RapidAPI-Key", key)
                .asString();
        assertEquals(200, idResponse.getStatus());

        Gson gID = new Gson();
        JsonObject r = gID.fromJson(response.getBody(), JsonObject.class);
        JsonArray result = r.getAsJsonArray("results");
        JsonObject firstRecipe = result.get(0).getAsJsonObject();

        Integer id = firstRecipe.get("id").getAsInt();

        String sid = id.toString();
        HttpResponse<String> fullResponse = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + sid + "/information")
                .header("X-RapidAPI-Host", host)
                .header("X-RapidAPI-Key", key)
                .asString();

        assertEquals(200, fullResponse.getStatus());
        Gson fg = new Gson();
        JsonObject recipe = g.fromJson(response.getBody(), JsonObject.class);
        JsonArray analIns = recipe.get("analyzedInstructions").getAsJsonArray().get(0).getAsJsonObject().get("steps").getAsJsonArray();
        String recipeName = recipe.get("title").getAsString();
        Integer cooktime = recipe.get("readyInMinutes").getAsInt();
        List<String> instructs = new ArrayList<>();
        for (JsonElement element : analIns) {
            JsonObject obj = element.getAsJsonObject();
            String inst = obj.get("step").getAsString();
            instructs.add(inst);
        }

        JsonArray ingredients = recipe.getAsJsonArray("extendedIngredients");
        List<String> recipeIngredients = new ArrayList<>();
        for (JsonElement element : ingredients) {
            JsonObject ing = element.getAsJsonObject();
            String ingName = ing.get("name").getAsString();
            recipeIngredients.add(ingName);
        }

        List<Ingredient> toAdd = new ArrayList<>();
        for(String Rname : recipeIngredients){
            Ingredient add = new Ingredient(Rname, 1, 10);
            toAdd.add(add);
        }

        Recipe nRecipe = new Recipe(rName, " ", toAdd, instructs, cooktime);
        List<Ingredient> ings = nRecipe.getIngredients();
        Ingredient apple = new Ingredient("apple", 1,10);
        assertTrue(ings.contains(apple));


    }


    @Test
    public static void ingredientRequestTest(){
        List<String> ingredientTest = readInIngredients();
        assertTrue(ingredientTest.size() > 10);
    }





}
