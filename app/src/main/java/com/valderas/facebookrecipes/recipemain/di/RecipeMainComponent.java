package com.valderas.facebookrecipes.recipemain.di;

import com.valderas.facebookrecipes.libs.base.ImageLoader;
import com.valderas.facebookrecipes.libs.di.LibsModule;
import com.valderas.facebookrecipes.recipemain.RecipeMainPresenter;
import com.valderas.facebookrecipes.recipemain.ui.RecipeMainActivity;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * Created by LEO on 22/6/2016.
 */
@Singleton
@Component(modules = {RecipeMainModule.class, LibsModule.class})
public interface RecipeMainComponent {
   // void inject(RecipeMainActivity activity);
    ImageLoader getImageLoader();
    RecipeMainPresenter getPresenter();
}
