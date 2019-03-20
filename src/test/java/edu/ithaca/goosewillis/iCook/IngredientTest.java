package edu.ithaca.goosewillis.iCook;

import edu.ithaca.goosewillis.icook.Ingredient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {

    @Test
    void constructorTest(){
        Ingredient ingredient1 = new Ingredient("Chicken Breast", 4);

        assertEquals("Chicken Breast", ingredient1.getName());
        assertEquals(4, ingredient1.getCount());

        ingredient1.setName("Chicken Wing");
        ingredient1.setCount(12);
        assertEquals("Chicken Wing", ingredient1.getName());
        assertEquals(12, ingredient1.getCount());

        assertThrows(IllegalArgumentException.class, ()-> new Ingredient("Sauerkraut", 0));
        assertThrows(IllegalArgumentException.class, ()-> new Ingredient("Sauerkraut", -5));
        assertThrows(IllegalArgumentException.class, ()-> new Ingredient("Sauerkraut", -.01));
    }
}
