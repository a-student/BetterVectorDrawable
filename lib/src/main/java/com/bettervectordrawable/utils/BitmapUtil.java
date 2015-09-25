package com.bettervectordrawable.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by tagakov on 25.09.15.
 * vladimir@tagakov.com
 */
public class BitmapUtil {
    private BitmapUtil() {
    }// prevent instantiation

    /**
     * Converts DP into PX depending on screen density
     *
     * @param metrics display metrics for converting
     * @param dp      value in dp that should be converted to px
     * @return px value
     */
    private static int toPx(@NonNull final DisplayMetrics metrics, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }


    /**
     * Converts drawable into bitmap
     *
     * @param source bitmap which has preferred size ({@link Drawable#getIntrinsicHeight()} and {@link Drawable#getIntrinsicHeight()} return positive values), or set bounds ({@link Drawable#getBounds()} has meaningful values)
     * @return bitmap with size set to {@link Drawable#getIntrinsicHeight()} and {@link Drawable#getIntrinsicHeight()} or {@link Rect#width()} and {@link Rect#height()}
     */
    @NonNull
    public static Bitmap toBitmap(@NonNull Drawable source, @NonNull DisplayMetrics metrics) {
        Rect boudns = source.getBounds();
        int width = source.getIntrinsicWidth();
        int height = source.getIntrinsicHeight();
        width = width > 0 ? width : boudns.width();
        height = height > 0 ? height : boudns.height();
        return toBitmapWithExactSize(source, metrics, width, height);
    }

    /**
     * Converts drawable into bitmap with specified dimensions, if method fails while
     * ensuring aspect ratio unknown metric will be set to 1 px
     *
     * @param source   drawable
     * @param widthDp  if 0 is passed method will try to resize image keeping aspect ratio
     * @param heightDp if 0 is passed method will try to resize image keeping aspect ratio
     * @return result bitmap
     */
    @NonNull
    public static Bitmap toBitmap(@NonNull Drawable source, @NonNull DisplayMetrics metrics, float widthDp, float heightDp) {
        return toBitmapWithExactSize(source, metrics, toPx(metrics, widthDp), toPx(metrics, heightDp), 1, 1);
    }

    /**
     * Converts drawable into bitmap with specified dimensions, if method fails while
     * ensuring aspect ratio unknown metric will be set to appropriate <code>fallbackWidth*</code> value
     *
     * @param widthDp          if 0 is passed method will try to resize image keeping aspect ratio
     * @param heightDp         if 0 is passed method will try to resize image keeping aspect ratio
     * @param fallbackWidthDp  fallback width value, that will be used if ensuring width appropriate to aspect ratio fails
     * @param fallbackHeightDp fallback width value, that will be used if ensuring height appropriate to aspect ratio fails
     * @return result bitmap
     */
    @NonNull
    public static Bitmap toBitmap(@NonNull Drawable source, @NonNull DisplayMetrics metrics, float widthDp, float heightDp, float fallbackWidthDp, float fallbackHeightDp) {
        return toBitmapWithExactSize(source, metrics, toPx(metrics, widthDp), toPx(metrics, heightDp), toPx(metrics, fallbackWidthDp), toPx(metrics, fallbackHeightDp));
    }


    /**
     * Converts drawable into bitmap with specified dimensions, if method fails while
     * ensuring aspect ratio unknown metric will be set to 1 px
     *
     * @param source   drawable
     * @param widthPx  if 0 is passed method will try to resize image keeping aspect ratio
     * @param heightPx if 0 is passed method will try to resize image keeping aspect ratio
     * @return result bitmap
     */
    @NonNull
    public static Bitmap toBitmapWithExactSize(@NonNull Drawable source, @NonNull DisplayMetrics metrics, int widthPx, int heightPx) {
        return toBitmapWithExactSize(source, metrics, widthPx, heightPx, 1, 1);
    }

    /**
     * Converts drawable into bitmap with specified dimensions, if method fails while
     * ensuring aspect ratio unknown metric will be set to appropriate <code>fallbackWidth*</code> value
     *
     * @param widthPx          if 0 is passed method will try to resize image keeping aspect ratio
     * @param heightPx         if 0 is passed method will try to resize image keeping aspect ratio
     * @param fallbackWidthPx  fallback width value, that will be used if ensuring width appropriate to aspect ratio fails
     * @param fallbackHeightPx fallback width value, that will be used if ensuring height appropriate to aspect ratio fails
     * @return result bitmap
     */
    @NonNull
    public static Bitmap toBitmapWithExactSize(
            @NonNull Drawable source,
            @NonNull DisplayMetrics metrics,
            int widthPx,
            int heightPx,
            int fallbackWidthPx,
            int fallbackHeightPx) {

        Rect prevBounds = source.copyBounds();

        if (heightPx == 0 || widthPx == 0) {// we should keep aspect ratio
            //get intrinsic size for obtaining source aspect ratio
            int intrinsicWidth = source.getIntrinsicWidth();
            int intrinsicHeight = source.getIntrinsicHeight();

            //falling back to bounds if needed
            intrinsicWidth = intrinsicWidth > 0 ? intrinsicWidth : prevBounds.width();
            intrinsicHeight = intrinsicHeight > 0 ? intrinsicHeight : prevBounds.height();

            if (heightPx == 0 && widthPx > 0 && intrinsicWidth > 0) {
                heightPx = widthPx * intrinsicHeight / intrinsicWidth;
            } else if (heightPx >= 0 && widthPx == 0 && intrinsicHeight > 0) {
                widthPx = heightPx * intrinsicWidth / intrinsicHeight;
            }
        }

        int width = widthPx > 0 ? widthPx : fallbackWidthPx;
        int height = heightPx > 0 ? heightPx : fallbackHeightPx;

        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        canvas.setDensity(metrics.densityDpi);

        source.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        source.draw(canvas);

        source.setBounds(prevBounds);

        return result;
    }
}
