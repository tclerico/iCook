package edu.ithaca.goosewillis.icook.cookbook;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.Recipe;
import edu.ithaca.goosewillis.icook.util.FileUtil;
import edu.ithaca.goosewillis.icook.util.JsonDeserializer;
import edu.ithaca.goosewillis.icook.util.JsonSerializer;

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
        if (FileUtil.fileExists("cookbook.json")) {
            FileUtil.deleteFile("cookbook.json");
        }
        try {
            File file = FileUtil.createFile("cookbook.json");
            FileWriter writer = new FileWriter(file);
            writer.write(FileUtil.gson.toJson(this));
            writer.flush();
            writer.close();
            logger.log(Level.INFO, "Saved cookbook to json file!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Recipe> getRecipes() {
        return recipes;
    }

}