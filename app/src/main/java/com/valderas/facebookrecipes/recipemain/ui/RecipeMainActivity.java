package com.valderas.facebookrecipes.recipemain.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.valderas.facebookrecipes.FacebookRecipesApp;
import com.valderas.facebookrecipes.R;
import com.valderas.facebookrecipes.recipelist.ui.RecipeListActivity;
import com.valderas.facebookrecipes.entities.Recipe;
import com.valderas.facebookrecipes.libs.base.ImageLoader;
import com.valderas.facebookrecipes.recipemain.RecipeMainPresenter;
import com.valderas.facebookrecipes.recipemain.di.RecipeMainComponent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeMainActivity extends AppCompatActivity implements RecipeMainView, SwipeGestureListener {

    @Bind(R.id.imgRecipe)
    ImageView imgRecipe;
    @Bind(R.id.imgDismiss)
    ImageButton imgDismiss;
    @Bind(R.id.imgKeep)
    ImageButton imgKeep;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.layoutContainer)
    RelativeLayout layoutContainer;


    private RecipeMainPresenter presenter;
    private Recipe currentRecipe;
    private ImageLoader imageLoader;
    private RecipeMainComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_main);
        ButterKnife.bind(this);
        setupInjection();
        setupImageloder();
        setupGestureDetection();
        presenter.onCreate();
        presenter.getNextRecipe();
    }

    private void setupGestureDetection() {
        final GestureDetector gestureDetector = new GestureDetector(this, new SwipeGestureDetector(this));
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        imgRecipe.setOnTouchListener(gestureListener);
    }

    private void setupImageloder() {
        RequestListener glideRequestlistener = new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                presenter.imageError(e.getLocalizedMessage());
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                presenter.imageReady();
                return false;
            }
        };
        imageLoader.setOnFinishedImageLoadingListener(glideRequestlistener);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    private void setupInjection() {
        FacebookRecipesApp app = (FacebookRecipesApp) getApplication();
        //app.getRecipeMainComponent(this, this).inject(this);
        component = app.getRecipeMainComponent(this, this);
        imageLoader = getImageLoader();
        presenter = getPresenter();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showUIElements() {
        imgDismiss.setVisibility(View.VISIBLE);
        imgKeep.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUIElements() {
        imgDismiss.setVisibility(View.GONE);
        imgKeep.setVisibility(View.GONE);
    }
    @Override
    @OnClick(R.id.imgKeep)
    public void onKeep() {
        if (currentRecipe != null) {
            presenter.saveRecipe(currentRecipe);
        }
    }
    @Override
    @OnClick(R.id.imgDismiss)
    public void onDismiss() {
        presenter.dismissRecipe();
    }

    @Override
    public void saveAnimation() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_save);
        anim.setAnimationListener(getAnimationListener());
        imgRecipe.startAnimation(anim);
    }

    private Animation.AnimationListener getAnimationListener() {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                clearImage();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

    }

    @Override
    public void dismissAnimation() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_dismiss);
        anim.setAnimationListener(getAnimationListener());
        imgRecipe.startAnimation(anim);
    }

    private void clearImage() {
        imgRecipe.setImageResource(0);
    }

    @Override
    public void onRecipeSaved() {
        Snackbar.make(layoutContainer, R.string.recipemain_notice_saved, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setRecipe(Recipe recipe) {
        this.currentRecipe = recipe;
        imageLoader.load(imgRecipe, recipe.getImageURL());
    }

    @Override
    public void onGetRecipeError(String error) {
        String msgError = String.format(getString(R.string.recipemain_error), error);
        Snackbar.make(layoutContainer, msgError, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_list) {
            navigateToListScreen();
        } else {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FacebookRecipesApp app = (FacebookRecipesApp) getApplication();
        app.logout();
    }

    private void navigateToListScreen() {
        startActivity(new Intent(this, RecipeListActivity.class));
    }

    public ImageLoader getImageLoader() {
        return component.getImageLoader();
    }

    public RecipeMainPresenter getPresenter() {
        return component.getPresenter();
    }
}
