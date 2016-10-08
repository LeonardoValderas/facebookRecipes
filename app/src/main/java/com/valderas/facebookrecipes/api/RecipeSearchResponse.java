package com.valderas.facebookrecipes.api;

import com.google.gson.annotations.SerializedName;
import com.valderas.facebookrecipes.entities.Recipe;

import java.util.List;

/**
 * Created by LEO on 21/6/2016.
 */
public class RecipeSearchResponse {
    private int count;
    @SerializedName("recipes")
    private List<Recipe> recipes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Recipe getFirstRecipe(){
        Recipe first = null;
        if (!recipes.isEmpty()) {
            first = recipes.get(0);
        }
        return first;
    }
}
