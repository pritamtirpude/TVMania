package com.example.pritam.tvmania.utils;

import com.example.pritam.tvmania.model.TvTopRatedModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Pritam on 3/17/2018.
 */

public interface TopRatedService {
    @GET("tv/top_rated")
    Call<TvTopRatedModel> getTvTopRatedShows(@Query("api_key") String apiKey);
}
