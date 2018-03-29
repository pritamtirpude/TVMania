package com.example.pritam.tvmania.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pritam on 3/17/2018.
 */

public class TvTopRatedData implements Parcelable{

    @SerializedName("original_name")
    private String tvTopRatedShowOrginalName;

    @SerializedName("name")
    private String tvTopRatedShowName;

    @SerializedName("first_air_date")
    private String tvTopRatedShowFirstAirDate;

    @SerializedName("backdrop_path")
    private String tvTopRatedShowBackdropPath;

    @SerializedName("id")
    private Integer tvTopRatedShowId;

    @SerializedName("vote_average")
    private String tvTopRatedShowVoteAverage;

    @SerializedName("overview")
    private String tvTopRatedShowOverview;

    @SerializedName("poster_path")
    private String tvTopRatedShowPosterPath;

    public TvTopRatedData(String tvTopRatedShowOrginalName, String tvTopRatedShowName, String tvTopRatedShowFirstAirDate, String tvTopRatedShowBackdropPath, Integer tvTopRatedShowId, String tvTopRatedShowVoteAverage, String tvTopRatedShowOverview, String tvTopRatedShowPosterPath) {
        this.tvTopRatedShowOrginalName = tvTopRatedShowOrginalName;
        this.tvTopRatedShowName = tvTopRatedShowName;
        this.tvTopRatedShowFirstAirDate = tvTopRatedShowFirstAirDate;
        this.tvTopRatedShowBackdropPath = tvTopRatedShowBackdropPath;
        this.tvTopRatedShowId = tvTopRatedShowId;
        this.tvTopRatedShowVoteAverage = tvTopRatedShowVoteAverage;
        this.tvTopRatedShowOverview = tvTopRatedShowOverview;
        this.tvTopRatedShowPosterPath = tvTopRatedShowPosterPath;
    }

    protected TvTopRatedData(Parcel in) {
        tvTopRatedShowOrginalName = in.readString();
        tvTopRatedShowName = in.readString();
        tvTopRatedShowFirstAirDate = in.readString();
        tvTopRatedShowBackdropPath = in.readString();
        if (in.readByte() == 0) {
            tvTopRatedShowId = null;
        } else {
            tvTopRatedShowId = in.readInt();
        }
        tvTopRatedShowVoteAverage = in.readString();
        tvTopRatedShowOverview = in.readString();
        tvTopRatedShowPosterPath = in.readString();
    }

    public static final Creator<TvTopRatedData> CREATOR = new Creator<TvTopRatedData>() {
        @Override
        public TvTopRatedData createFromParcel(Parcel in) {
            return new TvTopRatedData(in);
        }

        @Override
        public TvTopRatedData[] newArray(int size) {
            return new TvTopRatedData[size];
        }
    };

    public String getTvTopRatedShowOrginalName() {
        return tvTopRatedShowOrginalName;
    }

    public String getTvTopRatedShowName() {
        return tvTopRatedShowName;
    }

    public String getTvTopRatedShowFirstAirDate() {
        return tvTopRatedShowFirstAirDate;
    }

    public String getTvTopRatedShowBackdropPath() {
        return tvTopRatedShowBackdropPath;
    }

    public Integer getTvTopRatedShowId() {
        return tvTopRatedShowId;
    }

    public String getTvTopRatedShowVoteAverage() {
        return tvTopRatedShowVoteAverage;
    }

    public String getTvTopRatedShowOverview() {
        return tvTopRatedShowOverview;
    }

    public String getTvTopRatedShowPosterPath() {
        return tvTopRatedShowPosterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tvTopRatedShowOrginalName);
        parcel.writeString(tvTopRatedShowName);
        parcel.writeString(tvTopRatedShowFirstAirDate);
        parcel.writeString(tvTopRatedShowBackdropPath);
        if (tvTopRatedShowId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(tvTopRatedShowId);
        }
        parcel.writeString(tvTopRatedShowVoteAverage);
        parcel.writeString(tvTopRatedShowOverview);
        parcel.writeString(tvTopRatedShowPosterPath);
    }
}
