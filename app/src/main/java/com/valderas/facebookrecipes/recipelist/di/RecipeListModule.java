package com.valderas.facebookrecipes.recipelist.di;

import com.valderas.facebookrecipes.entities.Recipe;
import com.valderas.facebookrecipes.libs.base.EventBus;
import com.valderas.facebookrecipes.libs.base.ImageLoader;
import com.valderas.facebookrecipes.recipelist.RecipeListInteractor;
import com.valderas.facebookrecipes.recipelist.RecipeListInteractorImpl;
import com.valderas.facebookrecipes.recipelist.RecipeListPresenter;
import com.valderas.facebookrecipes.recipelist.RecipeListPresenterImpl;
import com.valderas.facebookrecipes.recipelist.RecipeListRepository;
import com.valderas.facebookrecipes.recipelist.RecipeListRepositoryImpl;
import com.valderas.facebookrecipes.recipelist.StoredRecipesInteractor;
import com.valderas.facebookrecipes.recipelist.StoredRecipesInteractorImpl;
import com.valderas.facebookrecipes.recipelist.adapters.OnItemClickListener;
import com.valderas.facebookrecipes.recipelist.adapters.RecipesAdapter;
import com.valderas.facebookrecipes.recipelist.ui.RecipeListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RecipeListModule {
    RecipeListView view;
    OnItemClickListener onItemClickListener;

    public RecipeListModule(RecipeListView view, OnItemClickListener onItemClickListener) {
        this.view = view;
        this.onItemClickListener = onItemClickListener;
    }

    @Provides
    @Singleton
    RecipeListView provideRecipeListView() {
        return this.view;
    }

    @Provides @Singleton
    RecipeListPresenter provideRecipeListPresenter(EventBus eventBus, RecipeListView view, RecipeListInteractor listInteractor, StoredRecipesInteractor storedInteractor) {
        return new RecipeListPresenterImpl(eventBus, view, listInteractor, storedInteractor);
    }

    @Provides @Singleton
    RecipeListInteractor provideRecipeListInteractor(RecipeListRepository repository) {
        return new RecipeListInteractorImpl(repository);
    }

    @Provides @Singleton
    StoredRecipesInteractor provideStoredRecipesInteractor(RecipeListRepository repository) {
        return new StoredRecipesInteractorImpl(repository);
    }

    @Provides @Singleton
    RecipeListRepository provideRecipeListRepository(EventBus eventBus) {
        return new RecipeListRepositoryImpl(eventBus);
    }

    @Provides @Singleton
    RecipesAdapter provideRecipesAdapter(List<Recipe> recipes, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        return new RecipesAdapter(recipes, imageLoader, onItemClickListener);
    }

    @Provides @Singleton
    OnItemClickListener provideOnItemClickListener() {
        return this.onItemClickListener;
    }

    @Provides @Singleton
    List<Recipe> provideRecipesList() {
        return new ArrayList<Recipe>();
    }

}
