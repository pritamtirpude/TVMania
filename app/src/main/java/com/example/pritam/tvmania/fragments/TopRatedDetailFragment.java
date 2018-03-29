package com.example.pritam.tvmania.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pritam.tvmania.R;
import com.example.pritam.tvmania.adapters.ReviewAdapter;
import com.example.pritam.tvmania.model.TvReviewData;
import com.example.pritam.tvmania.model.TvReviewModel;
import com.example.pritam.tvmania.model.TvTopRatedData;
import com.example.pritam.tvmania.utils.TVReviewService;
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
 * Created by Pritam on 3/24/2018.
 */

public class TopRatedDetailFragment extends Fragment {

    public static final String PARCELABLE_LIST = "parcelable_list";
    public static final String REVIEW_LIST = "review_list";

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = /* YOUR API HERE */;

    public static final String TAG = TopRatedDetailFragment.class.getSimpleName();

    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/";

    private List<TvTopRatedData> mTopRatedDataList;
    private List<TvReviewData> mTVReviewDataList;
    private ReviewAdapter mReviewAdapter;
    private String topRatedBackdropImageUrl;
    private String topRatedTvTitle;
    private String topRatedPosterPath;
    private String topRatedFirstAirDate;
    private String topRatedRating;
    private Integer topRatedTvId;
    private String topRatedTvOverview;

    @BindView(R.id.top_rated_collapsing_toolbar_layout)
    CollapsingToolbarLayout mTopRatedCollapsingToolbarLayout;
    @BindView(R.id.top_rated_backdrop_image_detail)
    ImageView mTopRatedBackdropImageView;
    @BindView(R.id.top_rated_poster_image_view)
    ImageView mTopRatedPosterImageView;
    @BindView(R.id.top_rated_first_air_date_text_view)
    TextView mTopRatedFirsAirDateTextView;
    @BindView(R.id.top_rated_rating_text_view)
    TextView mTopRatedRatingTextView;
    @BindView(R.id.top_rated_overview_text_view)
    TextView mTopRatedOverviewTextView;
    @BindView(R.id.empty_reviews_text_view)
    TextView mEmptyReviewTextView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.top_rated_review_recycler_view)
    RecyclerView mTopRatedReviewRecyclerView;
    @BindView(R.id.top_rated_tv_detail_toolbar)
    Toolbar mTopRatedToolbar;

    public static TopRatedDetailFragment newInstance() {
        return new TopRatedDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        savedInstanceState = getActivity().getIntent().getExtras();
        if (savedInstanceState != null) {
            mTopRatedDataList = getActivity().getIntent().getParcelableArrayListExtra(PARCELABLE_LIST);
        } else {
            mTopRatedDataList = getArguments().getParcelableArrayList(PARCELABLE_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_rated_detail_fragment, container, false);
        ButterKnife.bind(this, view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        topRatedTvId = mTopRatedDataList.get(0).getTvTopRatedShowId();

        topRatedBackdropImageUrl = IMAGE_BASE_URL + mTopRatedDataList.get(0).getTvTopRatedShowBackdropPath();

        Picasso.with(getContext())
                .load(topRatedBackdropImageUrl)
                .placeholder(R.drawable.tv_placeholder)
                .error(R.drawable.error_placeholder)
                .into(mTopRatedBackdropImageView);

        topRatedTvTitle = mTopRatedDataList.get(0).getTvTopRatedShowOrginalName();
        mTopRatedCollapsingToolbarLayout.setTitle(topRatedTvTitle);

        topRatedPosterPath = IMAGE_BASE_URL + mTopRatedDataList.get(0).getTvTopRatedShowPosterPath();
        Picasso.with(getContext())
                .load(topRatedPosterPath)
                .placeholder(R.drawable.tv_placeholder)
                .error(R.drawable.error_placeholder)
                .into(mTopRatedPosterImageView);

        topRatedFirstAirDate = mTopRatedDataList.get(0).getTvTopRatedShowFirstAirDate();
        mTopRatedFirsAirDateTextView.setText(topRatedFirstAirDate);

        topRatedRating = mTopRatedDataList.get(0).getTvTopRatedShowVoteAverage();
        mTopRatedRatingTextView.setText(topRatedRating);

        topRatedTvOverview = mTopRatedDataList.get(0).getTvTopRatedShowOverview();
        mTopRatedOverviewTextView.setText(topRatedTvOverview);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        TVReviewService client = retrofit.create(TVReviewService.class);
        Call<TvReviewModel> call = client.getTvReviews(topRatedTvId, API_KEY);

        mProgressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<TvReviewModel>() {
            @Override
            public void onResponse(Call<TvReviewModel> call, Response<TvReviewModel> response) {

                mProgressBar.setVisibility(View.INVISIBLE);
                mTVReviewDataList = response.body().getTvReviewResults();
                Log.d(TAG, "List Size : " + mTVReviewDataList.size());

                mTopRatedReviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mTopRatedReviewRecyclerView.setHasFixedSize(true);

                mReviewAdapter = new ReviewAdapter(mTVReviewDataList, getContext());

                mTopRatedReviewRecyclerView.setNestedScrollingEnabled(false);
                if (mTVReviewDataList.size() == 0) {
                    mTopRatedReviewRecyclerView.setVisibility(View.INVISIBLE);
                    mEmptyReviewTextView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyReviewTextView.setVisibility(View.INVISIBLE);
                    mTopRatedReviewRecyclerView.setVisibility(View.VISIBLE);
                    mTopRatedReviewRecyclerView.setAdapter(mReviewAdapter);
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

        final AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(mTopRatedToolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTopRatedToolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
        outState.putParcelableArrayList(PARCELABLE_LIST, (ArrayList<TvTopRatedData>) mTopRatedDataList);
        outState.putParcelableArrayList(REVIEW_LIST, (ArrayList<TvReviewData>) mTVReviewDataList);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState.getParcelableArrayList(PARCELABLE_LIST);
            savedInstanceState.getParcelableArrayList(REVIEW_LIST);
        }
    }
}
