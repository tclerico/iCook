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

    public User(String username, String password, Fridge fridge, ArrayList<Ingredient> dislikedIngredients,
                ArrayList<Recipe> favoriteRecipes, ArrayList<DietType> restrictions){
        if(isUsernameValid(username) && isPasswordValid(password)){
            this.username = username;
            this.password = password;
            this.fridge = fridge;
            this.dislikedIngredients = dislikedIngredients;
            this.favoriteRecipes = favoriteRecipes;
            this.restrictions = restrictions;
        }else{
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    public User(){}

    public Fridge getFridge(){ return this.fridge; }


    public void addToFridge(Ingredient ingredient){
        if(ingredient.isIngredientValid(ingredient)){
            fridge.addIngredient(ingredient);
        }else{
            throw new IllegalArgumentException("You can only add valid ingredients");
        }
    }

    public void removeFromFridge(Ingredient ingredient){
        if(ingredient.isIngredientValid(ingredient) && (fridge.searchIngredient(ingredient.getName()) == ingredient)){
            fridge.removeIngredient(ingredient);
        }else{
            throw new IllegalArgumentException("You sure this ingredient's in your fridge?");
        }
    }

    public void useIngredient(String ingredientName, int ingredientCount){
        if((fridge.searchIngredient(ingredientName).getName().equals(ingredientName)
                && fridge.searchIngredient(ingredientName).getCount() >= ingredientCount)){
                fridge.searchIngredient(ingredientName).setCount(fridge.searchIngredient(ingredientName).getCount() - ingredientCount);
        }else{
            throw new IllegalArgumentException("Invalid Ingredient name or count");
        }
    }

    public void favoriteRecipe(Recipe recipe){
        favoriteRecipes.add(recipe);
    }

    public void unfavoriteRecipe(Recipe recipe){
        favoriteRecipes.remove(recipe);
    }

    public List<Ingredient> displayFridge(){
        List<Ingredient> ingredients = fridge.getIngredients();
        return ingredients;

    }

    public void addDislikedIngredient(Ingredient ingredient){
        if(ingredient.isIngredientValid(ingredient)){
            dislikedIngredients.add(ingredient);
        }else{
            throw new IllegalArgumentException("You can only add valid ingredients");
        }
    }

    public void removeDislikedIngredient(Ingredient ingredient){
        dislikedIngredients.remove(ingredient);
    }

    public void addRestriction(String restriction){
        if(restriction.equalsIgnoreCase("Vegan")){
            restrictions.add(DietType.Vegan);
        }else if(restriction.equalsIgnoreCase("Vegetarian")){
            restrictions.add(DietType.Vegetarian);
        }else if(restriction.equalsIgnoreCase("Gluten Free")){
            restrictions.add(DietType.GlutenFree);
        }else if(restriction.equalsIgnoreCase("Non Dairy")){
            restrictions.add(DietType.NonDairy);
        }else{
            throw new IllegalArgumentException("Enter a valid diet type");
        }
    }

    public void removeRestriction(String restriction){
        if(restriction.equalsIgnoreCase("Vegan")){
            restrictions.remove(DietType.Vegan);
        }else if(restriction.equalsIgnoreCase("Vegetarian")){
            restrictions.remove(DietType.Vegetarian);
        }else if(restriction.equalsIgnoreCase("Gluten Free")){
            restrictions.remove(DietType.GlutenFree);
        }else if(restriction.equalsIgnoreCase("Non Dairy")){
            restrictions.remove(DietType.NonDairy);
        }else{
            throw new IllegalArgumentException("Enter a valid diet type");
        }
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

