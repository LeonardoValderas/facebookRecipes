package com.valderas.facebookrecipes.recipelist.adapters;

import com.valderas.facebookrecipes.entities.Recipe;

/**
 * Created by LEO on 24/6/2016.
 */
public interface OnItemClickListener {
    void onFavClick(Recipe recipe);
    void onItemClick(Recipe recipe);
    void onDeleteClick(Recipe recipe);
}
