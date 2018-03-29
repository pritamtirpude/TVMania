package com.example.pritam.tvmania.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pritam.tvmania.HomeDetailActivity;
import com.example.pritam.tvmania.R;
import com.example.pritam.tvmania.adapters.HomeAdapter;
import com.example.pritam.tvmania.model.TvData;
import com.example.pritam.tvmania.model.TvModel;
import com.example.pritam.tvmania.utils.ItemOffsetDecoration;
import com.example.pritam.tvmania.utils.RetrofitService;

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
 * Created by Pritam on 3/15/2018.
 */

public class HomeFragment extends Fragment implements HomeAdapter.ListItemClickListener {

    public static final String TAG = HomeFragment.class.getSimpleName();
    public static final String API_KEY = /* YOUR API HERE */;
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String PARCELABLE_LIST = "parcelable_list";
    public static final String RECYCLER_STATE = "recycler_state";

    @BindView(R.id.home_recycler_view)
    RecyclerView mHomeRecyclerView;

    private HomeAdapter mHomeAdapter;
    private List<TvData> shows;
    private GridLayoutManager layoutManager;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new GridLayoutManager(getContext(), 3);
            mHomeRecyclerView.setLayoutManager(layoutManager);
        } else {
            layoutManager = new GridLayoutManager(getContext(), 2);
            mHomeRecyclerView.setLayoutManager(layoutManager);
        }
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        mHomeRecyclerView.addItemDecoration(itemOffsetDecoration);
        mHomeRecyclerView.setHasFixedSize(true);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        RetrofitService client = retrofit.create(RetrofitService.class);
        Call<TvModel> call = client.getTvShows(API_KEY);

        call.enqueue(new Callback<TvModel>() {
            @Override
            public void onResponse(Call<TvModel> call, Response<TvModel> response) {
                shows = response.body().getTvDataList();
                mHomeAdapter = new HomeAdapter(getContext(), shows, HomeFragment.this);
                mHomeRecyclerView.setAdapter(mHomeAdapter);
                Log.d(TAG, "List Size : " + shows.size());
            }

            @Override
            public void onFailure(Call<TvModel> call, Throwable t) {
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
        outState.putParcelableArrayList(PARCELABLE_LIST, (ArrayList<TvData>) shows);
        outState.putParcelable(RECYCLER_STATE, mHomeRecyclerView.getLayoutManager().onSaveInstanceState());
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
    public void onHomeListItemClick(TvData clickedItemIndex) {
        Bundle tvHomeDataList = new Bundle();
        List<TvData> mData = new ArrayList<>();
        mData.add(clickedItemIndex);
        tvHomeDataList.putParcelableArrayList(PARCELABLE_LIST, (ArrayList<TvData>) mData);

        Intent intent = new Intent(getContext(), HomeDetailActivity.class);
        intent.putExtras(tvHomeDataList);
        startActivity(intent);
    }
}
