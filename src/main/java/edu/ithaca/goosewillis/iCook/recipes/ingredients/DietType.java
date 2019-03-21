package edu.ithaca.goosewillis.icook.recipes.ingredients;

public enum DietType {

    NONE("None"), VEGETERIAN("Vegeterian"), VEGAN("Vegan");

    private String name;

    DietType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
