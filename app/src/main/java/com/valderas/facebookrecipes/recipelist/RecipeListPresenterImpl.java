package com.valderas.facebookrecipes.recipelist;

import com.valderas.facebookrecipes.entities.Recipe;
import com.valderas.facebookrecipes.libs.base.EventBus;
import com.valderas.facebookrecipes.recipelist.events.RecipeListEvent;
import com.valderas.facebookrecipes.recipelist.ui.RecipeListView;


/**
 * Created by LEO on 24/6/2016.
 */
public class RecipeListPresenterImpl implements RecipeListPresenter {

    private EventBus eventBus;
    private RecipeListView view;
    private RecipeListInteractor recipeListInteractor;
    private StoredRecipesInteractor storedRecipesInteractor;

    public RecipeListPresenterImpl(EventBus eventBus, RecipeListView view, RecipeListInteractor recipeListInteractor, StoredRecipesInteractor storedRecipesInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.recipeListInteractor = recipeListInteractor;
        this.storedRecipesInteractor = storedRecipesInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;
    }

    @Override
    public void getRecipes() {
        recipeListInteractor.execute();
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        storedRecipesInteractor.executeDelete(recipe);
    }

    @Override
    public void toggleFavorite(Recipe recipe) {
        boolean fav = recipe.getFavorite();
        recipe.setFavorite(!fav);
        storedRecipesInteractor.executeUpdate(recipe);
    }

    @Override
    public void onEventMainThread(RecipeListEvent event) {
        if (this.view != null) {
            switch (event.getType()){
                case RecipeListEvent.READ_EVENT:
                    view.setRecipes(event.getRecipes());
                    break;
                case RecipeListEvent.UPDATE_EVENT:
                    view.recipeUpdated();
                    break;
                case RecipeListEvent.DELETE_EVENT:
                    Recipe recipe = event.getRecipes().get(0);
                    view.recipeDeleted(recipe);
                    break;

            }
        }
    }

    @Override
    public RecipeListView getView() {
        return this.view;
    }
}
