package com.valderas.facebookrecipes.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LEO on 21/6/2016.
 */
public class RecipeClient {
    private Retrofit retrofit;
    private final static String BASE_URL = "http://food2fork.com/api/";

    public RecipeClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public RecipeService getRecipeService() {
        return retrofit.create(RecipeService.class);
    }
}
