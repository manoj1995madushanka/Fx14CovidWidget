package com.covidlk.service;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HitAPI {
    @GET("https://hpb.health.gov.lk/api/get-current-statistical/")
    Call<JsonObject> getLKData();

    @GET("https://hpb.health.gov.lk/api/get-current-statistical/")
    Call<JsonObject> getCountryData(@Path(value = "countryName") String countryName);
}
