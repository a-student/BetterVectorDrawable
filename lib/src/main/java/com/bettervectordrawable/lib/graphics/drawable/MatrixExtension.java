package com.bettervectordrawable.lib.graphics.drawable;

import android.graphics.Matrix;
import android.support.annotation.NonNull;

import com.bettervectordrawable.utils.ReflectionHelper;

/**
 * @hide
 */
public class MatrixExtension {
    @NonNull
    public static final Matrix IDENTITY_MATRIX = ReflectionHelper.getFieldValue(null, ReflectionHelper.tryFindField(Matrix.class, "IDENTITY_MATRIX"), new Matrix());
}
