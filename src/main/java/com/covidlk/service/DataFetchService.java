package com.covidlk.service;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.CompletableFuture;

public class DataFetchService {

    public JsonObject getDataForCountry() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hpb.health.gov.lk/api/get-current-statistical/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HitAPI hitAPI = retrofit.create(HitAPI.class);

        // wait until response get
        CompletableFuture<JsonObject> collectData = new CompletableFuture<>();

        hitAPI.getLKData()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        //System.out.println(response.body());
                        collectData.complete(response.body());
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                       collectData.completeExceptionally(t);
                    }
                });

        // collect response data
        //Base out = collectData.join();

        return collectData.join();
    }
}
