package com.example.a230618_recyclerview.data.network;

import com.example.a230618_recyclerview.data.entity.SearchResultModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/")
    Call<SearchResultModel> getListMovieInfo(@Query("apikey") String apikey,
                                             @Query("s") String title);

    @GET("/")
    Call<SearchResultModel> getListMovieInfoByPage(@Query("apikey") String apikey,
                                                   @Query("s") String title,
                                                   @Query("page") int page);
}
