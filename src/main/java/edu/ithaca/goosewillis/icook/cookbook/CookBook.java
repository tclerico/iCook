package edu.ithaca.goosewillis.icook.cookbook;

import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.util.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

}
