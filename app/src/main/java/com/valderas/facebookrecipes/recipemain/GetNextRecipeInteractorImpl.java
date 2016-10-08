package com.valderas.facebookrecipes.recipemain;

import java.util.Random;

/**
 * Created by LEO on 22/6/2016.
 */
public class GetNextRecipeInteractorImpl implements GetNextRecipeInteractor{
RecipeMainRepository recipeMainRepository;

    public GetNextRecipeInteractorImpl(RecipeMainRepository recipeMainRepository) {
        this.recipeMainRepository = recipeMainRepository;
    }

    @Override
    public void execute() {

        int recipePage =  new Random().nextInt(recipeMainRepository.RECIPE_RANGE);
        recipeMainRepository.setRecipePage(recipePage);
        recipeMainRepository.getNextRecipe();
    }
}
