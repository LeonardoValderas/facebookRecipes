package com.valderas.facebookrecipes.libs.di;

import android.app.Activity;

import com.valderas.facebookrecipes.libs.GlideImageLoader;
import com.valderas.facebookrecipes.libs.GreenRobotEventBus;
import com.valderas.facebookrecipes.libs.base.EventBus;
import com.valderas.facebookrecipes.libs.base.ImageLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LEO on 21/6/2016.
 */
@Module
public class LibsModule {
    Activity activity;

    public LibsModule() {
    }
    public LibsModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return new GreenRobotEventBus();
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader(Activity activity) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (activity != null) {
            imageLoader.setLoaderContext(activity);
        }
        return imageLoader;
    }

    @Provides
    @Singleton
    Activity provideActivity(){
        return this.activity;
    }

}
