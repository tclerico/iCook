package edu.ithaca.icook.cookbook;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.ithaca.icook.Recipe;
import edu.ithaca.icook.util.FileUtil;
import edu.ithaca.icook.util.JsonDeserializer;
import edu.ithaca.icook.util.JsonSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CookBook implements JsonDeserializer<CookBook>, JsonSerializer<CookBook> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private Map<String, Recipe> recipes;

    public CookBook() {
        this.recipes = new HashMap<>();
    }


    public CookBook(JsonObject root) {
        JsonArray array = root.get("recipes").getAsJsonArray();
        for (JsonElement element : array) {
            JsonObject recipe = element.getAsJsonObject();
            String id = recipe.get("id").getAsString();
            //Recipe r = deserialize(recipe.get("recipe").getAsJsonObject())
            //put into the hashmap
        }
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


    @Override
    public CookBook deserialized(JsonObject root) {
        return null;
    }

    @Override
    public JsonObject serialize(CookBook toSerialize) {
        return null;
    }
}
