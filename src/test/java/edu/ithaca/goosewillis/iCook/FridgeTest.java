package edu.ithaca.goosewillis.iCook;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class FridgeTest {

    @Test
    void getIngredientsTest() {
        Ingredient testIngredient1 = new Ingredient("testIngredient1", 1);
        Ingredient testIngredient2 = new Ingredient("testIngredient2", 2);
        Ingredient testIngredient3 = new Ingredient("testIngredient3", 3);

        List<Ingredient> testIngredients = new ArrayList<Ingredient>();
        testIngredients.add(testIngredient1);
        testIngredients.add(testIngredient2);
        testIngredients.add(testIngredient3);

        Fridge testFridge = new Fridge(testIngredients);

        assertEquals(testIngredients, testFridge.getIngredients());
    }

    @Test
    void searchIngredientTest() {
        // create 3 ingredients
        Ingredient testIngredient1 = new Ingredient("testIngredient1", 1);
        Ingredient testIngredient2 = new Ingredient("testIngredient2", 2);
        Ingredient testIngredient3 = new Ingredient("testIngredient3", 3);

        // create a list and add the 3 ingredients to it
        List<Ingredient> testIngredients = new ArrayList<Ingredient>();
        testIngredients.add(testIngredient1);
        testIngredients.add(testIngredient2);
        testIngredients.add(testIngredient3);

        // create a fridge and pass it the list of ingredients
        Fridge testFridge = new Fridge(testIngredients);

        // test if searching for an ingredient returns that ingredient
        assertEquals(testIngredient1, testFridge.searchIngredient("testIngredient1"));
    }

}
