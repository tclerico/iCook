package edu.ithaca.goosewillis.icook.recipes.ratings;

import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.user.User;

public class Rating {

    private final int MAX_RATING = 5;

    private Recipe recipe;
    private User reviewer;
    private int rating;
    private String review;

    public Rating(Recipe recipe, User reviewer, int rating, String review) {
        if (this.rating  > MAX_RATING) {
            throw new IllegalArgumentException("Rating cannot exceed the MAX_RATING:  " + MAX_RATING);
        }
        this.recipe = recipe;
        this.reviewer = reviewer;
        this.rating = rating;
        this.review = review;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
