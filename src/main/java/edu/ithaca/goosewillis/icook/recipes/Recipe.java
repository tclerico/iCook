package edu.ithaca.goosewillis.icook.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.recipes.ratings.Rating;
import edu.ithaca.goosewillis.icook.recipes.ratings.RatingSerializer;
import edu.ithaca.goosewillis.icook.user.User;
import edu.ithaca.goosewillis.icook.user.UserSerializer;
import edu.ithaca.goosewillis.icook.util.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Recipe {

    private List<Ingredient> ingredients;
    private List<String> instructions;
    private List<Rating> ratings;
    private Set<DietType> tags;
    private String description;
    private String name;
    private double cookTime;

    public Recipe() {
    }

    public Recipe(String name, String description, List<Ingredient> needed, List<String> steps, double cooktime) {
        this(name, description, null, needed, steps, cooktime);
    }

    public Recipe(String name, String description, Set<DietType> tags, List<Ingredient> needed, List<String> steps, double cooktime) {
        if (needed.size() >= 1 && steps.size() >= 1) {
            this.name = name;
            this.description = description;
            this.tags = tags;
            this.ingredients = needed;
            this.instructions = steps;
            this.instructions.add("Enjoy!");
            this.cookTime = cooktime;
            this.ratings = new ArrayList<>();
        } else {
            throw new IllegalArgumentException("Not enough information to constitute a recipe");
        }
    }

    public void loadRatings() throws Exception {
        File ratingsDir = new File("/ratings");
        if (!ratingsDir.exists()) {
            ratingsDir.mkdir();
        }
        File ratingFile = new File(ratingsDir, name + "_ratings.json");
        JsonObject root = FileUtil.readFromJson(ratingFile);
        JsonArray ratings = root.getAsJsonArray("ratings");
        for (JsonElement element : ratings) {
            this.ratings.add(new RatingSerializer().deserialize(element.getAsJsonObject()));
        }
    }

    public void addRating(User user, int rating, String review) throws Exception {
        File ratingsDir = new File("/ratings");
        if (!ratingsDir.exists()) {
            ratingsDir.mkdir();
        }
        File ratingFile = new File(ratingsDir, name + "_ratings.json");
        JsonObject root = FileUtil.readFromJson(ratingFile);
        JsonArray ratings = root.getAsJsonArray("ratings");
        Rating r = new Rating(this, user, rating, review);
        ratings.add(new RatingSerializer().serialize(r));
        FileWriter writer = new FileWriter(ratingFile);
        writer.write(FileUtil.gson.toJson(root));
        writer.flush();
        writer.close();
        this.ratings.add(r);
    }


    @Override
    public String toString() {
        return "Name: " + name + " Description: " + description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public String getDescription() {
        return description;
    }

    public double getCookTime() {
        return cookTime;
    }

    public Set<DietType> getTags() {
        return tags;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public String display(){
        String toReturn = name+"\n"+description+"\n"+"Ingredients Needed:\n";
        for (int i=0; i<ingredients.size(); i++){
            toReturn = toReturn + Integer.toString(i+1)+": " + ingredients.get(i).getName()+"\n";
        }
        toReturn = toReturn+"Steps:\n";
        for (int j=0; j<instructions.size(); j++){
            toReturn = toReturn + instructions.get(j)+"\n";
        }
        toReturn = toReturn+"Tags:\n";
        for (DietType type : tags) {
            toReturn += type + "\n";
        }
        return toReturn;
    }


}
