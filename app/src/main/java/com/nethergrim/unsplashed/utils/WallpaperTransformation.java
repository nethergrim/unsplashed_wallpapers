package com.nethergrim.unsplashed.utils;

import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import com.nethergrim.unsplashed.App;
import com.squareup.picasso.Transformation;

/**
 * Created by andrej on 12.06.16.
 */
public class WallpaperTransformation implements Transformation {

    private float screenWidth;
    private float screenHeight;

    public WallpaperTransformation() {
        DisplayMetrics metrics = App.instance.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap result = getResizedBitmap(source,  (int) screenHeight);
        if (result != source) {
            source.recycle();
        }
        return result;
    }

    @Override
    public String key() {
        return "transform";
    }

    public Bitmap getResizedBitmap(Bitmap bm,  int newHeight) {
        float aspectRatio = (float) bm.getWidth() / (float) bm.getHeight();
        int height = newHeight;
        int width = Math.round(height / aspectRatio);



        Bitmap yourSelectedImage = Bitmap.createScaledBitmap(bm, width, height, false);
        return yourSelectedImage;
    }
}
