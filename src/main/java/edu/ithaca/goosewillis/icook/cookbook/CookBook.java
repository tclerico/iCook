package edu.ithaca.goosewillis.icook.cookbook;

import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.util.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CookBook {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private Map<String, Recipe> recipes;

    public CookBook() {
        this.recipes = new HashMap<>();
    }


    public CookBook(JsonObject root) {
        this.recipes = new CookbookSerializer().deserialize(root).recipes;
        logger.log(Level.INFO, "Recipes loaded into cookbook!");
    }

    public void saveToFile() {
        try {
            File file = new File("cookbook.json");
            if (!file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(new CookbookSerializer().serialize(this).toString());
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

}
