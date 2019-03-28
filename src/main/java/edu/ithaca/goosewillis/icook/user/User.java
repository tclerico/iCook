package edu.ithaca.goosewillis.icook.user;

import edu.ithaca.goosewillis.icook.fridge.Fridge;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String password;
    private Fridge fridge;
    private ArrayList<Ingredient> dislikedIngredients;
    private ArrayList<Recipe> favoriteRecipes;
    private ArrayList<DietType> restrictions;

    public User(String username, String password, ArrayList<Ingredient> dislikedIngredients){
        if(isUsernameValid(username) && isPasswordValid(password)){
            this.username = username;
            this.password = password;
            this.fridge = new Fridge();
            this.dislikedIngredients = dislikedIngredients;
        }else{
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    public void addToFridge(Ingredient ingredient){
        if(isIngredientValid(ingredient)){
            fridge.addIngredient(ingredient);
        }else{
            throw new IllegalArgumentException("You can only add valid ingredients");
        }
    }

    public void removeFromFridge(Ingredient ingredient){}

    public void useIngredient(String ingredientName, int ingredientCount){}

    public String generateOneTrayMeal(){}

    public void uploadRecipe(Recipe recipe){}

    public void favoriteRecipe(Recipe recipe){}

    public String allPossibleRecipes(){}

    public String getRecipesByTime(int time){}

    public void reviewRecipe(Recipe recipe){}

    public String displayFridge(){
        List<Ingredient> ingredients = fridge.getIngredients();
        String fridgeString = "Ingredients: \n";

        for(int i = 0; i < ingredients.size(); i++){
            fridgeString += ("Name: " + ingredients.get(i).getName() +
                             ", Count: " + ingredients.get(i).getCount() +
                             ", Cook Time: " + ingredients.get(i).getCookTime() +
                             ", Diet Type: " + ingredients.get(i).getDietType() + "\n");
        }
        return fridgeString;
    }

    public void addDislikedIngredient(Ingredient ingredient){
        if(isIngredientValid(ingredient)){
            dislikedIngredients.add(ingredient);
        }else{
            throw new IllegalArgumentException("You can only add valid ingredients");
        }
    }

    public void removeDislikedIngredient(Ingredient ingredient){
        dislikedIngredients.remove(ingredient);
    }

    private static boolean isUsernameValid(String username){
        if(username != null){
            return true;
        }else{
            return false;
        }
    }

    private static boolean isPasswordValid(String password){
        if(password != null){
            return true;
        }else{
            return false;
        }
    }

    private static boolean isIngredientValid(Ingredient ingredient){
        if(ingredient != null && ingredient.getName().matches("[a-zA-z]")
                && ingredient.getCount() >=1 && ingredient.getCookTime() >= 0){
            return true;
        }else{
            return false;
        }
    }

    public void setUsername(String username){
        if(isUsernameValid(username)){
            this.username = username;
        }else{
            throw new IllegalArgumentException("Invalid username");
        }
    }

    public String getUsername(){ return this.username; }

    public void setPassword(String password){
        if(isPasswordValid(password)){
            this.password = password;
        }else{
            throw new IllegalArgumentException("Invalid password");
        }
    }

    public String getPassword(){ return this.password; }
}


//Log so far: 3 hours