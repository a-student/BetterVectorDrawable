package com.bettervectordrawable;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.LongSparseArray;

/**
 * @hide
 */
@SuppressLint("NewApi")
public final class LongSparseArrayDrawableConstantStateWrapper extends LongSparseArray<Drawable.ConstantState> {
    private final LongSparseArray<Drawable.ConstantState> _base;
    private final VectorDrawableFactory _vectorDrawableFactory;

    public LongSparseArrayDrawableConstantStateWrapper(LongSparseArray<Drawable.ConstantState> base, VectorDrawableFactory vectorDrawableFactory) {
        _base = base;
        _vectorDrawableFactory = vectorDrawableFactory;
    }

    @Override
    public Drawable.ConstantState get(long key) {
        return get(key, null);
    }

    @Override
    public Drawable.ConstantState get(long key, Drawable.ConstantState valueIfKeyNotFound) {
        Drawable.ConstantState result = _base.get(key);
        if (result != null) {
            return result;
        }
        Drawable drawable = _vectorDrawableFactory.get(key);
        if (drawable != null) {
            return drawable.getConstantState();
        }
        return valueIfKeyNotFound;
    }

    @Override
    public void delete(long key) {
        _base.delete(key);
    }

    @Override
    public void remove(long key) {
        _base.remove(key);
    }

    @Override
    public void removeAt(int index) {
        _base.removeAt(index);
    }

    @Override
    public void put(long key, Drawable.ConstantState value) {
        _base.put(key, value);
    }

    @Override
    public int size() {
        return _base.size();
    }

    @Override
    public long keyAt(int index) {
        return _base.keyAt(index);
    }

    @Override
    public Drawable.ConstantState valueAt(int index) {
        return _base.valueAt(index);
    }

    @Override
    public void setValueAt(int index, Drawable.ConstantState value) {
        _base.setValueAt(index, value);
    }

    @Override
    public int indexOfKey(long key) {
        return _base.indexOfKey(key);
    }

    @Override
    public int indexOfValue(Drawable.ConstantState value) {
        return _base.indexOfValue(value);
    }

    @Override
    public void clear() {
        _base.clear();
    }

    @Override
    public void append(long key, Drawable.ConstantState value) {
        _base.append(key, value);
    }

    @Override
    public String toString() {
        return _base.toString();
    }
}
