package edu.ithaca.goosewillis.icook;
import edu.ithaca.goosewillis.icook.cookbook.CookBook;
import edu.ithaca.goosewillis.icook.cookbook.CookbookSerializer;
import edu.ithaca.goosewillis.icook.fridge.Fridge;
import edu.ithaca.goosewillis.icook.fridge.FridgeSerializer;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.util.FileUtil;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void cookbookTest(){
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
        pasta.add(new Ingredient("Pasta", 100, 1));
        pasta.add(new Ingredient("Tomato", 2, 1));
        pasta.add(new Ingredient("Pesto", 4, 1));
        chicken.add(new Ingredient("Chicken Breast", 5, 1));
        chicken.add(new Ingredient("Tomato", 4, 1));
        chicken.add(new Ingredient("Asparagus", 10, 1));
        pizza.add(new Ingredient("Pizza Dough", 1,1));
        pizza.add(new Ingredient("Tomato", 4,1));
        pizza.add(new Ingredient("Cheese", 10,1));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fridgeTest(){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Chicken Breast", 5, 1));
        ingredients.add(new Ingredient("Tomato", 4, 1));
        ingredients.add(new Ingredient("Broccoli", 3, 10));
        ingredients.add(new Ingredient("Carrot", 2, 6));
        Fridge fridge = new Fridge(ingredients);
        fridge.saveFridgeToFile();
        try{
            Fridge fromFile = new FridgeSerializer().deserialize(FileUtil.readFromJson("fridge.json"));
            System.out.println(fromFile.getIngredients().size());
            for(Ingredient i : fromFile.getIngredients()){
                System.out.println(i.toString());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        cookbookTest();
        fridgeTest();
    }
}