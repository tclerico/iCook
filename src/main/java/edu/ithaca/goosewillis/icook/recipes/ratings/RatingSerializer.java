package edu.ithaca.goosewillis.icook.recipes.ratings;

import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.cookbook.CookBook;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.user.User;
import edu.ithaca.goosewillis.icook.user.UserManager;
import edu.ithaca.goosewillis.icook.util.JsonDeserializer;
import edu.ithaca.goosewillis.icook.util.JsonSerializer;

public class RatingSerializer implements JsonSerializer<Rating>, JsonDeserializer<Rating> {

    @Override
    public Rating deserialize(JsonObject root) {
        int rating = root.get("rating").getAsInt();
        String review = root.get("review").getAsString();
        Recipe recipe = CookBook.getRecipe(root.get("recipe").getAsString());
        User user = UserManager.getInstance().getUser(root.get("user").getAsString());
        return new Rating(recipe, user, rating, review);
    }

    @Override
    public JsonObject serialize(Rating toSerialize) {
        JsonObject root = new JsonObject();
        root.addProperty("rating", toSerialize.getRating());
        root.addProperty("review", toSerialize.getReview());
        root.addProperty("recipe", toSerialize.getRecipe().getName());
        root.addProperty("user", toSerialize.getReviewer().getUsername());
        return root;
    }

}
