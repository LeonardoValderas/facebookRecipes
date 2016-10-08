package com.valderas.facebookrecipes.recipemain;

import com.valderas.facebookrecipes.BaseTest;
import com.valderas.facebookrecipes.entities.Recipe;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

/**
 * Created by LEO on 9/7/2016.
 */
public class SaveRecipeInteractorTest extends BaseTest {
    @Mock
    private RecipeMainRepository repository;
    @Mock
    Recipe recipe;
    private SaveRecipeInteractor interactor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        interactor = new SaveRecipeInteractorImpl(repository);
    }

    @Test
    public void testExecute_callsRepository() {

        interactor.execute(recipe);
        verify(repository).saveRecipe(recipe);
    }
}
