package com.valderas.facebookrecipes.libs.base;

import android.widget.ImageView;

/**
 * Created by LEO on 21/6/2016.
 */
public interface ImageLoader {
    void load(ImageView imageView, String URL);
    void setOnFinishedImageLoadingListener(Object object);
}
