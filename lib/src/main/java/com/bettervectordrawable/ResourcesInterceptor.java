package com.bettervectordrawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LongSparseArray;

import java.lang.reflect.Field;

/**
 * @hide
 */
public class ResourcesInterceptor {
    private static final String LOG_TAG = ResourcesInterceptor.class.getSimpleName();

    private static final VectorDrawableFactory _vectorDrawableFactory = new VectorDrawableFactory();

    public static void enableFor(@NonNull Resources resources, int[] ids) {
        _vectorDrawableFactory.enableFor(resources, ids);
        tryIntercept();
    }

    private static boolean _intercepted, _interceptionSuccessful;

    @SuppressWarnings("unchecked")
    private static void tryIntercept() {
        if (_intercepted) {
            return;
        } else {
            _intercepted = true;
        }

        try {
            Field field = Resources.class.getDeclaredField("sPreloadedDrawables");
            field.setAccessible(true);
            Object value = field.get(null);
            if (value instanceof LongSparseArray<?>[]) {
                LongSparseArray<Drawable.ConstantState>[] preloadedDrawables = (LongSparseArray<Drawable.ConstantState>[]) value;
                for (int i = 0; i < preloadedDrawables.length; i++) {
                    preloadedDrawables[i] = new LongSparseArrayDrawableConstantStateWrapper(preloadedDrawables[i], _vectorDrawableFactory);
                }
            } else if (value instanceof LongSparseArray<?>) {
                field.set(null, new LongSparseArrayDrawableConstantStateWrapper((LongSparseArray<Drawable.ConstantState>) value, _vectorDrawableFactory));
            } else {
                throw new Exception("Unknown type of the field");
            }
            _interceptionSuccessful = true;
        } catch (Exception e) {
            // Not very bad, it just means we met newer version of Android.
            // In this case, system will handle vector drawables for us.
            Log.w(LOG_TAG, "Unable to intercept", e);
        }
    }

    public static boolean isInterceptionEnabled() {
        return _interceptionSuccessful;
    }
}
