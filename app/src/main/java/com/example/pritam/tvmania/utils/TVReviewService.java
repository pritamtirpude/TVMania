package com.example.pritam.tvmania.utils;

import com.example.pritam.tvmania.model.TvReviewModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Pritam on 3/22/2018.
 */

public interface TVReviewService {

    @GET("tv/{id}/reviews")
    Call<TvReviewModel> getTvReviews(@Path("id") Integer tvId, @Query("api_key") String apiKey);
}
