package com.valderas.facebookrecipes.recipelist;

import com.valderas.facebookrecipes.entities.Recipe;

/**
 * Created by LEO on 24/6/2016.
 */
public interface RecipeListRepository {
    void getSavedRecipes();
    void updateRecipe(Recipe recipe);
    void removeRecipe(Recipe recipe);
}
