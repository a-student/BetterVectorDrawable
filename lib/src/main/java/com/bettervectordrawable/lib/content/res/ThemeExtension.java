package com.bettervectordrawable.lib.content.res;

import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;

import com.bettervectordrawable.utils.ReflectionHelper;

import java.lang.reflect.Method;

/**
 * @hide
 */
public class ThemeExtension {
    private static final Method _resolveAttributesMethod = ReflectionHelper.tryFindMethod(Theme.class, "resolveAttributes", int[].class, int[].class);

    public static TypedArray resolveAttributes(@NonNull Theme self, @NonNull int[] values, @NonNull int[] attrs) {
        return ReflectionHelper.tryInvokeMethod(self, _resolveAttributesMethod, values, attrs);
    }
}
