package com.valderas.facebookrecipes.recipemain;

import com.valderas.facebookrecipes.BuildConfig;
import com.valderas.facebookrecipes.api.RecipeSearchResponse;
import com.valderas.facebookrecipes.api.RecipeService;
import com.valderas.facebookrecipes.entities.Recipe;
import com.valderas.facebookrecipes.libs.base.EventBus;
import com.valderas.facebookrecipes.recipemain.events.RecipeMainEvent;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LEO on 22/6/2016.
 */
public class RecipeMainRepositoryImpl implements RecipeMainRepository {

    private int recipePage;
    private EventBus eventBus;
    private RecipeService service;

    public RecipeMainRepositoryImpl(EventBus eventBus, RecipeService service) {
        this.recipePage = new Random().nextInt(RECIPE_RANGE);
        this.eventBus = eventBus;
        this.service = service;
    }

    @Override
    public void getNextRecipe() {
        Call<RecipeSearchResponse> call = service.search(BuildConfig.FOOD_API_KEY, RECENT_SORT, COUNT, recipePage);
        call.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                if (response.isSuccess()) {
                    RecipeSearchResponse recipeSearchResponse = response.body();
                    if (recipeSearchResponse.getCount() == 0){
                        setRecipePage(new Random().nextInt(RECIPE_RANGE));
                        getNextRecipe();
                    } else {
                        Recipe recipe = recipeSearchResponse.getFirstRecipe();
                        if (recipe != null) {
                            post(recipe);
                        } else {
                            post(response.message());
                        }

                    }
                } else {
                    post(response.message());
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                post(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        recipe.save();
        post();
    }

    @Override
    public void setRecipePage(int recipePage) {
        this.recipePage = recipePage;
    }

    private void post(String error, int type, Recipe recipe) {
        RecipeMainEvent event = new RecipeMainEvent();
        event.setType(type);
        event.setError(error);
        event.setRecipe(recipe);
        eventBus.post(event);
    }

    private void post(Recipe recipe) {

        post(null, RecipeMainEvent.NEXT_EVENT, recipe);

//        RecipeMainEvent event = new RecipeMainEvent();
//        event.setType(RecipeMainEvent.NEXT_EVENT);
//        event.setRecipe(recipe);
//        eventBus.post(event);
    }

    private void post(String error) {

        post(error, RecipeMainEvent.NEXT_EVENT, null);
//        RecipeMainEvent event = new RecipeMainEvent();
//        event.setType(RecipeMainEvent.NEXT_EVENT);
//        event.setRecipe(recipe);
//        eventBus.post(event);
    }

    private void post() {

        post(null, RecipeMainEvent.SAVE_EVENT, null);
//        RecipeMainEvent event = new RecipeMainEvent();
//        event.setType(RecipeMainEvent.SAVE_EVENT);
//        eventBus.post(event);
    }

}
