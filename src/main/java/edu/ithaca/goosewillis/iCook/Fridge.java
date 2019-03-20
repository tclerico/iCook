package edu.ithaca.goosewillis.iCook;

import java.util.List;
import java.util.ArrayList;


public class Fridge {
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();

    Fridge() {

    }

    Fridge(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public Ingredient searchIngredient(String name) {
        return null;
    }

}
