package com.bettervectordrawable.utils;

import java.util.Collection;
import java.util.Iterator;

/**
 * @hide
 */
public class Ints {
    public static int[] toArray(Collection<? extends Number> collection) {
        int[] result = new int[collection.size()];
        Iterator<? extends Number> iterator = collection.iterator();
        for (int i = 0; i < result.length; i++) {
            result[i] = iterator.next().intValue();
        }
        return result;
    }
}
