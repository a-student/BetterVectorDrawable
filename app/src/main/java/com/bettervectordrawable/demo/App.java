package com.bettervectordrawable.demo;

import android.app.Application;

import com.bettervectordrawable.VectorDrawableCompat;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        VectorDrawableCompat.enableResourceInterceptionFor(getResources(),
                R.drawable.bike_vector,
                R.drawable.genius_vector,
                R.drawable.hourglass_vector,
                R.drawable.target_vector);
        // or
        //int[] ids = VectorDrawableCompat.findVectorResourceIdsByConvention(getResources(), R.drawable.class, Convention.ResourceNameHasVectorSuffix);
        //VectorDrawableCompat.enableResourceInterceptionFor(getResources(), ids);
        // or
        //int[] ids = VectorDrawableCompat.findAllVectorResourceIdsSlow(getResources(), R.drawable.class);
        //VectorDrawableCompat.enableResourceInterceptionFor(getResources(), ids);
    }
}
