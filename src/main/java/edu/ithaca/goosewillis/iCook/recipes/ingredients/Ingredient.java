package edu.ithaca.goosewillis.icook.recipes.ingredients;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;

public class Ingredient {

    private String name;
    private double count;
    private DietType dietType = DietType.NONE;
    public double cookTime;
    // will add appropriate tags (Vegan, Vegetarian, etc.) after API stuff
    // need to discuss with client about ovenTime

    public Ingredient(String name, double count, double cookTime) {
        if (count > 0) {
            this.name = name;
            this.count = count;
            this.cookTime = cookTime;
        } else {
            throw new IllegalArgumentException(count + "is not a valid ingredient count");
        }
    }


    public Ingredient(String name, double count, double cookTime, DietType dietType) {
        if (count > 0) {
            this.name = name;
            this.count = count;
            this.cookTime = cookTime;
            this.dietType = dietType;
        } else {
            throw new IllegalArgumentException(count + "is not a valid ingredient count");
        }
    }

    public void setCount(double count) {
        this.count = count;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }

    public DietType getDietType() {
        return dietType;
    }

    public String getName() {
        return name;
    }

    public double getCookTime(){return cookTime;}

    public double getCount() {
        return count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
