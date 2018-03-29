package com.example.pritam.tvmania.utils;

import com.example.pritam.tvmania.model.TvModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Pritam on 3/15/2018.
 */

public interface RetrofitService {
    @GET("discover/tv")
    Call<TvModel> getTvShows(@Query("api_key") String apiKey);
}
