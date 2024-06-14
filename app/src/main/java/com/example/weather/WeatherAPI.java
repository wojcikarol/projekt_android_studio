package com.example.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {
    @GET("weather")
    Call<WeatherResponse> getFullWeatherData(
            @Query("q") String cityName,
            @Query("units") String units,
            @Query("APPID") String apiKey
    );
}