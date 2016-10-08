package com.valderas.facebookrecipes.recipemain;

import com.valderas.facebookrecipes.entities.Recipe;
import com.valderas.facebookrecipes.recipemain.events.RecipeMainEvent;
import com.valderas.facebookrecipes.recipemain.ui.RecipeMainView;

/**
 * Created by LEO on 22/6/2016.
 */
public interface RecipeMainPresenter {
    void onCreate();

    void onDestroy();

    void dismissRecipe();

    void getNextRecipe();

    void saveRecipe(Recipe recipe);

    void onEventMainThread(RecipeMainEvent event);

    void imageError(String error);

    void imageReady();

    RecipeMainView getView();
}
