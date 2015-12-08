package com.bettervectordrawable;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bettervectordrawable.lib.graphics.drawable.VectorDrawable;

public class VectorDrawableCompat {
    private static final String LOG_TAG = VectorDrawableCompat.class.getSimpleName();

    /**
     * Overload for {@link #enableResourceInterceptionFor(boolean, Resources, int...)}
     * with {@code forceSystemHandlingWhenPossible = false}
     */
    public static void enableResourceInterceptionFor(@NonNull Resources resources, int... ids) {
        enableResourceInterceptionFor(false, resources, ids);
    }

    /**
     * Call this from {@link Application#onCreate()} to enable easy use of vector resources
     * from XML layouts or via {@link Resources#getDrawable}
     *
     * @param forceSystemHandlingWhenPossible Specify {@code true} if you want to force system handling of vector drawables on Android 5.0+.
     *        However, doing this is NOT RECOMMENDED, because system implementation of VectorDrawable does not support some extra features,
     *        like path's {@code fillType} attribute. For more info <a href="https://github.com/a-student/BetterVectorDrawable">read this</a>
     * @param resources Pass {@code getResources()}
     * @param ids List of vector resource IDs for which interception will be enabled.
     *        Pass them manually, for example {@code R.drawable.bike, R.drawable.hourglass},
     *        or call {@link #findAllVectorResourceIdsSlow} or {@link #findVectorResourceIdsByConvention}
     */
    public static void enableResourceInterceptionFor(boolean forceSystemHandlingWhenPossible, @NonNull Resources resources, int... ids) {
        if (!isSystemHandling(forceSystemHandlingWhenPossible)) {
            ResourcesInterceptor.enableFor(resources, ids);
        }
    }

    /**
     * Find all vector resources in the app.
     * This method scans through all drawable XML resources and ensures that each returned resource has vector data inside.
     * Usually it is OK to use this method by default. Nevertheless, it may increase application startup time on old devises if the app has many drawable resources.
     * In this case use {@link #findVectorResourceIdsByConvention} instead. If you are not sure, try to measure the performance of the each method.
     *
     * @param resources Pass {@code getResources()}
     * @param rDrawable Pass {@code R.drawable.class}
     */
    public static int[] findAllVectorResourceIdsSlow(@NonNull Resources resources, @NonNull Class<?> rDrawable) {
        return VectorResourceFinder.findAllIdsSlow(resources, rDrawable);
    }

    /**
     * Find all vector resources in the app by resource naming convention.
     * Opposite to {@link #findAllVectorResourceIdsSlow} this method does not load resources, thus it is faster.
     * <p/>
     * The method relies on that every vector resource name matches vector_* pattern or *_vector pattern, or both.
     * This can be controlled by {@param resourceNamingConvention}.
     *
     * @param resources Pass {@code getResources()}
     * @param rDrawable Pass {@code R.drawable.class}
     */
    public static int[] findVectorResourceIdsByConvention(@NonNull Resources resources, @NonNull Class<?> rDrawable, @NonNull Convention resourceNamingConvention) {
        return VectorResourceFinder.findIdsByConvention(resources, rDrawable, resourceNamingConvention);
    }

    /**
     * Find all vector resources in the app by resource naming convention.
     * Opposite to {@link #findAllVectorResourceIdsSlow} this method does not load resources, thus it is faster.
     * <p/>
     * The method relies on that every vector resource name has the prefix or the suffix, or both.
     * This can be controlled by {@param resourceNamingConvention}.
     *
     * @param resources Pass {@code getResources()}
     * @param rDrawable Pass {@code R.drawable.class}
     * @param prefix Pass prefix convention
     * @param suffix Pass suffix convention
     */
    public static int[] findVectorResourceIdsByConvention(@NonNull Resources resources, @NonNull Class<?> rDrawable, @NonNull Convention resourceNamingConvention, String prefix, String suffix) {
        return VectorResourceFinder.findIdsByConvention(resources, rDrawable, resourceNamingConvention, prefix, suffix);
    }

    /**
     * Overload for {@link #inflate(boolean, Resources, int)}
     * with {@code forceSystemHandlingWhenPossible = false}
     */
    public static Drawable inflate(@NonNull Resources resources, @DrawableRes int id) {
        return inflate(false, resources, id);
    }

    /**
     * Inflate a drawable from a vector XML resource.
     *
     * @param forceSystemHandlingWhenPossible Specify {@code true} if you want to force system handling of vector drawables on Android 5.0+.
     *        However, doing this is NOT RECOMMENDED, because system implementation of VectorDrawable does not support some extra features,
     *        like path's {@code fillType} attribute. For more info <a href="https://github.com/a-student/BetterVectorDrawable">read this</a>
     * @param resources Resources used to resolve attribute values
     * @param id The vector resource ID to inflate
     * @return An object that can be used to draw this resource
     */
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

    /**
     * Returns {@value true} if resource interception was successful.
     * Check this after calling {@link #enableResourceInterceptionFor}.
     */
    public static boolean isResourceInterceptionEnabled() {
        return ResourcesInterceptor.isInterceptionEnabled();
    }
}
