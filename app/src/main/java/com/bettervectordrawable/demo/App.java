package com.bettervectordrawable.demo;

import android.app.Application;

import com.bettervectordrawable.Convention;
import com.bettervectordrawable.VectorDrawableCompat;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        VectorDrawableCompat.enableResourceInterceptionFor(getResources(),
                R.drawable.airplane_vector,
                R.drawable.bicycle_vector,
                R.drawable.lamp_vector,
                R.drawable.rocket_vector);
        // or
        //int[] ids = VectorDrawableCompat.findVectorResourceIdsByConvention(getResources(), R.drawable.class, Convention.RESOURCE_NAME_HAS_VECTOR_SUFFIX);
        //VectorDrawableCompat.enableResourceInterceptionFor(getResources(), ids);
        // or
        //int[] ids = VectorDrawableCompat.findAllVectorResourceIdsSlow(getResources(), R.drawable.class);
        //VectorDrawableCompat.enableResourceInterceptionFor(getResources(), ids);

        if (BuildConfig.DEBUG && !VectorDrawableCompat.isResourceInterceptionEnabled()) {
            throw new AssertionError();
        }
    }
}
