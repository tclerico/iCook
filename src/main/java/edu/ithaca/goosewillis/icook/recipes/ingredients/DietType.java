package edu.ithaca.goosewillis.icook.recipes.ingredients;

public enum DietType {

    None("None"), Vegetarian("Vegetarian"), Vegan("Vegan"), GlutenFree("Gluten Free"), NonDairy("Non Dairy");

    private String name;

    DietType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
