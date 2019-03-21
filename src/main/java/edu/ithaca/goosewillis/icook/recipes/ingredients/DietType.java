package edu.ithaca.goosewillis.icook.recipes.ingredients;

public enum DietType {

    None("None"), Vegeterian("Vegeterian"), Vegan("Vegan");

    private String name;

    DietType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
