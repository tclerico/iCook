package edu.ithaca.goosewillis.icook.recipes;

import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Recipe {

    private List<Ingredient> ingredients;
    private List<String> instructions;
    private Set<DietType> tags;
    private String description;
    private String name;
    private double cookTime;

    public Recipe() {
    }

    public Recipe(String name, String description, List<Ingredient> needed, List<String> steps, double cooktime) {
        if (needed.size() >= 1 && steps.size() >= 1) {
            this.name = name;
            this.description = description;
            this.ingredients = needed;
            this.instructions = steps;
            this.cookTime = cooktime;
            this.tags = null;
        } else {
            throw new IllegalArgumentException("Not enough information to constitute a recipe");
        }
    }

    public Recipe(String name, String description, Set<DietType> tags, List<Ingredient> needed, List<String> steps, double cooktime) {
        if (needed.size() >= 1 && steps.size() >= 1) {
            this.name = name;
            this.description = description;
            this.tags = tags;
            this.ingredients = needed;
            this.instructions = steps;
            this.cookTime = cooktime;
        } else {
            throw new IllegalArgumentException("Not enough information to constitute a recipe");
        }
    }

    @Override
    public String toString() {
        return "Name: " + name + " Description: " + description;
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

    public double getCookTime() {
        return cookTime;
    }

    public Set<DietType> getTags() {
        return tags;
    }

    /**
     * displays the recipe as a String listing ingredients, steps, and tags
     * @return String of Recipe
     */
    public String display(){
        String toReturn = name+"\n"+description+"\n"+"Ingredients Needed:\n";
        for (int i=0; i<ingredients.size(); i++){
            toReturn = toReturn + Integer.toString(i+1)+": " + ingredients.get(i).getName()+"\n";
        }
        toReturn = toReturn+"Steps:\n";
        for (int j=0; j<instructions.size(); j++){
            toReturn = toReturn + instructions.get(j)+"\n";
        }
        toReturn = toReturn+"Tags:\n";
        for (DietType type : tags) {
            toReturn += type + "\n";
        }
        return toReturn;
    }


}
