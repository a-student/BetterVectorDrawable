package com.bettervectordrawable;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bettervectordrawable.lib.graphics.drawable.VectorDrawable;

public class VectorDrawableCompat {
    private static final String LOG_TAG = VectorDrawableCompat.class.getSimpleName();

    public static void enableResourceInterceptionFor(@NonNull Resources resources, int... ids) {
        enableResourceInterceptionFor(false, resources, ids);
    }

    public static void enableResourceInterceptionFor(boolean forceSystemHandlingWhenPossible, @NonNull Resources resources, int... ids) {
        if (!isSystemHandling(forceSystemHandlingWhenPossible)) {
            ResourcesInterceptor.enableFor(resources, ids);
        }
    }

    public static int[] findAllVectorResourceIdsSlow(@NonNull Resources resources, @NonNull Class<?> rDrawable) {
        return VectorResourceFinder.findAllIdsSlow(resources, rDrawable);
    }

    public static int[] findVectorResourceIdsByConvention(@NonNull Resources resources, @NonNull Class<?> rDrawable, @NonNull Convention resourceNamingConvention) {
        return VectorResourceFinder.findIdsByConvention(resources, rDrawable, resourceNamingConvention);
    }

    public static Drawable inflate(@NonNull Resources resources, @DrawableRes int id) {
        return inflate(false, resources, id);
    }

    @SuppressLint("NewApi")
    public static Drawable inflate(boolean forceSystemHandlingWhenPossible, @NonNull Resources resources, @DrawableRes int id) {
        boolean systemHandling = isSystemHandling(forceSystemHandlingWhenPossible);
        Log.d(LOG_TAG, String.format("Inflating resource with id #0x%s (system handling: %s)", Integer.toHexString(id), systemHandling));
        if (systemHandling) {
            return resources.getDrawable(id, null);
        }
        return VectorDrawable.create(resources, id);
    }

    private static boolean isSystemHandling(boolean forceSystemHandlingWhenPossible) {
        return forceSystemHandlingWhenPossible && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
