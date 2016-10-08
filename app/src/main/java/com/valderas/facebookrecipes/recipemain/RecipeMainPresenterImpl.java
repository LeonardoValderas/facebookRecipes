package com.valderas.facebookrecipes.recipemain;

import com.valderas.facebookrecipes.entities.Recipe;
import com.valderas.facebookrecipes.libs.base.EventBus;
import com.valderas.facebookrecipes.recipemain.events.RecipeMainEvent;
import com.valderas.facebookrecipes.recipemain.ui.RecipeMainView;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by LEO on 22/6/2016.
 */
public class RecipeMainPresenterImpl implements RecipeMainPresenter {

    EventBus eventBus;
    RecipeMainView view;
    SaveRecipeInteractor saveRecipeInteractor;
    GetNextRecipeInteractor getNextRecipeInteractor;

    public RecipeMainPresenterImpl(EventBus eventBus, RecipeMainView view, SaveRecipeInteractor saveRecipeInteractor, GetNextRecipeInteractor getNextRecipeInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.saveRecipeInteractor = saveRecipeInteractor;
        this.getNextRecipeInteractor = getNextRecipeInteractor;
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
    public void dismissRecipe() {
        if (this.view != null) {
            view.dismissAnimation();
        }
        getNextRecipe();
    }

    @Override
    public void getNextRecipe() {
        if (view != null) {
            view.showProgress();
            view.hideUIElements();
        }
        getNextRecipeInteractor.execute();
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        if (this.view != null) {
            view.saveAnimation();
            view.hideUIElements();
            view.showProgress();
        }
        saveRecipeInteractor.execute(recipe);
    }

    @Override
    @Subscribe
    public void onEventMainThread(RecipeMainEvent event) {
        if (this.view != null) {
            String error = event.getError();
            if (error == null) {
                if (event.getType() == RecipeMainEvent.NEXT_EVENT) {
                    view.setRecipe(event.getRecipe());
                } else if (event.getType() == RecipeMainEvent.SAVE_EVENT) {
                    view.onRecipeSaved();
                    getNextRecipeInteractor.execute();
                }
            } else {
                view.hideProgress();
                view.onGetRecipeError(error);
            }
        }

    }

    @Override
    public void imageError(String error) {
        if (this.view != null) {
            view.onGetRecipeError(error);
        }
    }

    @Override
    public void imageReady() {
        if (this.view != null) {
            view.hideProgress();
            view.showUIElements();
        }
    }

    @Override
    public RecipeMainView getView() {
        return this.view;
    }
}