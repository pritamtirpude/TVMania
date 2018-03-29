package com.example.pritam.tvmania.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pritam on 3/22/2018.
 */

public class TvReviewData implements Parcelable{

    @SerializedName("author")
    private String reviewAuthor;

    @SerializedName("content")
    private String reviewContent;

    @SerializedName("id")
    private String reviewId;

    @SerializedName("url")
    private String reviewUrl;

    public TvReviewData(String reviewAuthor, String reviewContent, String reviewId, String reviewUrl) {
        this.reviewAuthor = reviewAuthor;
        this.reviewContent = reviewContent;
        this.reviewId = reviewId;
        this.reviewUrl = reviewUrl;
    }

    protected TvReviewData(Parcel in) {
        reviewAuthor = in.readString();
        reviewContent = in.readString();
        reviewId = in.readString();
        reviewUrl = in.readString();
    }

    public static final Creator<TvReviewData> CREATOR = new Creator<TvReviewData>() {
        @Override
        public TvReviewData createFromParcel(Parcel in) {
            return new TvReviewData(in);
        }

        @Override
        public TvReviewData[] newArray(int size) {
            return new TvReviewData[size];
        }
    };

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(reviewAuthor);
        parcel.writeString(reviewContent);
        parcel.writeString(reviewId);
        parcel.writeString(reviewUrl);
    }
}
