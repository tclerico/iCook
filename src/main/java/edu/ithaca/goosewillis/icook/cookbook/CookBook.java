package edu.ithaca.goosewillis.icook.cookbook;

import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.fridge.Fridge;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.user.User;
import edu.ithaca.goosewillis.icook.util.FileUtil;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CookBook {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private static Map<String, Recipe> recipes;
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
        if(!recipes.containsKey(name)){
            recipes.put(name, recipe);
            logger.log(Level.INFO, "Added new recipe to cookbook!");
        }
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

    public Recipe generateOneTray(HashMap<String, String> toUse){
        // toUse is a map of ingredient names to food type, e.g.  Broccoli: Vegetable
        // cook time is given in gui when food type is selected

        List<String> beef = new ArrayList<>();
        List<String> chicken = new ArrayList<>();
        List<String> vegetable = new ArrayList<>();
        List<String> pork = new ArrayList<>();
        List<Ingredient> allIngredients = new ArrayList<>();
        for (Map.Entry entry : toUse.entrySet()){
            if (entry.getValue().equals("Beef")){
                beef.add(entry.getKey().toString());
            }
            else if (entry.getValue().equals("Chicken")){
                chicken.add(entry.getKey().toString());
            }
            else if (entry.getValue().equals("Vegetable")){
                vegetable.add(entry.getKey().toString());
            }
            else {
                pork.add(entry.getKey().toString());
            }
            allIngredients.add(new Ingredient(entry.getKey().toString(), 1,1));
        }

        Integer bsize = beef.size();
        Integer csize = chicken.size();
        Integer vsize = vegetable.size();
        Integer psize = pork.size();

        List<String> instructions = new ArrayList<>();

        double cookTime;
        if (bsize > 0 || csize > 0){
            cookTime = 35.00;
        }else if(vsize > 0){
            cookTime = 30.00;
        }else{
            cookTime = 15.00;
        }

        instructions.add("The total cooking time is: " + Double.toString(cookTime));
        String b = "";
        String c = "";
        String v = "";
        String p = "";
        //double delta = 0;
        if (bsize > 0){
            b = concatList(beef);
        }
        if (csize>0){
            c = concatList(chicken);
        }
        if (vsize > 0){
            v = concatList(vegetable);
        }
        if (psize > 0){
            p = concatList(pork);
        }

        instructions.add("Preheat oven to 425");
        instructions.add("Lightly coat pan in vegetable oil");

        if (b.length() > 0 || c.length() >0){
            instructions.add("Add "+b+" "+c+" to the pan");
            if (vsize>0){
                instructions.add("Cook for 5 minutes");
            }else if (psize>0){
                instructions.add("Cook for 20 minutes");
            }else{
                instructions.add("Cook for 35 minutes");
            }
        }

        if (vsize > 0){
            instructions.add("Add "+v+" to the pan");
            if (psize>0){
                instructions.add("Cook for 15 minutes");
            }else {
                instructions.add("Cook for 30 minutes");
            }
        }

        if (psize>0){
            instructions.add("Add "+p+" to the pan");
            instructions.add("Cook for 15 minutes");
        }

        instructions.add("Remove from oven and let stand for 2 minutes");
        instructions.add("ENJOY!");

        Recipe generated = new Recipe("OneTray meal", "Generated meal", allIngredients,instructions,cookTime);
        return generated;

    }


    public String concatList(List<String> tocat){
        String str = "";
        for (int i=0; i<tocat.size(); i++){
            if (i==tocat.size()-1){
                str+=tocat.get(i);
            }
            else {
                str+=tocat.get(i)+", ";
            }
        }
        return str;
    }

    public ArrayList<Recipe> getRecipeRecommendations(Fridge fridge, ArrayList<DietType> restrictions, ArrayList<Ingredient> dislikedIngredients){
        ArrayList<Recipe> recommendations = new ArrayList<Recipe>();
        try {
            Reccomend:
            for (Recipe currRecipe : this.recipes.values()) {
                //canMake is an incrementor that will not add a certain recipe to the recommendations ArrayList (if it is set above 1 for now)
                int canMake = 0;

                //if recipe does not contain a restriction in the user's ArrayList, it will not be recommended
                for(int i = 0; i < restrictions.size(); i++){
                    if(!currRecipe.getTags().contains(restrictions.get(i))){
                        break Reccomend;
                    }
                }

                List<Ingredient> recipeIngredients = currRecipe.getIngredients();
                for (Ingredient currIngredient : recipeIngredients) {
                    //increments canMake if user doesn't have ingredient
                    if (fridge.searchIngredient(currIngredient.getName()) == null) {
                        canMake++;
                    }
                    //increments canMake so recipe won't be recommended if there is a dislikedIngredient
//                    if(dislikedIngredients.contains(currIngredient)){
//                        canMake += 10;
//                    }
                }

                if (canMake <= 2) {
                    recommendations.add(currRecipe);
                }
            }
        }catch (NullPointerException e){
            System.out.println("NullPointerException Caught");
        }
        return recommendations;
    }

    public List<Recipe> recommendRecipes(Fridge fridge){
        List<Recipe> recommendations = new ArrayList<>();
        List<String> userIng = new ArrayList<>();
        for (Ingredient i : fridge.getIngredients()){
            userIng.add(i.getName());
        }

        for (Map.Entry<String, Recipe> entry : this.recipes.entrySet()){
            Recipe curr = entry.getValue();
            List<String> currList = new ArrayList<>();
            for (Ingredient i : curr.getIngredients()){
                currList.add(i.getName());
            }
            List<String> currListCopy = new ArrayList<>(currList);
            currListCopy.retainAll(userIng);
            int delta = currList.size()-currListCopy.size();
            if (delta <= 2){
                recommendations.add(curr);
            }

        }


        return recommendations;

    }

    public Set<Recipe> getRecipesByTag(DietType tag){
        Set<Recipe> recipesByTag = new HashSet<>();
        for (Recipe recipe : this.recipes.values()) {
                if (recipe.getTags().contains(tag)) {
                    if (!recipesByTag.contains(recipe)) {
                        recipesByTag.add(recipe);
                    }
            }
        }
        return recipesByTag;
    }

    public static Recipe getRecipe(String name) {
        Recipe recipe = recipes.get(name);
        if (recipe == null) return null;
        return recipe;
    }


    public Set<Recipe> getRecipesByTime(double minutes) {
        Set<Recipe> inTime = new HashSet<>();
        for (Recipe recipe : this.recipes.values()) {
            if (recipe.getCookTime() <= minutes) {
                inTime.add(recipe);
            }
        }
        return inTime;
    }
}
