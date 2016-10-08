package com.valderas.facebookrecipes.recipemain.ui;

import com.valderas.facebookrecipes.entities.Recipe;

/**
 * Created by LEO on 22/6/2016.
 */
public interface RecipeMainView {
    void showProgress();
    void hideProgress();
    void showUIElements();
    void hideUIElements();
    void saveAnimation();
    void dismissAnimation();

    void onRecipeSaved();
    void setRecipe(Recipe recipe);
    void onGetRecipeError(String error);
}
