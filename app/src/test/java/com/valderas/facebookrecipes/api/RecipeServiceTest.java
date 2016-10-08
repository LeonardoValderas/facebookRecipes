package com.valderas.facebookrecipes.api;

import com.valderas.facebookrecipes.BaseTest;
import com.valderas.facebookrecipes.BuildConfig;
import com.valderas.facebookrecipes.entities.Recipe;
import com.valderas.facebookrecipes.recipemain.RecipeMainRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import java.io.IOException;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by LEO on 9/7/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class RecipeServiceTest extends BaseTest {

    private RecipeService service;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        RecipeClient client = new RecipeClient();
        service = client.getRecipeService();
    }

    @Test
    public void doSearch_getRecipeFromBackend() throws IOException {
        int recipePage = 1;
        Call<RecipeSearchResponse> call = service.search(BuildConfig.FOOD_API_KEY, RecipeMainRepository.RECENT_SORT, RecipeMainRepository.COUNT, recipePage);

        Response<RecipeSearchResponse> response = call.execute();
        assertTrue(response.isSuccess());

        RecipeSearchResponse recipeSearchResponse = response.body();
        assertEquals(1, recipeSearchResponse.getCount());
        Recipe recipe = recipeSearchResponse.getFirstRecipe();
        assertNotNull(recipe);
    }

    @Test
    public void doSearch_getRecipeNoFromBackend() throws IOException {
        int recipePage = 1000000;
        Call<RecipeSearchResponse> call = service.search(BuildConfig.FOOD_API_KEY, RecipeMainRepository.RECENT_SORT, RecipeMainRepository.COUNT, recipePage);

        Response<RecipeSearchResponse> response = call.execute();
        assertTrue(response.isSuccess());

        RecipeSearchResponse recipeSearchResponse = response.body();
        assertEquals(0, recipeSearchResponse.getCount());
        Recipe recipe = recipeSearchResponse.getFirstRecipe();
        assertNull(recipe);
    }
    @Test
    public void doSearch_getRandomRecipeFromBackend() throws IOException{
        int recipePage = new Random().nextInt(RecipeMainRepository.RECIPE_RANGE);
        Call<RecipeSearchResponse> call = service.search(BuildConfig.FOOD_API_KEY, RecipeMainRepository.RECENT_SORT, RecipeMainRepository.COUNT, recipePage);

        Response<RecipeSearchResponse> response = call.execute();
        assertTrue(response.isSuccess());

        RecipeSearchResponse recipeSearchResponse = response.body();
        if (recipeSearchResponse.getCount() == 1) {
            Recipe recipe = recipeSearchResponse.getFirstRecipe();
            assertNotNull(recipe);
        } else {
            System.out.println("invalid recipe, trying again");
//            doSearch_getRecipeFromBackend();
        }
    }
}
