package com.valderas.facebookrecipes.recipemain;

import com.valderas.facebookrecipes.BaseTest;
import com.valderas.facebookrecipes.BuildConfig;
import com.valderas.facebookrecipes.api.RecipeSearchResponse;
import com.valderas.facebookrecipes.api.RecipeService;
import com.valderas.facebookrecipes.entities.Recipe;
import com.valderas.facebookrecipes.libs.base.EventBus;
import com.valderas.facebookrecipes.recipemain.events.RecipeMainEvent;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.Request;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by LEO on 9/7/2016.
 */
public class RecipeMainRepositoryTest extends BaseTest {

    @Mock
    private EventBus eventBus;
    @Mock
    private Recipe recipe;
    @Mock
    private RecipeService service;

    private RecipeMainRepository repository;
    private ArgumentCaptor<RecipeMainEvent> recipeMainEventArgumentCaptor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        repository = new RecipeMainRepositoryImpl(eventBus, service);
        recipeMainEventArgumentCaptor = ArgumentCaptor.forClass(RecipeMainEvent.class);
    }

    @Test
    public void saveRecipeCalled_eventPosted() {
//        int randomInt = (new Random()).nextInt(100000);
//        recipe.setRecipeId("__id__" + randomInt);
        when(recipe.exists()).thenReturn(true);

        repository.saveRecipe(recipe);

        verify(eventBus).post(recipeMainEventArgumentCaptor.capture());
        RecipeMainEvent event = recipeMainEventArgumentCaptor.getValue();
        assertEquals(RecipeMainEvent.SAVE_EVENT, event.getType());
        assertNull(event.getError());
        assertNull(event.getRecipe());
        // recipe.delete();
    }

    @Test
    public void getNextRecipeCalled_apiServiceSuccessCallEventPosted() {
        //final Recipe recipe = new Recipe();
//        recipe.setRecipeId("id");
//        recipe.setSourceURL("http://google.com");

        int recipePage = new Random().nextInt(RecipeMainRepository.RECIPE_RANGE);

        when(service.search(BuildConfig.FOOD_API_KEY,
                RecipeMainRepository.RECENT_SORT,
                RecipeMainRepository.COUNT, recipePage)).thenReturn(buildCall(true,null));

        repository.setRecipePage(recipePage);
        repository.getNextRecipe();

        verify(service).search(BuildConfig.FOOD_API_KEY, RecipeMainRepository.RECENT_SORT, RecipeMainRepository.COUNT, recipePage);
        verify(eventBus).post(recipeMainEventArgumentCaptor.capture());

        RecipeMainEvent event = recipeMainEventArgumentCaptor.getValue();
        assertEquals(RecipeMainEvent.NEXT_EVENT, event.getType());
        assertNull(event.getError());
        assertNotNull(event.getRecipe());
        assertEquals(recipe, event.getRecipe());

    }

    @Test
    public void getNextRecipeCalled_apiServiceFailedCallEventPosted() {
        int recipePage = new Random().nextInt(RecipeMainRepository.RECIPE_RANGE);
        String error = "error";

        when(service.search(BuildConfig.FOOD_API_KEY,
                RecipeMainRepository.RECENT_SORT,
                RecipeMainRepository.COUNT, recipePage)).thenReturn(buildCall(false,error));

        repository.setRecipePage(recipePage);
        repository.getNextRecipe();

        verify(service).search(BuildConfig.FOOD_API_KEY, RecipeMainRepository.RECENT_SORT, RecipeMainRepository.COUNT, recipePage);
        verify(eventBus).post(recipeMainEventArgumentCaptor.capture());
        RecipeMainEvent event = recipeMainEventArgumentCaptor.getValue();
        assertEquals(RecipeMainEvent.NEXT_EVENT, event.getType());
        assertNull(event.getRecipe());
        assertEquals(error, event.getError());
    }

    private Call<RecipeSearchResponse> buildCall(final Boolean success, final String error) {

        //Call<RecipeSearchResponse> response = new Call<RecipeSearchResponse>();
        return new Call<RecipeSearchResponse>() {
            @Override
            public Response<RecipeSearchResponse> execute() throws IOException {
                Response<RecipeSearchResponse> result = null;
                if (success) {
                    RecipeSearchResponse response = new RecipeSearchResponse();
                    response.setCount(1);
                    response.setRecipes(Arrays.asList(recipe));
                    result = Response.success(response);
                } else {
                    result = Response.error(null, null);
                }
                return result;
            }

            @Override
            public void enqueue(Callback<RecipeSearchResponse> callback) {
                if(success) {
                    try {
                        callback.onResponse(this, execute());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    callback.onFailure(this,new Throwable(error));
                }
            }

            @Override
            public boolean isExecuted() {
                return true;
            }

            @Override
            public void cancel() {
            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<RecipeSearchResponse> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
    }
}
