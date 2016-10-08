package com.valderas.facebookrecipes.recipelist;

import com.valderas.facebookrecipes.entities.Recipe;
import com.valderas.facebookrecipes.recipelist.events.RecipeListEvent;
import com.valderas.facebookrecipes.recipelist.ui.RecipeListView;

/**
 * Created by LEO on 24/6/2016.
 */
public interface RecipeListPresenter {
    void onCreate();
    void onDestroy();

    void getRecipes();
    void removeRecipe(Recipe recipe);
    void toggleFavorite(Recipe recipe);
    void onEventMainThread(RecipeListEvent event);

    RecipeListView getView();
}
