package edu.ithaca.goosewillis.icook.cookbook;

import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.fridge.Fridge;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.user.User;
import edu.ithaca.goosewillis.icook.util.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CookBook {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private Map<String, Recipe> recipes;
    private Map<String, Ingredient> ingredients;

    public CookBook() {
        this.recipes = new HashMap<>();
        this.ingredients = new HashMap<>();
    }


    public CookBook(JsonObject root) {
        CookBook deserialized = new CookbookSerializer().deserialize(root);
        this.recipes = deserialized.recipes;
        this.ingredients = deserialized.ingredients;
        logger.log(Level.INFO, "Recipes & Ingredients loaded into cookbook!");
    }

    public void saveToFile() {
        try {
            File file = new File("cookbook.json");
            if (!file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(FileUtil.gson.toJson(new CookbookSerializer().serialize(this)));
            writer.flush();
            writer.close();
            logger.log(Level.INFO, "Saved cookbook to json file!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRecipe(String name, Recipe recipe) {
        recipes.put(name, recipe);
        logger.log(Level.INFO, "Added new recipe to cookbook!");
    }

    public Map<String, Recipe> getRecipes() {
        return recipes;
    }

    public Map<String, Ingredient> getIngredients() {
        return ingredients;
    }

    public Recipe getSpecificRecipe(String name){
        if(recipes.containsKey(name)){
            return recipes.get(name);
        }else{
            return null;
        }
    }

    public Recipe generateOneTray(ArrayList<Ingredient> toUse){
        int i, j;
        Ingredient temp;
        boolean swapped;
        int n = toUse.size();

        //sort array based on cook time in descending order
        for (i=0; i<n-1; i++){
            swapped = false;
            for (j=0; j<n-i-1; j++){
                if (toUse.get(j).getCookTime() < toUse.get(j).getCookTime()){
                    temp = toUse.get(j);
                    toUse.set(j, toUse.get(j+1));
                    toUse.set(j+1, temp);
                    swapped = true;
                }
            }
            if (!swapped){
                break;
            }
        }

        ArrayList<String> instructions = new ArrayList<>();
        instructions.add("Preheat oven to 350");
        double fullTime = toUse.get(0).getCookTime();
        instructions.add("The total cooking time is: "+Double.toString(fullTime));
        instructions.add("Place "+toUse.get(0).getName()+" in the oven");
        for (i=1; i<toUse.size(); i++){
            String ct = Double.toString(fullTime - toUse.get(i).getCookTime());
            instructions.add("After "+ct+" minutes, add the "+toUse.get(i).getName());
        }
        instructions.add("Remove from oven and let stand for 2 minutes");

        String descript = toUse.get(0).getName()+" one tray meal";
        String name = toUse.get(0).getName()+" one tray meal";

        return new Recipe( name, descript, toUse, instructions, fullTime);

    }

    public ArrayList<Recipe> getRecipeRecommendations(Fridge fridge){
        ArrayList<Recipe> recommendations = new ArrayList<>();

        for(Recipe currRecipe : recipes.values()){
            int canMake = 0;
            List<Ingredient> recipeIngredients = currRecipe.getIngredients();
            for(Ingredient currIngredient : recipeIngredients){
                if(fridge.searchIngredient(currIngredient.getName()).getName() == null){
                    canMake++;
                }
            }
            if(canMake <= 1){
                recommendations.add(currRecipe);
            }
        }
        return recommendations;
    }

}
