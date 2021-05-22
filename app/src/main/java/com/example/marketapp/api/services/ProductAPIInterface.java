package com.example.marketapp.api.services;

import com.example.marketapp.api.models.Market;
import com.example.marketapp.api.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ProductAPIInterface {
    @GET("/api/Product/Market")
    Call<List<Product>> GetMarketProducts(@Query("MarketId") int MarketId);

}
