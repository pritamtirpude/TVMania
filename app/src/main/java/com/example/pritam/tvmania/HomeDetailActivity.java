package com.example.pritam.tvmania;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.pritam.tvmania.fragments.HomeDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pritam on 3/19/2018.
 */

public class HomeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            HomeDetailFragment homeDetailFragment = new HomeDetailFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.home_detail_container, homeDetailFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }
}
