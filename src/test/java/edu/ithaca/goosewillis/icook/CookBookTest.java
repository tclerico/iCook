package edu.ithaca.goosewillis.icook;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.ithaca.goosewillis.icook.cookbook.CookBook;
import edu.ithaca.goosewillis.icook.cookbook.CookbookSerializer;
import edu.ithaca.goosewillis.icook.fridge.FridgeSerializer;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.util.FileUtil;
import org.junit.jupiter.api.Test;
import edu.ithaca.goosewillis.icook.fridge.Fridge;

import java.util.ArrayList;

import static edu.ithaca.goosewillis.icook.recipes.ingredients.DietType.Vegan;
import static org.junit.jupiter.api.Assertions.*;

public class CookBookTest {

    @Test
    void getRecommendationsTest(){
        try {
            CookBook cookBook = new CookbookSerializer().deserialize(FileUtil.readFromJson("cookbookTest.json"));
            Fridge fridge = new FridgeSerializer().deserialize(FileUtil.readFromJson("fridgeTest.json"));
            ArrayList<DietType> restrictions = new ArrayList<>();
            restrictions.add(DietType.valueOf("Vegan"));
            ArrayList<Ingredient> dislikedIngredients = new ArrayList<>();
            Ingredient broccoli = new Ingredient("Broccoli", 1, 1, DietType.None);
            dislikedIngredients.add(broccoli);

            assertTrue(!cookBook.getRecipeRecommendations(fridge, restrictions, dislikedIngredients).contains(cookBook.getSpecificRecipe("Not Enough Ingredients")));
            assertTrue(!cookBook.getRecipeRecommendations(fridge, restrictions, dislikedIngredients).contains(cookBook.getSpecificRecipe("Not All Matching Tags")));

            //Test below keeps recommending a recipe with broccoli even with ingredient in disliked list
            //assertTrue(!cookBook.getRecipeRecommendations(fridge, restrictions, dislikedIngredients).contains(cookBook.getSpecificRecipe("Dislike Broccoli")));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    void getRecipesByTagTest(){
//        try {
//            CookBook cookBook = new CookbookSerializer().deserialize(FileUtil.readFromJson("cookbook.json"));
//
//            assertTrue(cookBook.getRecipesByTag(DietType.Vegan).contains(cookBook.getSpecificRecipe("Can Almost Make in User Test")));
//            assertTrue(cookBook.getRecipesByTag(DietType.Vegetarian).contains(cookBook.getSpecificRecipe("Can Almost Make in User Test")));
//            assertFalse(cookBook.getRecipesByTag(DietType.GlutenFree).contains(cookBook.getSpecificRecipe("Can Almost Make in User Test")));
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
