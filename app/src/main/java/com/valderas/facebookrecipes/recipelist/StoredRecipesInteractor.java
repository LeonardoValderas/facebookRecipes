package com.valderas.facebookrecipes.recipelist;

import com.valderas.facebookrecipes.entities.Recipe;

/**
 * Created by LEO on 24/6/2016.
 */
public interface StoredRecipesInteractor {
    void executeUpdate(Recipe recipe);
    void executeDelete(Recipe recipe);
}
