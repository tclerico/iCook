package edu.ithaca.goosewillis.icook.fridge;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.recipes.ingredients.IngredientSerializer;
import edu.ithaca.goosewillis.icook.util.JsonSerializer;
import edu.ithaca.goosewillis.icook.util.JsonDeserializer;


import java.util.ArrayList;
import java.util.List;

public class FridgeSerializer implements JsonSerializer<Fridge>, JsonDeserializer<Fridge> {

    @Override
    public Fridge deserialize(JsonObject root) {
        Fridge fridge = new Fridge();
        List<Ingredient> ingredients = fridge.getIngredients();
        JsonArray array = root.get("ingredients").getAsJsonArray();
        for (JsonElement element : array){
            JsonObject ingredient = element.getAsJsonObject();
            Ingredient i = new IngredientSerializer().deserialize(ingredient);
            ingredients.add(i);
        }
        return fridge;
    }

    @Override
    public JsonObject serialize(Fridge toSerialize) {
        JsonObject root = new JsonObject();
        JsonArray array = new JsonArray();
        for (Ingredient ingredient : toSerialize.getIngredients()){
            array.add(new IngredientSerializer().serialize(ingredient));
        }
        root.add("ingredients", array);
        return root;
    }

}
