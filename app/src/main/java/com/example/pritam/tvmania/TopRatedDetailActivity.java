package com.example.pritam.tvmania;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.pritam.tvmania.fragments.TopRatedDetailFragment;

/**
 * Created by Pritam on 3/24/2018.
 */

public class TopRatedDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated_detail);

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();

            TopRatedDetailFragment detailFragment = new TopRatedDetailFragment();

            fm.beginTransaction()
                    .add(R.id.top_rated_detail_container, detailFragment)
                    .commit();
        }
    }
}
