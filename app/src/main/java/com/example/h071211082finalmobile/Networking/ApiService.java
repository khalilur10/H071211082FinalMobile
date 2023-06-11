package com.example.h071211082finalmobile.Networking;

import com.example.h071211082finalmobile.Model.MovieResponse;
import com.example.h071211082finalmobile.Model.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("movie/popular?api_key=b5e4f7d9454522523dc42f1521739e5b")
    Call<MovieResponse> getListMovie();

    @GET("tv/popular?api_key=b5e4f7d9454522523dc42f1521739e5b")
    Call<TvResponse> getListTv();

}
