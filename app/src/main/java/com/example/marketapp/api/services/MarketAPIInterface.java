package com.example.marketapp.api.services;

import com.example.marketapp.api.models.Location;
import com.example.marketapp.api.models.LocationPost;
import com.example.marketapp.api.models.Market;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MarketAPIInterface {
    @GET("/api/Market/Nearest")
    Call<List<Market>> GetNearestMarkets(@Query("AddressId") String AddressId, @Header("Authorization") String authHeader);

    @GET("/api/Market")
    Call<List<Market>> GetAll();
}
