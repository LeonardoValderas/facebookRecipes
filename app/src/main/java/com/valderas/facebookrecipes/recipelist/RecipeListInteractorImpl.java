package com.valderas.facebookrecipes.recipelist;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by LEO on 24/6/2016.
 */
public class RecipeListInteractorImpl implements RecipeListInteractor{
    private RecipeListRepository recipeListRepository;

    public RecipeListInteractorImpl(RecipeListRepository recipeListRepository) {
        this.recipeListRepository = recipeListRepository;
    }

    @Override
    public void execute() {
        recipeListRepository.getSavedRecipes();
    }
}
