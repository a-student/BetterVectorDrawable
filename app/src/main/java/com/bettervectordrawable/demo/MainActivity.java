package com.bettervectordrawable.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bettervectordrawable.VectorDrawableCompat;

import static android.view.View.inflate;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.all_button).setOnClickListener(allButtonClickListener);
    }

    private final View.OnClickListener allButtonClickListener = new View.OnClickListener() {
        @SuppressWarnings("deprecation")
        @Override
        public void onClick(View v) {
            ViewGroup allGroup = (ViewGroup) findViewById(R.id.all_group);
            allGroup.removeAllViews();
            int[] ids = VectorDrawableCompat.findAllVectorResourceIdsSlow(getResources(), R.drawable.class);
            for (int id : ids) {
                ImageView image = (ImageView) inflate(v.getContext(), R.layout.image, null);
                image.setImageDrawable(getResources().getDrawable(id));
                allGroup.addView(image);
            }
        }
    };
}
