package com.example.pritam.tvmania.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pritam.tvmania.HomeDetailActivity;
import com.example.pritam.tvmania.R;
import com.example.pritam.tvmania.fragments.HomeDetailFragment;
import com.example.pritam.tvmania.model.TvData;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pritam on 3/15/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/";

    private List<TvData> mTvDataList;
    private Context mContext;
    private ListItemClickListener mListItemClickListener;

    public interface ListItemClickListener {
        void onHomeListItemClick(TvData clickedItemIndex);
    }

    public HomeAdapter(Context mContext, List<TvData> mTvDataList, ListItemClickListener listItemClickListener) {
        this.mTvDataList = mTvDataList;
        this.mContext = mContext;
        this.mListItemClickListener = listItemClickListener;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.home_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        HomeViewHolder homeViewHolder = new HomeViewHolder(view);

        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeViewHolder holder, int position) {
        String posterPath = IMAGE_BASE_URL + mTvDataList.get(position).getTvPosterPath();

        Picasso.with(mContext)
                .load(posterPath)
                .placeholder(R.drawable.tv_placeholder)
                .error(R.drawable.error_placeholder)
                .into(holder.mTvShowImageView);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mTvDataList.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_show_image_View)
        ImageView mTvShowImageView;

        public HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mListItemClickListener.onHomeListItemClick(mTvDataList.get(clickedPosition));
        }
    }
}
