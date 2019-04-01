package edu.ithaca.goosewillis.icook.user;

import edu.ithaca.goosewillis.icook.cookbook.CookBook;
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

    public void addToFridge(Ingredient ingredient){
        if(isIngredientValid(ingredient)){
            fridge.addIngredient(ingredient);
        }else{
            throw new IllegalArgumentException("You can only add valid ingredients");
        }
    }

    public void removeFromFridge(Ingredient ingredient){
        if(isIngredientValid(ingredient) && (fridge.searchIngredient(ingredient.getName()) == ingredient)){
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

    public String generateOneTray(){
        return null;
    }

    public void uploadRecipe(Recipe recipe){}

    public void favoriteRecipe(Recipe recipe){}

    public String getRecipeRecommendations(){return null;}

    public String getRecipesByTime(int cookTime){return null;}

    public void reviewRecipe(Recipe recipe){}

    public String displayFridge(){
        List<Ingredient> ingredients = fridge.getIngredients();
        String fridgeString = "Your fridge: \n";

        for(int i = 0; i < ingredients.size(); i++){
            fridgeString += ("Ingredient: " + ingredients.get(i).getName() +
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

    public void addRestriction(){}

    public void removeRestriction(){}

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
        if(ingredient != null && ingredient.getCount() >=1 && ingredient.getCookTime() >= 0){
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

