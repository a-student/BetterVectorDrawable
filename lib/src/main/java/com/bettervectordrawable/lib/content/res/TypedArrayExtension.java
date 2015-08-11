package com.bettervectordrawable.lib.content.res;

import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;

import com.bettervectordrawable.utils.ReflectionHelper;

import java.lang.reflect.Method;

/**
 * @hide
 */
public class TypedArrayExtension {
    private static final Method _extractThemeAttrsMethod = ReflectionHelper.tryFindMethod(TypedArray.class, "extractThemeAttrs");

    public static int[] extractThemeAttrs(@NonNull TypedArray self) {
        return ReflectionHelper.tryInvokeMethod(self, _extractThemeAttrsMethod);
    }

    public static int getChangingConfigurations(@NonNull TypedArray self) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return self.getChangingConfigurations();
        }
        return 0;
    }
}
