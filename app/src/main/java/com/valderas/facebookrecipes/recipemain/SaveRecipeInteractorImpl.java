package com.valderas.facebookrecipes.recipemain;

import com.valderas.facebookrecipes.entities.Recipe;

/**
 * Created by LEO on 22/6/2016.
 */
public class SaveRecipeInteractorImpl implements SaveRecipeInteractor {
    RecipeMainRepository recipeMainRepository;

    public SaveRecipeInteractorImpl(RecipeMainRepository recipeMainRepository) {
        this.recipeMainRepository = recipeMainRepository;
    }

    @Override
    public void execute(Recipe recipe) {
        recipeMainRepository.saveRecipe(recipe);
    }
}
