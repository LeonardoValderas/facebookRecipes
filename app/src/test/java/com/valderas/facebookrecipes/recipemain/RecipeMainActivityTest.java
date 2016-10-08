package com.valderas.facebookrecipes.recipemain;

import android.content.ComponentName;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.valderas.facebookrecipes.BaseTest;
import com.valderas.facebookrecipes.BuildConfig;
import com.valderas.facebookrecipes.R;
import com.valderas.facebookrecipes.entities.Recipe;
import com.valderas.facebookrecipes.libs.base.ImageLoader;
import com.valderas.facebookrecipes.login.ui.LoginActivity;
import com.valderas.facebookrecipes.recipelist.ui.RecipeListActivity;
import com.valderas.facebookrecipes.recipemain.ui.RecipeMainActivity;
import com.valderas.facebookrecipes.recipemain.ui.RecipeMainView;
import com.valderas.facebookrecipes.support.ShadowImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by LEO on 9/7/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows = {ShadowImageView.class})
public class RecipeMainActivityTest extends BaseTest {

    @Mock
    private ImageLoader imageLoader;
    @Mock
    private Recipe currentRecipe;
    @Mock
    private RecipeMainPresenter presenter;

    private RecipeMainView view;
    private RecipeMainActivity activity;
    private ShadowActivity shadowActivity;
    private ActivityController<RecipeMainActivity> controller;


    @Override
    public void setUp() throws Exception {
        super.setUp();
        RecipeMainActivity recipeMainActivity = new RecipeMainActivity(){

            @Override
            public ImageLoader getImageLoader() {
                return imageLoader;
            }

            @Override
            public RecipeMainPresenter getPresenter() {
                return presenter;
            }
        };

        controller = ActivityController.of(Robolectric.getShadowsAdapter(), recipeMainActivity).create().visible();
        activity = controller.get();
        view = (RecipeMainView)activity;
        shadowActivity = shadowOf(activity);
    }

    @Test
    public void onActivityCreated_getsNextRecipe() {
        verify(presenter).onCreate();
        verify(presenter).getNextRecipe();
    }

    @Test
    public void onActivityDestroyed_destroyPresenter() {
        controller.destroy();
        verify(presenter).onDestroy();
    }

    @Test
    public void setRecipe_imageLoaderCalled() {
        String url = "http//galileo.edu";
        when(currentRecipe.getImageURL()).thenReturn(url);
        view.setRecipe(currentRecipe);

        ImageView imgRecipe = (ImageView)activity.findViewById(R.id.imgRecipe);
        verify(imageLoader).load(imgRecipe, currentRecipe.getImageURL());
    }

    @Test
    public void showProgress_progressBarVisible() {
        view.showProgress();

        ProgressBar progressBar = (ProgressBar)activity.findViewById(R.id.progressBar);
        assertEquals(View.VISIBLE, progressBar.getVisibility());
    }

    @Test
    public void hideProgress_progressBarGone() {
        view.hideProgress();

        ProgressBar progressBar = (ProgressBar)activity.findViewById(R.id.progressBar);
        assertEquals(View.GONE, progressBar.getVisibility());
    }

    @Test
    public void showUIElements_buttonsVisible() {
        view.showUIElements();

        ImageButton btnKeep = (ImageButton)activity.findViewById(R.id.imgKeep);
        ImageButton btnDismiss = (ImageButton)activity.findViewById(R.id.imgDismiss);
        assertEquals(View.VISIBLE, btnKeep.getVisibility());
        assertEquals(View.VISIBLE, btnDismiss.getVisibility());
    }

    @Test
    public void hidesUIElements_buttonsGone() {
        view.hideUIElements();

        ImageButton btnKeep = (ImageButton)activity.findViewById(R.id.imgKeep);
        ImageButton btnDismiss = (ImageButton)activity.findViewById(R.id.imgDismiss);
        assertEquals(View.GONE, btnKeep.getVisibility());
        assertEquals(View.GONE, btnDismiss.getVisibility());
    }

    @Test
    public void logoutMenuClicked_launchLoginActivity() {
        shadowActivity.clickMenuItem(R.id.action_logout);
        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(activity, LoginActivity.class),intent.getComponent());
    }

    @Test
    public void favoriteListMenuClick_launchRecipeListActivity() {
        shadowActivity.clickMenuItem(R.id.action_list);
        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(activity, RecipeListActivity.class),intent.getComponent());
    }

    @Test
    public void keepButtonClicked_saveRecipe() {
        activity.setRecipe(currentRecipe);

        ImageButton btnKeep = (ImageButton)activity.findViewById(R.id.imgKeep);
        btnKeep.performClick();

        verify(presenter).saveRecipe(currentRecipe);
    }

    @Test
    public void dismissButtonClicked_dismissRecipe() {
        ImageButton btnDismiss = (ImageButton)activity.findViewById(R.id.imgDismiss);
        btnDismiss.performClick();
        verify(presenter).dismissRecipe();
    }

    @Test
    public void saveAnimation_animationStarted() {
        activity.saveAnimation();

        ImageView imgRecipe = (ImageView)activity.findViewById(R.id.imgRecipe);

        assertNotNull(imgRecipe.getAnimation());
        assert(imgRecipe.getAnimation().hasStarted());
    }

    @Test
    public void dismissAnimation_animationStarted() {
        activity.dismissAnimation();
        ImageView imgRecipe = (ImageView)activity.findViewById(R.id.imgRecipe);
        assertNotNull(imgRecipe.getAnimation());
        assert(imgRecipe.getAnimation().hasStarted());
    }

    @Test
    public void onSwipeToKeep_saveRecipe(){
        activity.setRecipe(currentRecipe);

        ImageView imgRecipe = (ImageView)activity.findViewById(R.id.imgRecipe);
        ShadowImageView shadowImage = (ShadowImageView) ShadowExtractor.extract(imgRecipe);
        shadowImage.performSwipe(200, 200, 500, 250, 50);
        verify(presenter).saveRecipe(currentRecipe);
    }

    @Test
    public void onSwipeToDismiss_discardRecipe(){
        ImageView imgRecipe = (ImageView)activity.findViewById(R.id.imgRecipe);
        ShadowImageView shadowImage = (ShadowImageView) ShadowExtractor.extract(imgRecipe);
        shadowImage.performSwipe(200, 200, -500, 250, 50);

        verify(presenter).dismissRecipe();
    }
}
