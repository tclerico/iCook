package edu.ithaca.goosewillis.icook;

import edu.ithaca.goosewillis.icook.cookbook.CookBook;
import edu.ithaca.goosewillis.icook.cookbook.CookbookSerializer;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //Main cookbook instance
        CookBook cookBook = new CookBook();
        //These are the lists holding the ingredients for specific recipes
        List<Ingredient> pasta = new ArrayList<>();
        List<Ingredient> chicken = new ArrayList<>();
        List<Ingredient> pizza = new ArrayList<>();

        List<String> pastaInstructions = new ArrayList<>();
        List<String> chickenInstructions = new ArrayList<>();
        List<String> pizzaInstructions = new ArrayList<>();

        pastaInstructions.add("Cook the pasta");
        chickenInstructions.add("Cook the ingredients(Chicken, tomato, asparagus)");
        pizzaInstructions.add("Roll the dough out");

        pasta.add(new Ingredient("Pasta", 100));
        pasta.add(new Ingredient("Tomato", 2));
        pasta.add(new Ingredient("Pesto", 4));

        chicken.add(new Ingredient("Chicken Breast", 5));
        chicken.add(new Ingredient("Tomato", 4));
        chicken.add(new Ingredient("Asparagus", 10));

        pizza.add(new Ingredient("Pizza Dough", 1));
        pizza.add(new Ingredient("Tomato", 4));
        pizza.add(new Ingredient("Cheese", 10));


        Recipe pastaRecipe = new Recipe("T&P Pasta", "Tomato Pesto Pasta", pasta, pastaInstructions, 100);
        Recipe chickenRecipe = new Recipe("Chicken Chicken", "Winner winner chicken dinner", chicken, chickenInstructions, 105);
        Recipe pizzaRecipe = new Recipe("Cheese Pizza", "Classic cheese pizza", pizza, pizzaInstructions, 50);


        cookBook.addRecipe(pastaRecipe.getName(), pastaRecipe);
        cookBook.addRecipe(chickenRecipe.getName(), chickenRecipe);
        cookBook.addRecipe(pizzaRecipe.getName(), pizzaRecipe);

        cookBook.saveToFile();


        try {
            CookBook fromFile = new CookbookSerializer().deserialize(FileUtil.readFromJson("cookbook.json"));
            System.out.println(fromFile.getRecipes().size());
            for (Recipe recipe : fromFile.getRecipes().values()) {
                System.out.println(recipe.toString());
            }

            System.out.println("Commit test");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
