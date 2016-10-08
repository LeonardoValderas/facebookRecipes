package com.valderas.facebookrecipes.recipemain;

import com.valderas.facebookrecipes.BaseTest;
import com.valderas.facebookrecipes.entities.Recipe;
import com.valderas.facebookrecipes.libs.base.EventBus;
import com.valderas.facebookrecipes.recipemain.events.RecipeMainEvent;
import com.valderas.facebookrecipes.recipemain.ui.RecipeMainView;

import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by LEO on 9/7/2016.
 */
public class RecipeMainPresenterTest extends BaseTest {
    @Mock
    private EventBus eventBus;
    @Mock
    private RecipeMainView view;
    @Mock
    private SaveRecipeInteractor saveRecipe;
    @Mock
    private GetNextRecipeInteractor getNextRecipe;
    @Mock
    Recipe recipe;
    @Mock
    RecipeMainEvent event;

    private RecipeMainPresenter presenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        presenter = new RecipeMainPresenterImpl(eventBus, view, saveRecipe, getNextRecipe);
    }

    @Test
    public void onCreate_SubscribedToEventBus() {
        presenter.onCreate();
        verify(eventBus).register(presenter);
    }


    @Test
    public void onDestroy_UnsubscribedToEventBusAndViewDestroyed() throws NoSuchFieldException {
        presenter.onDestroy();

        assertNull(presenter.getView());
        verify(eventBus).unregister(presenter);
    }

    @Test
    public void saveRecipe_executeSaveInteractor() {
        //     Recipe recipe = new Recipe();

        presenter.saveRecipe(recipe);

        verify(view).saveAnimation();
        verify(view).hideUIElements();
        verify(view).showProgress();
        verify(saveRecipe).execute(recipe);
    }

    @Test
    public void dismissRecipe_executeGetNextRecipeInteractor() {
        presenter.dismissRecipe();

        verify(view).dismissAnimation();
        //getNextRecipe_executeGetNextRecipeInteractor();
    }

    @Test
    public void getNextRecipe_executeGetNextRecipeInteractor() {
        presenter.getNextRecipe();

        verify(view).hideUIElements();
        verify(view).showProgress();

        verify(getNextRecipe).execute();
    }

    @Test
    public void onEvent_hasError() {
        //    event.setError("");
        String msgError = "Error";
        when(event.getError()).thenReturn(msgError);
        presenter.onEventMainThread(event);
        assertNotNull(presenter.getView());

        verify(view).hideProgress();
        verify(view).onGetRecipeError(event.getError());
    }

    @Test
    public void onNextEvent_setRecipeOnView() {
        //    RecipeMainEvent event = new RecipeMainEvent();

        when(event.getType()).thenReturn(RecipeMainEvent.NEXT_EVENT);
        when(event.getRecipe()).thenReturn(recipe);

        presenter.onEventMainThread(event);
        assertNotNull(presenter.getView());
        verify(view).setRecipe(event.getRecipe());
    }

    @Test
    public void onSaveEvent_notifyView_getNextRecipe() {
        when(event.getType()).thenReturn(RecipeMainEvent.SAVE_EVENT);
        presenter.onEventMainThread(event);
        verify(view).onRecipeSaved();
        verify(getNextRecipe).execute();
    }

    @Test
    public void testImageReady_updateIU() throws Exception {
        presenter.imageReady();

        assertNotNull(presenter.getView());
        verify(view).hideProgress();
        verify(view).showUIElements();
    }

    @Test
    public void testImageError_updateIU() throws Exception {
        String msgError = "Error";

        presenter.imageError(msgError);

        assertNotNull(presenter.getView());
        verify(view).onGetRecipeError(msgError);
    }

    @Test
    public void testGetView_returnView() throws Exception {
        assertEquals(view, presenter.getView());
    }
}
