package edu.ithaca.goosewillis.icook.fridge;

import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


public class Fridge {

    private List<Ingredient> ingredients;

    public Fridge() {
        this.ingredients = new ArrayList<>();
    }

    public Fridge(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    // null otherwise
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

}
