package com.example.pritam.tvmania.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pritam.tvmania.HomeDetailActivity;
import com.example.pritam.tvmania.R;
import com.example.pritam.tvmania.TopRatedDetailActivity;
import com.example.pritam.tvmania.adapters.TopRatedAdapter;
import com.example.pritam.tvmania.model.TvTopRatedData;
import com.example.pritam.tvmania.model.TvTopRatedModel;
import com.example.pritam.tvmania.utils.ItemOffsetDecoration;
import com.example.pritam.tvmania.utils.TopRatedService;

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
 * Created by Pritam on 3/17/2018.
 */

public class TopRatedFragment extends Fragment implements TopRatedAdapter.TopRatedListItemClickListener {

    public static final String TAG = TopRatedFragment.class.getSimpleName();
    public static final String API_KEY = /* YOUR API HERE */;
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String PARCELABLE_LIST = "parcelable_list";
    public static final String RECYCLER_STATE = "recycler_state";

    @BindView(R.id.top_rated_recycler_view)
    RecyclerView mTopRatedShowRecyclerView;

    private TopRatedAdapter mTopRatedAdapter;
    private List<TvTopRatedData> mTvTopRatedData;
    private GridLayoutManager mGridLayoutManager;

    public static TopRatedFragment newInstance() {
        return new TopRatedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_rated_fragment, container, false);
        ButterKnife.bind(this, view);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mGridLayoutManager = new GridLayoutManager(getContext(), 3);
            mTopRatedShowRecyclerView.setLayoutManager(mGridLayoutManager);
        } else {
            mGridLayoutManager = new GridLayoutManager(getContext(), 2);
            mTopRatedShowRecyclerView.setLayoutManager(mGridLayoutManager);
        }
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        mTopRatedShowRecyclerView.addItemDecoration(itemOffsetDecoration);
        mTopRatedShowRecyclerView.setHasFixedSize(true);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        TopRatedService client = retrofit.create(TopRatedService.class);
        Call<TvTopRatedModel> call = client.getTvTopRatedShows(API_KEY);

        call.enqueue(new Callback<TvTopRatedModel>() {
            @Override
            public void onResponse(Call<TvTopRatedModel> call, Response<TvTopRatedModel> response) {
                mTvTopRatedData = response.body().getTvTopRatedDataList();
                mTopRatedAdapter = new TopRatedAdapter(mTvTopRatedData, getContext(), TopRatedFragment.this);
                mTopRatedShowRecyclerView.setAdapter(mTopRatedAdapter);
                Log.d(TAG, "List Size : " + mTvTopRatedData.size());

            }

            @Override
            public void onFailure(Call<TvTopRatedModel> call, Throwable t) {
                Snackbar snackbar = Snackbar.make(getView(), getResources().getString(R.string.error_loading_data),
                        Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PARCELABLE_LIST, (ArrayList<TvTopRatedData>) mTvTopRatedData);
        outState.putParcelable(RECYCLER_STATE, mTopRatedShowRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState.getParcelableArrayList(PARCELABLE_LIST);
            savedInstanceState.getParcelable(RECYCLER_STATE);
        }
    }

    @Override
    public void onTopListItemClick(TvTopRatedData ratedDataIndex) {
        Bundle tvHomeDataList = new Bundle();
        List<TvTopRatedData> mData = new ArrayList<>();
        mData.add(ratedDataIndex);
        tvHomeDataList.putParcelableArrayList(PARCELABLE_LIST, (ArrayList<TvTopRatedData>) mData);

        Intent intent = new Intent(getContext(), TopRatedDetailActivity.class);
        intent.putExtras(tvHomeDataList);
        startActivity(intent);
    }
}
