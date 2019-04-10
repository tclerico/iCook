package edu.ithaca.goosewillis.icook;

import edu.ithaca.goosewillis.icook.fridge.Fridge;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.user.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static edu.ithaca.goosewillis.icook.recipes.ingredients.DietType.None;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test //test for constructor and getters/setters
    void constructorTest(){
        Fridge fridge = new Fridge();
        ArrayList<Ingredient> dislikedIngredients = new ArrayList<>();
        ArrayList<Recipe> favoriteRecipes = new ArrayList<>();
        ArrayList<DietType> restrictions = new ArrayList<>();

        User user1 = new User("user1", "password", fridge, dislikedIngredients, favoriteRecipes, restrictions);
        user1.setUsername("newUser1");
        user1.setPassword("newPassword");
        assertEquals("newUser1", user1.getUsername());
        assertEquals("newPassword", user1.getPassword());

        assertThrows(IllegalArgumentException.class, ()-> new User(null, "password", fridge, dislikedIngredients, favoriteRecipes, restrictions));
        assertThrows(IllegalArgumentException.class, ()-> new User("user1", null, fridge, dislikedIngredients, favoriteRecipes, restrictions));
    }

    @Test //tests user's ability to add to fridge, remove from fridge, and use ingredient
    void fridgeTest(){
        Ingredient chickenBreast = new Ingredient("Chicken Breast", 3, 2, None);
        Ingredient brownRice = new Ingredient("Brown Rice", 2, 1, None);
        Ingredient broccoli = new Ingredient("Broccoli", 5, 1, None);

        Fridge fridge = new Fridge();
        ArrayList<Ingredient> dislikedIngredients = new ArrayList<>();
        ArrayList<Recipe> favoriteRecipes = new ArrayList<>();
        ArrayList<DietType> restrictions = new ArrayList<>();

        User user1 = new User("user1", "password", fridge, dislikedIngredients, favoriteRecipes, restrictions);
        user1.addToFridge(chickenBreast);
        user1.addToFridge(brownRice);
        user1.addToFridge(broccoli);

        assertEquals(fridge.searchIngredient("Chicken Breast").getName(), "Chicken Breast");
        assertEquals(fridge.searchIngredient("Chicken Breast").getCount(), 3);
        assertEquals(fridge.searchIngredient("Chicken Breast").getCookTime(), 2);
        assertEquals(fridge.searchIngredient("Brown Rice").getName(), "Brown Rice");
        assertEquals(fridge.searchIngredient("Brown Rice").getCount(), 2);
        assertEquals(fridge.searchIngredient("Brown Rice").getCookTime(), 1);
        assertEquals(fridge.searchIngredient("Broccoli").getName(), "Broccoli");
        assertEquals(fridge.searchIngredient("Broccoli").getCount(), 5);
        assertEquals(fridge.searchIngredient("Broccoli").getCookTime(), 1);

        user1.removeFromFridge(brownRice);

        assertTrue(fridge.searchIngredient("Brown Rice") == null);

        user1.useIngredient("Chicken Breast", 2);

        assertEquals(fridge.searchIngredient("Chicken Breast").getCount(), 1);
    }

    @Test //tests user's ability to add/remove disliked ingredients and dietary restrictions
    void updateListsTest(){
        Fridge fridge = new Fridge();
        ArrayList<Ingredient> dislikedIngredients = new ArrayList<>();
        ArrayList<Recipe> favoriteRecipes = new ArrayList<>();
        ArrayList<DietType> restrictions = new ArrayList<>();

        User user1 = new User("user1", "password", fridge, dislikedIngredients, favoriteRecipes, restrictions);

        Ingredient mayo = new Ingredient("Mayo", 1, 0, None);
        user1.addDislikedIngredient(mayo);

        assertTrue(dislikedIngredients.contains(mayo));
        assertFalse(!dislikedIngredients.contains(mayo));

        user1.removeDislikedIngredient(mayo);

        assertTrue(!dislikedIngredients.contains(mayo));
        assertFalse(dislikedIngredients.contains(mayo));

    }

}
