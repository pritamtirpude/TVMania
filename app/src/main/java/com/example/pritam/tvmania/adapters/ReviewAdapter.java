package com.example.pritam.tvmania.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pritam.tvmania.R;
import com.example.pritam.tvmania.model.TvReviewData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pritam on 3/23/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<TvReviewData> mReviewDataList;
    private Context mContext;

    public ReviewAdapter(List<TvReviewData> mReviewDataList, Context mContext) {
        this.mReviewDataList = mReviewDataList;
        this.mContext = mContext;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_review_items;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ReviewViewHolder reviewViewHolder = new ReviewViewHolder(view);
        return reviewViewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.mAuthorTextView.setText(mReviewDataList.get(position).getReviewAuthor());
        holder.mContentTextView.setText(mReviewDataList.get(position).getReviewContent());
    }

    @Override
    public int getItemCount() {
        return mReviewDataList.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.author_text_view)
        TextView mAuthorTextView;
        @BindView(R.id.content_text_view)
        TextView mContentTextView;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
