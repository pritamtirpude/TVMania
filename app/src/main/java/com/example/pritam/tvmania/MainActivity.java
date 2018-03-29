package com.example.pritam.tvmania;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pritam.tvmania.fragments.HomeFragment;
import com.example.pritam.tvmania.fragments.TopRatedFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.empty_text_view)
    TextView mEmptyTextView;
    @BindView(R.id.main_activity_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.no_connection_image_view)
    ImageView mNoConnectionImageView;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;

    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        isConnected = activeNetwork != null && activeNetwork.isConnected();

        if (isConnected) {
            FragmentManager fm = getSupportFragmentManager();

            HomeFragment homeFragment = new HomeFragment();
            if (savedInstanceState == null){
                fm.beginTransaction()
                        .add(R.id.home_fragment_container, homeFragment)
                        .commit();
            }
        } else {
            mNoConnectionImageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.no_connection));
            mEmptyTextView.setText(getResources().getString(R.string.no_connection));
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarTitle.setText(R.string.app_name);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragments = null;
                switch (item.getItemId()){
                    case R.id.action_home:
                        fragments = HomeFragment.newInstance();
                        break;
                    case R.id.action_top_Rated:
                        fragments = TopRatedFragment.newInstance();
                        break;
                }

                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.home_fragment_container, fragments);
                fm.commit();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
