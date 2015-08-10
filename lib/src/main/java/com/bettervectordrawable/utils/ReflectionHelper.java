package com.bettervectordrawable.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @hide
 */
public class ReflectionHelper {
    private static final String LOG_TAG = ReflectionHelper.class.getSimpleName();

    @Nullable
    public static Method tryFindMethod(@NonNull Class<?> target, @NonNull String name, @Nullable Class<?>... parameterTypes) {
        try {
            return target.getDeclaredMethod(name, parameterTypes);
        } catch (Exception e) {
            Log.e(LOG_TAG, String.format("Unable to find method %s.%s", target.getSimpleName(), name), e);
        }
        return null;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T tryInvokeMethod(@Nullable Object target, @Nullable Method method, @Nullable Object... args) {
        if (method != null) {
            try {
                return (T) method.invoke(target, args);
            } catch (InvocationTargetException e) {
                Throwable targetException = e.getTargetException();
                if (targetException instanceof RuntimeException) {
                    throw (RuntimeException) targetException;
                }
                if (targetException instanceof Error) {
                    throw (Error) targetException;
                }
                throw new RuntimeException(targetException);
            } catch (Exception e) {
                Log.e(LOG_TAG, String.format("Unable to invoke method %s.%s", method.getDeclaringClass().getSimpleName(), method.getName()), e);
            }
        }
        return null;
    }

    @Nullable
    public static Field tryFindField(@NonNull Class<?> target, @NonNull String name) {
        try {
            return target.getDeclaredField(name);
        } catch (Exception e) {
            Log.e(LOG_TAG, String.format("Unable to find field %s.%s", target.getSimpleName(), name), e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(@Nullable Object target, @Nullable Field field, T defaultValue) {
        if (field != null) {
            try {
                return (T) field.get(target);
            } catch (Exception e) {
                Log.e(LOG_TAG, String.format("Unable to get value for field %s.%s", field.getDeclaringClass().getSimpleName(), field.getName()), e);
            }
        }
        return defaultValue;
    }
}
