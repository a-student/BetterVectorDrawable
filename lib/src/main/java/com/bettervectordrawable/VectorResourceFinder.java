package com.bettervectordrawable;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.util.TypedValue;

import com.bettervectordrawable.utils.Ints;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @hide
 */
public class VectorResourceFinder {
    public static int[] findAllIdsSlow(@NonNull Resources resources, @NonNull Class<?> rDrawable) {
        ArrayList<Integer> result = new ArrayList<>();

        for (Field field : rDrawable.getDeclaredFields()) {
            try {
                int id = field.getInt(null);
                if (isVectorSlow(resources, id)) {
                    result.add(id);
                }
            } catch (Exception ignored) {
            }
        }

        return Ints.toArray(result);
    }

    private static boolean isVectorSlow(Resources resources, int id) {
        TypedValue value = new TypedValue();
        resources.getValue(id, value, false);
        if (value.string == null || !value.string.toString().endsWith(".xml")) {
            return false;
        }
        return "vector".equals(getXmlName(resources, id));
    }

    private static String getXmlName(Resources resources, int id) {
        XmlResourceParser rp = resources.getXml(id);
        try {
            int type;
            do {
                type = rp.next();
            }
            while (type != XmlPullParser.START_TAG && type != XmlPullParser.END_DOCUMENT);
            if (type != XmlPullParser.START_TAG) {
                throw new XmlPullParserException("No start tag found");
            }
            return rp.getName();
        } catch (Exception e) {
            return null;
        } finally {
            rp.close();
        }
    }

    public static int[] findIdsByConvention(@NonNull Resources resources, @NonNull Class<?> rDrawable, @NonNull Convention resourceNamingConvention) {
        findIdsByConvention(resources, rDrawable, resourceNamingConvention, "vector_", "_vector")
    }

    public static int[] findIdsByConvention(@NonNull Resources resources, @NonNull Class<?> rDrawable, @NonNull Convention resourceNamingConvention, String vectorPrefix, String vectorSuffix) {
        ArrayList<Integer> result = new ArrayList<>();

        for (Field field : rDrawable.getDeclaredFields()) {
            try {
                if (nameMatchesConvention(field.getName(), resourceNamingConvention, vectorPrefix, vectorSuffix)) {
                    result.add(field.getInt(null));
                }
            } catch (Exception ignored) {
            }
        }

        return Ints.toArray(result);
    }

    private static boolean nameMatchesConvention(String resourceName, Convention resourceNamingConvention, String vectorPrefix, String vectorSuffix) {
        resourceName = resourceName.toLowerCase();
        switch (resourceNamingConvention) {
            case RESOURCE_NAME_HAS_VECTOR_PREFIX:
                return resourceName.startsWith(vectorPrefix);
            case RESOURCE_NAME_HAS_VECTOR_SUFFIX:
                return resourceName.endsWith(vectorSuffix);
            case RESOURCE_NAME_HAS_VECTOR_PREFIX_OR_SUFFIX:
                return resourceName.startsWith(vectorPrefix) || resourceName.endsWith(vectorSuffix);
            default:
                throw new IllegalArgumentException();
        }
    }
}
