package com.bettervectordrawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.util.TypedValue;

/**
 * @hide
 */
public class VectorDrawableFactory {
    private final LongSparseArray<Integer> _map = new LongSparseArray<>();
    private Resources _resources;

    public void enableFor(@NonNull Resources resources, int[] ids) {
        _resources = resources;
        if (ids != null) {
            for (int id : ids) {
                _map.put(getKey(resources, id), id);
            }
        }
    }

    private static long getKey(Resources resources, int id) {
        TypedValue value = new TypedValue();
        resources.getValue(id, value, true);
        // taken from source code of the Resources.loadDrawable method
        return (((long) value.assetCookie) << 32) | value.data;
    }

    @Nullable
    public Drawable get(long key) {
        Integer id = _map.get(key);
        return id == null ? null : VectorDrawableCompat.inflate(false, _resources, id);
    }
}
