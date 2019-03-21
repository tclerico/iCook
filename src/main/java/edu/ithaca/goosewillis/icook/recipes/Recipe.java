package edu.ithaca.goosewillis.icook.recipes;

import edu.ithaca.goosewillis.icook.cookbook.CookBook;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;

import java.util.List;

public class Recipe {

    private List<Ingredient> ingredients;
    private List<String> instructions;
    private String description;
    private String name;
    private int cookTime;

    public Recipe() {
    }

    public Recipe(String name, String description, List<Ingredient> needed, List<String> steps, int cooktime) {
        if (needed.size() > 2 && steps.size() > 2) {
            this.name = name;
            this.ingredients = needed;
            this.instructions = steps;
            this.description = description;
            this.cookTime = cooktime;

        } else {
            throw new IllegalArgumentException("Not enough information to constitute a recipe");
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public String getDescription() {
        return description;
    }

    public int getCookTime() {
        return cookTime;
    }

}
