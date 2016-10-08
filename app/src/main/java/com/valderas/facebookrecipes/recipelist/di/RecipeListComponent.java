package com.valderas.facebookrecipes.recipelist.di;

import com.valderas.facebookrecipes.libs.di.LibsModule;
import com.valderas.facebookrecipes.recipelist.RecipeListPresenter;
import com.valderas.facebookrecipes.recipelist.adapters.RecipesAdapter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RecipeListModule.class, LibsModule.class})
public interface RecipeListComponent {
    //void inject(RecipeListActivity activity);
    RecipeListPresenter getPresenter();
    RecipesAdapter getAdapter();
}
