package edu.ithaca.goosewillis.icook;

import edu.ithaca.goosewillis.icook.fridge.FridgeSerializer;
import edu.ithaca.goosewillis.icook.util.FileUtil;
import org.junit.jupiter.api.Test;

import edu.ithaca.goosewillis.icook.fridge.Fridge;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FridgeTest {

    @Test
    void getIngredientsTest() {
        Ingredient testIngredient1 = new Ingredient("testIngredient1", 1, 3);
        Ingredient testIngredient2 = new Ingredient("testIngredient2", 2, 3);
        Ingredient testIngredient3 = new Ingredient("testIngredient3", 3, 3);

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
        Ingredient testIngredient1 = new Ingredient("testIngredient1", 1, 2);
        Ingredient testIngredient2 = new Ingredient("testIngredient2", 2, 1);
        Ingredient testIngredient3 = new Ingredient("testIngredient3", 3, 1);

        // create a list and add the 3 ingredients to it
        List<Ingredient> testIngredients = new ArrayList<Ingredient>();
        testIngredients.add(testIngredient1);
        testIngredients.add(testIngredient2);
        testIngredients.add(testIngredient3);

        // create a fridge and pass it the list of ingredients
        Fridge testFridge = new Fridge(testIngredients);

        // test if searching for an ingredient returns that ingredient
        assertEquals(testIngredient1, testFridge.searchIngredient("testIngredient1"));
        // test when the I in ingredient is lowercase
        assertEquals(testIngredient1, testFridge.searchIngredient("testingredient1"));
        // test when the ingredient is not found
        assertEquals(null, testFridge.searchIngredient("fakeIngredient"));
    }

    @Test
    void getIngredientsByCookTimeTest(){
        try {
            Fridge fridge = new FridgeSerializer().deserialize(FileUtil.readFromJson("fridgeTest.json"));

            assertTrue(fridge.getIngredientsByCookTime(1).contains(fridge.searchIngredient("Chicken Breast")));
            assertTrue(fridge.getIngredientsByCookTime(1).contains(fridge.searchIngredient("Tomato")));
            assertFalse(fridge.getIngredientsByCookTime(1).contains(fridge.searchIngredient("Broccoli")));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
