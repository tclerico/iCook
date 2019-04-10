package edu.ithaca.goosewillis.icook;

import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {

    @Test
    void constructorTest(){

        //Constructor with all valid inputs
        Ingredient i1 = new Ingredient("Chicken Breast", 2, 2);
        Ingredient i2 = new Ingredient("Egg", 3, 1);
        Ingredient i3 = new Ingredient("Bread Crumb", 1, 1);

        List <Ingredient> needed = new ArrayList<>();
        needed.add(i1);
        needed.add(i3);
        needed.add(i2);

        List<String> steps = new ArrayList<>();
        steps.add("Preheat oven to 350 degrees");
        steps.add("Beat eggs in mixing bowl");
        steps.add("Coat chicken breasts in egg");
        steps.add("Roll chicken in bread crumbs");
        steps.add("Add to oven for 35 minutes");

        String descript = "This is the meal description";

        int cooktime = 45;

        Recipe rec = new Recipe("chicken", descript, needed, steps, cooktime);

        List<Ingredient> test1 = rec.getIngredients();
        assertEquals(3, test1.size());

        List<String> test2 = rec.getInstructions();
        assertEquals(5, test2.size());

        assertEquals(45, rec.getCookTime());


        //Constructor with not enough ingredients
        Ingredient ing = new Ingredient("Chicken Breast", 2, 3);

        List <Ingredient> needed1 = new ArrayList<>();
        needed.add(ing);

        List<String> steps1 = new ArrayList<>();
        steps.add("Preheat oven to 350 degrees");
        steps.add("Beat eggs in mixing bowl");
        steps.add("Coat chicken breasts in egg");
        steps.add("Roll chicken in bread crumbs");
        steps.add("Add to oven for 35 minutes");

        String desc = "This is the meal description";

        int time = 45;

        assertThrows(IllegalArgumentException.class, ()-> new Recipe("Chicken", descript, needed1,steps1, time));


        //Constructor with not enough steps
        Ingredient ing1 = new Ingredient("Chicken Breast", 2, 2);
        Ingredient ing2 = new Ingredient("Egg", 3, 1);
        Ingredient ing3 = new Ingredient("Bread Crumb", 1, 2);

        List <Ingredient> req = new ArrayList<>();
        needed.add(ing1);
        needed.add(ing3);
        needed.add(ing2);

        List<String> instruct = new ArrayList<>();
        steps.add("Preheat oven to 350 degrees");

        String description = "This is the meal description";

        int ct = 45;

        assertThrows(IllegalArgumentException.class, ()-> new Recipe("Chicken", description, req, instruct, ct));

        // constructor with tags
        List<DietType> tags = new ArrayList<>();
        tags.add(DietType.Vegeterian);
        tags.add(DietType.Vegan);

        Recipe taggedRecipe = new Recipe("chicken", descript, tags, needed, steps, cooktime);

        List<DietType> myTags = taggedRecipe.getTags();
        assertEquals(2, myTags.size());

    }

    @Test
    void checkIngredients(){
        Ingredient i1 = new Ingredient("Chicken Breast", 2, 1);
        Ingredient i2 = new Ingredient("Egg", 3, 1);
        Ingredient i3 = new Ingredient("Bread Crumb", 1, 1);

        List <Ingredient> needed = new ArrayList<>();
        needed.add(i1);
        needed.add(i2);
        needed.add(i3);

        List<String> steps = new ArrayList<>();
        steps.add("step1");
        steps.add("step2");
        steps.add("step3");


        String descript = "This is the meal description";

        Recipe rec = new Recipe("Chicken", descript, needed,steps, 10);

        //Check length of ingredient list
        List<Ingredient> test = rec.getIngredients();
        assertEquals(3, test.size());

        //check each ingredient
        assertEquals("Chicken Breast", test.get(0).getName());
        assertEquals("Egg", test.get(1).getName() );
        assertEquals("Bread Crumb", test.get(2).getName());
    }

    @Test
    void checkInstructions(){
        Ingredient i1 = new Ingredient("Chicken Breast", 2, 1);
        Ingredient i2 = new Ingredient("Egg", 3, 1);
        Ingredient i3 = new Ingredient("Bread Crumb", 1, 1);

        List <Ingredient> needed = new ArrayList<>();
        needed.add(i1);
        needed.add(i3);
        needed.add(i2);

        List<String> steps = new ArrayList<>();
        steps.add("step1");
        steps.add("step2");
        steps.add("step3");


        String descript = "This is the meal description";

        Recipe rec = new Recipe("Shicken", descript, needed,steps, 10);

        List<String> test = rec.getInstructions();
        assertEquals(3, test.size());


        assertEquals("step1", test.get(0));
        assertEquals("step2", test.get(1));
        assertEquals("step3", test.get(2));

    }

    @Test
    void checkCookTime(){
        Ingredient i1 = new Ingredient("Chicken Breast", 2, 1);
        Ingredient i2 = new Ingredient("Egg", 3, 1);
        Ingredient i3 = new Ingredient("Bread Crumb", 1, 1);

        List <Ingredient> needed = new ArrayList<>();
        needed.add(i1);
        needed.add(i3);
        needed.add(i2);

        List<String> steps = new ArrayList<>();
        steps.add("step1");
        steps.add("step2");
        steps.add("step3");


        String descript = "This is the meal description";

        Recipe rec = new Recipe("Shicken", descript, needed,steps, 10);

        assertEquals(10, rec.getCookTime());
    }

    @Test
    void getTagsTest() {
        // test if get tags returns the current number of tags in the recipe

        Ingredient i1 = new Ingredient("Chicken Breast", 2, 2);

        List<String> testSteps = new ArrayList<String>();
        testSteps.add("Preheat oven to 350 degrees");

        List<Ingredient> testNeeded = new ArrayList<Ingredient>();
        testNeeded.add(i1);

        List<DietType> tags = new ArrayList<DietType>();

        tags.add(DietType.Vegan);
        tags.add(DietType.Vegeterian);

        Recipe rec = new Recipe("test", "test", tags, testNeeded, testSteps, 1.0);

        assertEquals(2, rec.getTags().size());
    }


}
