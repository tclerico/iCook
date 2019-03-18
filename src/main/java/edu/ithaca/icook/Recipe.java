package edu.ithaca.icook;

import java.util.List;

public class Recipe {

    public List<Ingredient> ingredients;
    public List<String> instructions;
    public String description;
    public int cookTime;

    public Recipe(){}

    public Recipe(List<Ingredient> needed, List<String> steps, String description, int cooktime){
        this.ingredients = needed;
        this.instructions = steps;
        this.description = description;
        this.cookTime = cooktime;
    }

    public List<Ingredient> getIngredients(){ return ingredients; }

    public List<String> getInstructions(){ return instructions; }

    public String getDescription(){ return description; }

    public int getCookTime(){ return cookTime; }

}
