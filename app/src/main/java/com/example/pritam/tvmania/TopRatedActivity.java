package com.example.pritam.tvmania;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pritam.tvmania.fragments.TopRatedFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pritam on 3/17/2018.
 */

public class TopRatedActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.empty_text_view)
    TextView mEmptyTextView;
    @BindView(R.id.no_connection_image_view)
    ImageView mNoConnectionImageView;

    private boolean isConnected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        isConnected = activeNetwork != null && activeNetwork.isConnected();

        if (isConnected){
            FragmentManager fragmentManager = getSupportFragmentManager();

            TopRatedFragment topRatedFragment = new TopRatedFragment();
            if (savedInstanceState == null){
                fragmentManager.beginTransaction()
                        .add(R.id.top_rated_container, topRatedFragment)
                        .commit();
            }
        }else {
            mNoConnectionImageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.no_connection));
            mEmptyTextView.setText(getResources().getString(R.string.no_connection));
        }
    }
}
