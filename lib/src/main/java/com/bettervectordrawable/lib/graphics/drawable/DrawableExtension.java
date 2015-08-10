package com.bettervectordrawable.lib.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.bettervectordrawable.utils.ReflectionHelper;

import java.lang.reflect.Method;

/**
 * @hide
 */
public class DrawableExtension {
    private static final Method _setColorMethod = ReflectionHelper.tryFindMethod(PorterDuffColorFilter.class, "setColor", int.class);
    private static final Method _setModeMethod = ReflectionHelper.tryFindMethod(PorterDuffColorFilter.class, "setMode", Mode.class);

    public static PorterDuffColorFilter updateTintFilter(@NonNull Drawable self, PorterDuffColorFilter tintFilter, ColorStateList tint, Mode tintMode) {
        if (tint == null || tintMode == null) {
            return null;
        }
        final int color = tint.getColorForState(self.getState(), Color.TRANSPARENT);
        if (tintFilter == null) {
            return new PorterDuffColorFilter(color, tintMode);
        }
        ReflectionHelper.tryInvokeMethod(tintFilter, _setColorMethod, color);
        ReflectionHelper.tryInvokeMethod(tintFilter, _setModeMethod, tintMode);
        return tintFilter;
    }

    public static TypedArray obtainAttributes(Resources res, Theme theme, AttributeSet set, int[] attrs) {
        if (theme == null) {
            return res.obtainAttributes(set, attrs);
        }
        return theme.obtainStyledAttributes(set, attrs, 0, 0);
    }

    public static Mode parseTintMode(int value, Mode defaultMode) {
        switch (value) {
            case 3:
                return Mode.SRC_OVER;
            case 5:
                return Mode.SRC_IN;
            case 9:
                return Mode.SRC_ATOP;
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            case 16:
                return Mode.ADD;
            default:
                return defaultMode;
        }
    }

    public static final Mode DEFAULT_TINT_MODE = Mode.SRC_IN;

    private static final Method _getLayoutDirectionMethod = ReflectionHelper.tryFindMethod(Drawable.class, "getLayoutDirection");

    public static int getLayoutDirection(@NonNull Drawable self) {
        Integer result = ReflectionHelper.tryInvokeMethod(self, _getLayoutDirectionMethod);
        return result == null ? 0 : result;
    }
}
