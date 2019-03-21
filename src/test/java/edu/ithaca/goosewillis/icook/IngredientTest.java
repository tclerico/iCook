package edu.ithaca.goosewillis.icook;

import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {

    @Test
    void constructorTest(){
        Ingredient ingredient1 = new Ingredient("Chicken Breast", 4, 15);

        //check getters
        assertEquals("Chicken Breast", ingredient1.getName());
        assertEquals(4, ingredient1.getCount());
        //assertEquals(15, ingredient1.getCookTime());

        //check setters
        ingredient1.setName("Chicken Wing");
        ingredient1.setCount(12);
        //ingredient1.setCookTime(20);
        assertEquals("Chicken Wing", ingredient1.getName());
        assertEquals(12, ingredient1.getCount());
        //assertEquals(20, ingredient1.getCookTime());

        //check illegal inputs
        assertThrows(IllegalArgumentException.class, ()-> new Ingredient("Sauerkraut", 0, 10));
        assertThrows(IllegalArgumentException.class, ()-> new Ingredient("Sauerkraut", -5, 10));
        assertThrows(IllegalArgumentException.class, ()-> new Ingredient("Sauerkraut", -.01, 10));
        assertThrows(IllegalArgumentException.class, ()-> new Ingredient("Sauerkraut", 5, 0));
        assertThrows(IllegalArgumentException.class, ()-> new Ingredient("Sauerkraut", 5, -10));
        assertThrows(IllegalArgumentException.class, ()-> new Ingredient("", 5, 10));
        assertThrows(IllegalArgumentException.class, ()-> new Ingredient(" ", 5, 10));
    }
}
