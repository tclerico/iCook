package edu.ithaca.goosewillis.icook.fridge;

import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;


public class Fridge {

    private List<Ingredient> ingredients;

    public Fridge() {
        this.ingredients = new ArrayList<>();
    }

    public Fridge(JsonObject root){
        this.ingredients = new FridgeSerializer().deserialize(root).ingredients;
    }

    public Fridge(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void addIngredient(Ingredient ingredient){ ingredients.add(ingredient); }

    public void removeIngredient(Ingredient ingredient){ ingredients.remove(ingredient); }

    /**
     * returns input ingredient if it exists in the user's fridge, else null
     * @param name
     * @return Ingredient
     */
    public Ingredient searchIngredient(String name) {
        Iterator<Ingredient> myIterator = this.getIngredients().iterator();
        while (myIterator.hasNext()) {
            Ingredient current = myIterator.next();
            if (current.getName().equalsIgnoreCase(name)) {
                return current;
            }
        }
        return null;
    }

    /**
     * returns ingredients of the same cook time
     * @param cookTime
     * @return ArrayList of Ingredients
     */
    public ArrayList<Ingredient> getIngredientsByCookTime(int cookTime){
        ArrayList<Ingredient> ingredientsWithSameCookTime = new ArrayList<>();
        Iterator<Ingredient> itr = this.getIngredients().iterator();
        while(itr.hasNext()){
            Ingredient current = itr.next();
            if(current.getCookTime() == cookTime){
                ingredientsWithSameCookTime.add(current);
            }
        }
        return ingredientsWithSameCookTime;
    }

    /**
     * saves fridge and what it contains to a json file
     */
    public void saveFridgeToFile(){
        try{
            File file = new File("fridge.json");
            if(!file.exists()){
                file.delete();
            }
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(new FridgeSerializer().serialize(this).toString());
            writer.flush();
            writer.close();


        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
