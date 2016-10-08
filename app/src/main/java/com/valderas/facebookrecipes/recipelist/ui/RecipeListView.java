package com.valderas.facebookrecipes.recipelist.ui;

import com.valderas.facebookrecipes.entities.Recipe;

import java.util.List;

/**
 * Created by LEO on 24/6/2016.
 */
public interface RecipeListView {
    void setRecipes(List<Recipe> data);
    void recipeUpdated();
    void recipeDeleted(Recipe recipe);
}
