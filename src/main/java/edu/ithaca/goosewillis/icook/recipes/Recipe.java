package edu.ithaca.goosewillis.icook.recipes;

import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private List<Ingredient> ingredients;
    private List<String> instructions;
    private String description;
    private String name;
    private double cookTime;

    public Recipe() {
    }

    public Recipe(String name, String description, List<Ingredient> needed, List<String> steps, double cooktime) {
        if (needed.size() >= 1 && steps.size() >= 1) {
            this.name = name;
            this.ingredients = needed;
            this.instructions = steps;
            this.description = description;
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

    public Recipe generateOneTray(ArrayList<Ingredient> toUse){
        int i, j;
        Ingredient temp;
        boolean swapped;
        int n = toUse.size();

        //sort array based on cook time in descending order
        for (i=0; i<n-1; i++){
            swapped = false;
            for (j=0; j<n-i-1; j++){
                if (toUse.get(j).getCookTime() < toUse.get(j).getCookTime()){
                    temp = toUse.get(j);
                    toUse.set(j, toUse.get(j+1));
                    toUse.set(j+1, temp);
                    swapped = true;
                }
            }
            if (!swapped){
                break;
            }
        }

        ArrayList<String> instructions = new ArrayList<>();
        instructions.add("Preheat oven to 350");
        double fullTime = toUse.get(0).getCookTime();
        instructions.add("The total cooking time is: "+Double.toString(fullTime));
        instructions.add("Place "+toUse.get(0).getName()+" in the oven");
        for (i=1; i<toUse.size(); i++){
            String ct = Double.toString(fullTime - toUse.get(i).getCookTime());
            instructions.add("After "+ct+" minutes, add the "+toUse.get(i).getName());
        }
        instructions.add("Remove from oven and let stand for 2 minutes");

        String descript = toUse.get(i).getName()+" one tray meal";
        String name = toUse.get(i).getName()+" one tray meal";

        //Recipe oneTray = new Recipe( name, descript, toUse, instructions, fullTime);

        return new Recipe( name, descript, toUse, instructions, fullTime);

    }


}
