package com.valderas.facebookrecipes.recipemain;

import com.valderas.facebookrecipes.BaseTest;
import com.valderas.facebookrecipes.entities.Recipe;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

/**
 * Created by LEO on 9/7/2016.
 */
public class GetNextRecipeInteractorTest extends BaseTest {
    @Mock
    private RecipeMainRepository repository;
    private GetNextRecipeInteractor interactor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        interactor = new GetNextRecipeInteractorImpl(repository);
    }

    @Test
    public void testExecute_callsRepository() {

        interactor.execute();
        verify(repository).getNextRecipe();
    }
}
