package com.valderas.facebookrecipes.recipemain.di;

import com.valderas.facebookrecipes.api.RecipeClient;
import com.valderas.facebookrecipes.api.RecipeService;
import com.valderas.facebookrecipes.libs.base.EventBus;
import com.valderas.facebookrecipes.recipemain.GetNextRecipeInteractor;
import com.valderas.facebookrecipes.recipemain.GetNextRecipeInteractorImpl;
import com.valderas.facebookrecipes.recipemain.RecipeMainPresenter;
import com.valderas.facebookrecipes.recipemain.RecipeMainPresenterImpl;
import com.valderas.facebookrecipes.recipemain.RecipeMainRepository;
import com.valderas.facebookrecipes.recipemain.RecipeMainRepositoryImpl;
import com.valderas.facebookrecipes.recipemain.SaveRecipeInteractor;
import com.valderas.facebookrecipes.recipemain.SaveRecipeInteractorImpl;
import com.valderas.facebookrecipes.recipemain.ui.RecipeMainView;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LEO on 22/6/2016.
 */
@Module
public class RecipeMainModule {
    RecipeMainView view;

    public RecipeMainModule(RecipeMainView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    RecipeMainView provideRecipeMainView() {
        return this.view;
    }

    @Provides
    @Singleton
    RecipeMainPresenter provideRecipeMainPresenter(EventBus eventBus, RecipeMainView view, SaveRecipeInteractor saveRecipeInteractor, GetNextRecipeInteractor getNextRecipeInteractor) {
        return new RecipeMainPresenterImpl(eventBus, view, saveRecipeInteractor, getNextRecipeInteractor);
    }


    @Provides
    @Singleton
    SaveRecipeInteractor provideSaveRecipeInteractor(RecipeMainRepository recipeMainRepository) {
        return new SaveRecipeInteractorImpl(recipeMainRepository);
    }

    @Provides
    @Singleton
    GetNextRecipeInteractor provideGetNextRecipeInteractor (RecipeMainRepository recipeMainRepository) {
        return new GetNextRecipeInteractorImpl(recipeMainRepository);
    }

    @Provides
    @Singleton
    RecipeMainRepository provideRecipeMainRepository (EventBus eventBus, RecipeService service) {
        return new RecipeMainRepositoryImpl(eventBus,service);
    }

    @Provides
    @Singleton
    RecipeService provideRecipeService () {
        RecipeClient client = new RecipeClient();
        return client.getRecipeService();
    }
}