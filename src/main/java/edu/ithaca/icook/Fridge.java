package edu.ithaca.icook;

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


}