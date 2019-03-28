package edu.ithaca.goosewillis.icook;

import edu.ithaca.goosewillis.icook.fridge.Fridge;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.user.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void constructorTest(){

    }

    @Test
    void addToFridgeTest(){
        Ingredient chickenBreast = new Ingredient("Chicken Breast", 3, 2);
        Ingredient brownRice = new Ingredient("Brown Rice", 2, 1);
        Ingredient broccoli = new Ingredient("Broccoli", 5, 1);

        Fridge testFridge = new Fridge();
        User user1 = new User("username", "password", testFridge, );

        user1.addToFridge(chickenBreast);
        user1.addToFridge(brownRice);
        user1.addToFridge(broccoli);

    }

    @Test
    void getFromFridgeTest(){

    }
}
