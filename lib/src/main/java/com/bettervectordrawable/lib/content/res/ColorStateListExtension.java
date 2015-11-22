package com.bettervectordrawable.lib.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.support.annotation.NonNull;

import com.bettervectordrawable.utils.ReflectionHelper;

import java.lang.reflect.Method;

/**
 * @hide
 */
public class ColorStateListExtension {
    private static final Method _canApplyThemeMethod = ReflectionHelper.tryFindMethod(ColorStateList.class, "canApplyTheme");

    public static boolean canApplyTheme(@NonNull ColorStateList self) {
        Boolean result = ReflectionHelper.tryInvokeMethod(self, _canApplyThemeMethod);
        return result == null ? false : result;
    }

    private static final Method _obtainForThemeMethod = ReflectionHelper.tryFindMethod(ColorStateList.class, "obtainForTheme", Theme.class);

    public static ColorStateList obtainForTheme(@NonNull ColorStateList self, Theme t) {
        return ReflectionHelper.tryInvokeMethod(self, _obtainForThemeMethod, t);
    }

    private static final Method _getChangingConfigurationsMethod = ReflectionHelper.tryFindMethod(ColorStateList.class, "getChangingConfigurations");

    public static int getChangingConfigurations(@NonNull ColorStateList self) {
        Integer result = ReflectionHelper.tryInvokeMethod(self, _getChangingConfigurationsMethod);
        return result == null ? 0 : result;
    }
}
