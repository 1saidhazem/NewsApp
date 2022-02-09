package com.example.retrofetnews;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Interface {

    @GET("top-headlines")
    Call<News> getNews(
            @Query("country")String country,
            @Query("category")String category,
            @Query("apiKey")String apiKey
    );


}
