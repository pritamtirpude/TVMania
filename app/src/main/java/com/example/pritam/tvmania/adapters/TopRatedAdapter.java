package com.example.pritam.tvmania.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pritam.tvmania.R;
import com.example.pritam.tvmania.model.TvTopRatedData;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pritam on 3/17/2018.
 */

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.TopRatedViewHolder>{
    private List<TvTopRatedData> mTvTopRatedDataList;
    private Context mContext;

    private TopRatedListItemClickListener mTopRatedClickedListener;

    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/";

    public interface TopRatedListItemClickListener{
        void onTopListItemClick(TvTopRatedData ratedDataIndex);
    }

    public TopRatedAdapter(List<TvTopRatedData> mTvTopRatedDataList, Context mContext, TopRatedListItemClickListener itemClickListener) {
        this.mTvTopRatedDataList = mTvTopRatedDataList;
        this.mContext = mContext;
        this.mTopRatedClickedListener = itemClickListener;
    }

    @Override
    public TopRatedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.top_rated_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        TopRatedViewHolder topRatedViewHolder = new TopRatedViewHolder(view);

        return topRatedViewHolder;
    }

    @Override
    public void onBindViewHolder(TopRatedViewHolder holder, int position) {
        String posterPath = IMAGE_BASE_URL + mTvTopRatedDataList.get(position).getTvTopRatedShowPosterPath();

        Picasso.with(mContext)
                .load(posterPath)
                .placeholder(R.drawable.tv_placeholder)
                .error(R.drawable.error_placeholder)
                .into(holder.mTopRatedShowImageView);
    }

    @Override
    public int getItemCount() {
        return mTvTopRatedDataList.size();
    }

    class TopRatedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.top_rated_image_view)
        ImageView mTopRatedShowImageView;

        public TopRatedViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mTopRatedClickedListener.onTopListItemClick(mTvTopRatedDataList.get(clickedPosition));
        }
    }
}

