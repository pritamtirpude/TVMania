package com.example.pritam.tvmania.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pritam.tvmania.R;
import com.example.pritam.tvmania.adapters.ReviewAdapter;
import com.example.pritam.tvmania.model.TvData;
import com.example.pritam.tvmania.model.TvReviewData;
import com.example.pritam.tvmania.model.TvReviewModel;
import com.example.pritam.tvmania.utils.TVReviewService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pritam on 3/19/2018.
 */

public class HomeDetailFragment extends Fragment {

    public static final String PARCELABLE_LIST = "parcelable_list";
    public static final String REVIEW_LIST = "review_list";
    public static final String REVIEW_RECYCLER_STATE = "review_recycler_state";

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "2122d87c84bf8a2e80168dbc32001aca";

    public static final String TAG = HomeDetailFragment.class.getSimpleName();

    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/";

    private List<TvData> mDataListItems;
    private List<TvReviewData> mReviewDataList;
    private ReviewAdapter mReviewAdapter;
    private String backdropImageUrl;
    private String tvTitle;
    private String miniPosterPath;
    private String firstAirDate;
    private String tvRating;
    private Integer tvId;
    private String tvOverview;

    /* Firebase Instance Variables */
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;
    private ChildEventListener mChildEventListener;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.backdrop_image_detail)
    ImageView mBackdropImageView;
    @BindView(R.id.tv_detail_poster_image_view)
    ImageView mTvPosterImageView;
    @BindView(R.id.tv_first_air_date_text_view)
    TextView mFirstAirDateTextView;
    @BindView(R.id.tv_rating_text_view)
    TextView mTvRatingTextView;
    @BindView(R.id.tv_overview_text_view)
    TextView mTvOverviewTextView;
    @BindView(R.id.empty_reviews_text_view)
    TextView mEmptyTextView;
    @BindView(R.id.tv_detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.review_recycler_view)
    RecyclerView mReviewRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.home_detail_fab)
    FloatingActionButton mHomeDetailFloatingActionButton;

    public static HomeDetailFragment newInstance() {
        return new HomeDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        savedInstanceState = getActivity().getIntent().getExtras();
        if (savedInstanceState != null) {
            mDataListItems = getActivity().getIntent().getParcelableArrayListExtra(PARCELABLE_LIST);
        } else {
            mDataListItems = getArguments().getParcelableArrayList(PARCELABLE_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_detail_fragment, container, false);
        ButterKnife.bind(this, view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference().child("tvdata");

        tvId = mDataListItems.get(0).getTvId();

        backdropImageUrl = IMAGE_BASE_URL + mDataListItems.get(0).getTvBackdropPath();
        Picasso.with(getContext())
                .load(backdropImageUrl)
                .placeholder(R.drawable.tv_placeholder)
                .error(R.drawable.error_placeholder)
                .into(mBackdropImageView);

        tvTitle = mDataListItems.get(0).getTvOrginalName();
        mCollapsingToolbarLayout.setTitle(tvTitle);

        miniPosterPath = IMAGE_BASE_URL + mDataListItems.get(0).getTvPosterPath();
        Picasso.with(getContext())
                .load(miniPosterPath)
                .placeholder(R.drawable.tv_placeholder)
                .error(R.drawable.error_placeholder)
                .into(mTvPosterImageView);

        firstAirDate = mDataListItems.get(0).getTvFirstAirDate();
        mFirstAirDateTextView.setText(firstAirDate);

        tvRating = mDataListItems.get(0).getTvVoteAverage();
        mTvRatingTextView.setText(tvRating);

        tvOverview = mDataListItems.get(0).getTvOverview();
        mTvOverviewTextView.setText(tvOverview);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        TVReviewService client = retrofit.create(TVReviewService.class);
        Call<TvReviewModel> call = client.getTvReviews(tvId, API_KEY);

        mProgressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<TvReviewModel>() {
            @Override
            public void onResponse(Call<TvReviewModel> call, Response<TvReviewModel> response) {

                mProgressBar.setVisibility(View.INVISIBLE);
                mReviewDataList = response.body().getTvReviewResults();
                Log.d(TAG, "List Size : " + mReviewDataList.size());

                mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mReviewRecyclerView.setHasFixedSize(true);

                mReviewAdapter = new ReviewAdapter(mReviewDataList, getContext());

                mReviewRecyclerView.setNestedScrollingEnabled(false);
                if (mReviewDataList.size() == 0) {
                    mReviewRecyclerView.setVisibility(View.INVISIBLE);
                    mEmptyTextView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyTextView.setVisibility(View.INVISIBLE);
                    mReviewRecyclerView.setVisibility(View.VISIBLE);
                    mReviewRecyclerView.setAdapter(mReviewAdapter);
                }
            }

            @Override
            public void onFailure(Call<TvReviewModel> call, Throwable t) {
                mProgressBar.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(getView(), getResources().getString(R.string.error_parsing_reviews),
                        Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        mHomeDetailFloatingActionButton.setRippleColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mHomeDetailFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TvData mTvData = new TvData(tvTitle, null, firstAirDate, backdropImageUrl,
                        tvId, tvRating, tvOverview, miniPosterPath);

                mFirebaseDatabaseReference.push().setValue(mTvData);

                Snackbar snackbarSave = Snackbar.make(getView(), getString(R.string.info_saved),
                        Snackbar.LENGTH_SHORT);

                snackbarSave.show();

            }
        });

        final AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(mToolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appCompatActivity.onBackPressed();
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PARCELABLE_LIST, (ArrayList<TvData>) mDataListItems);
        outState.putParcelableArrayList(REVIEW_LIST, (ArrayList<TvReviewData>) mReviewDataList);
        outState.putParcelable(REVIEW_RECYCLER_STATE, mReviewRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState.getParcelableArrayList(PARCELABLE_LIST);
            savedInstanceState.getParcelableArrayList(REVIEW_LIST);
            savedInstanceState.getParcelable(REVIEW_RECYCLER_STATE);
        }
    }


}
