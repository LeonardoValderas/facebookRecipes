package com.valderas.facebookrecipes.recipelist;

import com.valderas.facebookrecipes.entities.Recipe;

/**
 * Created by LEO on 24/6/2016.
 */
public class StoredRecipesInteractorImpl implements StoredRecipesInteractor {
    private RecipeListRepository recipeListRepository;

    public StoredRecipesInteractorImpl(RecipeListRepository recipeListRepository) {
        this.recipeListRepository = recipeListRepository;
    }

    @Override
    public void executeUpdate(Recipe recipe) {
        recipeListRepository.updateRecipe(recipe);
    }

    @Override
    public void executeDelete(Recipe recipe) {
        recipeListRepository.removeRecipe(recipe);
    }
}
