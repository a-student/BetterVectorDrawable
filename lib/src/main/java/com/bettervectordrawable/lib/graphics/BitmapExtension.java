package com.bettervectordrawable.lib.graphics;

/**
 * @hide
 */
public class BitmapExtension {
    /**
     * Indicates that the bitmap was created for an unknown pixel density.
     */
    public static final int DENSITY_NONE = 0;

    public static int scaleFromDensity(int size, int sdensity, int tdensity) {
        if (sdensity == DENSITY_NONE || tdensity == DENSITY_NONE || sdensity == tdensity) {
            return size;
        }
        // Scale by tdensity / sdensity, rounding up.
        return ((size * tdensity) + (sdensity >> 1)) / sdensity;
    }
}
