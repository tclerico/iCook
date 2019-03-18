package edu.ithaca.icook;


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

}
