package com.example.potato_disease;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyApi {

    @GET("forecast.json")
    Call<JsonObject> getCurrentWeather(
            @Query("key") String apiKey,
            @Query("q") String latLong,
            @Query("days") int days,
            @Query("aqi") String aqi,
            @Query("alerts") String alerts,
            @Query("lang") String language
    );

}
