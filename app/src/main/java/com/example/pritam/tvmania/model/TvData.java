package com.example.pritam.tvmania.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pritam on 3/15/2018.
 */

public class TvData implements Parcelable{
    @SerializedName("original_name")
    private String originalName;

    @SerializedName("name")
    private String tvName;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("id")
    private Integer tvId;

    @SerializedName("vote_average")
    private String voteAverage;

    @SerializedName("overview")
    private String tvOverview;

    @SerializedName("poster_path")
    private String posterPath;

    public TvData(){

    }

    public TvData(String tvOrginalName, String tvShowName, String tvFirstAirDate, String tvBackdropPath, Integer tvId, String tvVoteAverage, String tvOverview, String tvPosterPath) {
        this.originalName = tvOrginalName;
        this.tvName = tvShowName;
        this.firstAirDate = tvFirstAirDate;
        this.backdropPath = tvBackdropPath;
        this.tvId = tvId;
        this.voteAverage = tvVoteAverage;
        this.tvOverview = tvOverview;
        this.posterPath = tvPosterPath;
    }

    protected TvData(Parcel in) {
        originalName = in.readString();
        tvName = in.readString();
        firstAirDate = in.readString();
        backdropPath = in.readString();
        if (in.readByte() == 0) {
            tvId = null;
        } else {
            tvId = in.readInt();
        }
        voteAverage = in.readString();
        tvOverview = in.readString();
        posterPath = in.readString();
    }

    public static final Creator<TvData> CREATOR = new Creator<TvData>() {
        @Override
        public TvData createFromParcel(Parcel in) {
            return new TvData(in);
        }

        @Override
        public TvData[] newArray(int size) {
            return new TvData[size];
        }
    };

    public String getTvOrginalName() {
        return originalName;
    }

    public String getTvShowName() {
        return tvName;
    }

    public String getTvFirstAirDate() {
        return firstAirDate;
    }

    public String getTvBackdropPath() {
        return backdropPath;
    }

    public Integer getTvId() {
        return tvId;
    }

    public String getTvVoteAverage() {
        return voteAverage;
    }

    public String getTvOverview() {
        return tvOverview;
    }

    public String getTvPosterPath() {
        return posterPath;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setTvId(Integer tvId) {
        this.tvId = tvId;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setTvOverview(String tvOverview) {
        this.tvOverview = tvOverview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(originalName);
        parcel.writeString(tvName);
        parcel.writeString(firstAirDate);
        parcel.writeString(backdropPath);
        if (tvId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(tvId);
        }
        parcel.writeString(voteAverage);
        parcel.writeString(tvOverview);
        parcel.writeString(posterPath);
    }
}
