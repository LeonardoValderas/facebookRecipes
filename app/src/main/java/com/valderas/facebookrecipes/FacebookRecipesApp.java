package com.valderas.facebookrecipes;

import android.app.Application;
import android.content.Intent;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.valderas.facebookrecipes.libs.di.LibsModule;
import com.valderas.facebookrecipes.login.ui.LoginActivity;
import com.valderas.facebookrecipes.recipelist.adapters.OnItemClickListener;
import com.valderas.facebookrecipes.recipelist.di.DaggerRecipeListComponent;
import com.valderas.facebookrecipes.recipelist.di.RecipeListComponent;
import com.valderas.facebookrecipes.recipelist.di.RecipeListModule;
import com.valderas.facebookrecipes.recipelist.ui.RecipeListActivity;
import com.valderas.facebookrecipes.recipelist.ui.RecipeListView;
import com.valderas.facebookrecipes.recipemain.di.DaggerRecipeMainComponent;
import com.valderas.facebookrecipes.recipemain.di.RecipeMainComponent;
import com.valderas.facebookrecipes.recipemain.di.RecipeMainModule;
import com.valderas.facebookrecipes.recipemain.ui.RecipeMainActivity;
import com.valderas.facebookrecipes.recipemain.ui.RecipeMainView;

/**
 * Created by LEO on 21/6/2016.
 */
public class FacebookRecipesApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFacebook();
        initDB();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        BDTearDown();
    }

    private void BDTearDown() {
        FlowManager.destroy();
    }

    private void initDB() {
        FlowManager.init(this);
    }

    private void initFacebook() {
        FacebookSdk.sdkInitialize(this);
    }

    public void logout() {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public RecipeMainComponent getRecipeMainComponent(RecipeMainActivity activity , RecipeMainView view){
        return DaggerRecipeMainComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .recipeMainModule(new RecipeMainModule(view))
                .build();
    }
    public RecipeListComponent getRecipeListComponent(RecipeListActivity activity, RecipeListView view, OnItemClickListener onItemClickListener) {
        return DaggerRecipeListComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .recipeListModule(new RecipeListModule(view, onItemClickListener))
                .build();
    }
}
