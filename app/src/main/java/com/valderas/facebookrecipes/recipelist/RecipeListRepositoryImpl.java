package com.valderas.facebookrecipes.recipelist;

import com.raizlabs.android.dbflow.list.FlowCursorList;
import com.valderas.facebookrecipes.entities.Recipe;
import com.valderas.facebookrecipes.libs.base.EventBus;
import com.valderas.facebookrecipes.recipelist.events.RecipeListEvent;

import java.util.Arrays;
import java.util.List;

/**
 * Created by LEO on 24/6/2016.
 */
public class RecipeListRepositoryImpl implements RecipeListRepository{

    private EventBus eventBus;

    public RecipeListRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getSavedRecipes() {
        FlowCursorList<Recipe> storedRecipes = new FlowCursorList<Recipe>(true, Recipe.class);
        post(RecipeListEvent.READ_EVENT, storedRecipes.getAll());
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        recipe.update();
        post(RecipeListEvent.UPDATE_EVENT);
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        recipe.delete();
        post(RecipeListEvent.DELETE_EVENT, Arrays.asList(recipe));
    }

    private void post(int type, List<Recipe> recipes) {
        RecipeListEvent event = new RecipeListEvent();
        event.setRecipes(recipes);
        event.setType(type);
        eventBus.post(event);
    }

    private void post(int type) {
        post(type, null);
    }

}


